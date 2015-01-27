/**
 * 
 */
package uk.ac.abertay.eduapp.common;

/**
 * @author Sam Okide
 *
 */
public class Constants {
	public static final String APP_NAME = "/EduAppServer";
    public static final String APP_VERSION = "api-1.1";
    
    public static String AUTHORIZATION_KEY_TOKEN = "$MiL3";

    public static enum Actions {
	CREATE, DELETE, UPDATE, READ, SCAN;
    }
    
    public static enum SearchableEntity {
	Product("fn","description","brand"), 
	Property("key"), 
	Item("name","description"), 
	Identifer("key"); 

	private String[] searchableFields;

	private SearchableEntity(String ... searchableFields){
	    this.searchableFields = searchableFields;
	}
	
	public String[] getSearchableFields() {
	    return searchableFields;
	}

	public void setSearchableFields(String ... searchableFields) {
	    this.searchableFields = searchableFields;
	}
    }

    public static enum Entities {
	ITEM, PRODUCT, COLLECTION, PHOTO, PROPERTY, USERS;
    }

    public static enum Gender{
	MALE, FEMALE, OTHER;
    }
    
    public static enum DefaultApplication{
	DEVELOPER, WEB, MOBILE;
    }
    

    
    public static enum HTTP_CODE{
	CONTINUE(100),
	OK(200),
	CREATED(201),
	ACCEPTED(202),
	NO_CONTENT(204),
	BAD_REQUEST(400),
	UNAUTHORIZED(401),
	PAYMENT_REQUIRED(402),
	FORBIDDEN(403),
	NOT_FOUND(404),
	METHOD_NOT_ALLOWED(405),
	NOT_ACCEPTED(406), 
	PROXY_AUTHENTICATION_REQUIRED(407),
	REQUEST_TIME_OUT(408),
	CONFLICT(409),
	GONE(410),
	UNSUPPORTED_MEDIA_TYPE(415),
	INTERNAL_SERVER_ERROR(500),
	NOT_IMPLEMENTED(501),
	BAD_GATEWAY(502),
	SERVICE_UNAVAILABLE(503),
	GATEWAY_TIME_OUT(504),
	HTTP_VERSION_NOT_SUPPORTED(505);	
	
	private int code;	
	HTTP_CODE(int code){
	    this.setCode(code);
	}
	public int getCode() {
	    return code;
	}
	public void setCode(int code) {
	    this.code = code;
	}
    }
    
    public static enum LogOutStatus {
	LOGOUT_SUCCESSFUL("Logout ok!", HTTP_CODE.OK.getCode()), 
	LOGOUT_FAILED("Logout failed!", HTTP_CODE.NOT_ACCEPTED.getCode());
	private String status;
	private int code;
	LogOutStatus(String status, int code) {
	    this.status = status;
	    this.code = code;
	}
	LogOutStatus() {
	}
	public String getStatus() {
	    return status;
	}
	public int getCode(){
	    return code;
	}

    }
    
    public static enum Privileges{

	//User service privileges
	POST_USER("POST_USER", "Privilage for creating a User for a given business", true),
	
	//Search service privileges
	GET_SEARCH("GET_SEARCH", "Privilage for Searching", true);	
	
	private String code;
	private String description;
	private boolean requiresBusinessAuthorizationKey;

	
	Privileges(String code, String desciption, boolean requiresBusinessAuthorizationKey){
	    this.code = code;
	    this.description = desciption;
	    this.requiresBusinessAuthorizationKey = requiresBusinessAuthorizationKey;
	}
	
	public String getCode(){
	    return this.code;
	}
	public String getDescription(){
	    return this.description;
	}	
	public boolean isRequiresBusinessAuthorizationKey() {
	    return requiresBusinessAuthorizationKey;
	}
	public void setRequiresBusinessAuthorizationKey(boolean requiresBusinessAuthorizationKey) {
	    this.requiresBusinessAuthorizationKey = requiresBusinessAuthorizationKey;
	} 
    }
}
