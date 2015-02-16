/**
 * 
 */
package uk.ac.abertay.eduapp.pojo;

import uk.ac.abertay.eduapp.mock.Application;
import uk.ac.abertay.eduapp.mock.Users;

/**
 * @author Sam Okide
 *
 */
public class ApplicationStatePojo {
	private Application currentApplication;
	private Users currentUser;
	private ExceptionPojo exceptionPojo;
	private Boolean authorizationStatus;

	public Application getCurrentApplication() {
		return currentApplication;
	}

	public void setCurrentApplication(Application currentApplication) {
		this.currentApplication = currentApplication;
	}

	public Users getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}

	public ExceptionPojo getExceptionPojo() {
		return exceptionPojo;
	}

	public void setExceptionPojo(ExceptionPojo exceptionPojo) {
		this.exceptionPojo = exceptionPojo;
	}

	public Boolean getAuthorizationStatus() {
		return authorizationStatus;
	}

	public void setAuthorizationStatus(Boolean authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

}