package vwg.skoda.cocafop.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GZ38T_MK_ORDER", schema = "COCAFOP")
public class MkOrder implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne()
	@JoinColumn(name = "ID_MODELKEY")
	ModelKey modelKey;
	
	@ManyToOne()
	@JoinColumn(name = "ID_ORDER")
	Order order;

	@OneToMany(mappedBy="mkOrder")
	Set<Bom> bom;

	@Id
	@GeneratedValue
	Integer id;
	
	@Column
	Boolean plantApprove = false;

	@Column
	Boolean brandApprove = false;

	@Column
	Integer mesic;

	@Column
	String uuser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	Date utime;

	public ModelKey getModelKey() {
		return modelKey;
	}

	public void setModelKey(ModelKey modelKey) {
		this.modelKey = modelKey;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Boolean getPlantApprove() {
		return plantApprove;
	}

	public void setPlantApprove(Boolean plantApprove) {
		this.plantApprove = plantApprove;
	}

	public Boolean getBrandApprove() {
		return brandApprove;
	}

	public void setBrandApprove(Boolean brandApprove) {
		this.brandApprove = brandApprove;
	}

	public Integer getMesic() {
		return mesic;
	}

	public void setMesic(Integer mesic) {
		this.mesic = mesic;
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

	public Set<Bom> getBom() {
		return bom;
	}

	public void setBom(Set<Bom> bom) {
		this.bom = bom;
	}
	

}
