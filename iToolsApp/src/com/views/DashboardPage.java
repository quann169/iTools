package com.views;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.controllers.LoginController;
import com.controllers.SyncController;
import com.controllers.TransactionController;
import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.EmailUtils;
import com.utils.StringUtils;

public class DashboardPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	List<Role> listRoles;
	Assessor user;

	JLabel logOutLabel = new JLabel(bundleMessage.getString("App_Logout"));
	JLabel changePassLabel = new JLabel(bundleMessage.getString("App_ChangePassword"));
	JLabel splitLabel = new JLabel(" | ");

	JButton getToolButton = new JButton(bundleMessage.getString("Dashboard_Page_Get_Tool"));
	JButton resetPasswordButton = new JButton(bundleMessage.getString("Dashboard_Page_Reset_Password"));
	JButton lockAccountButton = new JButton(bundleMessage.getString("Dashboard_Page_Lock_Account"));
	JButton manualSyncButton = new JButton(bundleMessage.getString("Dashboard_Page_Manual_Sync"));

	JButton unlockMachineButton = new JButton(bundleMessage.getString("Dashboard_Page_Unlock_Machine"));
	JButton putInsButton = new JButton(bundleMessage.getString("Dashboard_Page_Putins"));
	JButton takeOverButton = new JButton(bundleMessage.getString("Dashboard_Page_Take_Over"));

	final static Logger logger = Logger.getLogger(DashboardPage.class);

	private static final Config cfg = new Config();
	private static final String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE"));
	private static final String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("MACHINE_CODE"));
	static String productMode = cfg.getProperty("PRODUCT_MODE");
	LoginController ctlObj = new LoginController();
	String userName = "";

	EmailUtils emailUtils = new EmailUtils(Enum.DASHBOARD_PAGE, userName, companyCode, machineCode);

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int windowWidth = (int) screenSize.getWidth();
	static int windowHeight = (int) screenSize.getHeight();
	static int extWidth = (windowWidth > 900) ? 0 : 0;
	static int extHeight = (windowHeight > 700) ? 0 : 0;

	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;

	List<String> listRoleName = new ArrayList<>();

	DashboardPage(List<Role> listRoles, Assessor user) {
		this.listRoles = listRoles;
		userName = user.getUsername();
		for (Role role : this.listRoles) {
			listRoleName.add(role.getRoleName().toLowerCase());
		}

		this.user = user;
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		logger.info("Init " + Enum.DASHBOARD_PAGE + " with list roles: " + listRoleName);

	}

	public void setLayoutManager() {
		container.setLayout(null);

	}

	public void setLocationAndSize() {

		Font labelFont = container.getFont();

		// Map<TextAttribute, Integer> fontAttributes = new
		// HashMap<TextAttribute, Integer>();
		// fontAttributes.put(TextAttribute.UNDERLINE,
		// TextAttribute.UNDERLINE_ON);

		changePassLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_ChangePassword") + "</u></i></b></font></html></html>");
		changePassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePassLabel.setBounds(530 + extWidth, 5 + extHeight, 170, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();

				logger.info(userName + " click change password from " + Enum.DASHBOARD_PAGE);
				JFrame old = root;
				root = new ChangePasswordPage(user);
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Change_Password_Page_Title"));
				root.getRootPane().setDefaultButton(((ChangePasswordPage) root).changePassButton);
				old.dispose();
			}
		});

		splitLabel.setBounds(693 + extWidth, 5 + extHeight, 25, 60);
		splitLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		logOutLabel.setText("<html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_Logout") + "</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(715 + extWidth, 5 + extHeight, 150, 60);
		logOutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();
				logger.info(userName + " logout.");
				JFrame old = root;
				root = new LoginPage();
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Login_Page_Title"));
				root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
				old.dispose();
			}
		});

		// accounting role
		unlockMachineButton.setBounds(80 + extWidth, 100 + extHeight, 305, 60);
		unlockMachineButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		takeOverButton.setBounds(420 + extWidth, 100 + extHeight, 305, 60);
		takeOverButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		putInsButton.setBounds(80 + extWidth, 240 + extHeight, 305, 60);
		putInsButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		// sub admin role
		resetPasswordButton.setBounds(80 + extWidth, 100 + extHeight, 305, 60);
		resetPasswordButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		lockAccountButton.setBounds(420 + extWidth, 100 + extHeight, 305, 60);
		lockAccountButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		manualSyncButton.setBounds(80 + extWidth, 240 + extHeight, 305, 60);
		manualSyncButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		getToolButton.setBounds(420 + extWidth, 240 + extHeight, 305, 60);
		getToolButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container,
						"<html><font size=\"5\" face=\"arial\">" + timeoutMess + "</font></html>", "Time Out Dashboard",
						JOptionPane.WARNING_MESSAGE);

				logger.info(userName + ": " + Enum.DASHBOARD_PAGE + " time out.");
				JFrame old = root;
				root = new LoginPage();
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Login_Page_Title"));
				root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
				old.dispose();
			}
		});
		updateTimer.setRepeats(false);
		updateTimer.restart();
	}

	public void addComponentsToContainer() {
		container.add(splitLabel);
		container.add(changePassLabel);
		container.add(logOutLabel);

		if (listRoleName.contains(Enum.ACCT.text().toLowerCase())
				|| listRoleName.contains(Enum.TKOVER.text().toLowerCase())
				|| listRoleName.contains(Enum.PUTIN.text().toLowerCase())) {
			container.add(unlockMachineButton);
			container.add(takeOverButton);
			container.add(putInsButton);
			takeOverButton.setEnabled(false);
			putInsButton.setEnabled(false);
		} else {
			container.add(getToolButton);
			container.add(resetPasswordButton);
			container.add(lockAccountButton);
			container.add(manualSyncButton);
		}

	}

	public void addActionEvent() {
		unlockMachineButton.addActionListener(this);
		takeOverButton.addActionListener(this);
		putInsButton.addActionListener(this);
		getToolButton.addActionListener(this);
		resetPasswordButton.addActionListener(this);
		lockAccountButton.addActionListener(this);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(root,
						"<html><font size=\"5\" face=\"arial\">Are you sure you want to exit?</font></html>",
						"Confirm Close", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
						ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		if (e.getSource() == unlockMachineButton) {
			updateTimer.stop();
			logger.info(userName + " - " + Enum.UNLOCK_MACHINE);
			final JDialog d = new JDialog();
			JPanel p1 = new JPanel(new GridBagLayout());
			JLabel progress = new JLabel("");
			p1.add(progress, new GridBagConstraints());
			d.getContentPane().add(p1);
			d.setBounds(150 + extWidth, 200 + extHeight, 500, 200);
			// d.setLocationRelativeTo(f);
			d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			d.setModal(true);

			SwingWorker<?, ?> worker = new SwingWorker<Void, String>() {
				protected Void doInBackground() throws InterruptedException {
					try {
						int x = 0;
						for (; x <= 100; x += 10) {
							// publish("" + x);
							// Thread.sleep(200);
						}
						publish("Start getting report...");
						TransactionController controller = new TransactionController();

						List<List<String>> quantityTrayInfo = controller.getQuantityTrayInfo(machineCode);
						List<String> header = new ArrayList<String>() {
							/**
							* 
							*/
							private static final long serialVersionUID = 1L;

							{
								add("MachineCode");
								add("ToolCode");
								add("TrayIndex");
								add("Quantity");
								add("UpdatedDate");
							}
						};
						publish("Completed getting report...");

						DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
						Date date = new Date();
						String dateTime = dateFormat.format(date);
						String outFilePath = "./log/report_" + dateTime + ".csv";

						StringUtils.writeFileTab(outFilePath, header, quantityTrayInfo);

						publish("Completed writing report to file...");

						List<String> ccEmail = new ArrayList<>();
						ccEmail.add("quann169@gmail.com");

						String email = ctlObj.getEmailUser(userName);

						emailUtils.sendEmailCCWithAttachedFile(email, ccEmail,
								companyCode + " - " + machineCode + ": Report before takeover or putin", outFilePath);

						try {
							Files.deleteIfExists(Paths.get(outFilePath));
							logger.info("Delete report file successfully.");
						} catch (Exception e) {
							logger.error("Cannot delete report file");
						}

						publish("Sent email...");
						Thread.sleep(2000);
					} catch (Exception e2) {
						logger.error(e2.getMessage());
					}

					return null;
				}

				protected void process(List<String> chunks) {
					String selection = chunks.get(chunks.size() - 1);
					progress.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b>" + selection
							+ "</b></font></html></html>");

				}

				protected void done() {
					logger.info("Complete");
					d.dispose();
					putInsButton.setEnabled(true);
					takeOverButton.setEnabled(true);
				}
			};
			worker.execute();
			d.setVisible(true);
		}
		if (e.getSource() == takeOverButton) {
			updateTimer.stop();
			logger.info(userName + " - " + Enum.TKOVER);
			JFrame old = root;
			root = new PutInTakeOverPage(user, Enum.TKOVER.text());
			StringUtils.frameInit(root, bundleMessage);
			root.setTitle(user.getFirstName() + " " + user.getLastName() + " - " + Enum.TKOVER.text() + " Page");
			root.show();
			old.dispose();
		}
		if (e.getSource() == putInsButton) {
			updateTimer.stop();
			logger.info(userName + " - " + Enum.PUTIN);
			JFrame old = root;
			root = new PutInTakeOverPage(user, Enum.PUTIN.text());
			StringUtils.frameInit(root, bundleMessage);
			root.setTitle(user.getFirstName() + " " + user.getLastName() + " - " + Enum.PUTIN.text() + " Page");
			root.show();
			old.dispose();
		}

		if (e.getSource() == resetPasswordButton) {
			updateTimer.stop();
			logger.info(resetPasswordButton.getText() + " clicked by " + userName);
			JFrame old = root;
			root = new ResetPasswordPage(user, true, false);
			StringUtils.frameInit(root, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			root.setTitle(user.getFirstName() + " " + user.getLastName());
			root.show();
			old.dispose();
		}
		if (e.getSource() == lockAccountButton) {
			updateTimer.stop();
			logger.info(lockAccountButton.getText() + " clicked by " + userName);
			JFrame old = root;
			root = new LockUnlockAccountPage(user, true);
			StringUtils.frameInit(root, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			root.setTitle(user.getFirstName() + " " + user.getLastName());
			root.show();
			old.dispose();
		}

		if (e.getSource() == getToolButton) {
			updateTimer.stop();
			logger.info(getToolButton.getText() + " clicked by " + userName);
			JFrame old = root;
			root = new EmployeePage(user, true);
			StringUtils.frameInit(root, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			root.setTitle(user.getFirstName() + " " + user.getLastName());
			root.show();
			old.dispose();
		}

		if (e.getSource() == manualSyncButton) {
			SyncController syncCtl = new SyncController();
			syncCtl.syncDataManually(companyCode, machineCode, productMode);
			logger.info(manualSyncButton.getText() + " clicked by " + userName);

		}
	}

}
