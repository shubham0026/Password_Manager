package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;

public class LoginFrame extends JFrame {

	private JPanel paneMain;
	private JPasswordField textFieldPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private interface MainInterface{
		void submitButtonAction();
	}
	
	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setTitle("Password Manager Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		paneMain = new JPanel();
		paneMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(paneMain);
		paneMain.setLayout(null);
		
				
		
		JLabel labelTitle = new JLabel("Login");
		labelTitle.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 45));
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setBounds(15, 16, 464, 51);
		paneMain.add(labelTitle);
		
		JFormattedTextField textFieldUsername = new JFormattedTextField();
		textFieldUsername.setBounds(146, 101, 333, 26);
		paneMain.add(textFieldUsername);
		
		MainInterface r = () -> {
			JFrame frameContentFrame = new ContentFrame(textFieldUsername.getText(),
					String.copyValueOf(textFieldPassword.getPassword()));
			frameContentFrame.setVisible(true);
			dispose();
		};
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					r.submitButtonAction();
				}
			}
		});
		textFieldPassword.setBounds(146, 148, 333, 26);
		paneMain.add(textFieldPassword);
		
		JLabel labelUsername = new JLabel("Username:");
		labelUsername.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelUsername.setBounds(25, 101, 106, 26);
		paneMain.add(labelUsername);
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelPassword.setBounds(25, 148, 106, 26);
		paneMain.add(labelPassword);
		
		JButton buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r.submitButtonAction();
			}
		});
		buttonSubmit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 24));
		buttonSubmit.setBounds(146, 208, 333, 36);
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
		buttonBack.setBounds(15, 208, 116, 36);
		paneMain.add(buttonBack);
		
		JSeparator separatorContentButton = new JSeparator();
		separatorContentButton.setBounds(0, 190, 494, 2);
		paneMain.add(separatorContentButton);
		
		JSeparator separatorTitleButton = new JSeparator();
		separatorTitleButton.setBounds(0, 83, 494, 2);
		paneMain.add(separatorTitleButton);
	}
}
