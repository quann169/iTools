package com.utils;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.views.ChangePasswordPage;
import com.views.EmployeePage;
import com.views.ForgotPasswordPage;
import com.views.LockUnlockAccountPage;
import com.views.LoginPage;
import com.views.PutInTakeOverPage;
import com.views.ResetPasswordPage;

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

	public static void frameInit(JFrame frame, ResourceBundle bundleMessage) {
		PopUpKeyboard typing = new PopUpKeyboard();
		if (frame instanceof LoginPage) {
			LoginPage obj = (LoginPage) frame;
			obj.keyboard = typing;
			obj.addVirtualKeyboardListener();
		}

		if (frame instanceof EmployeePage) {
			EmployeePage obj = (EmployeePage) frame;
			obj.keyboard = typing;
			obj.addVirtualKeyboardListener();
		}

		if (frame instanceof ForgotPasswordPage) {
			ForgotPasswordPage obj = (ForgotPasswordPage) frame;
			obj.keyboard = typing;
			obj.addVirtualKeyboardListener();
		}

		if (frame instanceof LockUnlockAccountPage) {
			LockUnlockAccountPage obj = (LockUnlockAccountPage) frame;
			obj.keyboard = typing;
			obj.addVirtualKeyboardListener();
		}

		if (frame instanceof PutInTakeOverPage) {
			PutInTakeOverPage obj = (PutInTakeOverPage) frame;
			obj.keyboard = typing;
			obj.addVirtualKeyboardListener();
		}

		if (frame instanceof ResetPasswordPage) {
			ResetPasswordPage obj = (ResetPasswordPage) frame;
			obj.keyboard = typing;
			obj.addVirtualKeyboardListener();
		}

		if (frame instanceof ChangePasswordPage) {
			ChangePasswordPage obj = (ChangePasswordPage) frame;
			obj.keyboard = typing;
			obj.addVirtualKeyboardListener();
		}

		frame.setLayout(new BorderLayout());

		// frame.setBounds(0, 0, 700, 460);
		frame.setBounds(0, 0, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				StringUtils.titleAlign(frame);
			}

		});
		frame.setResizable(false);

		// // create the status bar panel and shove it down the bottom of the
		// frame
		// JPanel statusPanel = new JPanel();
		// statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		// frame.add(statusPanel, BorderLayout.SOUTH);
		// statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 30));
		// statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		// JLabel statusLabel = new JLabel();
		// statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// statusPanel.add(statusLabel);
		//
		// String textFooter = StringUtils.calculateAlign(frame,
		// bundleMessage.getString("App_Footer"));
		// statusLabel.setFont(new Font(statusLabel.getFont().getName(),
		// Font.BOLD, 15));
		// statusLabel.setText(textFooter);
		// typing.setVisible(true);

		frame.add(typing, BorderLayout.SOUTH);
		// frame.add(typing);
		// frame.setAlwaysOnTop(true);

		frame.setVisible(true);
	}

	public static JMenuBar addMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu logOut = new JMenu("   Log Out  "); // Create File menu
		JMenu changePass = new JMenu("    Change Password    "); // Create
																	// Elements
																	// menu
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(logOut); // Add the file menu
		// menuBar.add(Box.createHorizontalGlue());
		// logOut.setSize(390, 30);

		menuBar.add(changePass);

		changePass.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub

			}
		});
		return menuBar;
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
