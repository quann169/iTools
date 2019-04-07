package com.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JFrame;

public class StringUtils {

	public StringUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String getCurrentClassAndMethodNames() {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		final String s = e.getClassName();
		return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
	}

	public static void titleAlign(JFrame frame) {

		Font font = frame.getFont();

		String currentTitle = frame.getTitle().trim();
		FontMetrics fm = frame.getFontMetrics(font);
		int frameWidth = frame.getWidth();
		int titleWidth = fm.stringWidth(currentTitle);
		int spaceWidth = fm.stringWidth(" ");
		int centerPos = (frameWidth / 2) - (titleWidth / 2);
		int spaceCount = centerPos / spaceWidth;
		String pad = "";
		pad = String.format("%" + (spaceCount - 14) + "s", pad);
		frame.setTitle(pad + currentTitle);

	}


	public static boolean converToBoolean(String data) {
		try {
			if (data != "" && "true".equals(data.toLowerCase())) {
				return true;
			}
			int result = Integer.valueOf(data);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	public static String writeFileTab(String outFilePath, List<String> header, List<List<String>> quantityTrayInfo) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath));
			String dataLog = "";
			for (String hd : header) {
				dataLog += "\t" + hd;
			}
			dataLog = dataLog + "\n";

			for (List<String> list : quantityTrayInfo) {
				String data1Line = "";
				for (String string : list) {
					data1Line += "\t" + string;
				}
				dataLog += data1Line + "\n";
			}

			writer.write(dataLog);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
