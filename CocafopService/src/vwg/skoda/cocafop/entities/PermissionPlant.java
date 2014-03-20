package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

@Entity
@Table(name="GZ38T_PERMISSIONS_PLANTS", schema="COCAFOP")
public class PermissionPlant implements Serializable {
	
	static Logger log = Logger.getLogger(Plant.class);
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	Integer id;
	
	
	@ManyToOne() 
	@JoinColumn(name="netUserName")
	UserApl userApl;
	
	@ManyToOne()
	@JoinColumn(name="ID_PLANT")
	Plant plant;
	
	@Column
	Boolean read = false;
	
	@Column
	Boolean write = false;

	@Column
	Boolean approve = false;

	@Column
	String uuser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date utime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public UserApl getUserApl() {
		return userApl;
	}

	public void setUserApl(UserApl userApl) {
		this.userApl = userApl;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getWrite() {
		return write;
	}

	public void setWrite(Boolean write) {
		this.write = write;
	}

	public Boolean getApprove() {
		return approve;
	}

	public void setApprove(Boolean approve) {
		this.approve = approve;
	}

	public String getUuser() {
		return uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}


}
