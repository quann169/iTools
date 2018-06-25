/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gary Rowe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.controllers;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.lib.hid4java.HidDevice;
import com.lib.hid4java.HidException;
import com.lib.hid4java.HidManager;
import com.lib.hid4java.HidServices;
import com.lib.hid4java.HidServicesListener;
import com.lib.hid4java.HidServicesSpecification;
import com.lib.hid4java.ScanMode;
import com.lib.hid4java.event.HidServicesEvent;

/**
 *
 */
public class UsbHIDDeviceController implements HidServicesListener {

	// private Integer VENDOR_ID = 0x0461;
	// private Integer PRODUCT_ID = 0x0020;
	private Integer VENDOR_ID;
	private Integer PRODUCT_ID;

	public static final String SERIAL_NUMBER = null;
	private String messages;
	private int readWaitTime;

	/**
	 * @param vendorId
	 * @param productId
	 * @param messages
	 * @param readWaitTime
	 */
	public UsbHIDDeviceController(String vendorId, String productId, String messages, int readWaitTime) {
		super();
		VENDOR_ID = Integer.decode(vendorId);
		PRODUCT_ID = Integer.decode(productId);
		this.messages = messages;
		this.readWaitTime = readWaitTime;
	}

	public void executeAction() throws HidException {

		// Configure to use custom specification
		HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
		hidServicesSpecification.setAutoShutdown(true);
		hidServicesSpecification.setScanInterval(500);
		hidServicesSpecification.setPauseInterval(5000);
		hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

		// Get HID services using custom specification
		HidServices hidServices = HidManager.getHidServices(hidServicesSpecification);
		hidServices.addHidServicesListener(this);

		// Start the services
		System.out.println("\n\nStarting HID services.\n\n");
		hidServices.start();

		System.out.println("Calculating attached devices...\n\n");

		// Provide a list of attached devices
		for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
			System.out.println(hidDevice);
		}

		HidDevice hidDevice = hidServices.getHidDevice(VENDOR_ID, PRODUCT_ID, SERIAL_NUMBER);

		if (hidDevice != null) {
			sendMessage(hidDevice);
		} else {
			System.out.println("\nDo not find HID device - please help me to recheck config file\n\n");
			return;
		}

		// hidServices.shutdown();

	}

	@Override
	public void hidDeviceAttached(HidServicesEvent event) {

		System.out.println("\nAdd device: " + event.getHidDevice() + "\n");

	}

	@Override
	public void hidDeviceDetached(HidServicesEvent event) {

		System.out.println("\nRemove device: " + event.getHidDevice());

	}

	@Override
	public void hidFailure(HidServicesEvent event) {

		System.err.println("HID failure: " + event);

	}

	private void sendMessage(HidDevice hidDevice) {

		// Ensure device is open after an attach/detach event
		if (hidDevice != null && !hidDevice.isOpen()) {
			hidDevice.open();
		}

		byte value = (byte) Integer.parseInt(this.messages, 10);
		byte[] message = new byte[2];
		message[0] = 125;
		message[1] = value;

		System.out.println("\nData send to board: " + Arrays.toString(message));
		int val = -1;
		try {
			val = hidDevice.write(message, 2, (byte) 0x00);
		} catch (Exception e) {
			System.out.println("\n\n[ERR] Cannot send data.\n\n");
			return;
		}

		if (val >= 0) {
			System.out.println("> [" + val + "]");
			System.out.println(
					"> Send message " + this.messages + " successfully. Wait to read data in " + readWaitTime + "s...");
		} else {
			System.err.println(hidDevice.getLastErrorMessage());
		}

		System.out.println("\n\n-------------Begin wait to read data in " + readWaitTime + "s------------\n");

		for (int i = 0; i < 5; i++) {
			// Prepare to read a single data packet
			boolean moreData = true;

			while (moreData) {
				byte data[] = new byte[4];
				// This method will now block for 500ms or until data is
				// read

				val = hidDevice.read(data, readWaitTime * 1000);
				System.out.println("\nSwitch case --- " + val + " ----\n");
				// System.out.println("Read result: " + val);
				switch (val) {
				case -1:
					System.err.println(hidDevice.getLastErrorMessage());
					moreData = false;
					break;
				case 0:
					System.out.println("\n-------------No data receive, end read data------------\n");
					moreData = false;
					break;
				default:
					System.out.print("<Data received from board [");
					for (byte b : data) {
						System.out.printf(" " + b);
					}
					System.out.println("]");
					// System.out.print("<Convert [");
					// for (byte b1 : data) {
					// System.out.printf(" %02x", b1);
					// }
					// System.out.println("]");

					// System.out.println("\n\n-------------Try to read more
					// data------------\n");
					moreData = false;
					break;
				}
			}

		}

		System.out.println(
				"############################################\nComplete sending all messages!!!\n############################################\n");

	}

	/**
	 * Invokes {@code unit.}{@link java.util.concurrent.TimeUnit#sleep(long)
	 * sleep(sleepFor)} uninterruptibly.
	 */
	public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
		boolean interrupted = false;
		try {
			long remainingNanos = unit.toNanos(sleepFor);
			long end = System.nanoTime() + remainingNanos;
			while (true) {
				try {
					// TimeUnit.sleep() treats negative timeouts just like zero.
					NANOSECONDS.sleep(remainingNanos);
					return;
				} catch (InterruptedException e) {
					interrupted = true;
					remainingNanos = end - System.nanoTime();
				}
			}
		} finally {
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
