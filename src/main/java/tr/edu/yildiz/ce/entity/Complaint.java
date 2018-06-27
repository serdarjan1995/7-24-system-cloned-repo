package tr.edu.yildiz.ce.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "complaint")
public class Complaint {
	private Integer id;
	private Integer locationId;
	private Integer supportTypeId;
	private Integer parentId;
	private Integer complainantUserId;
	private byte[] complaintFile;
	private byte[] responseFile;
	
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "location_id")
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	@Column(name = "support_type_id")
	public Integer getSupportTypeId() {
		return supportTypeId;
	}
	public void setSupportTypeId(Integer supportTypeId) {
		this.supportTypeId = supportTypeId;
	}
	
	@Column(name = "parent_id")
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "complainant_user_id")
	public Integer getComplainantUserId() {
		return complainantUserId;
	}
	public void setComplainantUserId(Integer complainantUserId) {
		this.complainantUserId = complainantUserId;
	}
	

	@Column(name = "complaint_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getComplaintTime() {
		return complaintTime;
	}
	public void setComplaintTime(Date complaintTime) {
		this.complaintTime = complaintTime;
	}
	
	@Column(name = "complaint_text", length = 255)
	public String getComplaintText() {
		return complaintText;
	}
	public void setComplaintText(String complaintText) {
		this.complaintText = complaintText;
	}
	
	@Column(name = "support_user_id")
	public Integer getSupportUserId() {
		return supportUserId;
	}
	public void setSupportUserId(Integer supportUserId) {
		this.supportUserId = supportUserId;
	}
	
	@Column(name = "response_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
	@Column(name = "response_text", length = 255)
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	@Column(name = "child_id")
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	@Column(name = "ended")
	public boolean isEnded() {
		return ended;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
	@Column(name = "ack")
	public boolean isAck() {
		return ack;
	}
	public void setAck(boolean ack) {
		this.ack = ack;
	}
	
	@Column(name = "ack_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAckTime() {
		return ackTime;
	}
	public void setAckTime(Date ackTime) {
		this.ackTime = ackTime;
	}
	
	@Column(name = "reported")
	public boolean isReported() {
		return reported;
	}
	public void setReported(boolean reported) {
		this.reported = reported;
	}

	@Column(name = "assign_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	
	@Column(name = "complaint_file")
	public byte[] getComplaintFile() {
		return complaintFile;
	}
	public void setComplaintFile(byte[] complaintFile) {
		this.complaintFile = complaintFile;
	}
	
	@Column(name = "response_file")
	public byte[] getResponseFile() {
		return responseFile;
	}
	public void setResponseFile(byte[] responseFile) {
		this.responseFile = responseFile;
	}
	
}
