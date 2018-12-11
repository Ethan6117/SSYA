package com.empl.mgr.dto;

import java.io.Serializable;

import com.empl.mgr.field.TeProjectField;

public class PlanOverviewListDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer year;
	private Integer weekOfYear;
	private String date;
	private Integer state;
	private String strState;
	
	public PlanOverviewListDto() {
	}

	public PlanOverviewListDto(Integer id, Integer year, Integer weekOfYear, String date, Integer state) {
		super();
		this.id = id;
		this.year = year;
		this.weekOfYear = weekOfYear;
		this.date = date;
		this.state = state;
	}

	public String setStateToStr(Integer state) {
		String stateString= TeProjectField.STRTJ_STATE;
		switch(state){
          default:
        	  stateString=TeProjectField.STRBLANK_STATE;
              break;
          case TeProjectField.TJ_STATE:
              stateString=TeProjectField.STRTJ_STATE;
              break;
          case TeProjectField.KXG_STATE:
        	  stateString=TeProjectField.STRKXG_STATE;
              break;
          case TeProjectField.CK_STATE:
        	  stateString=TeProjectField.STRCK_STATE;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

	@Override
	public String toString() {
		return "PlanOverviewListDto [id=" + id + ", year=" + year + ", weekOfYear=" + weekOfYear + ", date=" + date
				+ ", strState=" + strState + "]";
	}
}
