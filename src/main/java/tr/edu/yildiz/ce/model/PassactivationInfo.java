package tr.edu.yildiz.ce.model;

public class PassactivationInfo {
	private Integer id;
	private Integer userId;
	private String code;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public PassactivationInfo(Integer id, Integer userId, String code) {
		super();
		this.id = id;
		this.userId = userId;
		this.code = code;
	}
	
	public PassactivationInfo() {
		
	}
}
