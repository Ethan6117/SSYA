package com.empl.mgr.dto;

import java.io.Serializable;
import java.util.Date;

import com.empl.mgr.field.TeProjectField;

public class PlanListDto implements Serializable {

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
	private String strState;
	private String strEstimatedTime;
	private String strCompleteTime;
	private Integer date;
	private Integer lastWeek;

	public PlanListDto() {
	}

	public PlanListDto(Integer id, String projectName, String manager, String participants, String lastWeekPercent,
			String percent, Integer state, String progress, Date estimatedTime, Date completeTime, Integer date) {
		this.id = id;
		this.projectName = projectName;
		this.manager = manager;
		this.participants = participants;
		this.lastWeekPercent = lastWeekPercent;
		this.percent = percent;
		this.state = state;
		this.progress = progress;
		this.estimatedTime = estimatedTime;
		this.completeTime = completeTime;
		this.date = date;
	}

	public String setStateToStr() {
		String stateString = TeProjectField.STRJX_STATE;
		switch (state==null? 0 : state) {
		default:
			stateString = TeProjectField.STRBLANK_STATE;
			break;
		case TeProjectField.JX_STATE:
			stateString = TeProjectField.STRJX_STATE;
			break;
		case TeProjectField.QD_STATE:
			stateString = TeProjectField.STRQD_STATE;
			break;
		case TeProjectField.DD_STATE:
			stateString = TeProjectField.STRDD_STATE;
			break;
		case TeProjectField.WC_STATE:
			stateString = TeProjectField.STRWC_STATE;
			break;
		case TeProjectField.BLANK_STATE:
			stateString = TeProjectField.STRBLANK_STATE;
			break;
		}
		return stateString;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getLastWeekPercent() {
		return lastWeekPercent;
	}

	public void setLastWeekPercent(String lastWeekPercent) {
		this.lastWeekPercent = lastWeekPercent;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Date getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(Date estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

	public String getStrEstimatedTime() {
		return strEstimatedTime;
	}

	public void setStrEstimatedTime(String strEstimatedTime) {
		this.strEstimatedTime = strEstimatedTime;
	}

	public String getStrCompleteTime() {
		return strCompleteTime;
	}

	public void setStrCompleteTime(String strCompleteTime) {
		this.strCompleteTime = strCompleteTime;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Integer getLastWeek() {
		return lastWeek;
	}

	public void setLastWeek(Integer lastWeek) {
		this.lastWeek = lastWeek;
	}

	@Override
	public String toString() {
		return "PlanListDto [id=" + id + ", projectName=" + projectName + ", manager=" + manager + ", participants="
				+ participants + ", lastWeekPercent=" + lastWeekPercent + ", percent=" + percent + ", progress="
				+ progress + ", strEstimatedTime=" + strEstimatedTime + ", strCompleteTime=" + strCompleteTime + ", strState="
				+ strState + ", date=" + date + "]";
	}

}
