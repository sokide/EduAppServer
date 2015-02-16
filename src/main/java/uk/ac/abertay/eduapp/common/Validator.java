/**
 * 
 */
package uk.ac.abertay.eduapp.common;

/**
 * @author Sam Okide
 *
 */
public class Validator {
    
    public static boolean validateAllMandatoryStringParametersIsNotNull(String ... args){
	for(String arg : args)
	    if(arg == null)
		throw new IllegalArgumentException("One of the mandatory parameters was not supplied!");
	return true;
    }
    
    public static boolean validateNotNullorEmptyString(String param){
	if(param == null || param == "")
	    throw new IllegalArgumentException("Null or empty empty string argument supplied!");
	return true;
    }
}
