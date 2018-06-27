package tr.edu.yildiz.ce.model;

public class SupporterInfo {
	private Integer id;
	private Integer userId;
	private Integer supportTypeId;
	private Integer locationId;
	private UserInfo userInfo;
	private SupportTypeInfo supportTypeInfo;
	private LocationInfo locationInfo;
	private long avgAwarenessTime;
    private long avgResponseTime;
	private Integer waitingAck;
	private Integer active;
	private Integer waitingChild;
	private Integer total;
	private Integer ended;
	private Integer reported;

	
	public SupporterInfo(){
		
	}
	public SupporterInfo(Integer id,Integer userId,Integer supportTypeId,Integer locationId){
		this.id=id;
		this.userId=userId;
		this.supportTypeId=supportTypeId;
		this.locationId=locationId;
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
	
	public Integer getSupportTypeId() {
		return supportTypeId;
	}
	public void setSupportTypeId(Integer supportTypeId) {
		this.supportTypeId = supportTypeId;
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
	public SupportTypeInfo getSupportTypeInfo() {
		return supportTypeInfo;
	}
	public void setSupportTypeInfo(SupportTypeInfo supportTypeInfo) {
		this.supportTypeInfo = supportTypeInfo;
	}
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	public long getAvgAwarenessTime() {
		return avgAwarenessTime;
	}
	public void setAvgAwarenessTime(long avgAwarenessTime) {
		this.avgAwarenessTime = avgAwarenessTime;
	}
	public long getAvgResponseTime() {
		return avgResponseTime;
	}
	public void setAvgResponseTime(long avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}
	public Integer getWaitingAck() {
		return waitingAck;
	}
	public void setWaitingAck(Integer waitingAck) {
		this.waitingAck = waitingAck;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getWaitingChild() {
		return waitingChild;
	}
	public void setWaitingChild(Integer waitingChild) {
		this.waitingChild = waitingChild;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getReported() {
		return reported;
	}
	public void setReported(Integer reported) {
		this.reported = reported;
	}
	public Integer getEnded() {
		return ended;
	}
	public void setEnded(Integer ended) {
		this.ended = ended;
	}
	


}
