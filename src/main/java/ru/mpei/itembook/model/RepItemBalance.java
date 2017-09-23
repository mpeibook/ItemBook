package ru.mpei.itembook.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "rep_item_balance_v")
public class RepItemBalance {
	private Integer id;
	private RefPlace refPlace;
	private RefItem refItem;
	private Integer quantity;
	
	@Id
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	public RefPlace getRefPlace() {
		return refPlace;
	}
	
	public void setRefPlace(RefPlace refPlace) {
		this.refPlace = refPlace;
	}
	
	@ManyToOne
	public RefItem getRefItem() {
		return refItem;
	}
	
	public void setRefItem(RefItem refItem) {
		this.refItem = refItem;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
