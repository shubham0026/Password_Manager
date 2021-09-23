package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class ErrorLogger {
	public static void main(String[] args) {
		log(ExtraFunctions.logNameToFilePath("test"), "meme");
	}
	
	static void log(String filePath, String stringError) {
		String stringCurrentLog = SimpleFileIO.readToString(filePath);
		
		String stringDateFormat = "yyyy-MM-dd HH:mm:ss";
		DateFormat dateFormatMain = new SimpleDateFormat(stringDateFormat);
		
		String stringTodayDate = dateFormatMain.format(Calendar.getInstance().getTime());
		
		
		String stringOutput = stringCurrentLog + stringTodayDate + " " + stringError;
		SimpleFileIO.writeFromString(filePath, stringOutput);
	}
}
