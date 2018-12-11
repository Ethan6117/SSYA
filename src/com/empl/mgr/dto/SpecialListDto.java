package com.empl.mgr.dto;

import java.io.Serializable;
import java.util.Date;

import com.empl.mgr.field.TeSpecialField;


public class SpecialListDto  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Integer id;
	private String specialName;  //专题名称
	private String editors;      //策划编辑
	private String area;		 //区县
	private Date addTime;		 //提交时间
	private String strAddTime;		 //提交时间
	private Date startTime;		 //规划上线时间
	private String strStartTime;		 //规划上线时间
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
	private Float sjTime;		 //设计用时
	private Float qdTime;		 //前端用时
	private Float hdTime;		 //后端用时
	private Date completeTime;	 //实际完成时间
	private String strCompleteTime;	 //实际完成时间
	private Integer state;		 //专题状态（0：申请1：进行中2：已完成3：关闭）
	private String strState;		 //专题状态（0：申请 1：进行中 2：已完成 3：关闭）
	private Integer month;
	private Double totalTime;
	
	public SpecialListDto(){
		
	}
	
	@Override
	public String toString() {
		return "SpecialListDto [id=" + id + ", specialName=" + specialName
				+ ", editors=" + editors + ", area=" + area + ", addTime="
				+ addTime + ", strAddTime=" + strAddTime + ", startTime="
				+ startTime + ", strStartTime=" + strStartTime
				+ ", wcmVersion=" + wcmVersion + ", pageDesign=" + pageDesign
				+ ", pageStyle=" + pageStyle + ", mainColors=" + mainColors
				+ ", flashDesign=" + flashDesign + ", picture=" + picture
				+ ", server=" + server + ", serverContent=" + serverContent
				+ ", enclosure=" + enclosure + ", other=" + other + ", sj="
				+ sj + ", qd=" + qd + ", hd=" + hd + ", sjTime=" + sjTime
				+ ", qdTime=" + qdTime + ", hdTime=" + hdTime
				+ ", completeTime=" + completeTime + ", strCompleteTime="
				+ strCompleteTime + ", state=" + state + ", strState="
				+ strState + "]";
	}
	
	public SpecialListDto(Integer id, String specialName, String editors,
			String area, Date addTime, Date startTime,Integer state,String sj,String qd,String hd,Float sjTime,Float qdTime,Float hdTime,Date completeTime) {
		super();
		this.id = id;
		this.specialName = specialName;
		this.editors = editors;
		this.area = area;
		this.addTime = addTime;
		this.startTime = startTime;
		this.state = state;
		this.sj=sj;
		this.qd=qd;
		this.hd=hd;
		this.sjTime = sjTime;
		this.qdTime = qdTime;
		this.hdTime = hdTime;
		this.completeTime = completeTime;
		
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSpecialName() {
		return specialName;
	}


	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}


	public String getEditors() {
		return editors;
	}


	public void setEditors(String editors) {
		this.editors = editors;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public String getWcmVersion() {
		return wcmVersion;
	}


	public void setWcmVersion(String wcmVersion) {
		this.wcmVersion = wcmVersion;
	}


	public Integer getPageDesign() {
		return pageDesign;
	}


	public void setPageDesign(Integer pageDesign) {
		this.pageDesign = pageDesign;
	}


	public String getPageStyle() {
		return pageStyle;
	}


	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}


	public String getMainColors() {
		return mainColors;
	}


	public void setMainColors(String mainColors) {
		this.mainColors = mainColors;
	}


	public Integer getFlashDesign() {
		return flashDesign;
	}


	public void setFlashDesign(Integer flashDesign) {
		this.flashDesign = flashDesign;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public Integer getServer() {
		return server;
	}


	public void setServer(Integer server) {
		this.server = server;
	}


	public String getServerContent() {
		return serverContent;
	}


	public void setServerContent(String serverContent) {
		this.serverContent = serverContent;
	}


	public String getEnclosure() {
		return enclosure;
	}


	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}


	public String getOther() {
		return other;
	}


	public void setOther(String other) {
		this.other = other;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getQd() {
		return qd;
	}

	public void setQd(String qd) {
		this.qd = qd;
	}

	public String getHd() {
		return hd;
	}

	public void setHd(String hd) {
		this.hd = hd;
	}

	public Float getSjTime() {
		return sjTime;
	}
	public void setSjTime(Float sjTime) {
		this.sjTime = sjTime;
	}
	public Float getQdTime() {
		return qdTime;
	}
	public void setQdTime(Float qdTime) {
		this.qdTime = qdTime;
	}
	public Float getHdTime() {
		return hdTime;
	}
	public void setHdTime(Float hdTime) {
		this.hdTime = hdTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}


	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}
	public String getStrAddTime() {
		return strAddTime;
	}
	public void setStrAddTime(String strAddTime) {
		this.strAddTime = strAddTime;
	}
	public String getStrStartTime() {
		return strStartTime;
	}
	public void setStrStartTime(String strStartTime) {
		this.strStartTime = strStartTime;
	}
	public String getStrCompleteTime() {
		return strCompleteTime;
	}
	public void setStrCompleteTime(String strCompleteTime) {
		this.strCompleteTime = strCompleteTime;
	}
	public String getStrState() {
		return strState;
	}
	public void setStrState(String strState) {
		this.strState = strState;
	}
	
	public Double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getMonth(){
		return month;
	}
	public void setMonth(Integer month){
		this.month=month;
	}
    
	public String setStateToStr(Integer state) {
		String stateString= TeSpecialField.STRSQ_STATE;
		switch(state){
          default:
        	  stateString= TeSpecialField.STRSQ_STATE;
              break;
          case TeSpecialField.SQ_STATE:
              stateString=TeSpecialField.STRSQ_STATE;
              break;
          case TeSpecialField.JX_STATE:
        	  stateString=TeSpecialField.STRJX_STATE;
              break;
          case TeSpecialField.WC_STATE:
        	  stateString=TeSpecialField.STRWC_STATE;
              break;
          case TeSpecialField.GB_STATE:
        	  stateString=TeSpecialField.STRGB_STATE;
              break;
          
      }
		return stateString;
	}





	public SpecialListDto(Integer id, String specialName, String editors,
			String area, Date addTime, String strAddTime, Date startTime,
			String strStartTime, String wcmVersion, Integer pageDesign,
			String pageStyle, String mainColors, Integer flashDesign,
			String picture, Integer server, String serverContent,
			String enclosure, String other, String sj, String qd, String hd,
			Float sjTime, Float qdTime, Float hdTime, Date completeTime,
			String strCompleteTime, Integer state, String strState) {
		super();
		this.id = id;
		this.specialName = specialName;
		this.editors = editors;
		this.area = area;
		this.addTime = addTime;
		this.strAddTime = strAddTime;
		this.startTime = startTime;
		this.strStartTime = strStartTime;
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
		this.strCompleteTime = strCompleteTime;
		this.state = state;
		this.strState = strState;
	}
	
	public SpecialListDto(Integer month,Double totalTime,String area){
		super();
		this.area=area;
		this.month=month;
		this.totalTime=totalTime;		
	}
}
