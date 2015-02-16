/**
 * 
 */
package uk.ac.abertay.eduapp.services;


import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.abertay.eduapp.common.ApplicationSecurityController;
import uk.ac.abertay.eduapp.common.Constants;
import uk.ac.abertay.eduapp.common.Constants.Gender;
import uk.ac.abertay.eduapp.common.MD5Util;
import uk.ac.abertay.eduapp.common.Util;
import uk.ac.abertay.eduapp.dao.DataAccessObject;
import uk.ac.abertay.eduapp.mock.Roles;
import uk.ac.abertay.eduapp.mock.Users;
import uk.ac.abertay.eduapp.pojo.ApplicationStatePojo;
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

		ApplicationStatePojo authorizationState = ApplicationSecurityController.isAuthorized(req, Constants.Privileges.POST_USER);
		if (authorizationState.getExceptionPojo() != null) {
			return Response.status(authorizationState.getExceptionPojo().getStatusCode()).entity(authorizationState.getExceptionPojo()).build();
		}

		URI uri = null;
		Roles userRole = null;
		if (jsonUser.getUserRole() != null) {
			userRole = DataAccessObject.getUserRoleByAuthority(jsonUser.getUserRole());
		}

		if (userRole == null) {
			authorizationState = null;
			return Response.status(Constants.HTTP_CODE.NOT_FOUND.getCode()).entity(new ExceptionPojo(Constants.HTTP_CODE.NOT_FOUND, null, "Invalid userRole specified!")).build();
		}

		Users user = new Users();
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
		user.setRoles(userRole);
		user.setEmail(jsonUser.getEmail());
		user.setFirstName(jsonUser.getFirstName());
		user.setLastName(jsonUser.getLastName());
		user.setPassword(MD5Util.getMD5(jsonUser.getPassword()));
		user.setEnabled(true);
		if (jsonUser.getBirthday() == null) {
			user.setBirthday(new Date());
		} else {
			try {
				Date gottenBirthDay = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).parse(jsonUser.getBirthday().toString());
				user.setBirthday(gottenBirthDay);
			} catch (ParseException e) {
				user.setBirthday(new Date());
				e.printStackTrace();
			}
		}
		user.setLocale(jsonUser.getLocale());
		user.setGender(Gender.valueOf(jsonUser.getGender().toUpperCase()).name());
		user.setTimeZone(jsonUser.getTimeZone());
		user = (Users) DataAccessObject.createNewRecord(user);

		UserPojo userPojo = new UserPojo(user, jsonUser.getBirthday());
		try {
			uri = new URI(Util.getContextPath(req) + "/" + Constants.APP_NAME + "/" + Constants.APP_VERSION + "/users/" + user.getId());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		authorizationState = null;
		return Response.status(Constants.HTTP_CODE.CREATED.getCode()).entity(userPojo).location(uri).build();
	}

}
