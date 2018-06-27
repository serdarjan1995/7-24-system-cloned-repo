package tr.edu.yildiz.ce.model;
 
public class UserInfo {
	
	private Integer id;
	private String email;
    private String username;
    private String password;
    private String passwordConf;
    private boolean enabled;
    private boolean banned;
    
    private long avgAwarenessTime;
    private long avgResponseTime;
	private Integer waitingAck;
	private Integer active;
	private Integer waitingChild;
	private Integer total;
	private Integer reported;
	private Integer ended;
    public UserInfo() {
    	
    }
    
    public UserInfo(Integer id,String email,String username, String password, boolean enabled) {
    	this.id=id;
    	this.email=email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
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

	/**
	 * @return the ended
	 */
	public Integer getEnded() {
		return ended;
	}

	/**
	 * @param ended the ended to set
	 */
	public void setEnded(Integer ended) {
		this.ended = ended;
	}
    
    
 
}