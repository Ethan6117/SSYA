package com.empl.mgr.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "te_special_design")
public class TeSpecialDesign {
	
	
	private Integer id;
	private Integer speId;
	private String imgdesc;
	private String designUrl;
	
	public TeSpecialDesign() {

	}
	
	public TeSpecialDesign(Integer id, Integer speId, String imgdesc,
			String designUrl) {
		super();
		this.id = id;
		this.speId = speId;
		this.imgdesc = imgdesc;
		this.designUrl = designUrl;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "speId",nullable = false)
	public Integer getSpeId() {
		return speId;
	}

	public void setSpeId(Integer speId) {
		this.speId = speId;
	}

	@Column(name = "imgdesc",length=200 )
	public String getImgdesc() {
		return imgdesc;
	}

	public void setImgdesc(String imgdesc) {
		this.imgdesc = imgdesc;
	}

	@Column(name = "designUrl",length=100 ,nullable = false)
	public String getDesignUrl() {
		return designUrl;
	}

	public void setDesignUrl(String designUrl) {
		this.designUrl = designUrl;
	}

	@Override
	public String toString() {
		return "TeSpecialDesign [id=" + id + ", speId=" + speId
				+ ", imgdesc=" + imgdesc + ", designUrl=" + designUrl
				+ "]";
	}
	
	
}
