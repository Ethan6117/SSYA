package com.empl.mgr.dto;

import java.io.Serializable;

/**
 * 员工教育经历传输实体
 * . [K]
 * @author Kiro
 */
public class EmployeesEduDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long eduId;
	private String timestamp;
	private long emplNo;
	private String eduSchool;
	private String eduStartTime;//入学时间
	private String eduLeaveTime;//离开时间
	private String eduSchooling;//学历
	private String eduMajor;//专业

	public EmployeesEduDto() {
		// TODO Auto-generated constructor stub
	}

	public long getEduId() {
		return eduId;
	}

	public void setEduId(long eduId) {
		this.eduId = eduId;
	}

	

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public long getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(long emplNo) {
		this.emplNo = emplNo;
	}

	public String getEduSchool() {
		return eduSchool;
	}

	public void setEduSchool(String eduSchool) {
		this.eduSchool = eduSchool;
	}

	public String getEduStartTime() {
		return eduStartTime;
	}

	public void setEduStartTime(String eduStartTime) {
		this.eduStartTime = eduStartTime;
	}

	public String getEduLeaveTime() {
		return eduLeaveTime;
	}

	public void setEduLeaveTime(String eduLeaveTime) {
		this.eduLeaveTime = eduLeaveTime;
	}

	public String getEduSchooling() {
		return eduSchooling;
	}

	public void setEduSchooling(String eduSchooling) {
		this.eduSchooling = eduSchooling;
	}

	public String getEduMajor() {
		return eduMajor;
	}

	public void setEduMajor(String eduMajor) {
		this.eduMajor = eduMajor;
	}

	

}
