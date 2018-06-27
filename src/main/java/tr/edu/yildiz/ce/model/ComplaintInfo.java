package tr.edu.yildiz.ce.model;

import java.io.File;
import java.util.Date;



public class ComplaintInfo{
	private Integer id;
	private Integer locationId;
	private Integer supportTypeId;
	private Integer parentId;
	private Integer complainantUserId;
	private Date complaintTime;
	private Date assignTime;
	private String complaintText;
	private Integer supportUserId;
	private Date responseTime;
	private String responseText;
	private Integer childId;
	private boolean ended;
	private boolean ack;
	private boolean reported;
	private Date ackTime;
	private Byte[] photo;
	private File file;
	private LocationInfo locationInfo;
	private SupportTypeInfo supportTypeInfo;
	private ComplaintInfo parentInfo;
	private UserInfo complainantUserInfo;
	private UserInfo supportUserInfo;
	private ComplaintInfo childInfo;
	private long percentAssign;
	private long percentAck;
	private long percentResponse;
	private long percentReported;
	private byte[] complaintFile;
	private byte[] responseFile;
	
	public ComplaintInfo(){
		
	}

	public ComplaintInfo(Integer id,Integer locationId,Integer supportTypeId,Integer parentId,Integer complainantUserId,Date complaintTime,
			String complaintText,Integer supportUserId,Date responseTime,String responseText,Integer childId,boolean ended,Date ackTime,
			boolean ack,boolean reported,Date assignTime,byte[] complaintFile,byte[] responseFile){
		this.id=id;
		this.locationId=locationId;
		this.supportTypeId=supportTypeId;
		this.parentId=parentId;
		this.complainantUserId=complainantUserId;
		this.complaintTime=complaintTime;
		this.complaintText=complaintText;
		this.supportUserId=supportUserId;
		this.responseTime=responseTime;
		this.responseText=responseText;
		this.childId=childId;
		this.ended=ended;
		this.ack=ack;
		this.ackTime=ackTime;
		this.reported=reported;
		this.assignTime=assignTime;
		this.complaintFile=complaintFile;
		this.responseFile=responseFile;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public Integer getSupportTypeId() {
		return supportTypeId;
	}
	public void setSupportTypeId(Integer supportTypeId) {
		this.supportTypeId = supportTypeId;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getComplainantUserId() {
		return complainantUserId;
	}
	public void setComplainantUserId(Integer complainantUserId) {
		this.complainantUserId = complainantUserId;
	}
	
	public Date getComplaintTime() {
		return complaintTime;
	}
	public void setComplaintTime(Date complaintTime) {
		this.complaintTime = complaintTime;
	}
	
	public String getComplaintText() {
		return complaintText;
	}
	public void setComplaintText(String complaintText) {
		this.complaintText = complaintText;
	}
	

	public Integer getSupportUserId() {
		return supportUserId;
	}
	public void setSupportUserId(Integer supportUserId) {
		this.supportUserId = supportUserId;
	}
	
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public boolean isEnded() {
		return ended;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
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

	public ComplaintInfo getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(ComplaintInfo parentInfo) {
		this.parentInfo = parentInfo;
	}

	public UserInfo getComplainantUserInfo() {
		return complainantUserInfo;
	}

	public void setComplainantUserInfo(UserInfo complainantUserInfo) {
		this.complainantUserInfo = complainantUserInfo;
	}

	public UserInfo getSupportUserInfo() {
		return supportUserInfo;
	}

	public void setSupportUserInfo(UserInfo supportUserInfo) {
		this.supportUserInfo = supportUserInfo;
	}

	public ComplaintInfo getChildInfo() {
		return childInfo;
	}

	public void setChildInfo(ComplaintInfo childInfo) {
		this.childInfo = childInfo;
	}

	public Byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isAck() {
		return ack;
	}

	public void setAck(boolean ack) {
		this.ack = ack;
	}

	public Date getAckTime() {
		return ackTime;
	}

	public void setAckTime(Date ackTime) {
		this.ackTime = ackTime;
	}

	public boolean isReported() {
		return reported;
	}
	public void setReported(boolean reported) {
		this.reported = reported;
	}





	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public byte[] getComplaintFile() {
		return complaintFile;
	}

	public void setComplaintFile(byte[] complaintFile) {
		this.complaintFile = complaintFile;
	}

	public byte[] getResponseFile() {
		return responseFile;
	}

	public void setResponseFile(byte[] responseFile) {
		this.responseFile = responseFile;
	}

	public long getPercentAssign() {
		return percentAssign;
	}

	public void setPercentAssign(long percentAssign) {
		this.percentAssign = percentAssign;
	}

	public long getPercentAck() {
		return percentAck;
	}

	public void setPercentAck(long percentAck) {
		this.percentAck = percentAck;
	}

	public long getPercentResponse() {
		return percentResponse;
	}

	public void setPercentResponse(long percentResponse) {
		this.percentResponse = percentResponse;
	}

	public long getPercentReported() {
		return percentReported;
	}

	public void setPercentReported(long percentReported) {
		this.percentReported = percentReported;
	}

}
