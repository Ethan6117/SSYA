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
@Table(name = "te_project")
public class TeProject implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer state;			//状态(1、进行中2、跟进3、完成）
	private String projectName; 	//项目名称
	private String client;			//客户名称
	private String partner;			//合作方
	private String linkman;			//客户联系人
	private String phone;       	//客户电话
	private String manager;  		//我方负责人
	private String participants;	//我方项目参与人员
	private String domainName;		//访问方式
	private String serverAndLogin;
	private String path;
	private String summary;			//项目概述
	private String boss;			//主管领导
	private Date addTime;
	private Date updateTime;	
	private Integer deleteTag;
	
	public TeProject() {
		
	}

	public TeProject(Integer id, Integer state, String projectName, String client, String partner, String linkman,
			String phone, String manager, String participants, String domainName, String serverAndLogin, String path,
			String summary, String boss, Date addTime, Date updateTime, Integer deleteTag) {
		this.id = id;
		this.state = state;
		this.projectName = projectName;
		this.client = client;
		this.partner = partner;
		this.linkman = linkman;
		this.phone = phone;
		this.manager = manager;
		this.participants = participants;
		this.domainName = domainName;
		this.serverAndLogin = serverAndLogin;
		this.path = path;
		this.summary = summary;
		this.boss = boss;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.deleteTag = deleteTag;
	}

	@Override
	public String toString() {
		return "TeProject [id=" + id + ", state=" + state + ", projectName=" + projectName + ", client=" + client
				+ ", partner=" + partner + ", linkman=" + linkman + ", phone=" + phone + ", manager=" + manager
				+ ", participants=" + participants + ", domainName=" + domainName + ", serverAndLogin=" + serverAndLogin
				+ ", path=" + path + ", summary=" + summary + ", boss=" + boss + ", addTime=" + addTime
				+ ", updateTime=" + updateTime + ", deleteTag=" + deleteTag + "]";
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

	@Column(name = "projectName", length = 100)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "client", length = 50)
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Column(name = "partner", length = 50)
	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}


	@Column(name = "manager", length = 30)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Column(name = "domainName", length = 200)
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}


	@Column(name = "serverAndLogin", length = 100)
	public String getServerAndLogin() {
		return serverAndLogin;
	}

	public void setServerAndLogin(String login) {
		this.serverAndLogin = login;
	}

	@Column(name = "path", length = 200)
	public String getPath() {
		return path;
	}

	
	public void setPath(String path) {
		this.path = path;
	}

	
	@Column(name = "linkman", length = 30)
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "phone", length = 30)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "participants", length = 100)
	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	@Column(name = "summary", length = 500)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "boss", length = 100)
	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
}
