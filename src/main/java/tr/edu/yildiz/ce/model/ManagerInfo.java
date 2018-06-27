package tr.edu.yildiz.ce.model;

public class ManagerInfo {
	private Integer id;
	private Integer userId;
	private Integer locationId;
	private UserInfo userInfo;
	private LocationInfo locationInfo;
	public ManagerInfo() {

	}
	public ManagerInfo(Integer id, Integer userId, Integer locationId) {
		this.id = id;
		this.userId = userId;
		this.locationId = locationId;
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
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	
}
