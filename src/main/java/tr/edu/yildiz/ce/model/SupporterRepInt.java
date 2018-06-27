package tr.edu.yildiz.ce.model;

public class SupporterRepInt {
	private UserInfo userInfo;
	private LocationInfo locationInfo;
	private SupportTypeInfo supportTypeInfo;
	private Integer comps;
	private long avgAwarenessTime;
    private long avgResponseTime;
	private Integer waitingAck;
	private Integer active;
	private Integer waitingChild;
	private Integer total;
	private Integer reported;
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
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	public SupportTypeInfo getSupportTypeInfo() {
		return supportTypeInfo;
	}
	public void setSupportTypeInfo(SupportTypeInfo supportTypeInfo) {
		this.supportTypeInfo = supportTypeInfo;
	}
	public Integer getComps() {
		return comps;
	}
	public void setComps(Integer comps) {
		this.comps = comps;
	}
	public SupporterRepInt(LocationInfo locationInfo, SupportTypeInfo supportTypeInfo, Integer comps) {
		super();
		this.locationInfo = locationInfo;
		this.supportTypeInfo = supportTypeInfo;
		this.comps = comps;
	}
	public SupporterRepInt() {
		super();
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
}
