package tr.edu.yildiz.ce.model;

import java.util.Date;

public class BanInfo {
	private Integer id;
	private Integer userId;
	private String explanation;
	private Date banTime;
	private Date endTime;
	private boolean banned;
	private Integer banDay;
	private UserInfo userInfo;
	public BanInfo() {
		
	}
	
	public BanInfo(Integer id, Integer userId, String explanation, Date banTime, Date endTime, boolean banned) {
		this.id = id;
		this.userId = userId;
		this.explanation = explanation;
		this.banTime = banTime;
		this.endTime = endTime;
		this.banned = banned;
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
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public Date getBanTime() {
		return banTime;
	}
	public void setBanTime(Date banTime) {
		this.banTime = banTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getBanDay() {
		return banDay;
	}

	public void setBanDay(Integer banDay) {
		this.banDay = banDay;
	}
}
