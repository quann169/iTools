package com.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
import com.models.Machine;
import com.models.Tool;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;
import com.utils.StringUtils;

public class EmployeePage extends JFrame implements ActionListener, HidServicesListener {

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

	Map<String, List<List<Object>>> toolVstrayAndQuantityMap = new HashMap<>();

	JButton sendRequestButton = new JButton(bundleMessage.getString("Employee_Page_Send_Request"));
	JButton cancelButton = new JButton(bundleMessage.getString("Employee_Page_Cancel"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));

	int transactionID = -1;
	LogController masterLogObj = new LogController();
	String userName = "";

	JFrame root = this;

	final static Logger logger = Logger.getLogger(EmployeePage.class);
	EmployeeController empCtlObj = new EmployeeController();
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;
	int wo_min_length = Integer.valueOf(cfg.getProperty("Employee_Page_WO_Min_Length"));
	int op_min_length = Integer.valueOf(cfg.getProperty("Employee_Page_OP_Min_Length"));

	String vendorId = cfg.getProperty("VENDOR_ID");
	String productId = cfg.getProperty("PRODUCT_ID");
	int readWaitTime = Integer.valueOf(cfg.getProperty("READ_WAIT_TIME"));
	HashMap<String, String> hashMessage = new HashMap<>();
	TransactionController transCtl = new TransactionController();

	int MOTOR_START = Integer.valueOf(cfg.getProperty("MOTOR_START"));
	int MOTOR_STOP = Integer.valueOf(cfg.getProperty("MOTOR_STOP"));
	int PRODUCT_OK = Integer.valueOf(cfg.getProperty("PRODUCT_OK"));
	int PRODUCT_FAIL = Integer.valueOf(cfg.getProperty("PRODUCT_FAIL"));

	int resultValue;
	boolean isDashboard;

	JFrame parent;

	EmployeePage(JFrame parent, String userName, boolean isDashboard) {
		this.userName = userName;
		this.isDashboard = isDashboard;
		this.parent = parent;
		toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.EMP_PAGE, "", "", companyCode, machineCode,
				StringUtils.getCurrentClassAndMethodNames());

		List<String> listAllTrays = Enum.getTrays();
		System.out.println("listAllTrays: " + listAllTrays);
		for (String tray : listAllTrays) {
			System.out.println(tray + ": " + cfg.checkKey(tray));
			if (cfg.checkKey(tray)) {
				hashMessage.put(tray, cfg.getProperty(tray));
			}
		}
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = woLabel.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		backToDashboardLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("Employee_Back_To_Dashboard") + "</u></i></b></font></html></html>");
		backToDashboardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToDashboardLabel.setBounds(10, 0, 170, 60);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("backToDashboardLabel");
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.SHOW_DASHBOARD, "", "", companyCode,
						machineCode, StringUtils.getCurrentClassAndMethodNames());
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
		changePassLabel.setBounds(440, 0, 170, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("changePassLabel");
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.CHANGE_PASS, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
			}
		});

		splitLabel.setBounds(605, 0, 15, 60);
		splitLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 16));

		logOutLabel.setText("<html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_Logout") + "</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(620, 0, 100, 60);
		logOutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.LOGOUT, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				System.out.println("logOutLabel");
				root.dispose();
				parent.dispose();
				// ((EmployeePage)
				// e.getComponent().getParent().getParent().getParent().getParent()).dispose();
			}
		});

		woLabel.setBounds(100, 70, 150, 60);
		woLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		woTextField.setBounds(250, 90, 300, 30);

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

		woTextField.requestFocus();

		opLabel.setBounds(100, 120, 150, 60);
		opLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		opTextField.setBounds(250, 140, 300, 30);

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

		toolLabel.setBounds(100, 170, 150, 60);
		toolLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		trayTextField.setBounds(250, 250, 180, 30);

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

		trayLabel.setBounds(250, 205, 150, 60);
		trayLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 15));

		toolComboBox.setBounds(250, 190, 300, 30);
		toolComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectValue = toolComboBox.getSelectedItem().toString();
				// System.out.println(toolVstrayAndQuantityMap);
				// System.out.println("selectValue: " + selectValue);
				if (toolVstrayAndQuantityMap.containsKey(selectValue)) {

					List<List<Object>> existedValue = toolVstrayAndQuantityMap.get(selectValue);
					if (existedValue.size() > 0) {
						List<String> listTrays = new ArrayList<>();
						for (List<Object> trayQuantity : existedValue) {
							// System.out.println("trayQuantity: " +
							// trayQuantity);
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

					if (!selectValue.equals("")) {
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
		toolComboBox.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				toolComboBox.showPopup();
			}
		});
		String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));
		List<Tool> listTools = empCtlObj.getToolsOfMachine(machineCode);
		Collections.sort(listTools, new Comparator<Tool>() {
			public int compare(Tool o1, Tool o2) {
				if (o1.getToolName() == o2.getToolName())
					return 0;
				return o1.getToolName().compareToIgnoreCase(o2.getToolName());
			}
		});

		toolComboBox.addItem("");
		for (Tool tool : listTools) {
			toolComboBox.addItem(tool.getToolName());
		}

		AutoCompletion.enable(toolComboBox);

		quantityLabel.setBounds(450, 205, 150, 60);
		quantityLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 15));

		quantityTextField.setBounds(450, 250, 100, 30);
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

		sendRequestButton.setEnabled(false);
		sendRequestButton.setBounds(250, 300, 180, 30);
		sendRequestButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		cancelButton.setBounds(450, 300, 100, 30);
		cancelButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				masterLogObj.insertLog(userName, Enum.EMP_PAGE, "", Enum.TIME_OUT, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, timeoutMess, "Time Out Emp", JOptionPane.WARNING_MESSAGE);

				root.dispose();
				parent.dispose();
			}
		});
		updateTimer.setRepeats(false);
		updateTimer.restart();

	}

	private boolean validateAllFields() {
		try {
			updateTimer.restart();
		} catch (Exception e) {
			System.err.println("validateAllFields of emppage");
		}
		int woLength = woTextField.getText().length();
		int opLength = opTextField.getText().length();
		int trayLength = trayTextField.getText().length();
		int toolLength = ((String) toolComboBox.getSelectedItem()).length();
		int quantityLength = quantityTextField.getText().length();
		// System.out.println("----");
		// System.out.println("wo: " + woTextField.getText());
		// System.out.println("op: " + opTextField.getText());
		// System.out.println("tray: " + trayTextField.getText());
		// System.out.println("tool: " + (String)
		// toolComboBox.getSelectedItem());
		// System.out.println("quan: " + quantityTextField.getText());
		updateTimer.restart();
		if (woLength > 0 && opLength > 0 && toolLength > 0 && trayLength > 0 && quantityLength > 0) {
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
		container.add(backToDashboardLabel);
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

			// panel.setBounds(100, 100, 500, 200);

			panel.setLayout(null);

			String wo = woTextField.getText();
			String op = opTextField.getText();
			String ctid = toolComboBox.getSelectedItem().toString();
			String tray = trayTextField.getText();
			int quantity = 1;

			JLabel label2 = new JLabel("<html>Review and confirm information<br/>WO: " + wo + "<br/>OP: " + op
					+ "<br/>CTID: " + ctid + "<br/>Tray: " + tray + "<br/>Quantity: " + quantity + "</html>",
					SwingConstants.CENTER);
			label2.setVerticalAlignment(SwingConstants.CENTER);
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			label2.setFont(new Font("Arial", Font.BOLD, 17));
			label2.setBounds(0, 0, 350, 150);
			panel.add(label2);

			int dialogResult = JOptionPane.showConfirmDialog(this, panel, "Admin Rights Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);

			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			panel.setLayout(new BorderLayout());
			panel.add(new JScrollPane(textArea));

			if (dialogResult == 0) {
				System.out.println("Yes option");
				updateTimer.restart();
				masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.REQUEST_TOOL, "", "", companyCode,
						machineCode, StringUtils.getCurrentClassAndMethodNames());
				final JDialog d = new JDialog();
				JPanel p1 = new JPanel(new GridBagLayout());
				JLabel progress = new JLabel("Please Wait...");
				p1.add(progress, new GridBagConstraints());
				d.getContentPane().add(p1);
				d.setBounds(100, 100, 500, 200);
				// d.setLocationRelativeTo(f);
				d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				d.setModal(true);

				SwingWorker<?, ?> worker = new SwingWorker<Void, String>() {
					protected Void doInBackground() throws InterruptedException {
						updateTimer.restart();
						Thread.sleep(1000);
						publish("Insert into transaction");
						Thread.sleep(1000);

						transactionID = transCtl.insertTransaction(userName, companyCode, machineCode, "", "",
								toolComboBox.getSelectedItem().toString(), tray, quantityTextField.getText(),
								Enum.GETTOOL.text(), "Send request to board");

						masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.CREATE, "",
								"Create transaction", companyCode, machineCode,
								StringUtils.getCurrentClassAndMethodNames());

						if (hashMessage.containsKey(trayTextField.getText().toUpperCase())) {
							String message = hashMessage.get(trayTextField.getText().toUpperCase());
							try {
								resultValue = executeAction(message);
							} catch (Exception e2) {
								e2.printStackTrace();
							}

						} else {
							masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.CREATE, "",
									"Tray " + trayTextField.getText() + "is not defined in config file", companyCode,
									machineCode, StringUtils.getCurrentClassAndMethodNames());
							JOptionPane.showMessageDialog(container,
									"Tray " + trayTextField.getText() + "is not defined in config file",
									"Notify result", JOptionPane.ERROR_MESSAGE);
						}

						if (resultValue == -1) {
							JOptionPane.showMessageDialog(container, "Failed!", "Notify result",
									JOptionPane.ERROR_MESSAGE);
						} else if (resultValue == 0) {
							JOptionPane.showMessageDialog(container, "No result!", "Notify result",
									JOptionPane.WARNING_MESSAGE);
						} else if (resultValue == 1) {
							empCtlObj.updateToolTray(machineCode, toolComboBox.getSelectedItem().toString(),
									trayTextField.getText(), "-1");

							masterLogObj.insertLog(userName, Enum.TOOLSMACHINETRAY, "", Enum.UPDATE, "X", "X - 1",
									companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());

							JOptionPane.showMessageDialog(container, "Completed!", "Notify result",
									JOptionPane.INFORMATION_MESSAGE);
						}

						// for (int x = 0; x <= 100; x += 10) {
						// updateTimer.restart();
						// int lower = 0;
						// int upper = 10;
						//
						// int value = (int) (Math.random() * (upper - lower)) +
						// lower;
						// System.out.println("ramdom value: " + value);
						// publish("Waiting for result: " + x);
						// if (value == 5) {
						// transCtl.updateTransaction(transactionID,
						// "TransactionStatus", "Complete");
						//
						// masterLogObj.insertLog(userName,
						// Enum.WORKINGTRANSACTION, "", Enum.CREATE,
						// "Send request to board", "Complete", companyCode,
						// machineCode,
						// StringUtils.getCurrentClassAndMethodNames(), 0);
						// System.out.println("OK");
						// JOptionPane.showMessageDialog(container,
						// "Completed!", "Notify result",
						// JOptionPane.INFORMATION_MESSAGE);
						// resultValue = 1;
						// break;
						// } else if (value == 7) {
						// System.out.println("Fail");
						// transCtl.updateTransaction(transactionID,
						// "TransactionStatus", "Fail");
						// masterLogObj.insertLog(userName,
						// Enum.WORKINGTRANSACTION, "", Enum.CREATE_FAIL,
						// "Send request to board", "Fail", companyCode,
						// machineCode,
						// StringUtils.getCurrentClassAndMethodNames());
						// JOptionPane.showMessageDialog(container, "Failed!",
						// "Notify result",
						// JOptionPane.ERROR_MESSAGE);
						// break;
						// }
						// Thread.sleep(1000);
						// }
						// if (0 >= 100) {
						// System.out.println("No result");
						// transCtl.updateTransaction(transactionID,
						// "TransactionStatus", "No result");
						// masterLogObj.insertLog(userName,
						// Enum.WORKINGTRANSACTION, "", Enum.CREATE_FAIL,
						// "Send request to board", "No result", companyCode,
						// machineCode,
						// StringUtils.getCurrentClassAndMethodNames());
						// JOptionPane.showMessageDialog(container, "No
						// result!", "Notify result",
						// JOptionPane.WARNING_MESSAGE);
						// }
						updateTimer.restart();
						return null;
					}

					protected void process(List<String> chunks) {
						String selection = chunks.get(chunks.size() - 1);
						progress.setText(selection);
					}

					protected void done() {
						System.out.println("Complete");
						d.dispose();
						if (resultValue == 1) {
							woTextField.setText("");
							opTextField.setText("");
							toolComboBox.setSelectedIndex(0);
							quantityTextField.setText("");
							trayTextField.setText("");
							toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode);
						}

					}
				};
				worker.execute();
				d.setVisible(true);

			} else {
				System.out.println("No Option");
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

		int VENDOR_ID = Integer.decode(vendorId);
		int PRODUCT_ID = Integer.decode(productId);
		
		System.out.println("vendorId: " + vendorId + " - " + VENDOR_ID);
		System.out.println("productId: " + productId + " - " + PRODUCT_ID);
		
		HidDevice hidDevice = hidServices.getHidDevice(VENDOR_ID, PRODUCT_ID, null);
		int result = -1;
		if (hidDevice != null) {
			result =  sendMessage(hidDevice, messageData);
		} else {
			System.out.println("\nDo not find HID device - please help me to recheck config file\n\n");

		}

//		 hidServices.shutdown();
		 return result;
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

	private int sendMessage(HidDevice hidDevice, String messageData) {

		// Ensure device is open after an attach/detach event
		if (hidDevice != null && !hidDevice.isOpen()) {
			hidDevice.open();
		}

		byte value = (byte) Integer.parseInt(messageData, 10);
		byte[] message = new byte[2];
		message[0] = 125;
		message[1] = value;

		System.out.println("\nData send to board: " + Arrays.toString(message));
		int val = -1;
		try {
			val = hidDevice.write(message, 2, (byte) 0x00);
		} catch (Exception e) {
			System.out.println("\n\n[ERR] Cannot send data.\n\n");
			System.out.println(e.getMessage());
			
			transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.SEND_SIGNAL_TO_BOARD_FAIL.text());
			masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.SEND_SIGNAL_TO_BOARD_FAIL, "",
					e.getMessage(), companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());
			return -1;
		}

		if (val >= 0) {
			System.out.println("> [" + val + "]");
			System.out.println(
					"> Send message " + messageData + " successfully. Wait to read data in " + readWaitTime + "s...");
		} else {
			System.err.println(hidDevice.getLastErrorMessage());
			transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.SEND_SIGNAL_TO_BOARD_FAIL.text());
			masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.SEND_SIGNAL_TO_BOARD_FAIL, "",
					hidDevice.getLastErrorMessage(), companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
		}

		System.out.println("\n\n-------------Begin wait to read data in " + readWaitTime + "s------------\n");
		List<Integer> listDataReceived = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			// Prepare to read a single data packet
			boolean moreData = true;
			while (moreData) {
				byte data[] = new byte[2];
				val = hidDevice.read(data, readWaitTime * 1000);
				System.out.println("Switch case --- " + val + " ----");
				// System.out.println("Read result: " + val);
				switch (val) {
				case -1:
					System.err.println(hidDevice.getLastErrorMessage());
					moreData = false;
					break;
				case 0:
					System.out.println("-------------No data receive, end read data------------");
					moreData = false;
					break;
				default:
					if (data.length == 2 && data[0] == value) {
						int receivedCode = data[1];
						listDataReceived.add(receivedCode);
					} else {
						masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.INVALID_SIGNAL_RECEIVE, "",
								Arrays.toString(data), companyCode, machineCode,
								StringUtils.getCurrentClassAndMethodNames());
					}
					System.out.print("<Data received from board [");
					for (byte b : data) {
						System.out.printf(" " + b);
					}
					System.out.println("]");
					moreData = false;
					break;
				}
			}

		}
		int result = -1;
		for (int dataReceived : listDataReceived) {
			if (dataReceived == MOTOR_START) {
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.MOTOR_START.text());
				masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.MOTOR_START, "", "" + dataReceived,
						companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());
			} else if (dataReceived == MOTOR_STOP) {
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.MOTOR_STOP.text());
				masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.MOTOR_STOP, "", "" + dataReceived,
						companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());
			} else if (dataReceived == PRODUCT_OK) {
				result = 1;
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.PRODUCT_OK.text());
				masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.PRODUCT_OK, "", "" + dataReceived,
						companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());
			} else if (dataReceived == PRODUCT_FAIL) {
				result = 0;
				transCtl.updateTransaction(transactionID, "TransactionStatus", Enum.PRODUCT_FAIL.text());
				masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.PRODUCT_FAIL, "", "" + dataReceived,
						companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());
			}
		}

		System.out.println(
				"############################################\nComplete sending all messages!!!\n############################################\n");
		
		return result;
	}

}
