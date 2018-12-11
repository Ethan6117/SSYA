package com.empl.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TeSpecialScore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "te_special_score")
public class TeSpecialScore implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer speId;
	private Integer score;
	private String opinion;
	private String infoimg;
	// Constructors

	/** default constructor */
	public TeSpecialScore() {
	}

	public TeSpecialScore(Integer id, Integer speId, Integer score,
			String opinion,String infoimg) {
		super();
		this.id = id;
		this.speId = speId;
		this.score = score;
		this.opinion = opinion;
		this.infoimg = infoimg;
	}

	@Override
	public String toString() {
		return "TeSpecialScore [id=" + id + ", speId=" + speId + ", score="
				+ score + ", opinion=" + opinion + ", infoimg=" + infoimg + "]";
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="speId",nullable = false)
	public Integer getSpeId() {
		return speId;
	}

	public void setSpeId(Integer speId) {
		this.speId = speId;
	}

	@Column(name="score")
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name="opinion",length=200)
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getInfoimg() {
		return infoimg;
	}

	public void setInfoimg(String infoimg) {
		this.infoimg = infoimg;
	}
}