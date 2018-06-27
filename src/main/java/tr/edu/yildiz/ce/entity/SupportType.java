package tr.edu.yildiz.ce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "support_type")
public class SupportType {
	private Integer id;
	private String type;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "type", length = 45)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
