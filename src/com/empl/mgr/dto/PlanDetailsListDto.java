package com.empl.mgr.dto;

import java.io.Serializable;

import com.empl.mgr.field.TeProjectField;

public class PlanDetailsListDto implements Serializable {

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
	private String strState;
	private String reason;
	private Integer weekday;
	private Float time;
	
	
	public PlanDetailsListDto() {
	}

	public PlanDetailsListDto(Integer id, String projectName, String districts, String task, String description,
			Integer planOrAdjust, String staff, Integer state, String reason, Integer weekday, Float time) {
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
	}
	
	@Override
	public String toString() {
		return "PlanDetailsListDto [id=" + id + ", projectName=" + projectName + ", districts=" + districts + ", task="
				+ task + ", description=" + description + ", planOrAdjust=" + planOrAdjust + ", staff=" + staff
				+ ", state=" + state + ", strState=" + strState + ", reason=" + reason + ", weekday=" + weekday
				+ ", time=" + time + "]";
	}
	
	public String setStateToStr(Integer state) {
		String stateString= TeProjectField.STRJX_STATE;
		switch(state){
          default:
        	  stateString=TeProjectField.STRBLANK_STATE;
              break;
          case TeProjectField.JX_STATE:
              stateString=TeProjectField.STRJX_STATE;
              break;
          case TeProjectField.WC_STATE:
        	  stateString=TeProjectField.STRWC_STATE;
              break;
          case TeProjectField.WWC_STATE:
        	  stateString=TeProjectField.STRWWC_STATE;
              break;
          case TeProjectField.BLANK_STATE:
        	  stateString=TeProjectField.STRBLANK_STATE;
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

	public String getDistricts() {
		return districts;
	}

	public void setDistricts(String districts) {
		this.districts = districts;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPlanOrAdjust() {
		return planOrAdjust;
	}

	public void setPlanOrAdjust(Integer planOrAdjust) {
		this.planOrAdjust = planOrAdjust;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public Float getTime() {
		return time;
	}

	public void setTime(Float time) {
		this.time = time;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

}
