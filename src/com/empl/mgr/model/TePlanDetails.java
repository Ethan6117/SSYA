package com.empl.mgr.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "te_plan_Details")
public class TePlanDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String projectName;
	private String districts;
	private String task;
	private String description;
	private Integer planOrAdjust;
	private String staff;
	private Integer state;
	private String reason;
	private Integer weekday;
	private Float time;
	private Date addTime;
	private Date updateTime;
	private Integer deleteTag;
	private Integer weekOfYear;
	
	public TePlanDetails() {
	}

	public TePlanDetails(Integer id, String projectName, String districts, String task, String description,
			Integer planOrAdjust, String staff, Integer state, String reason, Integer weekday, Float time,
			Date addTime, Date updateTime, Integer deleteTag, Integer weekOfYear) {
		this.id = id;
		this.projectName = projectName;
		this.districts = districts;
		this.task = task;
		this.description = description;
		this.planOrAdjust = planOrAdjust;
		this.staff = staff;
		this.state = state;
		this.reason = reason;
		this.weekday = weekday;
		this.time = time;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.deleteTag = deleteTag;
		this.weekOfYear = weekOfYear;
	}

	@Override
	public String toString() {
		return "TePlanDetails [id=" + id + ", projectName=" + projectName + ", districts=" + districts + ", task="
				+ task + ", description=" + description + ", planOrAdjust=" + planOrAdjust + ", staff=" + staff
				+ ", state=" + state + ", reason=" + reason + ", weekday=" + weekday + ", time=" + time + ", addTime="
				+ addTime + ", updateTime=" + updateTime + ", deleteTag=" + deleteTag + ", weekOfYear=" + weekOfYear
				+ "]";
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
	
	@Column(name = "projectName")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "districts")
	public String getDistricts() {
		return districts;
	}

	public void setDistricts(String districts) {
		this.districts = districts;
	}

	@Column(name = "task")
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name = "planOrAdjust")
	public Integer getPlanOrAdjust() {
		return planOrAdjust;
	}

	public void setPlanOrAdjust(Integer planOrAdjust) {
		this.planOrAdjust = planOrAdjust;
	}


	@Column(name = "staff")
	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}


	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	@Column(name = "reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


	@Column(name = "weekday")
	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}


	@Column(name = "time")
	public Float getTime() {
		return time;
	}

	public void setTime(Float time) {
		this.time = time;
	}


	@Column(name = "addTime")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	@Column(name = "deleteTag")
	public Integer getDeleteTag() {
		return deleteTag;
	}

	public void setDeleteTag(Integer deleteTag) {
		this.deleteTag = deleteTag;
	}

	@Column(name = "weekOfYear")
	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}
}
