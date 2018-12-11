package com.empl.mgr.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "te_plan_overview")
public class TePlanOverview implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer year;
	private Integer weekOfYear;
	private String date;
	private Integer state;
	
	public TePlanOverview() {
	}

	public TePlanOverview(Integer id, Integer year, Integer weekOfYear, String date, Integer state) {
		super();
		this.id = id;
		this.year = year;
		this.weekOfYear = weekOfYear;
		this.date = date;
		this.state = state;
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

	@Column(name = "year")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "weekOfYear")
	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	@Column(name = "Date",length=50)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "TePlanOverview [id=" + id + ", year=" + year + ", weekOfYear=" + weekOfYear + ", date=" + date
				+ ", state=" + state + "]";
	}
	
	
}
