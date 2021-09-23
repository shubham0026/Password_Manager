package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class SettingsFrame extends JFrame {

	private JPanel paneMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame frame = new SettingsFrame();
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
	public SettingsFrame() {
		setTitle("Password Manager Settings");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		paneMain = new JPanel();
		paneMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(paneMain);
		paneMain.setLayout(null);
		
		JLabel labelTitle = new JLabel("Settings");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 36));
		labelTitle.setBounds(15, 16, 464, 55);
		paneMain.add(labelTitle);
		
		
		JLabel labelInformation = new JLabel("Welcome.");
		labelInformation.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelInformation.setHorizontalAlignment(SwingConstants.CENTER);
		labelInformation.setBounds(15, 87, 464, 25);
		paneMain.add(labelInformation);
		
		
		JButton buttonGenerateSalt = new JButton("Generate New Salt");
		buttonGenerateSalt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				String[] stringArrayPaneOptions = {"Yes", "No"};
				
				int intOptionResponse = JOptionPane.showOptionDialog(paneMain,
						"If new salt is generated, currently encrypted passwords will become inaccessible",
						"Generate Salt Warning",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null, stringArrayPaneOptions,
						stringArrayPaneOptions[1]);
				
				if (intOptionResponse == 0) {
					String newStringSalt;
					
					newStringSalt = SimpleSHA.generateSalt();
					SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("username"), newStringSalt);
					
					newStringSalt = SimpleSHA.generateSalt();
					SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("applicationName"), newStringSalt);
					
					newStringSalt = SimpleSHA.generateSalt();
					SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("additionalComments"), newStringSalt);
					labelInformation.setText("New salt has been generated.");
				}
			}
		});
		buttonGenerateSalt.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 20));
		buttonGenerateSalt.setBounds(15, 212, 464, 50);
		paneMain.add(buttonGenerateSalt);
		
		
		JButton buttonGenerateNewKeys = new JButton("Generate New Keys");
		buttonGenerateNewKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] stringArrayPaneOptions = {"Yes", "No"};
				
				int intOptionResponse = JOptionPane.showOptionDialog(paneMain,
						"If new keys are generated, currently encrypted passwords will become inaccessible",
						"Generate Keys Warning",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null, stringArrayPaneOptions,
						stringArrayPaneOptions[1]);
				
				if (intOptionResponse == 0) {
					String newStringKey;
					
					newStringKey = SimpleAES.generateKey();
					SimpleFileIO.writeFromString(ExtraFunctions.keyFileNameToFilePath("applicationName"), newStringKey);
					
					newStringKey = SimpleAES.generateKey();
					SimpleFileIO.writeFromString(ExtraFunctions.keyFileNameToFilePath("additionalComments"), newStringKey);
					
					labelInformation.setText("New keys have been generated");
				}
			}
		});
		buttonGenerateNewKeys.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 20));
		buttonGenerateNewKeys.setBounds(15, 278, 464, 50);
		paneMain.add(buttonGenerateNewKeys);
		
		
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameMain = new MainFrame();
				frameMain.setVisible(true);
				dispose();
			}
		});
		buttonBack.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 20));
		buttonBack.setBounds(15, 344, 464, 50);
		paneMain.add(buttonBack);
		
		
		JButton buttonImportExport = new JButton("Import/Export Passwords");
		buttonImportExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameImportExport = new ImportExportFrame();
				frameImportExport.setVisible(true);
				dispose();
			}
		});
		buttonImportExport.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 20));
		buttonImportExport.setBounds(15, 146, 464, 50);
		paneMain.add(buttonImportExport);
		
		JSeparator separatorTitleButton = new JSeparator();
		separatorTitleButton.setBounds(0, 128, 494, 2);
		paneMain.add(separatorTitleButton);
	}
}
