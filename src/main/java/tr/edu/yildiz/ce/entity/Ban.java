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
@Table(name = "ban")
public class Ban {
	private Integer id;
	private Integer userId;
	private String explanation;
	private Date banTime;
	private Date endTime;
	private boolean banned;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "explanation", length = 255)
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	@Column(name = "ban_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBanTime() {
		return banTime;
	}
	public void setBanTime(Date banTime) {
		this.banTime = banTime;
	}
	
	@Column(name = "end_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "banned")
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	
}
