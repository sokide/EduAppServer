package uk.ac.abertay.eduapp.services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.abertay.eduapp.FtpClient;
import uk.ac.abertay.eduapp.common.Constants;
import uk.ac.abertay.eduapp.pojo.AuthenticationPojo;

@Path("/dirs")
@Produces({ MediaType.APPLICATION_JSON})
public class FileAccessService {
    private static Logger logger = LoggerFactory.getLogger(FileAccessService.class);
    
    @POST
    @Produces({ MediaType.APPLICATION_JSON})     
    public Response getDirs(AuthenticationPojo authPojo) {
	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> POST called - login -- Authorization : ");

	String dirs = FtpClient.getDir(authPojo.getEmail(), authPojo.getPassword(), "");
	return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(dirs).build();	    
    }
}
