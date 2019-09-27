package com.views;

import java.awt.BorderLayout;
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
import java.awt.font.TextAttribute;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.EmployeeController;
import com.controllers.LogController;
import com.controllers.LoginController;
import com.controllers.TransactionController;
import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.models.Tool;
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.EmailUtils;
import com.utils.FilterComboBox;
import com.utils.MyFocusListener;
import com.utils.PopUpKeyboard;
import com.utils.StringUtils;

public class PutInTakeOverPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	JLabel logOutLabel = new JLabel(bundleMessage.getString("App_Logout"));
	JLabel changePassLabel = new JLabel(bundleMessage.getString("App_ChangePassword"));
	JLabel splitLabel = new JLabel(" | ");

	JLabel backToDashboardLabel = new JLabel(bundleMessage.getString("Employee_Back_To_Dashboard"));

	JLabel toolLabel = new JLabel(bundleMessage.getString("Putin_TakeOver_Page_Tool"));
	JLabel trayLabel = new JLabel(bundleMessage.getString("Putin_TakeOver_Page_Tray"));
	JLabel quantityLabel = new JLabel(bundleMessage.getString("Putin_TakeOver_Page_Quantity"));

	JLabel quantityMessage = new JLabel("");

	JComboBox<String> trayCombobox;
	JComboBox<String> toolComboBox;
	JTextField quantityTextField = new JTextField();
	// JTextField toolTextField = new JTextField();
	// JTextField trayTextField = new JTextField();

	// public AutoCompletion comboBox1, comboBox2;

	boolean isReceiveResult = false;

	Map<String, List<List<Object>>> toolVstrayAndQuantityMap = new HashMap<>();

	JButton sendRequestButton = new JButton(bundleMessage.getString("Putin_TakeOver_Page_Send_Request"));
	JButton cancelButton = new JButton(bundleMessage.getString("Putin_TakeOver_Page_Cancel"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";

	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));
	private static final String companyCodeUH = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE_UH"));

	int maxItemsPerTray = Integer.valueOf(cfg.getProperty("MAX_ITEM_PER_TRAY"));

	final static Logger logger = Logger.getLogger(PutInTakeOverPage.class);
	EmployeeController empCtlObj = new EmployeeController();
	Map<String, Integer> mapTrayQuantity = new HashMap<>();
	boolean resultValue;
	String pageType;
	int defaultQuantity = -1;
	int newQuantity = -1;
	Assessor user;
	String userName;
	public PopUpKeyboard keyboard;
	// AutoCompletion combox1;

	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;
	String previousCombo = "";
	static LoginController ctlObj = new LoginController();

	ArrayList<String> listAllTrays = new ArrayList<String>();
	

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int windowWidth = (int) screenSize.getWidth();
	static int windowHeight = (int) screenSize.getHeight();
	static int extWidth = (windowWidth > 900) ? 0 : 0;
	static int extHeight = (windowHeight > 700) ? 0 : 0;

	// AutoCompletion comboBoxComplete;

	PutInTakeOverPage(Assessor user, String pageType) {
		toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode, -1);
		this.pageType = pageType;
		this.user = user;
		this.userName = user.getUsername();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		for (int i = 1; i < 61; i++) {
			if (i < 10) {
				listAllTrays.add("TRAY_0" + i);
			} else {
				listAllTrays.add("TRAY_" + i);
			}

		}
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = container.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		backToDashboardLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("Employee_Back_To_Dashboard") + "</u></i></b></font></html></html>");
		backToDashboardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToDashboardLabel.setBounds(15 + extWidth, 5 + extHeight, 300, 70);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();
				logger.info(userName + " back to dashboard from " + Enum.PUTIN_TAKEOVER_PAGE);
				JFrame old = root;

				List<Role> listRoles = ctlObj.getUserRoles(userName, companyCode, companyCodeUH);
				root = new DashboardPage(listRoles, user);
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(userName);
				old.dispose();
			}
		});

		backToDashboardLabel.setEnabled(true);

		changePassLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_ChangePassword") + "</u></i></b></font></html></html>");
		changePassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePassLabel.setBounds(530 + extWidth, 5 + extHeight, 170, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();

				logger.info(userName + " click change password from " + Enum.PUTIN_TAKEOVER_PAGE);
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

		////////////////////////////////////////
		toolLabel.setBounds(100 + extWidth, 85 + extHeight, 250, 40);
		toolLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		// List<Tool> listTools = empCtlObj.getToolsOfMachine(machineCode);
		List<Tool> listTools = new ArrayList<>();
		if (pageType.equals(Enum.TKOVER.text())) {
			listTools = empCtlObj.getToolsOfMachine(machineCode, companyCode);
		} else {
			listTools = empCtlObj.getAllTools(companyCode);
		}
		Collections.sort(listTools, new Comparator<Tool>() {
			public int compare(Tool o1, Tool o2) {
				if (o1.getToolName() == o2.getToolName())
					return 0;
				return o1.getToolName().compareToIgnoreCase(o2.getToolName());
			}
		});

		List<String> listToolNames = new ArrayList<>();
		listToolNames.add("");
		Set<String> setExtTmp = new HashSet<>();
		for (Tool tool : listTools) {
			if (!setExtTmp.contains(tool.getToolName())) {
				listToolNames.add(tool.getToolName());
				setExtTmp.add(tool.getToolName());
			}

		}
		toolComboBox = new FilterComboBox(listToolNames, keyboard);

		toolComboBox.setBounds(250 + extWidth, 90 + extHeight, 400, 35);
		toolComboBox.setFont(new Font(labelFont.getName(), Font.BOLD, 22));

		toolComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (trayCombobox.getItemCount() > 0) {
					trayCombobox.removeAllItems();
				}

				quantityTextField.setText("");
				String selectValue = toolComboBox.getSelectedItem().toString();
				// System.out.println(toolVstrayAndQuantityMap);
				// System.out.println("selectValue: " + selectValue);

				if (!"".equals(selectValue)) {
					if (toolVstrayAndQuantityMap.containsKey(selectValue)) {
						mapTrayQuantity = new HashMap<>();
						List<List<Object>> existedValue = toolVstrayAndQuantityMap.get(selectValue);
						if (existedValue.size() > 0) {
							for (List<Object> trayQuantity : existedValue) {
								String tray = (String) trayQuantity.get(0);
								int quantity = (int) trayQuantity.get(1);
								mapTrayQuantity.put(tray, quantity);
								// trayCombobox.addItem(tray);

							}
							// trayCombobox.setSelectedIndex(0);
						} else {
							logger.info("AAAAAAAAAAAAAAa");
						}

					}

					trayCombobox.removeAllItems();

					if (pageType.equals(Enum.TKOVER.text())) {
						Set<String> setExistedTmp = new HashSet<>();
						if (toolVstrayAndQuantityMap.containsKey(selectValue)) {
							List<List<Object>> toolTrays = toolVstrayAndQuantityMap.get(selectValue);
							trayCombobox.addItem("");
							for (List<Object> toolTray : toolTrays) {
								System.out.println("toolTray: " + toolTray);
								String trayTmp = (String) toolTray.get(0);
								int valueTmp = (int) toolTray.get(1);
								if (valueTmp > 0 && !setExistedTmp.contains(trayTmp)) {
									trayCombobox.addItem(trayTmp);
									setExistedTmp.add(trayTmp);
								}
							}
						}

					} else {
						Set<String> listSubtractTrays = new HashSet<>();
						for (String toolCode : toolVstrayAndQuantityMap.keySet()) {
							if (toolCode.equals(selectValue)) {
								continue;
							}

							List<List<Object>> toolTrays = toolVstrayAndQuantityMap.get(toolCode);
							for (List<Object> toolTray : toolTrays) {
								String trayTmp = (String) toolTray.get(0);
								int valueTmp = (int) toolTray.get(1);
								if (valueTmp > 0) {
									listSubtractTrays.add(trayTmp);
								}
							}
						}
						trayCombobox.addItem("");
						for (String trayName : listAllTrays) {
							if (!listSubtractTrays.contains(trayName)) {
								trayCombobox.addItem(trayName);
							}
						}
					}

					// toolComboBox.setSelectedIndex(0);
					quantityTextField.setText("");
					sendRequestButton.setEnabled(false);
				}

			}
		});

		//////////////////////////////////////////////////////////////////
		trayLabel.setBounds(100 + extWidth, 150 + extHeight, 250, 40);
		trayLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		// trayCombobox = new FilterComboBox(new ArrayList<>(), keyboard);
		trayCombobox = new JComboBox<>();
		trayCombobox.setBounds(250 + extWidth, 155 + extHeight, 400, 35);
		trayCombobox.setFont(new Font(labelFont.getName(), Font.BOLD, 22));

		trayCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (trayCombobox.getItemCount() == 0) {
					return;
				}

				String selectValue = trayCombobox.getSelectedItem().toString();
				// System.out.println(toolVstrayAndQuantityMap);
				// System.out.println("selectValue: " + selectValue);
				if (mapTrayQuantity.containsKey(selectValue)) {

					quantityTextField.setText("" + mapTrayQuantity.get(selectValue));

				} else {
					// trayCombobox.removeAllItems();
					// trayCombobox.addItem("");
					quantityTextField.setText("");
				}
			}
		});
		// trayCombobox.addFocusListener(new FocusAdapter() {
		//
		// @Override
		// public void focusGained(FocusEvent e) {
		// System.out.println("EEEEEEEEEEEEEEEE");
		// trayCombobox.showPopup();
		// }
		// });

		trayCombobox.addItem("");
		// comboBox2 = new AutoCompletion(trayCombobox, trayTextField);

		quantityLabel.setBounds(100 + extWidth, 210 + extHeight, 250, 40);
		quantityLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		quantityTextField.setBounds(250 + extWidth, 215 + extHeight, 180, 35);
		quantityTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
		// quantityTextField.setEditable(false);
		quantityTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateCombo();
			}

			public void removeUpdate(DocumentEvent e) {
				updateCombo();
			}

			public void insertUpdate(DocumentEvent e) {
				updateCombo();
			}

			public void updateCombo() {

				if (validateAllFields()) {
					sendRequestButton.setEnabled(true);
				}
			}
		});

		quantityMessage.setBounds(250 + extWidth, 250 + extHeight, 550, 35);
		quantityMessage.setFont(new Font(labelFont.getName(), Font.BOLD, 18));

		sendRequestButton.setEnabled(false);
		sendRequestButton.setBounds(250 + extWidth, 290 + extHeight, 250, 35);
		sendRequestButton.setFont(new Font(labelFont.getName(), Font.BOLD, 22));

		cancelButton.setBounds(510 + extWidth, 290 + extHeight, 140, 35);
		cancelButton.setFont(new Font(labelFont.getName(), Font.BOLD, 22));

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + timeoutMess + "</font></html>", "Time Out Putin", JOptionPane.WARNING_MESSAGE);

				logger.info(userName + ": " + Enum.PUTIN_TAKEOVER_PAGE + " time out.");
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

	/**
	 * 
	 * @return
	 */
	private boolean validateAllFields() {
		try {
			updateTimer.restart();
		} catch (Exception e) {
			logger.error(userName + ": validateAllFields of putintakeover");
		}
		if (trayCombobox == null || toolComboBox == null || trayCombobox.getSelectedItem() == null
				|| toolComboBox.getSelectedItem() == null || quantityTextField.getText() == null) {
			return false;
		}
		int trayLength = ((String) trayCombobox.getSelectedItem()).length();
		int toolLength = ((String) toolComboBox.getSelectedItem()).length();
		String strTmp = quantityTextField.getText().replaceAll("\\D+", "");
		int quantityLength = strTmp.length();

		if (toolLength > 0 && trayLength > 0 && quantityLength > 0) {

			defaultQuantity = 0;
			if (mapTrayQuantity.containsKey(trayCombobox.getSelectedItem())) {
				defaultQuantity = mapTrayQuantity.get(trayCombobox.getSelectedItem());
			}

			newQuantity = Integer.parseInt(strTmp);
			if (defaultQuantity != newQuantity) {
				if (this.pageType.equals(Enum.TKOVER.text())) {
					if (newQuantity > defaultQuantity) {
						sendRequestButton.setEnabled(false);
						quantityMessage.setText("<html><html><font face=\"arial\" color=\"RED\"><b>" + MessageFormat
								.format(bundleMessage.getString("Putin_TakeOver_Page_TakeOverErrMessage"),
										defaultQuantity)
								+ "</b></font></html></html>");
						return false;
					}
				} else {

					if (newQuantity < defaultQuantity || newQuantity > maxItemsPerTray) {
						sendRequestButton.setEnabled(false);
						quantityMessage.setText("<html><html><font face=\"arial\" color=\"RED\"><b>"
								+ MessageFormat.format(bundleMessage.getString("Putin_TakeOver_Page_PutinErrMessage"),
										defaultQuantity, maxItemsPerTray)
								+ "</b></font></html></html>");
						return false;
					}

				}

				sendRequestButton.setEnabled(true);
				quantityMessage.setText("");
				return true;
			}

			sendRequestButton.setEnabled(false);
			return false;
		} else {
			sendRequestButton.setEnabled(false);
			return false;
		}
	}

	public void addComponentsToContainer() {
		container.add(splitLabel);
		container.add(changePassLabel);
		container.add(logOutLabel);
		container.add(backToDashboardLabel);
		container.add(toolLabel);
		container.add(trayLabel);
		container.add(quantityLabel);
		container.add(toolComboBox);
		container.add(trayCombobox);
		container.add(quantityTextField);
		container.add(sendRequestButton);
		container.add(cancelButton);
		container.add(quantityMessage);

		// container.add(toolTextField);
		// container.add(trayTextField);

	}

	/**
	 * 
	 */
	public void addActionEvent() {
		sendRequestButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(root, "<html><font size=\"5\" face=\"arial\">Are you sure you want to exit?</font></html>", "Confirm Close",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		if (e.getSource() == sendRequestButton) {
			// ImageIcon icon = new ImageIcon("src/img/confirmation_60x60.png");

			JPanel panel = new JPanel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Dimension getPreferredSize() {
					return new Dimension(350, 150);
				}

			};
			// panel.setBackground(new Color(102, 205, 170));
			// panel.setSize(new Dimension(200, 64));

			// panel.setBounds(100 + extWidth, 100 + extHeight, 500, 200);

			panel.setLayout(null);

			String ctid = toolComboBox.getSelectedItem().toString();
			String tray = trayCombobox.getSelectedItem().toString();
			String quantity = quantityTextField.getText().replaceAll("\\D+", "");

			String messConfirm = "<head><style>table {    font-family: arial, sans-serif;    border: none;   "
					+ " width: 100%;} td, th {    text-align: left;    padding: 2px;} tr:nth-child(even) { "
					+ "   background-color: #dddddd;}</style></head><body><table> <tr>    <td>CTID:</td>    <td>" + ctid
					+ "</td>  </tr>  <tr>    <td>Tray:</td>    <td>" + tray
					+ "</td>  </tr>  <tr>    <td>Quantity:</td>    <td>" + quantity + "</td>  </tr> </table></body>";
			JLabel label2 = new JLabel("<html>" + messConfirm + "</html>", SwingConstants.CENTER);
			label2.setVerticalAlignment(SwingConstants.CENTER);
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			label2.setFont(new Font("Arial", Font.BOLD, 22));
			label2.setBounds(0 + extWidth, 0 + extHeight, 350, 150);
			panel.add(label2);

			final String action = this.pageType;
			int dialogResult = JOptionPane.showConfirmDialog(this, panel, action + " Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);

			// We can use JTextArea or JLabel to display messages
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			panel.setLayout(new BorderLayout());
			panel.add(new JScrollPane(textArea));

			// int dialogResult = JOptionPane.showConfirmDialog(null, panel, //
			// Here
			// // goes
			// // content
			// "Here goes the title", JOptionPane.OK_CANCEL_OPTION, // Options
			// // for
			// // JOptionPane
			// JOptionPane.ERROR_MESSAGE); // Message type

			if (dialogResult == 0) {
				logger.info("Yes option");
				final JDialog d = new JDialog();
				JPanel p1 = new JPanel(new GridBagLayout());
				JLabel progress = new JLabel("Please Wait...");
				p1.add(progress, new GridBagConstraints());
				d.getContentPane().add(p1);
				d.setBounds(100 + extWidth, 100 + extHeight, 500, 200);
				// d.setLocationRelativeTo(f);
				d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				d.setModal(true);

				SwingWorker<?, ?> worker = new SwingWorker<Void, String>() {
					protected Void doInBackground() throws InterruptedException {

						publish("Insert into transaction");
						Thread.sleep(1000);
						TransactionController transCtl = new TransactionController();
						transCtl.insertTransaction(userName, companyCode, machineCode, "", "",
								toolComboBox.getSelectedItem().toString(), trayCombobox.getSelectedItem().toString(),
								quantityTextField.getText(), action, "Complete");
						logger.info("Create transaction");

						publish("Update value");
						Thread.sleep(1000);
						resultValue = empCtlObj.updateToolTray(machineCode, toolComboBox.getSelectedItem().toString(),
								trayCombobox.getSelectedItem().toString(), quantityTextField.getText());

						Thread.sleep(1000);
						publish("Update log");

						// Enum actionType = Enum.PUTIN;
						// if (pageType.equals(Enum.TKOVER.text())) {
						// actionType = Enum.TKOVER;
						// }

						// Thread.sleep(1000);
						publish("Send email");

						String email = ctlObj.getEmailUser(userName);
						
//						System.out.println("email: " + email);
//						System.out.println("userName: " + userName);
						
						Thread one = new Thread() {
							public void run() {
								EmailUtils emailUtils = new EmailUtils(Enum.GETTOOL, userName, companyCode,
										machineCode);
								List<String> listCCEmail = new ArrayList<>();
								listCCEmail.add(ctlObj.getEmailAdmin());
								String fullName = ctlObj.getFullNameUser(companyCode, userName);
								emailUtils.sendEmail(email, listCCEmail,
										companyCode + " - " + machineCode + " " + pageType + " notification",
										"Hi " + fullName + "(" + userName + "),\n\nTool: " + ctid + "\nTray: " + tray + "\nNew Quantity: "
												+ quantity);

							}
						};
						one.start();
						Thread.sleep(1000);
						JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + "Completed!" + "</font></html>", "Notify result",
								JOptionPane.INFORMATION_MESSAGE);

						if (resultValue) {
							toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode, -1);
							toolComboBox.setSelectedIndex(0);
							trayCombobox.removeAllItems();
							quantityTextField.setText("");
							sendRequestButton.setEnabled(false);
						}
						return null;
					}

					protected void process(List<String> chunks) {
						String selection = chunks.get(chunks.size() - 1);
						progress.setText(selection);
					}

					protected void done() {
						logger.info("Complete");
						d.dispose();

					}
				};
				worker.execute();
				d.setVisible(true);
			} else {
				logger.info("No Option");
			}

		}
		if (e.getSource() == cancelButton) {
			toolComboBox.setSelectedIndex(0);
			trayCombobox.removeAllItems();
			// trayCombobox.setSelectedIndex(0);
			quantityTextField.setText("");
		}
	}

	public void addVirtualKeyboardListener() {
		MyFocusListener focus1 = new MyFocusListener(keyboard);
		quantityTextField.addFocusListener(focus1);
		toolComboBox.getEditor().getEditorComponent().addFocusListener(focus1);

		toolComboBox.addFocusListener(focus1);

		trayCombobox.getEditor().getEditorComponent().addFocusListener(focus1);
	}

}
