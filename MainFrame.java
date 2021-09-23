package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;


public class MainFrame extends JFrame {

	private JPanel paneMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Password Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		paneMain = new JPanel();
		paneMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(paneMain);
		paneMain.setLayout(null);
		
		JLabel labelTitle = new JLabel("Password Manager");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 40));
		labelTitle.setBounds(15, 16, 464, 55);
		paneMain.add(labelTitle);
		
		JButton buttonLogin = new JButton("Login");
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameLogin = new LoginFrame();
				frameLogin.setVisible(true);
				dispose();
			}
		});
		buttonLogin.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 24));
		buttonLogin.setBounds(15, 105, 464, 60);
		paneMain.add(buttonLogin);
		
		JButton buttonCreateAccount = new JButton("Create Account");
		buttonCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameCreateAccount = new CreateAccountFrame();
				frameCreateAccount.setVisible(true);
				dispose();
			}
		});
		buttonCreateAccount.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 24));
		buttonCreateAccount.setBounds(15, 181, 464, 60);
		paneMain.add(buttonCreateAccount);
		
		JButton buttonSettings = new JButton("Settings");
		buttonSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameSettings = new SettingsFrame();
				frameSettings.setVisible(true);
				dispose();
			}
		});
		buttonSettings.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 24));
		buttonSettings.setBounds(15, 257, 464, 60);
		paneMain.add(buttonSettings);
		
		JButton buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 24));
		buttonExit.setBounds(15, 333, 464, 60);
		paneMain.add(buttonExit);
		
		JSeparator separatorTitleButton = new JSeparator();
		separatorTitleButton.setBounds(0, 87, 494, 2);
		paneMain.add(separatorTitleButton);
	}
}
