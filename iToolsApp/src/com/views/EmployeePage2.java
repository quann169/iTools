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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import com.controllers.LoginController;
import com.controllers.TransactionController;
import com.lib.hid4java.HidDevice;
import com.lib.hid4java.HidException;
import com.lib.hid4java.HidManager;
import com.lib.hid4java.HidServices;
import com.lib.hid4java.HidServicesListener;
import com.lib.hid4java.HidServicesSpecification;
import com.lib.hid4java.ScanMode;
import com.lib.hid4java.event.HidServicesEvent;
import com.message.Enum;
import com.models.Assessor;
import com.models.Machine;
import com.models.Role;
import com.models.Tool;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;
import com.utils.EmailUtils;
import com.utils.MyFocusListener;
import com.utils.PopUpKeyboard;
import com.utils.StringUtils;

public class EmployeePage2 extends JFrame implements ActionListener, HidServicesListener {

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

	static LoginController ctlObj = new LoginController();

	JLabel woLabel = new JLabel(bundleMessage.getString("Employee_Page_WO"));
	JLabel opLabel = new JLabel(bundleMessage.getString("Employee_Page_OP"));
	JLabel toolLabel = new JLabel(bundleMessage.getString("Employee_Page_Tool"));
	JLabel trayLabel = new JLabel(bundleMessage.getString("Employee_Page_Tray"));
	JLabel quantityLabel = new JLabel(bundleMessage.getString("Employee_Page_Quantity"));

	JTextField woTextField = new JTextField();
	JTextField opTextField = new JTextField();
	JTextField trayTextField = new JTextField();
	JComboBox<String> toolComboBox = new JComboBox<String>();
	JTextField quantityTextField = new JTextField();
	boolean isReceiveResult = false;

	JLabel progress = new JLabel();

	Map<String, List<List<Object>>> toolVstrayAndQuantityMap = new HashMap<>();

	JButton sendRequestButton = new JButton(bundleMessage.getString("Employee_Page_Send_Request"));
	JButton cancelButton = new JButton(bundleMessage.getString("Employee_Page_Cancel"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));
	private static final String companyCodeUH = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE_UH"));

	int transactionID = -1;
	String userName = "";

	JFrame root = this;

	final static Logger logger = Logger.getLogger(EmployeePage2.class);
	EmployeeController empCtlObj = new EmployeeController();
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;
	int wo_min_length = Integer.valueOf(cfg.getProperty("Employee_Page_WO_Min_Length"));
	int op_min_length = Integer.valueOf(cfg.getProperty("Employee_Page_OP_Min_Length"));

	String vendorId = cfg.getProperty("VENDOR_ID");
	String productId = cfg.getProperty("PRODUCT_ID");
	int readWaitTime = Integer.valueOf(cfg.getProperty("READ_WAIT_TIME"));

	int readWaitTimeFinal = Integer.valueOf(cfg.getProperty("READ_WAIT_TIME_FINAL"));

	HashMap<String, String> hashMessage = new HashMap<>();
	TransactionController transCtl = new TransactionController();

	int MOTOR_START = Integer.valueOf(cfg.getProperty("MOTOR_START"));
	int MOTOR_STOP = Integer.valueOf(cfg.getProperty("MOTOR_STOP"));
	int PRODUCT_OK = Integer.valueOf(cfg.getProperty("PRODUCT_OK"));
	int PRODUCT_FAIL = Integer.valueOf(cfg.getProperty("PRODUCT_FAIL"));

	AutoCompletion combox1;

	int resultValue;
	boolean isDashboard;
	public PopUpKeyboard keyboard;
	List<Tool> allToolNames = new ArrayList<>();
	Assessor user;
	// JFrame parent;

	ActionListener toolComboboxListener;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int windowWidth = (int) screenSize.getWidth();
	static int windowHeight = (int) screenSize.getHeight();
	static int extWidth = (windowWidth > 900) ? 0 : 0;
	static int extHeight = (windowHeight > 700) ? 0 : 0;

	EmployeePage2(Assessor user, boolean isDashboard) {
		this.userName = user.getUsername();
		this.user = user;
		this.isDashboard = isDashboard;
		// this.parent = parent;
		toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode, 0);
		allToolNames = empCtlObj.getAllTools(companyCode);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		logger.info("Init " + Enum.EMP_PAGE);

		List<String> listAllTrays = Enum.getTrays();
		logger.info("listAllTrays: " + listAllTrays);
		for (String tray : listAllTrays) {
			if (cfg.checkKey(tray)) {
				hashMessage.put(tray, cfg.getProperty(tray));
			}
		}
		logger.info("hashMessage: " + hashMessage);
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = woLabel.getFont();

		backToDashboardLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("Employee_Back_To_Dashboard") + "</u></i></b></font></html></html>");
		backToDashboardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToDashboardLabel.setBounds(15 + extWidth, 5 + extHeight, 300, 70);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				updateTimer.restart();
				logger.info(userName + " back to dashboard from " + Enum.GETTOOL);
				JFrame old = root;

				List<Role> listRoles = ctlObj.getUserRoles(userName, companyCode, companyCodeUH);
				root = new DashboardPage(listRoles, user);
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(userName);
				old.dispose();

			}
		});

		if (this.isDashboard) {
			backToDashboardLabel.setEnabled(true);
		} else {
			backToDashboardLabel.setEnabled(false);
		}

		changePassLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_ChangePassword") + "</u></i></b></font></html></html>");
		changePassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePassLabel.setBounds(530 + extWidth, 5 + extHeight, 170, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();

				logger.info(userName + " click change password from " + Enum.EMP_PAGE);
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

		woLabel.setBounds(180 + extWidth, 70 + extHeight, 150, 60);
		woLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		woTextField.setBounds(250 + extWidth, 80 + extHeight, 400, 35);

		woTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				validateAllFields();
			}
		});
		woTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 22));

		woTextField.requestFocus();
		woTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (woTextField.getText().length() >= 20)
					e.consume();
			}
		});

		opLabel.setBounds(180 + extWidth, 125 + extHeight, 150, 60);
		opLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		opTextField.setBounds(250 + extWidth, 135 + extHeight, 400, 35);
		opTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
		opTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();

			}

			public void warn() {
				validateAllFields();
			}
		});

		opTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (opTextField.getText().length() >= 20)
					e.consume();
			}
		});

		toolLabel.setBounds(180 + extWidth, 180 + extHeight, 150, 60);
		toolLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		// toolComboboxListener = new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// if (toolComboBox.getItemCount() == 0) {
		// return;
		// }
		// String selectValue = toolComboBox.getSelectedItem().toString();
		// // System.out.println(toolVstrayAndQuantityMap);
		// // System.out.println("selectValue: " + selectValue);
		// if (toolVstrayAndQuantityMap.containsKey(selectValue)) {
		//
		// List<List<Object>> existedValue =
		// toolVstrayAndQuantityMap.get(selectValue);
		// if (existedValue.size() > 0) {
		// List<String> listTrays = new ArrayList<>();
		// for (List<Object> trayQuantity : existedValue) {
		// String tray = (String) trayQuantity.get(0);
		// int quantity = (int) trayQuantity.get(1);
		// quantityTextField.setText("" + quantity);
		// trayTextField.setText("" + tray);
		// listTrays.add(trayQuantity.toString());
		// }
		// trayTextField.setToolTipText(listTrays.toString());
		// }
		//
		// } else {
		// trayTextField.setText("");
		// quantityTextField.setText("0");
		//
		// if (!selectValue.equals("") && allToolNames.contains(selectValue)) {
		// List<Machine> availableMachine =
		// empCtlObj.findAvailableMachine(machineCode, selectValue);
		// String availableMachineNotify = MessageFormat.format(
		// bundleMessage.getString("Employee_AvailableMachine"), selectValue,
		// machineCode,
		// availableMachine.toString());
		//
		// JOptionPane.showMessageDialog(trayTextField.getParent(),
		// availableMachineNotify);
		// logger.info("Suggest machine for tool " + selectValue + " - company "
		// + COMPANY_CODE + ": "
		// + availableMachine);
		//
		// }
		// }
		// }
		// };

		updateToolCombobox();

		toolComboBox.setBounds(250 + extWidth, 190 + extHeight, 400, 35);
		toolComboBox.setFont(new Font(labelFont.getName(), Font.BOLD, 22));

		// toolComboBox.addFocusListener(new FocusAdapter() {
		//
		// @Override
		// public void focusGained(FocusEvent e) {
		// toolComboBox.showPopup();
		// }
		// });

		trayLabel.setBounds(250 + extWidth, 215 + extHeight, 150, 60);
		trayLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 15));

		trayTextField.setBounds(250 + extWidth, 265 + extHeight, 220, 35);
		trayTextField.setEditable(false);
		trayTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (validateAllFields()) {
					sendRequestButton.setEnabled(true);
				}
			}
		});

		trayTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 18));

		quantityLabel.setBounds(490 + extWidth, 215 + extHeight, 210, 60);
		quantityLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 15));

		quantityTextField.setBounds(490 + extWidth, 265 + extHeight, 160, 35);
		quantityTextField.setEditable(false);
		quantityTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (validateAllFields()) {
					sendRequestButton.setEnabled(true);
				}
			}
		});

		quantityTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 18));

		sendRequestButton.setEnabled(false);
		sendRequestButton.setBounds(250 + extWidth, 320 + extHeight, 220, 35);
		sendRequestButton.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		cancelButton.setBounds(490 + extWidth, 320 + extHeight, 160, 35);
		cancelButton.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container,
						"<html><font size=\"5\" face=\"arial\">" + timeoutMess + "</font></html>", "Time Out Emp",
						JOptionPane.WARNING_MESSAGE);

				logger.info(userName + ": " + Enum.EMP_PAGE + " time out.");
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

	private void updateToolCombobox() {
		toolComboBox.removeActionListener(toolComboBox.getAction());
		toolComboBox = new JComboBox<>();
		int size = toolComboBox.getItemCount();
		// List<String> listInt = new ArrayList<>();
		// for (int i = size - 1; i >= 0; i--) {
		// String value = toolComboBox.getItemAt(i);
		// if (!"".equals(value)) {
		// // System.out.println("listInt: " + listInt + " - " + i);
		// // listInt.add(value);
		// toolComboBox.removeItem(value);
		//
		// }
		// }
		// for (String pos : listInt) {
		// toolComboBox.removeItem(pos);
		// }

		// System.out.println("AAAAAAAAAAA: " + toolComboBox.getItemCount());
		// toolComboBox.removeAllItems();

		List<Tool> listTools = empCtlObj.getToolsOfMachine(machineCode, companyCode);
		Collections.sort(listTools, new Comparator<Tool>() {
			public int compare(Tool o1, Tool o2) {
				if (o1.getToolName() == o2.getToolName())
					return 0;
				return o1.getToolName().compareToIgnoreCase(o2.getToolName());
			}
		});

		List<String> listToolNames = new ArrayList<>();
		// if (toolComboBox.getItemCount() == 0) {
		listToolNames.add("");
		toolComboBox.addItem("");
		toolComboBox.setSelectedIndex(0);
		// }

		Set<String> existedTools = new HashSet<>();
		for (Tool tool : listTools) {
			String toolNameTmp = tool.getToolName();
			if (!existedTools.contains(toolNameTmp)) {
				listToolNames.add(toolNameTmp);
				toolComboBox.addItem(toolNameTmp);
				existedTools.add(toolNameTmp);
			}

		}

		logger.info("listToolNames: " + listToolNames);

		// toolComboBox = new FilterComboBox(listToolNames, keyboard);

		List<String> allItems = new ArrayList<>();
		size = toolComboBox.getItemCount();
		for (int i = 0; i < size; i++) {
			allItems.add(toolComboBox.getItemAt(i));
		}

		logger.info("toolComboBox: " + allItems);

		// toolComboBox.addItem("");
		// for (Tool tool : listTools) {
		// toolComboBox.addItem(tool.getToolName());
		// }
		//
		// new AutoCompletion(toolComboBox, keyboard);

		toolComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toolComboBox.getItemCount() == 0) {
					// System.out.println("XXXXXXXXXXXXXxx");
					return;
				}
				String selectValue = toolComboBox.getSelectedItem().toString();
				// String selectValue = ((JTextField)
				// toolComboBox.getEditor().getEditorComponent()).getText();
				// System.out.println(toolVstrayAndQuantityMap);
				System.out.println("selectValue: " + selectValue);
				if (toolVstrayAndQuantityMap.containsKey(selectValue)) {

					List<List<Object>> existedValue = toolVstrayAndQuantityMap.get(selectValue);
					if (existedValue.size() > 0) {
						List<String> listTrays = new ArrayList<>();
						for (List<Object> trayQuantity : existedValue) {
							String tray = (String) trayQuantity.get(0);
							int quantity = (int) trayQuantity.get(1);
							quantityTextField.setText("" + quantity);
							trayTextField.setText("" + tray);
							listTrays.add(trayQuantity.toString());
						}
						trayTextField.setToolTipText(listTrays.toString());
					}

				} else {
					trayTextField.setText("");
					quantityTextField.setText("0");

					if (!selectValue.equals("") && allToolNames.contains(selectValue)) {
						List<Machine> availableMachine = empCtlObj.findAvailableMachine(machineCode, selectValue);
						String availableMachineNotify = MessageFormat.format(
								bundleMessage.getString("Employee_AvailableMachine"), selectValue, machineCode,
								availableMachine.toString());

						JOptionPane.showMessageDialog(trayTextField.getParent(), availableMachineNotify);
						logger.info("Suggest machine for tool " + selectValue + " - company " + COMPANY_CODE + ": "
								+ availableMachine);

					}
				}
			}
		});

		// toolComboBox.repaint();
	}

	private boolean validateAllFields() {
		try {
			updateTimer.restart();
		} catch (Exception e) {
			logger.error("validateAllFields of emppage");
		}

		if (toolComboBox.getItemCount() == 0) {
			sendRequestButton.setEnabled(false);
			return false;
		}

		int woLength = woTextField.getText().length();
		int opLength = opTextField.getText().length();
		int trayLength = trayTextField.getText().length();
		int toolLength = ((String) toolComboBox.getSelectedItem()).length();
		int quantityLength = quantityTextField.getText().length();
		updateTimer.restart();
		if (woLength > 0 && opLength > 0 && toolLength > 0 && trayLength > 0 && quantityLength > 0
				&& Integer.valueOf(quantityTextField.getText()) > 0) {
			sendRequestButton.setEnabled(true);
			return true;
		} else {
			sendRequestButton.setEnabled(false);
			return false;
		}
	}

	public void addComponentsToContainer() {
		container.add(splitLabel);
		container.add(changePassLabel);
		container.add(logOutLabel);
		if (this.isDashboard) {
			container.add(backToDashboardLabel);
		}

		container.add(woLabel);
		container.add(opLabel);
		container.add(toolLabel);
		container.add(trayLabel);
		container.add(quantityLabel);
		container.add(woTextField);
		container.add(opTextField);
		container.add(toolComboBox);
		container.add(trayTextField);
		container.add(quantityTextField);
		container.add(sendRequestButton);
		container.add(cancelButton);
	}

	public void addActionEvent() {
		sendRequestButton.addActionListener(this);
		cancelButton.addActionListener(this);

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

			String wo = woTextField.getText();
			String op = opTextField.getText();
			String ctid = toolComboBox.getSelectedItem().toString();
			String tray = trayTextField.getText();
			int quantity = 1;

			String messConfirm = "<head><style>table {    font-family: arial, sans-serif;    border: none;   "
					+ " width: 100%;} td, th {    text-align: left;    padding: 2px;} tr:nth-child(even) { "
					+ "   background-color: #dddddd;}</style></head><body><table> <tr>    <th>WO:</th>    <th>" + wo
					+ "</th>  </tr>  <tr>    <td>OP:</td>    <td>" + op + "</td>  </tr>  <tr>    <td>CTID:</td>    <td>"
					+ ctid + "</td>  </tr>  <tr>    <td>Tray:</td>    <td>" + tray
					+ "</td>  </tr>  <tr>    <td>Quantity:</td>    <td>" + quantity + "</td>  </tr> </table></body>";
			// String messConfirm = "WO: " + wo + "<br/>OP: " + op + "<br/>CTID:
			// " + ctid + "<br/>Tray: " + tray
			// + "<br/>Quantity: " + quantity;
			JLabel label2 = new JLabel("<html>" + messConfirm + "</html>", SwingConstants.CENTER);
			label2.setVerticalAlignment(SwingConstants.CENTER);
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			label2.setFont(new Font("Arial", Font.BOLD, 15));
			label2.setBounds(0 + extWidth, 0 + extHeight, 350, 150);
			panel.add(label2);

			int dialogResult = JOptionPane.showConfirmDialog(this, panel, "Confirm information",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);

			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			panel.setLayout(new BorderLayout());
			panel.add(new JScrollPane(textArea));

			if (dialogResult == 0) {
				logger.info(userName + " confirm: " + messConfirm);
				updateTimer.restart();
				logger.info(userName + " getTools");
				final JDialog d = new JDialog();
				JPanel p1 = new JPanel(new GridBagLayout());
				progress.setText("Please Wait...");
				p1.add(progress, new GridBagConstraints());
				d.getContentPane().add(p1);
				d.setBounds(150 + extWidth, 200 + extHeight, 500, 200);
				// d.setLocationRelativeTo(f);
				d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				d.setModal(true);
				d.setTitle("Please wait while processing...");

				SwingWorker<?, ?> worker = new SwingWorker<Void, String>() {
					protected Void doInBackground() throws InterruptedException {
						updateTimer.restart();
						Thread.sleep(1000);
						publish("Insert into transaction");
						Thread.sleep(1000);

						transactionID = transCtl.insertTransaction(userName, companyCode, machineCode, wo, op, ctid,
								tray, "1", Enum.GETTOOL.text(), "Send request to board");

						logger.info("Create transaction");
						logger.info("hashMessage: " + hashMessage);
						logger.info("hashMessage containsKey " + trayTextField.getText().toUpperCase() + ": "
								+ hashMessage.containsKey(trayTextField.getText().toUpperCase()));
						if (hashMessage.containsKey(trayTextField.getText().toUpperCase())) {
							String message = hashMessage.get(trayTextField.getText().toUpperCase());
							try {
								publish("Send message to board");
								resultValue = executeAction(message);
								// resultValue = 1;
							} catch (Exception e2) {
								logger.error("executeAction exeoption: " + e2.getMessage());
								return null;
							}

						} else {
							logger.error("Tray " + trayTextField.getText() + " is not defined in config file");
							JOptionPane.showMessageDialog(container,
									"<html><font size=\"5\" face=\"arial\">" + "Tray " + trayTextField.getText()
											+ " is not defined in config file" + "</font></html>",
									"Notify result", JOptionPane.ERROR_MESSAGE);
						}
						logger.info("resultValue: " + resultValue);
						if (resultValue == -1) {
							JOptionPane.showMessageDialog(container,
									"<html><font size=\"5\" face=\"arial\">" + "Failed!" + "</font></html>",
									"Notify result", JOptionPane.ERROR_MESSAGE);
						} else if (resultValue == 0) {
							JOptionPane.showMessageDialog(container,
									"<html><font size=\"5\" face=\"arial\">" + "No result!" + "</font></html>",
									"Notify result", JOptionPane.WARNING_MESSAGE);
						} else if (resultValue == 1) {
							empCtlObj.updateToolTray(machineCode, toolComboBox.getSelectedItem().toString(),
									trayTextField.getText(), "-1");
							logger.info("Completed");
							String email = ctlObj.getEmailUser(companyCode, userName);
							String fullName = ctlObj.getFullNameUser(companyCode, userName);
							Thread one = new Thread() {
								public void run() {
									EmailUtils emailUtils = new EmailUtils(Enum.GETTOOL, userName, companyCode,
											machineCode);
									List<String> listCCEmail = new ArrayList<>();
									listCCEmail.add(ctlObj.getEmailAdmin());
									listCCEmail.add(ctlObj.getEmailSubAdmin(companyCode));
									emailUtils.sendEmail(email, listCCEmail,
											companyCode + " - " + machineCode + ": " + fullName + "(" + userName
													+ ") get tool notification",
											"Hi Employee " + fullName + "(" + userName + "),\nWO: " + wo + "\nOP: " + op
													+ "\nTool: " + ctid + "\nTray: " + tray + "\nQuantity: 1\n" + "Machine no.: " + machineCode);

								}
							};
							one.start();

							JOptionPane.showMessageDialog(container,
									"<html><font size=\"5\" face=\"arial\">" + "Completed!" + "</font></html>",
									"Notify result", JOptionPane.INFORMATION_MESSAGE);
						}

						updateTimer.restart();
						return null;
					}

					protected void process(List<String> chunks) {
						String selection = chunks.get(chunks.size() - 1);
						progress.setText(selection);
					}

					protected void done() {
						logger.info("Complete with resultValue = " + resultValue);
						d.dispose();
						if (resultValue == 1) {
							// woTextField.setText("");
							// opTextField.setText("");
							toolComboBox.setSelectedIndex(0);
							quantityTextField.setText("");
							trayTextField.setText("");
							toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode, 0);
							// updateToolCombobox();

							// toolComboBox.setSelectedItem("");
							// ((JTextField)toolComboBox.getEditor().getEditorComponent()).setText("");
						}

					}
				};
				worker.execute();
				d.setVisible(true);

			} else {
				logger.info("Choose No Option");
			}

		}
		if (e.getSource() == cancelButton) {
			woTextField.setText("");
			opTextField.setText("");
			trayTextField.setText("");
			toolComboBox.setSelectedIndex(0);
			quantityTextField.setText("");
		}
	}

	public int executeAction(String messageData) throws HidException {
		logger.info("Starting HID services.");
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

		hidServices.start();
		progress.setText("Calculating attached devices...");
		logger.info("Calculating attached devices...");

		// Provide a list of attached devices
		for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
			logger.info(hidDevice);
		}

		int VENDOR_ID = Integer.decode(vendorId);
		int PRODUCT_ID = Integer.decode(productId);

		logger.info("vendorId: " + vendorId + " - " + VENDOR_ID);
		logger.info("productId: " + productId + " - " + PRODUCT_ID);

		HidDevice hidDevice = hidServices.getHidDevice(VENDOR_ID, PRODUCT_ID, null);
		logger.info("hidDevice: " + hidDevice);
		logger.info("messageData: " + messageData);

		progress.setText("Get HidDevice " + hidDevice);
		int result = -1;
		if (hidDevice != null) {
			result = sendMessage(hidDevice, messageData);
		} else {
			logger.error("Do not find HID device - please help me to recheck config file");

		}

		hidServices.shutdown();
		return result;
	}

	@Override
	public void hidDeviceAttached(HidServicesEvent event) {
		logger.warn("Add device: " + event.getHidDevice());

	}

	@Override
	public void hidDeviceDetached(HidServicesEvent event) {
		logger.warn("Remove device: " + event.getHidDevice());

	}

	@Override
	public void hidFailure(HidServicesEvent event) {
		logger.warn("HID failure: " + event);

	}

	private int sendMessage(HidDevice hidDevice, String messageData) {

		// Ensure device is open after an attach/detach event
		logger.info("hidDevice: " + hidDevice);
		logger.info("hidDevice.isOpen(): " + hidDevice.isOpen());
		if (hidDevice != null && !hidDevice.isOpen()) {

			boolean openDevice = hidDevice.open();
			if (openDevice) {
				logger.info("Open device OK");
			} else {
				logger.error("Open device ERR: " + hidDevice.getLastErrorMessage());
			}
		}
		logger.info("hidDevice.isOpen(): " + hidDevice.isOpen());

		byte value = (byte) Integer.parseInt(messageData, 10);
		byte[] message = new byte[2];
		message[0] = 125;
		message[1] = value;
		logger.info("Data send to board: " + Arrays.toString(message));
		progress.setText("Data send to board: " + Arrays.toString(message));
		int val = -1;
		try {
			val = hidDevice.write(message, 2, (byte) 0x00);
		} catch (Exception e) {
			logger.error("[ERR] Cannot send data. " + e.getMessage() + " " + Enum.SEND_SIGNAL_TO_BOARD_FAIL);

			transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.SEND_SIGNAL_TO_BOARD_FAIL.text());
			transCtl.updateTransaction(transactionID, "Quantity", "0");
			hidDevice.close();
			return -1;
		}

		if (val >= 0) {
			logger.info("> [" + val + "]");
			logger.info(
					"> Send message " + messageData + " successfully. Wait to read data in " + readWaitTime + "s...");
		} else {
			logger.error(hidDevice.getLastErrorMessage());
			transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.SEND_SIGNAL_TO_BOARD_FAIL.text());
			transCtl.updateTransaction(transactionID, "Quantity", "0");
		}
		progress.setText("Begin wait to read data");
		logger.info("-------------Begin wait to read data in " + readWaitTime + "s------------");
		List<Integer> listDataReceived = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			// Prepare to read a single data packet
			boolean moreData = true;
			while (moreData) {
				byte data[] = new byte[2];
				if (i == 3) {
					val = hidDevice.read(data, readWaitTimeFinal * 1000);
				} else {
					val = hidDevice.read(data, readWaitTime * 1000);
				}

				logger.info("Switch case --- " + val + " ----");
				switch (val) {
				case -1:
					logger.error(hidDevice.getLastErrorMessage());
					progress.setText("ERR: " + hidDevice.getLastErrorMessage());
					moreData = false;
					break;
				case 0:
					logger.info("-------------No data receive, end read data------------");
					progress.setText("No data receive, end read data");
					moreData = false;
					break;
				default:
					if (Arrays.equals(data, message)) {
						progress.setText("Board return with meassage: " + Arrays.toString(data));
						continue;
					}
					if (data.length == 2 && data[0] == value) {
						int receivedCode = data[1];
						listDataReceived.add(receivedCode);
						if (receivedCode == MOTOR_START) {
							progress.setText("MOTOR_START");
							logger.info(Enum.MOTOR_START);
						} else if (receivedCode == MOTOR_STOP) {
							progress.setText("MOTOR_STOP");
							logger.info(Enum.MOTOR_STOP);
						} else if (receivedCode == PRODUCT_OK) {
							progress.setText("PRODUCT_OK - Wait " + readWaitTime + "s to next item.");
							logger.info(Enum.PRODUCT_OK);

						} else if (receivedCode == PRODUCT_FAIL) {
							progress.setText("PRODUCT_FAIL");
							logger.info(Enum.PRODUCT_FAIL);
						}

					} else {
						logger.error("INVALID_SIGNAL_RECEIVE " + Arrays.toString(data));
					}
					logger.info("<Data received from board [" + Arrays.toString(data) + "]");
					moreData = false;
					break;
				}
			}

		}
		int result = -1;
		logger.info("listDataReceived: " + listDataReceived);
		for (int dataReceived : listDataReceived) {
			if (dataReceived == MOTOR_START) {
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.MOTOR_START.text());
				// logger.info(Enum.MOTOR_START);
			} else if (dataReceived == MOTOR_STOP) {
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.MOTOR_STOP.text());
				// logger.info(Enum.MOTOR_STOP);
			} else if (dataReceived == PRODUCT_OK) {
				result = 1;
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.PRODUCT_OK.text());
				// logger.info(Enum.PRODUCT_OK);
			} else if (dataReceived == PRODUCT_FAIL) {
				result = 0;
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.PRODUCT_FAIL.text());
				transCtl.updateTransaction(transactionID, "Quantity", "0");
				// logger.info(Enum.PRODUCT_FAIL);
			}
		}

		logger.info("Complete send and receive all messages!!!: ");
		hidDevice.close();
		return result;
	}

	public void addVirtualKeyboardListener() {
		MyFocusListener focus1 = new MyFocusListener(keyboard);
		woTextField.addFocusListener(focus1);
		opTextField.addFocusListener(focus1);
		toolComboBox.getEditor().getEditorComponent().addFocusListener(focus1);
		// combox1.getEditor().addFocusListener(focus1);
	}

}
