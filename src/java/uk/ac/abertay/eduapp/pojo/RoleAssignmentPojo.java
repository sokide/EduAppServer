/**
 * 
 */
package uk.ac.abertay.eduapp.pojo;

/**
 * @author Sam Okide
 *
 */
public class RoleAssignmentPojo {

    private Long userId;
    private Long roleId;
    
    public RoleAssignmentPojo(){}
    
    public Long getUserId() {
	return userId;
    }
    public void setUserId(Long userId) {
	this.userId = userId;
    }
    public Long getRoleId() {
	return roleId;
    }
    public void setRoleId(Long roleId) {
	this.roleId = roleId;
    }
}
