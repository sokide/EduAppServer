/**
 * 
 */
package uk.ac.abertay.eduapp.services;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.sun.jersey.api.json.JSONWithPadding;

import uk.ac.abertay.eduapp.common.ApplicationSecurityController; 
import uk.ac.abertay.eduapp.common.Constants;
import uk.ac.abertay.eduapp.common.Constants.LogOutStatus;
import uk.ac.abertay.eduapp.pojo.AccessPojo;
import uk.ac.abertay.eduapp.pojo.AuthenticationPojo;
import uk.ac.abertay.eduapp.pojo.ExceptionPojo; 

/**
 * @author Sam Okide
 *
 */

@Path("/auth")
@Produces({ MediaType.APPLICATION_JSON})
public class AccessControlService {

    private static Logger logger = LoggerFactory.getLogger(AccessControlService.class);
    private static HashMap<String, AuthenticationPojo> loggedInUserAuth = new HashMap <String, AuthenticationPojo>();
//    @POST
//  @Produces({ MediaType.TEXT_PLAIN})     
//  public String logIn(@QueryParam("username") String username, @QueryParam("password") String password /*, AuthenticationPojo authPojo*/) {
//	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> POST called - login -- Authorization : ");
//	System.out.println("username : "+ username + " password : "+password);
////	AccessPojo accessPojo = ApplicationSecurityController.AuthenticateUser(authPojo.getEmail(), authPojo.getPassword());
////	if(accessPojo == null){
////	    return Response.status(Constants.HTTP_CODE.NOT_ACCEPTED.getCode()).entity(new ExceptionPojo(Constants.HTTP_CODE.NOT_ACCEPTED, new String[]{"Authentication failed!"}, "Could not authenticate the user with the supplied credential.")).build();
////	}
////	return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(accessPojo).build();	
//	return "success";
//  }
    
//    @GET 
//  @Produces({ MediaType.TEXT_PLAIN})     
//  public String logIn2(@QueryParam("username") String username, @QueryParam("password") String password /*, AuthenticationPojo authPojo*/) {
//	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GET called - login -- Authorization : ");
//	System.out.println("username : "+ username + " password : "+password);	
//	return "success";
//  }
   

    @POST 
    @Produces({ "application/json"})     
    public Response login(AuthenticationPojo authPojo/*, @QueryParam("callback") String callback*/){
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GET called - login -- Authorization : ");
    	System.out.println("username : "+ authPojo.getUsername() + " password : "+authPojo.getPassword());
    	AccessPojo accessPojo = ApplicationSecurityController.AuthenticateUser(authPojo.getUsername(), authPojo.getPassword());
		if(accessPojo == null){
		    return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(new ExceptionPojo(Constants.HTTP_CODE.NOT_ACCEPTED, new String[]{"Authentication failed!"}, "Could not authenticate the user with the supplied credential.")).build();
		}
		String userAuth = accessPojo.getUserApiKey();
		AccessControlService.getLoggedInUsers().put(userAuth, authPojo);
		return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(accessPojo).build();	

    }
    
//    @GET
//    @Produces({ "application/json"})     
//    public Response login2(AuthenticationPojo authPojo){
//    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GET called - login -- Authorization : ");
//    	System.out.println("username : "+ authPojo.getUsername() + " password : "+authPojo.getPassword());
//    	AccessPojo accessPojo = ApplicationSecurityController.AuthenticateUser(authPojo.getUsername(), authPojo.getPassword());
//		if(accessPojo == null){
//		    return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(new ExceptionPojo(Constants.HTTP_CODE.NOT_ACCEPTED, new String[]{"Authentication failed!"}, "Could not authenticate the user with the supplied credential.")).build();
//		}
//		return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(accessPojo).build();	
//
//    }

	public static HashMap<String, AuthenticationPojo> getLoggedInUsers() {
		return loggedInUserAuth;
	}

	public static void setLoggedInUsers(HashMap<String, AuthenticationPojo> loggedInUsers) {
		AccessControlService.loggedInUserAuth = loggedInUsers;
	}
    
//    @POST
//    @Path("/all/logout")
//    @Produces({ MediaType.APPLICATION_JSON})     
//    public Response logOut(@Context HttpHeaders headers) {	
//	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> POST called - logout -- Authorization : ");
//	if(headers.getRequestHeader("Authorization") == null){
//	    return Response.status(Constants.HTTP_CODE.NOT_ACCEPTED.getCode()).entity(new ExceptionPojo(Constants.HTTP_CODE.NOT_ACCEPTED, new String[]{"No authorization key supplied!"}, "A valid Authorization key is required for this service.")).build();
//	}
//	
//	String apiKey = headers.getRequestHeader("Authorization").get(0);
//	LogOutStatus accessStatus = ApplicationSecurityController.logUserOut(apiKey);
//	return Response.status(accessStatus.getCode()).entity(accessStatus).build();	    
//
//    }
//    
//    @Path("hello")
//    public static class HelloResource {
//        @GET
//        public String getHello() {
//            return "Hello World!";
//        }
//    }
}
