package uk.ac.abertay.eduapp;

import java.io.IOException;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;

public class FTPLetImpl extends DefaultFtplet {

	@Override
	public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
		System.out.println("----------------------->" + session.getUser().getName() + " Logged in");
		return super.onLogin(session, request);
	}

	@Override
	public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
		System.out.println("----------------------->" + session.getUser().getName() + " Disconnected");
		return super.onDisconnect(session);
	}

	@Override
	public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
		System.out.println("----------------------->" + session.getUser().getName() + " Started Downloading File " + request.getArgument());
		return super.onDownloadStart(session, request);
	}

	@Override
	public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
		System.out.println("----------------------->" + "Finished Downloading " + request.getArgument());
		return super.onDownloadEnd(session, request);
	}

}