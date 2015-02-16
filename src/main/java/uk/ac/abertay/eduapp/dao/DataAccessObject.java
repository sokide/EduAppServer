/**
 * 
 */
package uk.ac.abertay.eduapp.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.abertay.eduapp.common.Constants.Gender;
import uk.ac.abertay.eduapp.common.MD5Util;
import uk.ac.abertay.eduapp.mock.Application;
import uk.ac.abertay.eduapp.mock.EduAppService;
import uk.ac.abertay.eduapp.mock.EduAppServiceLocator;
import uk.ac.abertay.eduapp.mock.Privilege;
import uk.ac.abertay.eduapp.mock.Roles;
import uk.ac.abertay.eduapp.mock.ServiceLocator;
import uk.ac.abertay.eduapp.mock.Users;


/**
 * @author Sam Okide
 *
 */
public class DataAccessObject {
    private static Logger logger = LoggerFactory.getLogger(DataAccessObject.class);
	private static EduAppServiceLocator serviceLocator = ServiceLocator.getInstance();
	private static EduAppService sService = serviceLocator.getSmileService();
    
    public static void main (String [] args){
	logger.debug(">>>>>>> " + Gender.valueOf("male".toUpperCase()).name());
    } 
     
    public static Object createNewRecord(Object object){
        return sService.createNewRecord(object);         
    }
    
    public static void updateRecord(Object record){ 
    	sService.updateRecord(record);
    }
    
    public static void deleteRecord(Object object){
	sService.deleteRecord(object);
    }
    
    public static Object getRecordById(Class<?> entityClass, Long recordId){
        return sService.getRecordById(entityClass, recordId);
    }

    public static Collection<?> getAllRecords(Class<?> entityClass){
    	return sService.getAllRecords(entityClass);
    }
    
    public static boolean isDuplicateUserEmail(String email){
	Users user = (Users)sService.getUniqueRecordByHQL("SELECT u from Users u where u.email='" + email +"'"); 
	return  (user == null)? false : true;
    }


    public static Privilege getPrivilegeByName(String name){
	return (Privilege)sService.getUniqueRecordByHQL("SELECT p from Privilege p where p.name='" + name +"'"); 
    }

    public static Users getUserByEmailAndPassword(String email, String password){
	return (Users)sService.getUniqueRecordByHQL("SELECT u from Users u where u.email='" + email +"' and u.password='" + MD5Util.getMD5(password)+"' and u.enabled="+Boolean.TRUE); 
    }
    
	// public static Rights getRightsByRoleAndPrivilege(Roles role, String
	// privilege){
	// return
	// (Rights)sService.getUniqueRecordByHQL("SELECT r from Rights r where r.roles.id="
	// + role.getId() + " and r.privilege.code='"+privilege+"'");
	// }
    public static Users getBusinessAccountUserByBusinessId(Long businessId){
	return (Users)sService.getUniqueRecordByHQL("SELECT u from Users u where u.business.id=" + businessId +" and u.businessAccount="+Boolean.TRUE); 
    }
    
    public static Application getApplicationByAuthorizationKey(String authorizationKey) /*throws RecordNotFoundException*/{
	Application foundApplication =  (Application)sService.getUniqueRecordByHQL("SELECT a from Application a where a.authorizationKey='" + authorizationKey +"'"); 
//	if(foundApplication.getId() == null)
//	    throw new RecordNotFoundException("Invalid/no Application Authrisation key supplied!");	
	return foundApplication;
    }

    public static Users getUserByAuthorizationKey(String authorizationKey) /*throws UserNotFoundException*/{
	Users foundUser = (Users)sService.getUniqueRecordByHQL("SELECT u from Users u where u.authorizationKey='" + authorizationKey + "' and u.enabled="+Boolean.TRUE); 
//	if(foundUser.getId() == null)
//	    throw new UserNotFoundException("User with the supplied authrisation key was not found!");	
	return foundUser;
    }
        
    public static Collection<?> getPrivilegesByRole(Roles role){
	String hql = "select p.code from Privilege p where p.id in (select ri.privilege.id from Rights ri where ri.roles.id = "+role.getId()+")";
	return (Collection<?>)sService.getAllRecordsByHQL(hql);
    }
    
    
    public static Roles getUserRoleByAuthority(String authority){
	String hql = "SELECT u FROM Roles u WHERE u.authority = '" + authority + "'";
        return (Roles)sService.getUniqueRecordByHQL(hql);  
    }     
    
    public static void deleteRoleRights(Long roleId){ 
	String hql = "DELETE Rights r WHERE r.roles.id = :roleId";
	Map<String, Object> queryParams = new HashMap<String, Object>();
	queryParams.put("roleId", roleId);
	sService.executeHQLUpdate(hql, queryParams);
    }
}
