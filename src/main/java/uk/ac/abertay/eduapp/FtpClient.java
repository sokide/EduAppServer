package uk.ac.abertay.eduapp;


import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpClient {
	
	public static boolean authenticateUser(String username, String password) {
		FTPClient ftpClient = getFtpClient();
		boolean loggedIn = false;

		try {
			loggedIn = ftpClient.login(username, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// listDirectory(ftpClient, "", "", 1);
		disconectFTP(ftpClient);

		return loggedIn;
	}
	
	private static FTPClient getFtpClient(){
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect("localhost");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftpClient;
	}	
	
	public static void disconectFTP(FTPClient ftpClient) {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getDir(String username, String password, String dir){
		String response = "";
		if(authenticateUser(username, password)){
			StringBuilder dirBuilder = new StringBuilder();
			FTPClient ftpClient = getFtpClient();
			try {
				ftpClient.login(username, password);
				for(FTPFile ftpFile : ftpClient.listFiles()){
					dirBuilder.append("[").append(ftpFile).append("]");
				}
				disconectFTP(ftpClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			response = dirBuilder.toString();
		}else{
			response = "User authentication failed!";
		}
		return response;
	}

	static void listDirectory(FTPClient ftpClient, String parentDir,
	        String currentDir, int level) throws IOException {
	    String dirToList = parentDir;
	    if (!currentDir.equals("")) {
	        dirToList += "/" + currentDir;
	    }
	    FTPFile[] subFiles = ftpClient.listFiles(dirToList);
	    if (subFiles != null && subFiles.length > 0) {
	        for (FTPFile aFile : subFiles) {
	            String currentFileName = aFile.getName();
	            if (currentFileName.equals(".")
	                    || currentFileName.equals("..")) {
	                // skip parent directory and directory itself
	                continue;
	            }
	            for (int i = 0; i < level; i++) {
	                System.out.print("\t");
	            }
	            if (aFile.isDirectory()) {
	                System.out.println("[" + currentFileName + "]");
	                listDirectory(ftpClient, dirToList, currentFileName, level + 1);
	            } else {
	                System.out.println(currentFileName);
	            }
	        }
	    }
	}

	
	public static void main (String [] args){
		authenticateUser("admin","admin");
	}
}
