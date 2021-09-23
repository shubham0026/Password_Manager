package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

class SimpleFileIO {
	static ArrayList<String> readToArrayList(String filePath) {
		ArrayList<String> listOutput = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String tempStringLine;
			
			while ((tempStringLine = reader.readLine()) != null) {
				listOutput.add(tempStringLine);
			}
			
			reader.close();
		}
		catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "File Read(List) error due to " + e.toString());
		}
		
		return listOutput;
	}
	
	
	static String readToString(String filePath) {
		String stringOutput = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String tempStringLine;
			
			while ((tempStringLine = reader.readLine()) != null) {
				stringOutput = stringOutput + tempStringLine + "\n";
			}
			
			reader.close();
		}
		catch (Exception e) {
			System.err.println("File Read(String) error due to " + e.toString());
		}
		
		return stringOutput;	
	}
	
	
	static ArrayList<String> readFromDirectory(String filePath) {
		ArrayList<String> listOutput = new ArrayList<String>();
		
		try {
			File[] arrayFile = new File(filePath).listFiles();
			for (File fileEntry : arrayFile) {
				if (fileEntry.isFile()) {
					listOutput.add(readToString(fileEntry.getPath()));
				}
			}
		} catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "File Read(Directory) error due to " + e.toString());
		}
		
		return listOutput;
	}
	
	
	static ArrayList<String> readFileNamesFromDirectory(String filePath) {
		ArrayList<String> listOutput = new ArrayList<String>();
		
		try {
			File[] arrayFile = new File(filePath).listFiles();
			for (File fileEntry : arrayFile) {
				if (fileEntry.isFile()) {
					listOutput.add(fileEntry.getName());
				}
			}
		}
		catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "File Read(Names) error due to " + e.toString());
		}
		
		return listOutput;
	}
	
	
	static void writeFromString(String filePath, String stringInput) {
		try {			
			File file = new File(filePath);
			
			if (! file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			writer.write(stringInput);
			
			writer.close();
		} catch (Exception e) {
			System.err.println("File write error due to " + e.toString());
		}
	}
	
	
	static void writeFromArrayList(String filePath, ArrayList<String> listInput) {
		try {
			String stringOutput = "";
			for (int i = 0; i < listInput.size(); i++) {
				stringOutput += listInput.get(i) + "\n";
			}
			
			writeFromString(filePath, stringOutput);
		} catch (Exception e) {
			System.err.println("File write error due to " + e.toString());
		}
		
	}
	
	
	static void createFile(String filePath) {
		try {
			File file = new File(filePath);
			if (! file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			System.err.println("File creation error due to " + e.toString());
		}
	}
}
