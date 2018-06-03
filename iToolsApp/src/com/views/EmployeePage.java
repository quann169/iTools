package com.views;

import java.awt.Container;
import java.awt.Cursor;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.EmployeeController;
import com.models.Machine;
import com.models.Tool;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;

public class EmployeePage extends JFrame implements ActionListener {

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

	final static Logger logger = Logger.getLogger(EmployeePage.class);
	EmployeeController empCtlObj = new EmployeeController();
	Timer updateTimer;
	int delayTime = Integer.valueOf(cfg.getProperty("Employee_Page_Time_Change_Focus")) * 1000;
	int wo_min_length = Integer.valueOf(cfg.getProperty("Employee_Page_WO_Min_Length"));
	int op_min_length = Integer.valueOf(cfg.getProperty("Employee_Page_OP_Min_Length"));

	EmployeePage() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();

	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = woLabel.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		// JButton button = new JButton();
		// button.setText("<HTML><FONT color=\"#9BAFFF\"><U> Logout
		// </U></FONT></HTML>");
		// button.setHorizontalAlignment(SwingConstants.LEFT);
		// button.setBorderPainted(false);
		// button.setOpaque(false);
		// button.setBackground(Color.WHITE);
		// button.setBounds(620, 0, 150, 60);
		// container.add(button);

		changePassLabel.setText(
				"<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>Change Password</u></i></b></font></html></html>");
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

		logOutLabel.setText(
				"<html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>Logout</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(620, 0, 100, 60);
		logOutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("logOutLabel");
				((EmployeePage) e.getComponent().getParent().getParent().getParent().getParent()).dispose();

				// .getClass().getName();
				// LoginPage loginPage = new LoginPage();
				//
				// StringUtils.frameInit(loginPage, bundleMessage);
				//
				//
				// loginPage.setTitle(bundleMessage.getString("Login_Page_Title"));
				// loginPage.getRootPane().setDefaultButton(loginPage.loginButton);
			}
		});

		// changePassLabel.setBounds(480, 0, 150, 60);
		// changePassLabel.setFont(new Font(labelFont.getName(), Font.ITALIC,
		// 16).deriveFont(fontAttributes));
		// changePassLabel.setForeground(Color.getColor("#9BAFFF"));
		//
		// splitLabel.setBounds(610, 0, 20, 60);
		// splitLabel.setFont(new Font(labelFont.getName(), Font.ITALIC, 16));
		//
		// logOutLabel.setBounds(620, 0, 100, 60);
		// logOutLabel.setFont(new Font(labelFont.getName(), Font.ITALIC,
		// 16).deriveFont(fontAttributes));
		// logOutLabel.setForeground(Color.getColor("#9BAFFF"));

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
				updateTimer.restart();
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
				updateTimer.restart();
			}
		});

		toolLabel.setBounds(100, 170, 150, 60);
		toolLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		trayTextField.setBounds(250, 250, 180, 30);
		toolComboBox.setBounds(250, 190, 300, 30);
		trayTextField.setEditable(false);

		trayLabel.setBounds(250, 205, 150, 60);
		trayLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 15));

		toolComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectValue = toolComboBox.getSelectedItem().toString();

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

					if (!selectValue.equals("")) {
						List<Machine> availableMachine = empCtlObj.findAvailableMachine(machineCode, selectValue);

						JOptionPane.showMessageDialog(trayTextField.getParent(),
								bundleMessage.getString("Employee_AvailableMachine"));
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

		toolVstrayAndQuantityMap = empCtlObj.getToolTrayQuantity(machineCode);

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

		updateTimer = new Timer(delayTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (woTextField.getText().length() > wo_min_length && opTextField.getText().length() > op_min_length) {
					toolComboBox.requestFocusInWindow();
				} else if (woTextField.getText().length() > wo_min_length && opTextField.getText().length() == 0) {
					opTextField.requestFocusInWindow();
				}
			}
		});
		updateTimer.setRepeats(false);

	}

	private static boolean StringFilter(String emp, String textToFilter) {
		if (textToFilter.isEmpty()) {
			return true;
		}
		return emp.toLowerCase().contains(textToFilter.toLowerCase());
	}

	private boolean validateAllFields() {
		int woLength = woTextField.getText().length();
		int opLength = opTextField.getText().length();
		int toolLength = trayTextField.getText().length();
		int trayLength = ((String) toolComboBox.getSelectedItem()).length();
		int quantityLength = quantityTextField.getText().length();

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
		if (e.getSource() == sendRequestButton) {
			// JDialog dialog = new JDialog();
			// JLabel label = new JLabel("Please wait...");
			// dialog.setLocationRelativeTo(null);
			// dialog.setBounds(0, 0, 700, 460);
			// dialog.setTitle("Please Wait...");
			// dialog.add(label);
			// dialog.pack();
			//
			// dialog.setVisible(true);
			// final int percent = 0;
			// while (!isReceiveResult) {
			//
			// try {
			// SwingUtilities.invokeLater(new Runnable() {
			// public void run() {
			// it.updateBar(percent);
			// }
			// });
			// java.lang.Thread.sleep(100);
			// } catch (InterruptedException e1) {
			// ;
			// }
			//
			//
			// }
			//
			//// for (int i = 0; i < 5; i++) {
			//// try {
			//// Thread.sleep(1000);
			//// } catch (InterruptedException e1) {
			//// // TODO Auto-generated catch block
			//// e1.printStackTrace();
			//// }
			//// }
			// dialog.setVisible(false);

			final JDialog d = new JDialog();
			JPanel p1 = new JPanel(new GridBagLayout());
			JLabel progress = new JLabel("Please Wait...");
			p1.add(progress, new GridBagConstraints());
			d.getContentPane().add(p1);
			d.setBounds(100, 100, 500, 200);
			// d.setLocationRelativeTo(f);
			d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			d.setModal(true);

			SwingWorker<?, ?> worker = new SwingWorker<Void, Integer>() {
				protected Void doInBackground() throws InterruptedException {
					int x = 0;
					for (; x <= 100; x += 10) {
						publish(x);
						
						int lower = 0;
						int upper = 10;
						
						int value = (int) (Math.random() * (upper - lower)) + lower;
						System.out.println("ramdom value: " + value);
						if (value == 5) {
							System.out.println("OK");
							break;
						} else if (value == 7) {
							System.out.println("Fail");
							break;
						}
						Thread.sleep(1000);
					}
					if (x == 100) {
						System.out.println("No result");
					}
					return null;
				}

				protected void process(List<Integer> chunks) {
					int selection = chunks.get(chunks.size() - 1);
					progress.setText("Please Wait..." + selection + "s");
				}

				protected void done() {
					System.out.println("Complete");
					d.dispose();
					woTextField.setText("");
					opTextField.setText("");
					toolComboBox.setSelectedIndex(0);
					quantityTextField.setText("");
					trayTextField.setText("");
				}
			};
			worker.execute();
			d.setVisible(true);
		}
		if (e.getSource() == cancelButton) {
			woTextField.setText("");
			opTextField.setText("");
			trayTextField.setText("");
			toolComboBox.setSelectedIndex(0);
			quantityTextField.setText("");
		}
	}

}
