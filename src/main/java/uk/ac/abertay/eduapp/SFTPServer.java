package uk.ac.abertay.eduapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class SFTPServer {

	private final int FTP_PORT = 21;
	private final String DEFAULT_LISTENER = "default";
	// private final Logger LOG = LoggerFactory.getLogger(SFTPServer.class);
	private static final List<Authority> ADMIN_AUTHORITIES;
	private static final int BYTES_PER_KB = 1024;
	private static final String DEFAULT_USER_DIR = "C:\\EduAppCloud\\ftp_users\\";
	private static final String DEFAULT_USER_FILES = "Your_EduApp_Files";
	
	public final static int MAX_CONCURRENT_LOGINS = 1;
	public final static int MAX_CONCURRENT_LOGINS_PER_IP = 10;

	private static FtpServer mFTPServer;
	private static UserManager mUserManager;
	private static FtpServerFactory mFTPServerFactory;
	private ListenerFactory mListenerFactor;

	static {
		// Admin Authorities
		ADMIN_AUTHORITIES = new ArrayList<Authority>();
		ADMIN_AUTHORITIES.add(new WritePermission());
		ADMIN_AUTHORITIES.add(new ConcurrentLoginPermission(
				MAX_CONCURRENT_LOGINS, MAX_CONCURRENT_LOGINS_PER_IP));
		ADMIN_AUTHORITIES.add(new TransferRatePermission(Integer.MAX_VALUE,
				Integer.MAX_VALUE));
	}

	public void init() throws FtpException {
		mFTPServerFactory = new FtpServerFactory();
		mListenerFactor = new ListenerFactory();
		mListenerFactor.setPort(FTP_PORT);

		mFTPServerFactory.addListener(DEFAULT_LISTENER,
				mListenerFactor.createListener());
		mFTPServerFactory.getFtplets().put(FTPLetImpl.class.getName(),
				new FTPLetImpl());

		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		File propFile = new File("myusers.properties");
		System.out.println("Directory ------------> "
				+ propFile.getAbsolutePath());
		userManagerFactory.setFile(propFile);
		userManagerFactory.setPasswordEncryptor(new SaltedPasswordEncryptor());
		mUserManager = userManagerFactory.createUserManager();
		mFTPServerFactory.setUserManager(mUserManager);

		this.createAdminUser();

		mFTPServer = mFTPServerFactory.createServer();

		mFTPServer.start();
	}

	private UserManager createAdminUser() throws FtpException {
		UserManager userManager = mFTPServerFactory.getUserManager();
		String adminName = userManager.getAdminName();

		if (!userManager.doesExist(adminName)) {
			// LOG.info((new
			// StringBuilder()).append("Creating user : ").append(adminName).toString());
			BaseUser adminUser = new BaseUser();
			adminUser.setName(adminName);
			adminUser.setPassword(adminName);
			adminUser.setEnabled(true);
			adminUser.setAuthorities(ADMIN_AUTHORITIES);
			adminUser.setHomeDirectory(DEFAULT_USER_DIR);
			adminUser.setMaxIdleTime(0);
			userManager.save(adminUser);
		}

		return userManager;
	}

	public static void addUser(String username, String password,
			int uploadRateKB, int downloadRateKB) throws FtpException {

		UserManager userManager = mFTPServerFactory.getUserManager();

		if (!userManager.doesExist(username)) {
			BaseUser user = new BaseUser();
			user.setName(username);
			user.setPassword(password);
			new File(DEFAULT_USER_DIR + username).mkdir();
			user.setHomeDirectory(DEFAULT_USER_DIR + username);
			
			File userDir = new File(DEFAULT_USER_DIR + username);
			userDir.setWritable(true);
			userDir.setReadable(true);
			
			File newUserFiles  = new File(DEFAULT_USER_DIR + DEFAULT_USER_FILES);
			try {
				copyDirectory(newUserFiles, userDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			user.setEnabled(true);

			List<Authority> list = new ArrayList<Authority>();
			list.add(new TransferRatePermission(downloadRateKB * BYTES_PER_KB,
					uploadRateKB * BYTES_PER_KB)); // 20KB
			list.add(new ConcurrentLoginPermission(MAX_CONCURRENT_LOGINS,
					MAX_CONCURRENT_LOGINS_PER_IP));

			user.setAuthorities(list);

			mFTPServerFactory.getUserManager().save(user);
		} else {
			System.out.println("--------------------> user already exists!!!");
		}
	}
	
	 // If targetLocation does not exist, it will be created.
    public static void copyDirectory(File sourceLocation , File targetLocation)
    throws IOException {
        
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            
            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {
            
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);
            
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

	public static void restartFTP() throws FtpException {
		if (mFTPServer != null) {
			mFTPServer.stop();
			try {
				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) {
			}
			mFTPServer.start();
		}
	}

	public static void stopFTP() throws FtpException {
		if (mFTPServer != null) {
			mFTPServer.stop();
		}
	}

	public static void pauseFTP() throws FtpException {
		if (mFTPServer != null) {
			mFTPServer.suspend();
		}
	}

	public static void resumeFTP() throws FtpException {
		if (mFTPServer != null) {
			mFTPServer.resume();
		}
	}

	public static void main(String... are) {
		try {
			new SFTPServer().init();
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}

	public void getUserByEmailAndPassword(String username, String password) {

	}
	
}