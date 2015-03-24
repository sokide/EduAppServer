/**
 * 
 */
package uk.ac.abertay.eduapp.pojo;

/**
 * @author Sam Okide
 *
 */
public class AuthenticationPojo {
    private String username;
    private String password;
    
    public AuthenticationPojo(String username, String password){
    	this.username = username;
    	this.password = password;
    }  
    
    public AuthenticationPojo(){
	
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
    
}
