package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JSeparator;

public class CreateAccountFrame extends JFrame {

	private JPanel paneMain;
	private JPasswordField textFieldPassword;
	private JPasswordField textFieldConfirmPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccountFrame frame = new CreateAccountFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private interface mainInterface{
		void submitButtonAction();
	}
	
	/**
	 * Create the frame.
	 */
	public CreateAccountFrame() {
		setTitle("Password Manager Create Account");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 375);
		paneMain = new JPanel();
		paneMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(paneMain);
		paneMain.setLayout(null);
		
		JLabel labelTitle = new JLabel("Create Account");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 45));
		labelTitle.setBounds(15, 16, 464, 51);
		paneMain.add(labelTitle);
		
		JLabel labelInformation = new JLabel("Enter your desired username and password.");
		labelInformation.setHorizontalAlignment(SwingConstants.CENTER);
		labelInformation.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelInformation.setBounds(15, 76, 464, 20);
		paneMain.add(labelInformation);
		
		JLabel labelUsername = new JLabel("Username:");
		labelUsername.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelUsername.setBounds(15, 124, 161, 26);
		paneMain.add(labelUsername);
		
		JFormattedTextField textFieldUsername = new JFormattedTextField();
		textFieldUsername.setBounds(201, 124, 278, 26);
		paneMain.add(textFieldUsername);
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelPassword.setBounds(15, 166, 161, 26);
		paneMain.add(labelPassword);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(201, 166, 278, 26);
		paneMain.add(textFieldPassword);
		
		mainInterface r = () -> {
			ExtraFunctions.createDirectory(ExtraFunctions.dataFileNameToFilePath(""));
			ExtraFunctions.createDirectory(ExtraFunctions.logNameToFilePath(""));
			ExtraFunctions.createDirectory(ExtraFunctions.keyFileNameToFilePath(""));
			ExtraFunctions.createDirectory(ExtraFunctions.saltFileNameToFilePath(""));
			ExtraFunctions.createDirectory(ExtraFunctions.passwordFileNameToFilePath(""));
			ExtraFunctions.createDirectory(ExtraFunctions.importFileNameToFilePath(""));
			ExtraFunctions.createDirectory(ExtraFunctions.exportFileNameToFilePath(""));
			
			
			File fileTest;
			
			fileTest = new File(ExtraFunctions.keyFileNameToFilePath("applicationName"));
			if (! fileTest.exists()) {
				String newStringKey;
				
				newStringKey = SimpleAES.generateKey();
				SimpleFileIO.writeFromString(ExtraFunctions.keyFileNameToFilePath("applicationName"), newStringKey);
			}
			
			fileTest = new File(ExtraFunctions.keyFileNameToFilePath("additionalComments"));
			if (! fileTest.exists()) {
				String newStringKey;
				
				newStringKey = SimpleAES.generateKey();
				SimpleFileIO.writeFromString(ExtraFunctions.keyFileNameToFilePath("additionalComments"), newStringKey);
			}
			
			fileTest = new File(ExtraFunctions.saltFileNameToFilePath("username"));
			if (! fileTest.exists()) {
				String newStringSalt;
				
				newStringSalt = SimpleSHA.generateSalt();
				SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("username"), newStringSalt);
			}
			
			fileTest = new File(ExtraFunctions.saltFileNameToFilePath("applicationName"));
			if (! fileTest.exists()) {
				String newStringSalt;
				
				newStringSalt = SimpleSHA.generateSalt();
				SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("applicationName"), newStringSalt);
			}
			
			fileTest = new File(ExtraFunctions.saltFileNameToFilePath("additionalComments"));
			if (! fileTest.exists()) {
				String newStringSalt;
				
				newStringSalt = SimpleSHA.generateSalt();
				SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("additionalComments"), newStringSalt);
			}
			
			
			if (String.valueOf(textFieldPassword.getPassword()).equals(String.valueOf(textFieldConfirmPassword.getPassword()))) {
				SimpleFileIO.createFile(ExtraFunctions.passwordFileNameToFilePath(SimpleSHA.SHA256SecureMessage
						(textFieldUsername.getText(), SimpleFileIO.readToString(
								ExtraFunctions.saltFileNameToFilePath("username")))));
				
				JFrame frameContentFrame = new ContentFrame(textFieldUsername.getText(),
						String.valueOf(textFieldPassword.getPassword()));
				frameContentFrame.setVisible(true);
				dispose();
				}
			else {
				labelInformation.setText("Your passwords do not match.");
			}
		};
		
		JButton buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r.submitButtonAction();
			}
		});
		buttonSubmit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 24));
		buttonSubmit.setBounds(201, 268, 278, 51);
		paneMain.add(buttonSubmit);
		
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameMainFrame = new MainFrame();
				frameMainFrame.setVisible(true);
				dispose();
			}
		});
		buttonBack.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 24));
		buttonBack.setBounds(15, 268, 160, 51);
		paneMain.add(buttonBack);
		
		JLabel labelConfirmPassword = new JLabel("Confirm Password:");
		labelConfirmPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelConfirmPassword.setBounds(15, 208, 161, 26);
		paneMain.add(labelConfirmPassword);
		
		textFieldConfirmPassword = new JPasswordField();
		textFieldConfirmPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					r.submitButtonAction();
				}
			}
		});
		textFieldConfirmPassword.setBounds(201, 208, 278, 26);
		paneMain.add(textFieldConfirmPassword);
		
		JSeparator separatorContentButton = new JSeparator();
		separatorContentButton.setBounds(0, 250, 494, 2);
		paneMain.add(separatorContentButton);
		
		JSeparator separatorTitleContent = new JSeparator();
		separatorTitleContent.setBounds(0, 112, 494, 2);
		paneMain.add(separatorTitleContent);
	}
}
