package com.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
//import org.eclipse.wb.swt.SWTResourceManager;

public class LoginPage2 {

	protected Shell shlLogin;
	private Text userNameTxt;
	private Text passwordTxt;

	private String userName = null;
	private String password = null;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginPage2 window = new LoginPage2();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlLogin.open();
		shlLogin.layout();
		while (!shlLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlLogin = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shlLogin.setSize(450, 300);
		shlLogin.setText("Login");

		CLabel label = new CLabel(shlLogin, SWT.NONE);
//		label.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/jcg/rca/main/eclipse_logo.png"));
		label.setBounds(176, 10, 106, 70);
		label.setText("Logo Here");

		Label lblUsername = new Label(shlLogin, SWT.NONE);
		lblUsername.setBounds(125, 115, 55, 15);
		lblUsername.setText("Username");

		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setBounds(125, 144, 55, 15);
		lblPassword.setText("Password");

		userNameTxt = new Text(shlLogin, SWT.BORDER);
		userNameTxt.setBounds(206, 109, 173, 21);

		passwordTxt = new Text(shlLogin, SWT.BORDER | SWT.PASSWORD);
		passwordTxt.setBounds(206, 144, 173, 21);

		Button btnLogin = new Button(shlLogin, SWT.NONE);
		btnLogin.setBounds(206, 185, 75, 25);
		btnLogin.setText("Login");

		btnLogin.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				userName = userNameTxt.getText();
				password = passwordTxt.getText();

				if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
					String errorMsg = null;
					MessageBox messageBox = new MessageBox(shlLogin, SWT.OK | SWT.ICON_ERROR);

					messageBox.setText("Alert");
					if (userName == null || userName.isEmpty()) {
						errorMsg = "Please enter username";
					} else if (password == null || password.isEmpty()) {
						errorMsg = "Please enter password";
					}
					if (errorMsg != null) {
						messageBox.setMessage(errorMsg);
						messageBox.open();
					}
				} else {
					MessageBox messageBox = new MessageBox(shlLogin, SWT.OK | SWT.ICON_WORKING);
					messageBox.setText("Info");
					messageBox.setMessage("Valid");
					messageBox.open();
				}
			}
		});

	}

}
