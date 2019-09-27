package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.views.LoginPage;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialTest2 implements SerialPortEventListener {

	final static Logger logger = Logger.getLogger(SerialTest2.class);

	static Enumeration portList;
	static CommPortIdentifier portId;
	static String messageString = "TestString";
	static SerialPort serialPort;
	static OutputStream outputStream;

	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "COM1", "COM2", "COM3", "COM4", "COM5", "COM6"};

	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;

	/** The output stream to the port */
	private static OutputStream output;

	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 15000;

	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {

		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			logger.info("Could not find COM port.");
			System.out.println("Could not find COM port.");
			return;
		}

		logger.info("portId: " + portId.getName());

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
			logger.error("portId: " + portId.getName());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				while (input.ready()) {
					String inputLine = input.readLine();
					logger.info("Receive message [" + inputLine + "]");
					System.out.println(inputLine);
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	public static void main(String[] args) throws InterruptedException {
		try {
			logger.info("========================================");
			SerialTest2 main = new SerialTest2();
			main.initialize();
			
			System.out.println("Started");
			
			logger.info("Start sending message");
			output.write("01".getBytes());
			logger.info("Send message [" + "01" + "]");
//			for (int i = 0; i < 10; i++) {
//				output.write(messageString.getBytes());
//				logger.info("Send message [" + messageString + "]");
//				Thread.sleep(4000);
//			}
			
			Thread.sleep(10000);
			output.close();
			serialPort.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}