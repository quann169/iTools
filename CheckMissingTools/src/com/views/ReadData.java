package com.views;

import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import com.lib.hid4java.HidDevice;
import com.lib.hid4java.HidException;
import com.lib.hid4java.HidManager;
import com.lib.hid4java.HidServices;
import com.lib.hid4java.HidServicesListener;
import com.lib.hid4java.HidServicesSpecification;
import com.lib.hid4java.ScanMode;
import com.lib.hid4java.event.HidServicesEvent;

public class ReadData implements HidServicesListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String vendorId = "0x0461";
	String productId = "0x0020";
	boolean isContinue = true;
	
	int VENDOR_ID = Integer.decode(vendorId);
	int PRODUCT_ID = Integer.decode(productId);

	final static Logger logger = Logger.getLogger("file1");

	ReadData() {
	}

	public int executeAction() throws HidException {
		isContinue = true;
		logger.info("Starting HID services.");
		// Configure to use custom specification
		HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
		hidServicesSpecification.setAutoShutdown(true);
		hidServicesSpecification.setScanInterval(500);
		hidServicesSpecification.setPauseInterval(1000);
		hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

		// Get HID services using custom specification
		HidServices hidServices = HidManager.getHidServices(hidServicesSpecification);
		hidServices.addHidServicesListener(this);

		// Start the services

		hidServices.start();
//		logger.info("Calculating attached devices...");

//		// Provide a list of attached devices
//		for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
//			logger.info(hidDevice);
//		}

		

//		logger.info("vendorId: " + vendorId + " - " + VENDOR_ID);
//		logger.info("productId: " + productId + " - " + PRODUCT_ID);

		HidDevice hidDevice = hidServices.getHidDevice(VENDOR_ID, PRODUCT_ID, null);
		logger.info("hidDevice: " + hidDevice);

		System.out.println("" + new Date() + ": " + "hidDevice - " + hidDevice);

		int result = -1;
		if (hidDevice != null) {
			System.out.println("" + new Date() + ": Start reading message");
			readMessage(hidDevice);
		} else {
			logger.error("Do not find HID device");
			System.out.println("" + new Date() + ": " + "Do not find HID device");
		}

		hidServices.shutdown();
		return result;
	}

	@Override
	public void hidDeviceAttached(HidServicesEvent event) {
		logger.info("Add device: " + event.getHidDevice());
		
		if (!isContinue) {
			isContinue = true;
//			executeAction();
		}
		
	}

	@Override
	public void hidDeviceDetached(HidServicesEvent event) {
		logger.info("Remove device: " + event.getHidDevice());
		isContinue = false;
	}

	@Override
	public void hidFailure(HidServicesEvent event) {
		logger.info("HID failure: " + event);
		isContinue = false;
	}

	private void readMessage(HidDevice hidDevice) {

		// Ensure device is open after an attach/detach event
		if (hidDevice != null && !hidDevice.isOpen()) {

			boolean openDevice = hidDevice.open();
			if (openDevice) {
//				logger.info("Open device OK");
			} else {
//				logger.error("Open device ERR: " + hidDevice.getLastErrorMessage());
				System.out.println("" + new Date() + ": Open device ERR: " + hidDevice.getLastErrorMessage());
			}
		}
//		logger.info("hidDevice.isOpen(): " + hidDevice.isOpen());

		String messageBegin = "[127, 123]";

		byte data[] = new byte[2];

		while (isContinue) {
			int val = hidDevice.read(data);
			String readData = Arrays.toString(data);
			if (messageBegin.equals(readData)) {
				logger.info("Board return with meassage: " + Arrays.toString(data));
				System.out.println("" + new Date() + ": Board return with meassage: " + Arrays.toString(data));
				data = new byte[2];
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}

		}
	}

}
