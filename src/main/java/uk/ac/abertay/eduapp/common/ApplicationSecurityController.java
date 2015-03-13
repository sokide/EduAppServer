/**
 * 
 */
package uk.ac.abertay.eduapp.common;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import uk.ac.abertay.eduapp.FtpClient;
import uk.ac.abertay.eduapp.common.Constants.LogOutStatus;
import uk.ac.abertay.eduapp.dao.DataAccessObject;
import uk.ac.abertay.eduapp.mock.Application;
import uk.ac.abertay.eduapp.mock.Users;
import uk.ac.abertay.eduapp.pojo.AccessPojo;
import uk.ac.abertay.eduapp.pojo.ApplicationStatePojo;
import uk.ac.abertay.eduapp.pojo.ExceptionPojo;

/**
 * @author Sam Okide
 *
 */
public class ApplicationSecurityController { 

    public static ApplicationStatePojo isAuthorized(HttpServletRequest req, Constants.Privileges privilage)
    {
        ApplicationStatePojo currentState = new ApplicationStatePojo();
        Boolean authorized = Boolean.valueOf(false);
        String applicationKey = req.getHeader("ApplicationAuthorization");
        if(applicationKey == null || applicationKey.equals("")){
            ExceptionPojo ePojo = new ExceptionPojo(Constants.HTTP_CODE.UNAUTHORIZED, new String[] {"Unknown application."
            }, "You are trying to access a secure method from an unknown application.");
            currentState.setExceptionPojo(ePojo);
            return currentState;
        }
        Application application = DataAccessObject.getApplicationByAuthorizationKey(applicationKey);
        if(application != null){
            currentState.setCurrentApplication(application);
			// if(DataAccessObject.getRightsByRoleAndPrivilege(application.getRoles(),
			// privilage.getCode()) != null)
			// authorized = Boolean.valueOf(true);
        } else{
            ExceptionPojo ePojo = new ExceptionPojo(Constants.HTTP_CODE.UNAUTHORIZED, new String[] {
                "Malicious or outdated application."
            }, "You are trying to access a secure method from an malicious or oudated application.");
            currentState.setExceptionPojo(ePojo);
            return currentState;
        }
        String apiKey = req.getHeader("Authorization");
        Users user = DataAccessObject.getUserByAuthorizationKey(apiKey);
        if(user != null)
        {
            currentState.setCurrentUser(user);
			// if(DataAccessObject.getRightsByRoleAndPrivilege(user.getRoles(),
			// privilage.getCode()) != null)
			// authorized = Boolean.valueOf(true);
        }

        if(!authorized.booleanValue())
        {
            ExceptionPojo ePojo = new ExceptionPojo(Constants.HTTP_CODE.UNAUTHORIZED, new String[] {
                "Insufficient privilages"
            }, "The supplied credentatial has insufficent privilage to access this method.");
            currentState.setExceptionPojo(ePojo);
            return currentState;
        } else
        {
            return currentState;
        }
    }

    @SuppressWarnings("unchecked")
    public static AccessPojo AuthenticateUser(String email, String password)
    {
        boolean authenticated = false;
        try{
        	authenticated = FtpClient.authenticateUser(email, password);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!authenticated)
            return null;
        AccessPojo acPojo = new AccessPojo();
        acPojo.setEmail(email);
        String apiKey = generateApiKey(email, password);
        acPojo.setUserApiKey(apiKey);
        acPojo.setStatusCode(Constants.HTTP_CODE.OK.getCode());
//		user.setAuthorizationKey(apiKey);


//	DataAccessObject.updateRecord(user);
	return acPojo;
    }

    public static LogOutStatus logUserOut(String authorizationString)
    {
        Users user = DataAccessObject.getUserByAuthorizationKey(authorizationString);
        if(user != null)
        {
            user.setAuthorizationKey(null);
            DataAccessObject.updateRecord(user);
            return Constants.LogOutStatus.LOGOUT_SUCCESSFUL;
        } else
        {
            return Constants.LogOutStatus.LOGOUT_FAILED;
        }
    }

    public static String generateApiKey(String email, String password)
    {
        Timestamp timeStamp = new Timestamp((new Date()).getTime());
        String randomUUId = UUID.randomUUID().toString();
        return MD5Util.getMD5((new StringBuilder()).append(email).append(password).append(randomUUId).append(timeStamp).toString());
    }
}
