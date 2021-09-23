package main;

import java.io.File;

class ExtraFunctions {
	static void createDirectory(String filePath) {
		File fileTest;
		fileTest = new File(filePath);
		
		if (! fileTest.exists()) {
			try {
				fileTest.mkdir();
			}
			catch (Exception e) {
				ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "Directory creation error due to " + e.toString());
			}
		}
	}
	
	
	static String dataFileNameToFilePath(String stringDataName) {
		if (stringDataName.equals("")) {
			return "data";
		}
		else {
			return "data/" + stringDataName + ".txt";
		}
	}
	
	static String passwordFileNameToFilePath(String stringUsername) {
		if (stringUsername.equals("")) {
			return "data/passwords";
		}
		else {
			return "data/passwords/" + stringUsername + ".txt";
		}
	}
	
	static String saltFileNameToFilePath(String stringSaltName) {
		if (stringSaltName.equals("")) {
			return "data/salt";
		}
		else {
			return "data/salt/" + stringSaltName + ".txt";
		}
	}
	
	static String keyFileNameToFilePath(String stringKeyName) {
		if (stringKeyName.equals("")) {
			return "data/keys";
		}
		else {
			return "data/keys/" + stringKeyName + ".txt";
		}
	}
	
	static String logNameToFilePath(String stringLogName) {
		if (stringLogName.equals("")) {
			return "data/log";
		}
		else {
			return "data/log/" + stringLogName + ".txt";
		}
	}
	
	static String exportFileNameToFilePath(String stringExportName) {
		if (stringExportName.equals("")) {
			return "export";
		}
		else {
			return "export/" + stringExportName + ".txt";
		}
	}
	
	static String importFileNameToFilePath(String stringImportName) {
		if (stringImportName.equals("")) {
			return "import";
		}
		else {
			return "import/" + stringImportName + ".txt";
		}
	}
}
