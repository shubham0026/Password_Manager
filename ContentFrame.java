package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
	import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;

public class ContentFrame extends JFrame {

	private JPanel paneMain;
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;
	private JTextField textFieldApplicationName;
	private JTextArea textAreaAdditionalComments;
	private JLabel labelInformation;
	private JList<String> listContent;
	private ArrayList<PasswordItem> arrayPasswordInventory;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContentFrame frame = new ContentFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/*
	 * Creates an abstract interface for the implementation of methods
	 */
	private interface MainInterface {
		void loadItem();
	}
	
	/**
	 * Create the frame.
	 */
	public ContentFrame(String stringMainUsername, String stringMainPassword) {
		// Loads the frame
		setTitle("Password Manager Content");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 750);
		paneMain = new JPanel();
		paneMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(paneMain);
		
		
		// Loads required strings into the frame
		String stringSaltUsername;
		stringSaltUsername = SimpleFileIO.readToString(ExtraFunctions.saltFileNameToFilePath("username"));
		String stringKeyApplicationName;
		stringKeyApplicationName = SimpleFileIO.readToString(ExtraFunctions.keyFileNameToFilePath("applicationName"));
		String stringSaltApplicationName;
		stringSaltApplicationName = SimpleFileIO.readToString(ExtraFunctions.saltFileNameToFilePath("applicationName"));
		String stringKeyAdditionalComments;
		stringKeyAdditionalComments = SimpleFileIO.readToString(ExtraFunctions.keyFileNameToFilePath("additionalComments"));
		String stringSaltAdditionalComments;
		stringSaltAdditionalComments = SimpleFileIO.readToString(ExtraFunctions.saltFileNameToFilePath("additionalComments"));
		
		
		// Implements the loadItem() method
		MainInterface r = () -> {
			if (0 <= listContent.getSelectedIndex() && listContent.getSelectedIndex() < arrayPasswordInventory.size()) {
				String stringApplicationName;
				stringApplicationName = arrayPasswordInventory.get(listContent.getSelectedIndex()).applicationName;
				
				String stringDecryptedApplicationName;
				stringDecryptedApplicationName = SimpleAES.decrypt(stringApplicationName,
						stringKeyApplicationName, stringSaltApplicationName);
				
				String stringEncryptedUsername;
				stringEncryptedUsername = arrayPasswordInventory.get(listContent.getSelectedIndex()).encryptedUsername;
				String stringDecryptedUsername;
				stringDecryptedUsername = SimpleAES.decrypt(stringEncryptedUsername, stringMainUsername, stringApplicationName);
				
				String stringEncryptedPassword;
				stringEncryptedPassword = arrayPasswordInventory.get(listContent.getSelectedIndex()).encryptedPassword;
				String stringDecryptedPassword;
				stringDecryptedPassword = SimpleAES.decrypt(stringEncryptedPassword, stringMainPassword, stringApplicationName);
				
				String additionalComments;
				additionalComments = arrayPasswordInventory.get(listContent.getSelectedIndex()).additionalComments;
				additionalComments = SimpleAES.decrypt(additionalComments, stringKeyAdditionalComments,
						stringSaltAdditionalComments);
				
				
				textFieldApplicationName.setText(stringDecryptedApplicationName);
				textFieldUsername.setText(stringDecryptedUsername);
				textFieldPassword.setText(stringDecryptedPassword);
				textAreaAdditionalComments.setText(additionalComments);
				labelInformation.setText("The item has been loaded.");
				}
			else {
				labelInformation.setText("Please select a item in the \"Existing Passwords\" list first.");
			}
		};
		
		
		// Loads passwords into an ArrayList
		arrayPasswordInventory = new ArrayList<PasswordItem>();
		ArrayList<String> arrayStringPassword = SimpleFileIO.readToArrayList
				(ExtraFunctions.passwordFileNameToFilePath(SimpleSHA.SHA256SecureMessage
						(stringMainUsername, stringSaltUsername)));
		
		for (int i = 0; i < arrayStringPassword.size(); i++) {
			PasswordItem passwordItemTemp = new PasswordItem();
			passwordItemTemp.stringToItem(arrayStringPassword.get(i));
			arrayPasswordInventory.add(passwordItemTemp);
		}
		
		
		DefaultListModel<String> listModelContent = new DefaultListModel<String>();
		
		for (int i = 0; i < arrayPasswordInventory.size(); i++) {
			listModelContent.addElement(SimpleAES.decrypt(arrayPasswordInventory.get(i).applicationName,
					stringKeyApplicationName, stringSaltApplicationName));
		}
		paneMain.setLayout(null);
		
		
		JLabel labelTitle = new JLabel("Password Manager");
		labelTitle.setBounds(15, 16, 664, 50);
		labelTitle.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 40));
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		paneMain.add(labelTitle);
		
		labelInformation = new JLabel("Add a password or choose a password to load.");
		labelInformation.setBounds(15, 82, 664, 26);
		labelInformation.setHorizontalAlignment(SwingConstants.CENTER);
		labelInformation.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(labelInformation);
		
		
		JLabel labelApplicationName = new JLabel("Application Name:");
		labelApplicationName.setBounds(15, 134, 150, 26);
		labelApplicationName.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(labelApplicationName);
		
		textFieldApplicationName = new JTextField();
		textFieldApplicationName.setBounds(180, 134, 499, 26);
		textFieldApplicationName.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		textFieldApplicationName.setColumns(10);
		paneMain.add(textFieldApplicationName);
		
		
		JLabel labelUsername = new JLabel("Username:");
		labelUsername.setBounds(15, 176, 150, 26);
		labelUsername.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(labelUsername);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(180, 176, 499, 26);
		textFieldUsername.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setBounds(15, 218, 150, 26);
		labelPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(labelPassword);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(180, 218, 499, 26);
		textFieldPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		textFieldPassword.setColumns(10);
		paneMain.add(textFieldPassword);
		
		
		JLabel labelAdditionalComments = new JLabel("Additional Comments:");
		labelAdditionalComments.setBounds(15, 260, 200, 20);
		labelAdditionalComments.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(labelAdditionalComments);
		
		textAreaAdditionalComments = new JTextArea();
		textAreaAdditionalComments.setBounds(15, 296, 664, 79);
		textAreaAdditionalComments.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(textAreaAdditionalComments);
		
		
		JButton buttonAdd = new JButton("Add Item");
		buttonAdd.setBounds(15, 619, 210, 30);
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringApplicationName; 
				
				stringApplicationName = textFieldApplicationName.getText().trim();
				
				boolean existsInList = false;
				
				for (int i = 0; i < arrayPasswordInventory.size(); i++) {
					if (SimpleAES.decrypt(arrayPasswordInventory.get(i).applicationName,
							stringKeyApplicationName, stringSaltApplicationName).equals(stringApplicationName)) {
						existsInList = true;
						break;
					}
				}
				
				if (existsInList) {
					labelInformation.setText("This application has already been added.");
				} else if (stringApplicationName.equals("")) {
					labelInformation.setText("Please add a non-empty application name.");
				} else {
					stringApplicationName = SimpleAES.encrypt(stringApplicationName,
							stringKeyApplicationName, stringSaltApplicationName);
					
					String stringEncryptedUsername;
					stringEncryptedUsername = SimpleAES.encrypt(textFieldUsername.getText(), stringMainUsername, stringApplicationName);
					
					String stringEncryptedPassword;
					stringEncryptedPassword = SimpleAES.encrypt(textFieldPassword.getText(), stringMainPassword, stringApplicationName);
					
					String additionalComments;
					additionalComments = SimpleAES.encrypt(textAreaAdditionalComments.getText(),
							stringKeyAdditionalComments, stringSaltAdditionalComments);
					
					
					PasswordItem passwordItemTemp = new PasswordItem(stringApplicationName, stringEncryptedUsername
							, stringEncryptedPassword, additionalComments);
					
					arrayPasswordInventory.add(passwordItemTemp);
					
					listModelContent.addElement(textFieldApplicationName.getText());
					listContent.ensureIndexIsVisible(listModelContent.size() - 1);
				}				
			}
		});
		buttonAdd.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(buttonAdd);
		
		
		JButton buttonLoad = new JButton("Load Selected Item");
		buttonLoad.setBounds(15, 664, 210, 30);
		buttonLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r.loadItem();
			}
		});
		buttonLoad.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(buttonLoad);
		
		
		JButton buttonExit = new JButton("Exit Program");
		buttonExit.setBounds(469, 664, 210, 30);
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(buttonExit);
		
		
		JButton buttonBack = new JButton("Go Back To Login");
		buttonBack.setBounds(469, 619, 210, 30);
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameLogin = new LoginFrame();
				frameLogin.setVisible(true);
				dispose();
			}
		});
		buttonBack.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(buttonBack);
		
		
		JLabel labelCurrentContent = new JLabel("Existing Passwords:");
		labelCurrentContent.setBounds(15, 405, 175, 26);
		labelCurrentContent.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(labelCurrentContent);
		
		
		JButton buttonSave = new JButton("Save Passwords");
		buttonSave.setBounds(240, 619, 214, 30);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arrayStringPassword.clear();
				for (int i = 0; i < arrayPasswordInventory.size(); i++) {
					arrayStringPassword.add(arrayPasswordInventory.get(i).itemToString());
				}
				SimpleFileIO.writeFromArrayList(ExtraFunctions.passwordFileNameToFilePath(SimpleSHA.SHA256SecureMessage
						(stringMainUsername, stringSaltUsername)), arrayStringPassword);
				labelInformation.setText("The passwords have been successfully saved.");
			}
		});
		buttonSave.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(buttonSave);
		
		
		JButton buttonRemoveItem = new JButton("Remove Selected Item");
		buttonRemoveItem.setBounds(240, 664, 214, 30);
		buttonRemoveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listContent.getSelectedIndex();
				
				if (0 <= selectedIndex && selectedIndex < arrayPasswordInventory.size()) {
					String[] stringArrayPaneOptions = {"Yes", "No"};
					
					int intOptionResponse = JOptionPane.showOptionDialog(paneMain,
							"The removal of the selected item will be irreversible.",
							"Remove Item Warning",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null, stringArrayPaneOptions,
							stringArrayPaneOptions[1]);
					
					if (intOptionResponse == 0) {
						listModelContent.remove(selectedIndex);
						arrayPasswordInventory.remove(selectedIndex);
						labelInformation.setText("The item has been successfully removed.");
					}
				}
				else {
					labelInformation.setText("Please select a item in the \"Existing Passwords\" list first.");
				}
			}
		});
		buttonRemoveItem.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		paneMain.add(buttonRemoveItem);
		
		JSeparator separatorTitleContent = new JSeparator();
		separatorTitleContent.setBounds(0, 116, 694, 2);
		paneMain.add(separatorTitleContent);
		
		JSeparator separatorAddLoad = new JSeparator();
		separatorAddLoad.setBounds(0, 391, 694, 2);
		paneMain.add(separatorAddLoad);
		
		JSeparator separatorContentButton = new JSeparator();
		separatorContentButton.setBounds(0, 601, 694, 2);
		paneMain.add(separatorContentButton);
		
		JScrollPane scrollPaneContent = new JScrollPane();
		scrollPaneContent.setBounds(15, 447, 664, 138);
		paneMain.add(scrollPaneContent);
		
		
		// Displays list of applications for which passwords exist
		listContent = new JList<String>(listModelContent);
		scrollPaneContent.setViewportView(listContent);
		listContent.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					r.loadItem();
				}
			}
		});
		listContent.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		listContent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
