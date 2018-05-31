package com.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class StringUtils {

	public StringUtils() {
		// TODO Auto-generated constructor stub
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

	private static String calculateAlign(JPanel frame, String currentTitle) {

		Font font = frame.getFont();

		FontMetrics fm = frame.getFontMetrics(font);
		int frameWidth = frame.getWidth();
		int titleWidth = fm.stringWidth(currentTitle);
		int spaceWidth = fm.stringWidth(" ");
		int centerPos = (frameWidth / 2) - (titleWidth / 2);
		int spaceCount = centerPos / spaceWidth;
		String pad = "";
		pad = String.format("%" + (spaceCount - 1) + "s", pad);
		return pad + currentTitle;

	}

	public static void frameInit(JFrame frame, ResourceBundle bundleMessage) {
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setBounds(0, 0, 700, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				StringUtils.titleAlign(frame);
			}

		});
		frame.setResizable(false);
		
		// create the status bar panel and shove it down the bottom of the frame
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		frame.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 30));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel();
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);

		String textFooter = StringUtils.calculateAlign(statusPanel, bundleMessage.getString("App_Footer"));
		
		statusLabel.setText(textFooter);
	}

	public static JMenuBar addMenu() {
		JMenuBar menuBar = new JMenuBar(); 
		JMenu fileMenu = new JMenu("File"); // Create File menu
		JMenu elementMenu = new JMenu("Elements"); // Create Elements menu
		menuBar.add(fileMenu); // Add the file menu
		menuBar.add(elementMenu);
		return menuBar;
	}

}
