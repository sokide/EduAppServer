/**
 * 
 */
package uk.ac.abertay.eduapp.common;

import javax.servlet.http.HttpServletRequest;

import uk.ac.abertay.eduapp.dao.DataAccessObject;
import uk.ac.abertay.eduapp.mock.Users;

/**
 * @author Sam Okide
 *
 */
public class Util {
    public static String getContextPath(HttpServletRequest req){	
	return "http://"+req.getServerName()+":"+req.getServerPort();
    }
    
    public static String getInitiator(HttpServletRequest req){
	String apiKey =  req.getHeader("Authorization");
	Users user = DataAccessObject.getUserByAuthorizationKey(apiKey);
	if (user == null) {
	    return "";
	}
	return user.getId().toString();
    }

}
