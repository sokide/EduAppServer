/**
 * 
 */
package uk.ac.abertay.eduapp.pojo;

import java.util.HashMap;

import uk.ac.abertay.eduapp.mock.Users;

/**
 * @author Sam Okide
 *
 */
public class UserPojo {
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private String birthday;
    private String email;
    private String password;
    private String userRole;


    public UserPojo(){
	
    }
    
	public UserPojo(Users user, BirthdayPojo birthday) {
	 this.email = user.getEmail();
	 this.firstName = user.getFirstName();
	 this.lastName = user.getLastName();
	 this.gender = user.getGender();
	 this.userRole = user.getRoles().getAuthority();
	 this.password = "*****";
	 
	}
	
    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }
    
    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }


    public String getUserRole() {
	return userRole;
    }

    public void setUserRole(String userRole) {
	this.userRole = userRole;
    }


	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



}
