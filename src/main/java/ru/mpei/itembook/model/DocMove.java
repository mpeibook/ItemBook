package ru.mpei.itembook.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

@Entity
@NamedEntityGraph(name = "DocMove.docMoveTabList", attributeNodes = @NamedAttributeNode("docMoveTabList"))
public class DocMove {
	Integer id;
	LocalDate docDate;
	String docNum;
	RefPlace refPlaceFrom;
	RefPlace refPlaceTo;
	List<DocMoveTab> docMoveTabList;
	
	public DocMove() {	}
	
	public DocMove(LocalDate docDate, String docNum) {
		this.docDate = docDate;
		this.docNum = docNum;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDate getDocDate() {
		return docDate;
	}
	
	public void setDocDate(LocalDate docDate) {
		this.docDate = docDate;
	}
	
	public String getDocNum() {
		return docNum;
	}
	
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	
	@ManyToOne
	public RefPlace getRefPlaceFrom() {
		return refPlaceFrom;
	}
	
	public void setRefPlaceFrom(RefPlace refPlaceFrom) {
		this.refPlaceFrom = refPlaceFrom;
	}
	
	@ManyToOne
	public RefPlace getRefPlaceTo() {
		return refPlaceTo;
	}
	
	public void setRefPlaceTo(RefPlace refPlaceTo) {
		this.refPlaceTo = refPlaceTo;
	}
	
	@OneToMany(mappedBy = "docMove", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<DocMoveTab> getDocMoveTabList() {
		return docMoveTabList;
	}
	
	public void setDocMoveTabList(List<DocMoveTab> docMoveTabList) {
		this.docMoveTabList = docMoveTabList;
	}
}
