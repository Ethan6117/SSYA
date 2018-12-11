package com.empl.mgr.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="te_thematicReq")
public class TeThematicRequire implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long reqId;
	
	private String thematicName;
	
	private Date createTime;
	public TeThematicRequire(){}
	public TeThematicRequire(long reqId, String thematicName, Date createTime) {
		
		this.reqId = reqId;
		this.thematicName = thematicName;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TeThematicRequire [reqId=" + reqId + ", thematicName="
				+ thematicName + ", createTime=" + createTime + "]";
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public long getReqId() {
		return reqId;
	}

	public void setReqId(long reqId) {
		this.reqId = reqId;
	}
	@Column(name="thematicName",length = 40)
	public String getThematicName() {
		return thematicName;
	}

	public void setThematicName(String thematicName) {
		this.thematicName = thematicName;
	}
	@Version
	@Column(name="createTime",nullable = false, length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
