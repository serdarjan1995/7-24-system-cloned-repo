package tr.edu.yildiz.ce.model;

import java.util.Date;

public class ActivationInfo {
	private Integer id;
	private String username;
	private String code;
	private Date recordDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public ActivationInfo(Integer id, String username, String code, Date recordDate) {
		super();
		this.id = id;
		this.username = username;
		this.code = code;
		this.recordDate = recordDate;
	}
	
	public ActivationInfo(){
		
	}
}
