package uk.ac.abertay.eduapp;


import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;

import uk.ac.abertay.eduapp.pojo.FTPFilePojo;

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
	
	public static Collection<FTPFilePojo> getDir(String username, String password, String dir, String imageDir){
		List<FTPFilePojo> files = null; 
		if(authenticateUser(username, password)){
			
			FTPClient ftpClient = getFtpClient();
			try {
				ftpClient.login(username, password);
				files = new ArrayList<FTPFilePojo>();
//				String 
				if(dir != null && !dir.isEmpty()){
					String parentDir = getPreviousDir(dir);
					FTPFilePojo parentFile = new FTPFilePojo();
					
					parentFile.setImageUrl("<img src=\"" +imageDir + "back-icon.png\">");
					
					if(parentDir == null || parentDir.isEmpty()){
						parentFile.setCurrentDir("");
						parentFile.setName("Back to Home Dir");
					}else{
						parentFile.setCurrentDir(parentDir);
						parentFile.setName("Back to " + parentDir);
					}
					
					files.add(parentFile);
				}
				
				FTPFile []ftpFiles = (dir != null && !dir.isEmpty()) ? ftpClient.listFiles(dir) : ftpClient.listFiles();
				for(FTPFile ftpFile : ftpFiles){
					FTPFilePojo file = new FTPFilePojo(ftpFile,  dir, imageDir);
					files.add(file);
				}
				disconectFTP(ftpClient);
			} catch (FTPConnectionClosedException e) {
//				e.printStackTrace();
				getDir( username,  password,  dir,  imageDir);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}else{
//			response = "User authentication failed!";
			System.out.println("User authentication failed!!");
		}
		return files;
	}

    
    public static String getPreviousDir(String currentDir){
    	String previousDir = "";
    	if(!currentDir.isEmpty() && currentDir.contains("/")){
    		previousDir = currentDir.substring(0, currentDir.lastIndexOf('/'));
    	}    	 
    	return previousDir;
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
		Collection<FTPFilePojo> files = getDir("admin","admin", "ftp_users/bingo", "");
		for(FTPFilePojo file  : files){
			
			System.out.println("-----------> "+file.getName());
		}
		  
	    	System.out.println("previous dir --->" + getPreviousDir("user_dir/"));
	    
	}
}
