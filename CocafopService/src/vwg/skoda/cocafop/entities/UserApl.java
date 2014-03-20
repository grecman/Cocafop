package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

@Entity
@Table(name="GZ38T_USER", schema="COCAFOP")
public class UserApl implements Serializable {
	
	static Logger log = Logger.getLogger(Plant.class);
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	String netUserName;
	
	@OneToMany(mappedBy="userApl")
	Set<PermissionPlant> permissionPlant;

	@OneToMany(mappedBy="userApl")
	Set<PermissionBrand> permissionBrand; 
	
	@Column
	String uuser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date utime;

	public String getNetUserName() {
		return netUserName;
	}

	public void setNetUserName(String netUserName) {
		//this.netUserName = netUserName;
		this.netUserName = netUserName == null ? null : netUserName.toUpperCase();
	}

	public Set<PermissionPlant> getPermissionPlant() {
		return permissionPlant;
	}

	public void setPermissionPlant(Set<PermissionPlant> permissionPlant) {
		this.permissionPlant = permissionPlant;
	}

	public Set<PermissionBrand> getPermissionBrand() {
		return permissionBrand;
	}

	public void setPermissionBrand(Set<PermissionBrand> permissionBrand) {
		this.permissionBrand = permissionBrand;
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
