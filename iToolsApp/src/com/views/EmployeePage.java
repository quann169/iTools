package com.views;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.EmployeeController;
import com.controllers.LoginController;
import com.models.Assessor;
import com.models.Role;
import com.models.Tool;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;
import com.utils.StringUtils;

public class EmployeePage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
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

	Map<String, List<List<Object>>> toolVstrayAndQuantityMap = new HashMap<>();

	JButton sendRequestButton = new JButton(bundleMessage.getString("Employee_Page_Send_Request"));
	JButton cancelButton = new JButton(bundleMessage.getString("Employee_Page_Cancel"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));

	final static Logger logger = Logger.getLogger(EmployeePage.class);

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
				if (woTextField.getText().length() > 0 && opTextField.getText().length() > 0) {
					sendRequestButton.setEnabled(true);
				}
			}
		});

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
				if (validateAllFields()) {
					sendRequestButton.setEnabled(true);
				}
			}
		});

		toolLabel.setBounds(100, 170, 150, 60);
		toolLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		trayTextField.setBounds(250, 250, 180, 30);
		toolComboBox.setBounds(250, 190, 300, 30);
		//
		// trayTextField.getDocument().addDocumentListener(new
		// DocumentListener() {
		// public void changedUpdate(DocumentEvent e) {
		// warn();
		// }
		//
		// public void removeUpdate(DocumentEvent e) {
		// warn();
		// }
		//
		// public void insertUpdate(DocumentEvent e) {
		// warn();
		// }
		//
		// public void warn() {
		// if (validateAllFields()) {
		// sendRequestButton.setEnabled(true);
		// }
		// }
		// });
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
					List<String> availableTool = EmployeeController.findAvailableMachine(machineCode, selectValue);
					if (!selectValue.equals("")) {
						JOptionPane.showMessageDialog(trayTextField.getParent(), bundleMessage.getString("Login_Page_Login_Fail"));
						logger.info("Login Fail");
					}
					
					
				}
			}
		});
		EmployeeController controllerObj = new EmployeeController();
		String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));
		List<Tool> listTools = controllerObj.getToolsOfMachine(machineCode);
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

		toolVstrayAndQuantityMap = controllerObj.getToolTrayQuantity(machineCode);

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
			return true;
		} else {
			return false;
		}
	}

	public void addComponentsToContainer() {
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


		}
		if (e.getSource() == cancelButton) {
			woTextField.setText("");
			opTextField.setText("");
			trayTextField.setText("");
			toolComboBox.setSelectedIndex(0);;
			quantityTextField.setText("");
		}
	}

	private static void titleAlign(JFrame frame) {

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

		JMenuBar menubar = StringUtils.addMenu();
		frame.setJMenuBar(menubar);
	}

}
