package com.empl.mgr.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * TeEmployeesCompany entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "te_employees_edu")
public class TeEmployeesEdu implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long eduId;
	private Date timestamp;
	private long emplNo;
	private String eduSchool;
	private String eduStartTime;//入学时间
	private String eduLeaveTime;//离开时间
	private String eduSchooling;//学历
	private String eduMajor;//专业

	// Constructors

	

	/** default constructor */
	public TeEmployeesEdu() {
	}
    @Id
    @GeneratedValue(strategy = IDENTITY)
	@Column(name = "eduId", unique = true, nullable = false)
	public long getEduId() {
		return eduId;
	}

	public void setEduId(long eduId) {
		this.eduId = eduId;
	}
	@Version
	@Column(name = "timestamp", nullable = false, length = 19)
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
   @Column
	public long getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(long emplNo) {
		this.emplNo = emplNo;
	}
	@Column(length=200)
	public String getEduSchool() {
		return eduSchool;
	}

	public void setEduSchool(String eduSchool) {
		this.eduSchool = eduSchool;
	}
	@Column(length=30)
	public String getEduStartTime() {
		return eduStartTime;
	}

	public void setEduStartTime(String eduStartTime) {
		this.eduStartTime = eduStartTime;
	}
	@Column(length=30)
	public String getEduLeaveTime() {
		return eduLeaveTime;
	}

	public void setEduLeaveTime(String eduLeaveTime) {
		this.eduLeaveTime = eduLeaveTime;
	}
	@Column(length=50)
	public String getEduSchooling() {
		return eduSchooling;
	}

	public void setEduSchooling(String eduSchooling) {
		this.eduSchooling = eduSchooling;
	}
	@Column(length=50)
	public String getEduMajor() {
		return eduMajor;
	}

	public void setEduMajor(String eduMajor) {
		this.eduMajor = eduMajor;
	}
	
	@Override
	public String toString() {
		return "TeEmployeesEdu [eduId=" + eduId + ", timestamp=" + timestamp
				+ ", emplNo=" + emplNo + ", eduSchool=" + eduSchool
				+ ", eduStartTime=" + eduStartTime + ", eduLeaveTime="
				+ eduLeaveTime + ", eduSchooling=" + eduSchooling
				+ ", eduMajor=" + eduMajor + "]";
	}

	

}