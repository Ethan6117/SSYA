package com.empl.mgr.dto;

import java.io.Serializable;
import java.util.Date;

import com.empl.mgr.field.TeProjectField;

/**
 * 项目列表展示传输实体类
 * . [K]
 * @author Kiro
 */
public class ProjectListDto implements Serializable {


	private static final long serialVersionUID = 1L;

	private Integer id; // 部门ID
	private Integer state;		 //状态(1、进行中2、跟进3、完成）
	private String strState;		//状态(1、进行中2、跟进3、完成）
	private String projectName;  //项目名称
	private String client; //客户名称
	private String partner;	 //合作方
	private String linkman;		 //客户联系人
	private String phone;        //客户电话
	private String manager;  //我方负责人
	private String participants; //我方项目参与人员
	private String domainName;		//访问方式
	private String serverAndLogin;
	private String path;
	private String summary;		 //项目概述
	private String boss;		 //主管领导
	private Date addTime;
	private Date updateTime;	
	private Integer deleteTag;

	public ProjectListDto() {
		// TODO Auto-generated constructor stub
	}
	
	public ProjectListDto(Integer id, Integer state, String projectName, String client, String partner, String linkman,
			String phone, String manager, String participants, String domainName, String serverAndLogin, String path,
			String summary, String boss) {
		super();
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
	}

	public ProjectListDto(String projectName) {
		this.projectName = projectName;
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
          case TeProjectField.GJ_STATE:
        	  stateString=TeProjectField.STRGJ_STATE;
              break;
          case TeProjectField.WC_STATE:
        	  stateString=TeProjectField.STRWC_STATE;
              break;
          case TeProjectField.ZF_STATE:
        	  stateString=TeProjectField.STRZF_STATE;
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

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getServerAndLogin() {
		return serverAndLogin;
	}

	public void setServerAndLogin(String login) {
		this.serverAndLogin = login;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDeleteTag() {
		return deleteTag;
	}

	public void setDeleteTag(Integer deleteTag) {
		this.deleteTag = deleteTag;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
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
		return "ProjectListDto [id=" + id + ", state=" + state + ", strState=" + strState + ", projectName="
				+ projectName + ", client=" + client + ", partner=" + partner + ", linkman=" + linkman + ", phone="
				+ phone + ", manager=" + manager + ", participants=" + participants + ", domainName=" + domainName
				+ ", login=" + serverAndLogin + ", path=" + path + ", summary=" + summary + ", boss=" + boss + ", addTime="
				+ addTime + ", updateTime=" + updateTime + ", deleteTag=" + deleteTag + "]";
	}
}
