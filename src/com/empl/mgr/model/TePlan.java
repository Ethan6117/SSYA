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
@Table(name = "te_plan")
public class TePlan implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String projectName;
	private String manager;
	private String participants;
	private String lastWeekPercent;
	private String percent;
	private Integer state;
	private String progress;
	private Date estimatedTime;
	private Date completeTime;
	private Date addTime;
	private Date updateTime;
	private Integer deleteTag;
	private Integer date;
	
	public TePlan() {
		// TODO Auto-generated constructor stub
	}

	public TePlan(Integer id, String projectName, String manager, String participants, String lastWeekPercent,
			String percent, Integer state, String progress, Date estimateTime, Date completeTime, Date addTime,
			Date updateTime, Integer deleteTag, Integer date) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.manager = manager;
		this.participants = participants;
		this.lastWeekPercent = lastWeekPercent;
		this.percent = percent;
		this.state = state;
		this.progress = progress;
		this.estimatedTime = estimateTime;
		this.completeTime = completeTime;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.deleteTag = deleteTag;
		this.date = date;
	}

	@Override
	public String toString() {
		return "TeProjectPlan [id=" + id + ", projectName=" + projectName + ", manager=" + manager + ", participants="
				+ participants + ", lastWeekPercent=" + lastWeekPercent + ", Percent=" + percent + ", state=" + state
				+ ", progress=" + progress + ", estimatedTime=" + estimatedTime + ", completeTime=" + completeTime
				+ ", addTime=" + addTime + ", updateTime=" + updateTime + ", deleteTag=" + deleteTag + ", date=" + date + "]";
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


	@Column(name = "manager")
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Column(name = "lastWeekPercent")
	public String getLastWeekPercent() {
		return lastWeekPercent;
	}

	public void setLastWeekPercent(String lastWeekPercent) {
		this.lastWeekPercent = lastWeekPercent;
	}

	@Column(name = "percent")
	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Column(name = "progress")
	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	@Column(name = "completeTime")
	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	
	@Column(name = "AddTime")
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
	public Integer getdeleteTag() {
		return deleteTag;
	}

	public void setdeleteTag(Integer deleteTag) {
		this.deleteTag = deleteTag;
	}

	@Column(name = "participants")
	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	@Column(name = "estimatedTime")
	public Date getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(Date estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	@Column(name = "date")
	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}
}
