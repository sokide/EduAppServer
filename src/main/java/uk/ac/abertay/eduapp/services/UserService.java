/**
 * 
 */
package uk.ac.abertay.eduapp.services;


import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.ftpserver.ftplet.FtpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import uk.ac.abertay.eduapp.SFTPServer;
import uk.ac.abertay.eduapp.common.ApplicationSecurityController;
import uk.ac.abertay.eduapp.common.Constants;
import uk.ac.abertay.eduapp.common.Util;
import uk.ac.abertay.eduapp.mock.Users;
import uk.ac.abertay.eduapp.pojo.AccessPojo;
import uk.ac.abertay.eduapp.pojo.AuthenticationPojo;
import uk.ac.abertay.eduapp.pojo.ExceptionPojo;
import uk.ac.abertay.eduapp.pojo.UserPojo;

/**
 * @author Sam Okide
 *
 */

@Path("/users")
public class UserService {
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserPojo jsonUser, @Context HttpServletRequest req) {
		Logger logger = LoggerFactory.getLogger(UserService.class);
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Post called - create user ");

//		ApplicationStatePojo authorizationState = ApplicationSecurityController.isAuthorized(req, Constants.Privileges.POST_USER);
//		if (authorizationState.getExceptionPojo() != null) {
//			return Response.status(authorizationState.getExceptionPojo().getStatusCode()).entity(authorizationState.getExceptionPojo()).build();
//		}
//
		URI uri = null;
//		Roles userRole = null;
//		if (jsonUser.getUserRole() != null) {
//			userRole = DataAccessObject.getUserRoleByAuthority(jsonUser.getUserRole());
//		}
//
//		if (userRole == null) {
//			authorizationState = null;
//			return Response.status(Constants.HTTP_CODE.NOT_FOUND.getCode()).entity(new ExceptionPojo(Constants.HTTP_CODE.NOT_FOUND, null, "Invalid userRole specified!")).build();
//		}

		Users user = new Users();
		try {
			SFTPServer.addUser(jsonUser.getUserName(), jsonUser.getPassword(), 200, 200);
		} catch (FtpException e1) {
			e1.printStackTrace();
		}
		// Address address = new Address();
		// if (jsonUser.getAddress() != null) {
		// address.setCountry(jsonUser.getAddress().getCountry());
		// address.setCounty(jsonUser.getAddress().getCounty());
		// address.setHouseNumber(jsonUser.getAddress().getNumber());
		// address.setPostCode(jsonUser.getAddress().getPostcode());
		// address.setStreet(jsonUser.getAddress().getStreet());
		// address = (Address) DataAccessObject.createNewRecord(address);
		// user.setAddress(address);
		// }
//		user.setRoles(userRole);
//		user.setEmail(jsonUser.getEmail());
//		user.setFirstName(jsonUser.getFirstName());
//		user.setLastName(jsonUser.getLastName());
//		user.setPassword(MD5Util.getMD5(jsonUser.getPassword()));
//		user.setEnabled(true);
//		if (jsonUser.getBirthday() == null) {
//			user.setBirthday(new Date());
//		} else {
//			try {
//				Date gottenBirthDay = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).parse(jsonUser.getBirthday().toString());
//				user.setBirthday(gottenBirthDay);
//			} catch (ParseException e) {
//				user.setBirthday(new Date());
//				e.printStackTrace();
//			}
//		}
//		user.setLocale(jsonUser.getLocale());
//		user.setGender(Gender.valueOf(jsonUser.getGender().toUpperCase()).name());
//		user.setTimeZone(jsonUser.getTimeZone());
//		user = (Users) DataAccessObject.createNewRecord(user);

//		UserPojo userPojo = new UserPojo(user, jsonUser.getBirthday());
		
		
    	AccessPojo accessPojo = ApplicationSecurityController.AuthenticateUser(jsonUser.getUserName(), jsonUser.getPassword());
		if(accessPojo == null){
		    return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(new ExceptionPojo(Constants.HTTP_CODE.NOT_ACCEPTED, new String[]{"Authentication failed!"}, "Could not authenticate the user with the supplied credential.")).build();
		}
		String userAuth = accessPojo.getUserApiKey();
		AccessControlService.getLoggedInUsers().put(userAuth, new AuthenticationPojo(jsonUser.getUserName(), jsonUser.getPassword()));
				

		return Response.status(Constants.HTTP_CODE.OK.getCode()).entity(accessPojo).build();
		
	}

}
