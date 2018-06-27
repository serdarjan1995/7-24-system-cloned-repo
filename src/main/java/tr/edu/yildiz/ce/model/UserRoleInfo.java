package tr.edu.yildiz.ce.model;



public class UserRoleInfo {
	private Integer id;
	private Integer userId;
	private String role;
	private UserInfo userInfo;
	private Integer locid;
	public UserRoleInfo(){
		
	}
	public UserRoleInfo(Integer id,Integer userId,String role){
		this.id=id;
		this.userId=userId;
		this.role=role;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Integer getLocid() {
		return locid;
	}
	public void setLocid(Integer locid) {
		this.locid = locid;
	}

	
}
