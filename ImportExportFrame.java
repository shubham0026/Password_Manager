package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ImportExportFrame extends JFrame {

	private JPanel paneMain;
	private JLabel labelInformation;
	private JTextField textFieldExportName;
	private DefaultListModel<String> listModelContent;
	private JList<String> listImportSelection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportExportFrame frame = new ImportExportFrame();
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
	
	private interface MainInterface {
		void importItem();
		void exportItem();
	};
	
	public ImportExportFrame() {
		setTitle("Password Manager Import/Export");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 525);
		paneMain = new JPanel();
		paneMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(paneMain);
		paneMain.setLayout(null);
		
		
		MainInterface r = new MainInterface() {
			public void importItem() {
				int intSelectedInteger;
				intSelectedInteger = listImportSelection.getSelectedIndex();
				
				if (0 <= intSelectedInteger && intSelectedInteger < listModelContent.size()) {
					String[] stringArrayPaneOptions = {"Yes", "No"};
					
					int intOptionResponse = JOptionPane.showOptionDialog(paneMain,
							"Importing will override the currently saved password data.",
							"Import Warning",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null, stringArrayPaneOptions,
							stringArrayPaneOptions[1]);
					
					if (intOptionResponse == 0) {
						ArrayList<String> listImportContent;
						listImportContent = SimpleFileIO.readToArrayList(
								ExtraFunctions.importFileNameToFilePath(listModelContent.get(intSelectedInteger)));
						SimpleFileIO.writeFromString(ExtraFunctions.keyFileNameToFilePath("additionalComments"),
								listImportContent.get(0));
						SimpleFileIO.writeFromString(ExtraFunctions.keyFileNameToFilePath("applicationName"),
								listImportContent.get(1));
						SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("additionalComments"),
								listImportContent.get(2));
						SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("applicationName"),
								listImportContent.get(3));
						SimpleFileIO.writeFromString(ExtraFunctions.saltFileNameToFilePath("username"),
								listImportContent.get(4));
						
						for (int i = 5; i < listImportContent.size(); i++) {
							String[] arrayStringTempProcessing = listImportContent.get(i).split("\\{\\|\\}");
							
							StringBuilder stringBuilderTempContent = new StringBuilder();
							
							for (int j = 1; j < arrayStringTempProcessing.length; j++) {
								stringBuilderTempContent.append(arrayStringTempProcessing[j] + "\n");
							}
							String stringTempContent;
							stringTempContent = stringBuilderTempContent.toString();
							
							
							SimpleFileIO.writeFromString(ExtraFunctions.passwordFileNameToFilePath(arrayStringTempProcessing[0]),
									stringTempContent);
						}
						
						labelInformation.setText("Import complete.");
					}										
				}
				else {
					labelInformation.setText("Select one of the files in the \"import\" box to import.");
				}
			}
			
			
			public void exportItem() {
				StringBuilder stringBuilderExport = new StringBuilder();
				stringBuilderExport.append(SimpleFileIO.readToString(ExtraFunctions.keyFileNameToFilePath("additionalComments")));
				stringBuilderExport.append(SimpleFileIO.readToString(ExtraFunctions.keyFileNameToFilePath("applicationName")));
				stringBuilderExport.append(SimpleFileIO.readToString(ExtraFunctions.saltFileNameToFilePath("additionalComments")));
				stringBuilderExport.append(SimpleFileIO.readToString(ExtraFunctions.saltFileNameToFilePath("applicationName")));
				stringBuilderExport.append(SimpleFileIO.readToString(ExtraFunctions.saltFileNameToFilePath("username")));
				
				ArrayList<String> listStringPasswordFileNames = SimpleFileIO.readFileNamesFromDirectory(
						ExtraFunctions.passwordFileNameToFilePath(""));
				ArrayList<String> listStringPasswords = SimpleFileIO.readFromDirectory(ExtraFunctions.passwordFileNameToFilePath(""));
				
				StringBuilder stringBuilderExportPasswords = new StringBuilder();
				
				for (int i = 0; i < listStringPasswords.size(); i++) {
					String stringTempFileName = listStringPasswordFileNames.get(i);
					stringBuilderExportPasswords.append(stringTempFileName.substring(0, stringTempFileName.length() - 4) + "{|}");
					stringBuilderExportPasswords.append(stringTempFileName.substring(0, stringTempFileName.length() - 4) + "{|}");
					stringBuilderExportPasswords.append(listStringPasswords.get(i).replaceAll("\n", "{|}"));
				}
				
				stringBuilderExport.append(stringBuilderExportPasswords.toString());
				
				String stringExport;
				stringExport = stringBuilderExport.toString();
				
				SimpleFileIO.writeFromString(ExtraFunctions.exportFileNameToFilePath(textFieldExportName.getText()), stringExport);
				labelInformation.setText("Export complete.");
			}
		};
		
		
		ArrayList<String> listStringImportNames = SimpleFileIO.readFileNamesFromDirectory(ExtraFunctions.importFileNameToFilePath(""));
		
		listModelContent = new DefaultListModel<String>();
		
		for (int i = 0; i < listStringImportNames.size(); i++) {
			String tempNameString;
			tempNameString = listStringImportNames.get(i);
			
			if (tempNameString.substring(tempNameString.length() - 4).equals(".txt")) {
				listModelContent.addElement(tempNameString.substring(0, tempNameString.length() - 4));
			}
		}
		
		
		JLabel labelTitleMain = new JLabel("Import / Export");
		labelTitleMain.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 36));
		labelTitleMain.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitleMain.setBounds(15, 16, 464, 50);
		paneMain.add(labelTitleMain);
		
		labelInformation = new JLabel("Welcome.");
		labelInformation.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelInformation.setHorizontalAlignment(SwingConstants.CENTER);
		labelInformation.setBounds(25, 82, 454, 20);
		paneMain.add(labelInformation);
		
		JSeparator separatorTitleContent = new JSeparator();
		separatorTitleContent.setBounds(0, 118, 494, 2);
		paneMain.add(separatorTitleContent);
		
		JLabel labelTitleImport = new JLabel("Import:");
		labelTitleImport.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelTitleImport.setBounds(15, 136, 90, 20);
		paneMain.add(labelTitleImport);
		
		listImportSelection = new JList<String>(listModelContent);
		listImportSelection.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					r.importItem();
				}
			}
		});
		listImportSelection.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		listImportSelection.setBounds(91, 136, 388, 74);
		paneMain.add(listImportSelection);
		
		JButton buttonImport = new JButton("Import Selected File");
		buttonImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r.importItem();
			}
		});
		buttonImport.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		buttonImport.setBounds(15, 226, 464, 29);
		paneMain.add(buttonImport);
		
		JLabel labelTitleExport = new JLabel("Export:");
		labelTitleExport.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelTitleExport.setBounds(15, 289, 90, 20);
		paneMain.add(labelTitleExport);
		
		JLabel labelExportName = new JLabel("File Name:");
		labelExportName.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		labelExportName.setBounds(25, 328, 100, 20);
		paneMain.add(labelExportName);
		
		textFieldExportName = new JTextField();
		textFieldExportName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					r.exportItem();
				}
			}
		});
		textFieldExportName.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		textFieldExportName.setBounds(120, 325, 359, 26);
		paneMain.add(textFieldExportName);
		textFieldExportName.setColumns(10);
		
		JButton buttonBackSettings = new JButton("Back to Settings");
		buttonBackSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameSettings = new SettingsFrame();
				frameSettings.setVisible(true);
				dispose();
			}
		});
		buttonBackSettings.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		buttonBackSettings.setBounds(15, 430, 225, 39);
		paneMain.add(buttonBackSettings);
		
		JButton buttonBackMain = new JButton("Back to Main Page");
		buttonBackMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameMain = new MainFrame();
				frameMain.setVisible(true);
				dispose();
			}
		});
		buttonBackMain.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		buttonBackMain.setBounds(255, 430, 225, 39);
		paneMain.add(buttonBackMain);
		
		JButton buttonExport = new JButton("Export");
		buttonExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r.exportItem();
			}
		});
		buttonExport.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
		buttonExport.setBounds(15, 367, 464, 29);
		paneMain.add(buttonExport);
		
		JSeparator separatorExportButton = new JSeparator();
		separatorExportButton.setBounds(0, 412, 494, 2);
		paneMain.add(separatorExportButton);
		
		JSeparator separatorImportExport = new JSeparator();
		separatorImportExport.setBounds(0, 271, 494, 2);
		paneMain.add(separatorImportExport);
	}
}
