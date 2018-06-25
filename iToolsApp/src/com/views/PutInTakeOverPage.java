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
import com.message.Enum;
import com.models.Assessor;
import com.models.Tool;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;
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

	JComboBox<String> trayCombobox = new JComboBox<String>();
	JComboBox<String> toolComboBox = new JComboBox<String>();
	JTextField quantityTextField = new JTextField();
	boolean isReceiveResult = false;

	Map<String, List<List<Object>>> toolVstrayAndQuantityMap = new HashMap<>();

	JButton sendRequestButton = new JButton(bundleMessage.getString("Putin_TakeOver_Page_Send_Request"));
	JButton cancelButton = new JButton(bundleMessage.getString("Putin_TakeOver_Page_Cancel"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";

	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));

	int maxItemsPerTray = Integer.valueOf(cfg.getProperty("MAX_ITEM_PER_TRAY"));

	final static Logger logger = Logger.getLogger(PutInTakeOverPage.class);
	EmployeeController empCtlObj = new EmployeeController();
	Map<String, Integer> mapTrayQuantity = new HashMap<>();
	boolean resultValue;
	String pageType;
	int defaultQuantity = -1;
	int newQuantity = -1;

	LogController masterLogObj = new LogController();
	String userName = "";

	JFrame root = this;
	JFrame parent;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;

	PutInTakeOverPage(JFrame parent, Assessor user, String pageType) {
		toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode);
		this.parent = parent;
		this.pageType = pageType;
		System.out.println(toolVstrayAndQuantityMap);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();

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
		backToDashboardLabel.setBounds(10, 0, 170, 60);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("backToDashboardLabel");
			}
		});

		backToDashboardLabel.setEnabled(true);

		changePassLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_ChangePassword") + "</u></i></b></font></html></html>");
		changePassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePassLabel.setBounds(440, 0, 170, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("changePassLabel");
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
				System.out.println("logOutLabel");
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.LOGOUT, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				root.dispose();
				parent.dispose();
//				((PutInTakeOverPage) e.getComponent().getParent().getParent().getParent().getParent()).dispose();
			}
		});

		////////////////////////////////////////
		toolLabel.setBounds(100, 105, 150, 60);
		toolComboBox.setBounds(250, 110, 300, 30);

		toolLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

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
								trayCombobox.addItem(tray);

							}
							// trayCombobox.setSelectedIndex(0);
						} else {
							System.out.println("AAAAAAAAAAAAAAa");
						}

					} else {
						toolComboBox.setSelectedIndex(0);
						quantityTextField.setText("0");
						JOptionPane.showMessageDialog(container, MessageFormat
								.format(bundleMessage.getString("Putin_TakeOver_Page_toolNotTrayMessage"), selectValue),
								"Warning", JOptionPane.WARNING_MESSAGE);
						sendRequestButton.setEnabled(false);
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

		//////////////////////////////////////////////////////////////////
		trayLabel.setBounds(100, 160, 150, 60);
		trayCombobox.setBounds(250, 170, 300, 30);

		trayLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

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
					trayCombobox.removeAllItems();
					trayCombobox.addItem("");
					quantityTextField.setText("");
				}
			}
		});
		trayCombobox.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				trayCombobox.showPopup();
			}
		});

		trayCombobox.addItem("");
		AutoCompletion.enable(trayCombobox);

		quantityLabel.setBounds(100, 220, 150, 60);
		quantityLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		quantityTextField.setBounds(250, 230, 100, 30);
		// quantityTextField.setEditable(false);
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

		quantityMessage.setBounds(250, 260, 380, 30);

		sendRequestButton.setEnabled(false);
		sendRequestButton.setBounds(250, 290, 180, 30);
		sendRequestButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		cancelButton.setBounds(450, 290, 100, 30);
		cancelButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				masterLogObj.insertLog(userName, Enum.PUTIN_TAKEOVER_PAGE, "", Enum.TIME_OUT, "", "", companyCode,
						machineCode, StringUtils.getCurrentClassAndMethodNames());
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, timeoutMess, "Time Out Putin", JOptionPane.WARNING_MESSAGE);

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
			System.err.println("validateAllFields of putintakeover");
		}
		if (trayCombobox == null || toolComboBox == null || trayCombobox.getSelectedItem() == null
				|| toolComboBox.getSelectedItem() == null || quantityTextField.getText() == null) {
			return false;
		}
		int trayLength = ((String) trayCombobox.getSelectedItem()).length();
		int toolLength = ((String) toolComboBox.getSelectedItem()).length();
		int quantityLength = quantityTextField.getText().length();

		if (toolLength > 0 && trayLength > 0 && quantityLength > 0) {
			defaultQuantity = mapTrayQuantity.get(trayCombobox.getSelectedItem());
			newQuantity = Integer.parseInt(quantityTextField.getText());
			if (defaultQuantity != newQuantity) {
				if (this.pageType.equals(Enum.TKOVER.text())) {
					if (newQuantity > defaultQuantity) {
						sendRequestButton.setEnabled(false);
						quantityMessage
								.setText("<html><html><font size=\"4\" face=\"arial\" color=\"RED\"><b>" + MessageFormat
										.format(bundleMessage.getString("Putin_TakeOver_Page_TakeOverErrMessage"),
												defaultQuantity)
										+ "</b></font></html></html>");
						return false;
					}
				} else {

					if (newQuantity < defaultQuantity || newQuantity > maxItemsPerTray) {
						sendRequestButton.setEnabled(false);
						quantityMessage.setText("<html><html><font size=\"4\" face=\"arial\" color=\"RED\"><b>"
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

			String ctid = toolComboBox.getSelectedItem().toString();
			String tray = trayCombobox.getSelectedItem().toString();
			String quantity = quantityTextField.getText();

			JLabel label2 = new JLabel("<html>Review and confirm information<br/>CTID: " + ctid + "<br/>Tray: " + tray
					+ "<br/>Quantity: " + quantity + "</html>", SwingConstants.CENTER);
			label2.setVerticalAlignment(SwingConstants.CENTER);
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			label2.setFont(new Font("Arial", Font.BOLD, 15));
			label2.setBounds(0, 0, 350, 150);
			panel.add(label2);

			// UIManager.put("OptionPane.minimumSize", new Dimension(300, 120));
			final String action = this.pageType;
			int dialogResult = JOptionPane.showConfirmDialog(this, panel, action + " Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);

			// JPanel panel = new JPanel() {
			// @Override
			// public Dimension getPreferredSize() {
			// return new Dimension(320, 240);
			// }

			// };

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
				System.out.println("Yes option");
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

						publish("Insert into transaction");
						Thread.sleep(1000);
						TransactionController transCtl = new TransactionController();
						transCtl.insertTransaction(userName, companyCode, machineCode, "", "",
								toolComboBox.getSelectedItem().toString(), trayCombobox.getSelectedItem().toString(),
								quantityTextField.getText(), action, "Complete");

						masterLogObj.insertLog(userName, Enum.WORKINGTRANSACTION, "", Enum.CREATE, "", "", companyCode,
								machineCode, StringUtils.getCurrentClassAndMethodNames());

						publish("Update value");
						Thread.sleep(1000);
						resultValue = empCtlObj.updateToolTray(machineCode, toolComboBox.getSelectedItem().toString(),
								trayCombobox.getSelectedItem().toString(), quantityTextField.getText());
						if (resultValue) {
							toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode);
							toolComboBox.setSelectedIndex(0);
							trayCombobox.removeAllItems();
							quantityTextField.setText("");
						}
						Thread.sleep(1000);
						publish("Update log");

						Enum actionType = Enum.PUTIN;
						if (pageType.equals(Enum.TKOVER.text())) {
							actionType = Enum.TKOVER;
						}

						masterLogObj.insertLog(userName, Enum.TOOLSMACHINETRAY, "quantity", actionType,
								"" + defaultQuantity, "" + newQuantity, companyCode, machineCode,
								StringUtils.getCurrentClassAndMethodNames());
						Thread.sleep(1000);
						publish("Send email");
						Thread.sleep(1000);

						return null;
					}

					protected void process(List<String> chunks) {
						String selection = chunks.get(chunks.size() - 1);
						progress.setText(selection);
					}

					protected void done() {
						System.out.println("Complete");
						d.dispose();

					}
				};
				worker.execute();
				d.setVisible(true);
			} else {
				System.out.println("No Option");
			}

		}
		if (e.getSource() == cancelButton) {
			trayCombobox.setSelectedIndex(0);
			toolComboBox.setSelectedIndex(0);
			quantityTextField.setText("");
		}
	}

}
