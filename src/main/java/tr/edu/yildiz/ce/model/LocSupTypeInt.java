package tr.edu.yildiz.ce.model;

public class LocSupTypeInt {
	
	private LocationInfo locationInfo;
	private SupportTypeInfo supportTypeInfo;
	private Integer total;
	private Integer waitAsg;
	private Integer waitAck;
	private Integer waitChild;
	private Integer active;
	private Integer report;
	private Integer ended;
	private long avgAssignTime;
    private long avgAwarenessTime;
    private long avgResponseTime;
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
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getWaitAsg() {
		return waitAsg;
	}
	public void setWaitAsg(Integer waitAsg) {
		this.waitAsg = waitAsg;
	}
	public Integer getWaitAck() {
		return waitAck;
	}
	public void setWaitAck(Integer waitAck) {
		this.waitAck = waitAck;
	}
	public Integer getWaitChild() {
		return waitChild;
	}
	public void setWaitChild(Integer waitChild) {
		this.waitChild = waitChild;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getReport() {
		return report;
	}
	public void setReport(Integer report) {
		this.report = report;
	}
	public LocSupTypeInt(LocationInfo locationInfo, SupportTypeInfo supportTypeInfo, Integer total, Integer waitAsg,
			Integer waitAck, Integer waitChild, Integer active, Integer report) {
		super();
		this.locationInfo = locationInfo;
		this.supportTypeInfo = supportTypeInfo;
		this.total = total;
		this.waitAsg = waitAsg;
		this.waitAck = waitAck;
		this.waitChild = waitChild;
		this.active = active;
		this.report = report;
	}
	
	public LocSupTypeInt() {
		
	}
	public Integer getEnded() {
		return ended;
	}
	public void setEnded(Integer ended) {
		this.ended = ended;
	}
	public long getAvgAssignTime() {
		return avgAssignTime;
	}
	public void setAvgAssignTime(long avgAssignTime) {
		this.avgAssignTime = avgAssignTime;
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
	
}
