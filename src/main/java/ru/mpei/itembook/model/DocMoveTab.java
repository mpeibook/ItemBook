package ru.mpei.itembook.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DocMoveTab {
	private Integer id;
	private DocMove docMove;
	private RefItem refItem;
	private Integer quantity;
	
	public DocMoveTab() { }
	
	public DocMoveTab(DocMove docMove, RefItem refItem, Integer quantity) {
		this.docMove = docMove;
		this.refItem = refItem;
		this.quantity = quantity;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	public DocMove getDocMove() {
		return docMove;
	}
	
	public void setDocMove(DocMove docMove) {
		this.docMove = docMove;
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
