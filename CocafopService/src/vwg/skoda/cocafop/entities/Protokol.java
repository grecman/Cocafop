package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

@Entity
@Table(name="GZ38T_PROTOKOL", schema="COCAFOP")
public class Protokol implements Serializable{

static Logger log = Logger.getLogger(Plant.class);
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	Integer id;	
	
	@Column
	String netUserName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date time;
	
	@Column
	String action;
	
	@Column
	String info;
	
	@Column
	String sessionId;

	public String getNetUserName() {
		return netUserName;
	}

	public void setNetUserName(String netUserName) {
		this.netUserName = netUserName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
			
}
