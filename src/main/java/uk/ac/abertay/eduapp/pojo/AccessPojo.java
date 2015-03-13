/**
 * 
 */
package uk.ac.abertay.eduapp.pojo;

/**
 * @author Sam Okide
 *
 */

public class AccessPojo {
    private int statusCode;
    private String userApiKey;
    private String email;
    private String accountType;
    
    public AccessPojo(){
	
    }
    
    public int getStatusCode() {
	return statusCode;
    }

    public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
    }
    
    public String getUserApiKey() {
	return userApiKey;
    }
    
    public void setUserApiKey(String userApiKey) {
	this.userApiKey = userApiKey;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getAccountType() {
	return accountType;
    }

    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }



}
