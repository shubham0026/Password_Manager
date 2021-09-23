package main;

class PasswordItem {
	String applicationName;
	String encryptedUsername;
	String encryptedPassword;
	String additionalComments;
	
	PasswordItem() {
		applicationName = "";
		encryptedUsername = "";
		encryptedPassword = "";
		additionalComments = "";
	}
	
	PasswordItem(String applicationNameInput, String encryptedUsernameInput,
			String encryptedPasswordInput, String additionalCommentsInput) {
		applicationName = applicationNameInput;
		encryptedUsername = encryptedUsernameInput;
		encryptedPassword = encryptedPasswordInput;
		additionalComments = additionalCommentsInput;
	}
	
	void stringToItem(String stringInput) {
		try {
			String[] tempStringArrayInput = stringInput.split("–");
			applicationName = tempStringArrayInput[0];
			encryptedUsername = tempStringArrayInput[1];
			encryptedPassword = tempStringArrayInput[2];
			additionalComments = tempStringArrayInput[3];
		} catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "String-item conversion error due to " + e.toString());
		}	
	}
	
	String itemToString() {
		try { 
			return applicationName + "–" + encryptedUsername + "–" + encryptedPassword + "–" + additionalComments;
		} catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "Item-String conversion error due to " + e.toString());
		}
		
		return "";
	}
}
