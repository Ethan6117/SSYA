package com.empl.mgr.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TeSpecial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "te_special")
public class TeSpecial implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String specialName;  //专题名称
	private String editors;      //策划编辑
	private String area;		 //区县
	private Date addTime;		 //提交时间
	private Date startTime;		 //规划上线时间
	private String wcmVersion;	 //wcm版本
	private Integer pageDesign;	 //页面设计(1.需要，2.不需要)
	private String pageStyle;	 //页面风格
	private String mainColors;	 //主色调
	private Integer flashDesign; //flash动画设计（1.需要，2.不需要）
	private String picture;		 //专题所需图片
	private Integer server;	 	 //功能程序开发（1.需要，2.不需要）
	private String serverContent;//功能开发内容
	private String enclosure;	 //附件
	private String other;		 //其他说明
	private String sj;			 //设计
	private String qd;			 //前端
	private String hd;			 //后端
	private float sjTime;		 //设计用时
	private float qdTime;		 //前端用时
	private float hdTime;		 //后端用时
	private Date completeTime;	 //实际完成时间
	private Integer state;		 //专题状态（0：申请1：进行中2：已完成3：关闭）

	// Constructors

	/** default constructor */
	public TeSpecial() {
	}

	public TeSpecial(Integer id, String specialName, String editors,
			String area, Date addTime, Date startTime, String wcmVersion,
			Integer pageDesign, String pageStyle, String mainColors,
			Integer flashDesign, String picture, Integer server,
			String serverContent, String enclosure, String other, String sj,
			String qd, String hd, float sjTime, float qdTime, float hdTime,
			Date completeTime, Integer state) {
		super();
		this.id = id;
		this.specialName = specialName;
		this.editors = editors;
		this.area = area;
		this.addTime = addTime;
		this.startTime = startTime;
		this.wcmVersion = wcmVersion;
		this.pageDesign = pageDesign;
		this.pageStyle = pageStyle;
		this.mainColors = mainColors;
		this.flashDesign = flashDesign;
		this.picture = picture;
		this.server = server;
		this.serverContent = serverContent;
		this.enclosure = enclosure;
		this.other = other;
		this.sj = sj;
		this.qd = qd;
		this.hd = hd;
		this.sjTime = sjTime;
		this.qdTime = qdTime;
		this.hdTime = hdTime;
		this.completeTime = completeTime;
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "TeSpecial [id=" + id + ", specialName=" + specialName
				+ ", editors=" + editors + ", area=" + area + ", addTime="
				+ addTime + ", startTime=" + startTime + ", wcmVersion="
				+ wcmVersion + ", pageDesign=" + pageDesign + ", pageStyle="
				+ pageStyle + ", mainColors=" + mainColors + ", flashDesign="
				+ flashDesign + ", picture=" + picture + ", server=" + server
				+ ", serverContent=" + serverContent + ", enclosure="
				+ enclosure + ", other=" + other + ", sj=" + sj + ", qd=" + qd
				+ ", hd=" + hd + ", sjTime=" + sjTime + ", qdTime=" + qdTime
				+ ", hdTime=" + hdTime + ", completeTime=" + completeTime
				+ ", state=" + state + "]";
	}
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "specialName", length = 50)
	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	@Column(name = "editors", length = 30)
	public String getEditors() {
		return editors;
	}

	public void setEditors(String editors) {
		this.editors = editors;
	}

	@Column(name = "area", length = 30)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "addTime")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "wcmVersion", length = 30)
	public String getWcmVersion() {
		return wcmVersion;
	}

	public void setWcmVersion(String wcmVersion) {
		this.wcmVersion = wcmVersion;
	}

	@Column(name = "pageDesign")
	public Integer getPageDesign() {
		return pageDesign;
	}

	public void setPageDesign(Integer pageDesign) {
		this.pageDesign = pageDesign;
	}

	@Column(name = "pageStyle", length = 200)
	public String getPageStyle() {
		return pageStyle;
	}

	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}

	@Column(name = "mainColors", length = 50)
	public String getMainColors() {
		return mainColors;
	}

	public void setMainColors(String mainColors) {
		this.mainColors = mainColors;
	}
	
	@Column(name = "flashDesign")
	public Integer getFlashDesign() {
		return flashDesign;
	}

	public void setFlashDesign(Integer flashDesign) {
		this.flashDesign = flashDesign;
	}

	@Column(name = "picture", length = 200)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Column(name = "server")
	public Integer getServer() {
		return server;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	@Column(name = "serverContent", length = 200)
	public String getServerContent() {
		return serverContent;
	}

	public void setServerContent(String serverContent) {
		this.serverContent = serverContent;
	}

	@Column(name = "enclosure", length = 100)
	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	@Column(name = "other", length = 500)
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Column(name = "sj", length = 200)
	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	@Column(name = "qd", length = 200)
	public String getQd() {
		return qd;
	}

	public void setQd(String qd) {
		this.qd = qd;
	}

	@Column(name = "hd", length = 200)
	public String getHd() {
		return hd;
	}

	public void setHd(String hd) {
		this.hd = hd;
	}

	@Column(name = "sjTime")
	public float getSjTime() {
		return sjTime;
	}

	public void setSjTime(float sjTime) {
		this.sjTime = sjTime;
	}

	@Column(name = "qdTime")
	public float getQdTime() {
		return qdTime;
	}

	public void setQdTime(float qdTime) {
		this.qdTime = qdTime;
	}

	@Column(name = "hdTime")
	public float getHdTime() {
		return hdTime;
	}

	public void setHdTime(float hdTime) {
		this.hdTime = hdTime;
	}

	@Column(name = "completeTime")
	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}