package uk.ac.abertay.eduapp.services;

import java.net.URI;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.json.JSONWithPadding;

import uk.ac.abertay.eduapp.FtpClient;
import uk.ac.abertay.eduapp.common.Constants;
import uk.ac.abertay.eduapp.common.Util;
import uk.ac.abertay.eduapp.pojo.AuthenticationPojo;
import uk.ac.abertay.eduapp.pojo.FTPFilePojo;

@Path("/dirs")
@Produces({ MediaType.APPLICATION_JSON})
public class FileAccessService {
    private static Logger logger = LoggerFactory.getLogger(FileAccessService.class);
    

    
    @GET 
    @Produces({ "application/json"})     
    public Response getDirs( @Context HttpServletRequest req, @QueryParam("userAuth") String userAuth,  @QueryParam("currentDir") String currentDir,  @QueryParam("file") String file) {
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Get called - get parent Dir -- Authorization : "+ userAuth);
	String listDir = "";
	
	System.out.println("current dir : "+currentDir);
	System.out.println("Selected file : " + file);
	listDir = (currentDir == null || currentDir.isEmpty())? "": currentDir;
	
	if(file != null && !file.contains("Back to ")){
		file = (file == null || file.isEmpty())? "": "/" + file;
		listDir = listDir + file;
	}

	System.out.println("Current dir ----------------> "+ listDir);
	
	AuthenticationPojo authPojo = AccessControlService.getLoggedInUsers().get(userAuth);
    String imageDir = Util.getContextPath(req) + Constants.APP_NAME + "/images/";
	Collection<FTPFilePojo> dirs = FtpClient.getDir(authPojo.getUsername(), authPojo.getPassword(), listDir, imageDir);

	return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(dirs).build();
    }
    

  
}
