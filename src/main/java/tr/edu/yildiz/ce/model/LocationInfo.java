package tr.edu.yildiz.ce.model;

public class LocationInfo {
	
	private Integer id;
	private String description;
	private Integer parentId;
	private LocationInfo parent;
    private Integer waitingAssign;
	private Integer waitingAck;
	private Integer active;
	private Integer waitingChild;
	private Integer total;
	private Integer reported;
	private long avgAssignTime;
    private long avgAwarenessTime;
    private long avgResponseTime;
	public LocationInfo(){
		
	}
	public LocationInfo(Integer id,String description,Integer parentId){
		this.id=id;
		this.description=description;
		this.parentId=parentId;
	}	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public LocationInfo getParent() {
		return parent;
	}
	public void setParent(LocationInfo parent) {
		this.parent = parent;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getWaitingAssign() {
		return waitingAssign;
	}
	public void setWaitingAssign(Integer waitingAssign) {
		this.waitingAssign = waitingAssign;
	}
	public Integer getWaitingAck() {
		return waitingAck;
	}
	public void setWaitingAck(Integer waitingAck) {
		this.waitingAck = waitingAck;
	}
	public Integer getWaitingChild() {
		return waitingChild;
	}
	public void setWaitingChild(Integer waitingChild) {
		this.waitingChild = waitingChild;
	}
	/**
	 * @return the reported
	 */
	public Integer getReported() {
		return reported;
	}
	/**
	 * @param reported the reported to set
	 */
	public void setReported(Integer reported) {
		this.reported = reported;
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
