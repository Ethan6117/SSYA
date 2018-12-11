package com.empl.mgr.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.dao.SpecialScoreDao;
import com.empl.mgr.model.TeSpecialScore;
import com.empl.mgr.service.SpecialScoreService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.CompareUtil;

@Scope
@Service
@Transactional(readOnly = true)
public class SpecialScoreServiceImpl implements SpecialScoreService {

	@Autowired
	private SpecialScoreDao specialScoreDao;
	//@Autowired
	//private AccountDao accountDao;


	/*public JSONReturn findSpecialList(int page, String searchValue,String area,String state,String strTime,String endTime) {
		// TODO Auto-generated method stub
		List<SpecialListDto> dtoList = specialDao.findSpecialList(page, searchValue,area,state,strTime,endTime);
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");
		for (SpecialListDto dto : dtoList) {
			if (StringUtils.isEmpty(dto.getSj())) {
				dto.setSj("");
			}
			if (StringUtils.isEmpty(dto.getQd())) {
				dto.setQd("");
			}
			if (StringUtils.isEmpty(dto.getHd())) {
				dto.setHd("");
			}
			dto.setStrCompleteTime(DateTimeUtil.conversionTime(dto.getCompleteTime(), TimeFormatConstant.YYYY_MM_DD));
			dto.setStrStartTime(DateTimeUtil.conversionTime(dto.getStartTime(), TimeFormatConstant.YYYY_MM_DD));
			dto.setStrState(dto.setStateToStr(dto.getState()));
		}
	
		return JSONReturn.buildSuccess(dtoList);
	}
	public JSONReturn findSpecialCount(int page, String searchValue,String area,String state,String strTime,String endTime) {
		// TODO Auto-generated method stub
		int count = specialDao.findSpecialCount( searchValue,area,state,strTime,endTime);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.PAGE_LIST));
	}
	
	@Transactional
	public JSONReturn saveSpecialInfo(SpecialListDto dto, String acctName) {
		// TODO Auto-generated method stub
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("专题保存失败!");
		TeSpecial special=new TeSpecial();
		special.setSpecialName(dto.getSpecialName());
		special.setEditors(dto.getEditors());
		special.setArea(dto.getArea());
		special.setAddTime(DateTimeUtil.getCurrentTime());
		special.setStartTime(DateTimeUtil.stringToDate(dto.getStrStartTime(), "yyyy-MM-dd"));
		special.setWcmVersion(dto.getWcmVersion());
		special.setPageDesign(dto.getPageDesign());
		special.setPageStyle(dto.getPageStyle());
		special.setMainColors(dto.getMainColors());
		special.setFlashDesign(dto.getFlashDesign());
		special.setPicture(dto.getPicture());
		special.setServer(dto.getServer());
		special.setServerContent(dto.getServerContent());
		special.setEnclosure(dto.getEnclosure());
		special.setOther(dto.getOther());
		special.setState(0);
		specialDao.save(special);
		MailInfo mailinfo=new MailInfo();  
        //--设置邮件服务器开始  
        mailinfo.setMailServerHost("smtp.163.com");  
        mailinfo.setMailServerPort("25");  
        mailinfo.setValidate(true);  
        mailinfo.setUserName("ssyabgpt");  
        mailinfo.setPassword("ssyabgpt163com");  
        //--设置邮件服务器结束  
        mailinfo.setFromAddress("ssyabgpt@163.com");//邮件发送者的地址  
        //设置接受用户  
        String []ToAddress={"343785939@qq.com"}; 
        mailinfo.setToAddress(ToAddress);  
        mailinfo.setSubject(dto.getEditors()+"发布的新的专题需求");  
        mailinfo.setContent(dto.getEditors()+"发布了新的专题《"+dto.getSpecialName()+"》,请注意及时分配人员");//网页内容  
        SendMail sm=new SendMail();  
        sm.sendTextMail(mailinfo); 
		return JSONReturn.buildSuccess("添加成功!");
	}*/
	
	public JSONReturn findSpecialScoreById(Integer specId) {
		TeSpecialScore specialScore = specialScoreDao.findUniqueByProperty("speId", specId);
		if (CompareUtil.isEmpty(specialScore))
			return JSONReturn.buildFailure("获取源数据失败!");
		return JSONReturn.buildSuccess(specialScore);
	}

	@Transactional
	public Object saveSpecialScoreInfo(String specId, String score,
			String opinion, String infoimg) {
		TeSpecialScore specialScore=new TeSpecialScore();
		specialScore.setSpeId(Integer.valueOf(specId));
		specialScore.setScore(StringUtils.isNotBlank(score)?Integer.valueOf(score):0);
		specialScore.setOpinion(opinion);
		specialScore.setInfoimg(infoimg);
		specialScoreDao.save(specialScore);
		//return "提交成功";
		return JSONReturn.buildSuccess("提交成功");
	}
	
	/*@Transactional
	public JSONReturn modifySpecialInfo(SpecialListDto dto, String acctName) {
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("专题保存失败!");
		TeAccount teAccount = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, acctName);
		if (!acctName.equals("admin")&&!teAccount.getAcctNickname().equals(dto.getSpecialName())
				&&!teAccount.getAcctNickname().equals(dto.getSj())&&!teAccount.getAcctNickname().equals(dto.getQd())
				&&!teAccount.getAcctNickname().equals(dto.getHd())) {
			return JSONReturn.buildFailure("修改失败,没有权限!");
		}
		TeSpecial special=specialDao.findUniqueByProperty("id", dto.getId());
		special.setSpecialName(dto.getSpecialName());
		special.setEditors(dto.getEditors());
		special.setArea(dto.getArea());
		special.setAddTime(dto.getAddTime());
		special.setStartTime(DateTimeUtil.stringToDate(dto.getStrStartTime(), "yyyy-MM-dd"));
		special.setWcmVersion(dto.getWcmVersion());
		special.setPageDesign(dto.getPageDesign());
		special.setPageStyle(dto.getPageStyle());
		special.setMainColors(dto.getMainColors());
		special.setFlashDesign(dto.getFlashDesign());
		special.setPicture(dto.getPicture());
		special.setServer(dto.getServer());
		special.setServerContent(dto.getServerContent());
		special.setEnclosure(dto.getEnclosure());
		special.setOther(dto.getOther());
		special.setSj(dto.getSj());
		special.setQd(dto.getQd());
		special.setHd(dto.getHd());
		if (dto.getState()!=null) {
			special.setState(dto.getState());
		}
		if (dto.getSjTime()!=null) {
			special.setSjTime(dto.getSjTime());
		}
		if (dto.getQdTime()!=null) {
			special.setQdTime(dto.getQdTime());
		}
		if (dto.getHdTime()!=null) {
			special.setHdTime(dto.getHdTime());
		}
		if (StringUtils.isNotBlank(dto.getStrCompleteTime())) {
			special.setCompleteTime(DateTimeUtil.stringToDate(dto.getStrCompleteTime(), "yyyy-MM-dd"));
		}
		specialDao.save(special);
		if (special.getState()!=null&&special.getState()==2) {
			TeAccount account=accountDao.findUniqueByProperty(TeAccountField.ACCT_NICKNAME, special.getEditors());
			MailInfo mailinfo=new MailInfo();  
	        //--设置邮件服务器开始  
	        mailinfo.setMailServerHost("smtp.163.com");  
	        mailinfo.setMailServerPort("25");  
	        mailinfo.setValidate(true);  
	        mailinfo.setUserName("ssyabgpt");  
	        mailinfo.setPassword("ssyabgpt163com");  
	        //--设置邮件服务器结束  
	        mailinfo.setFromAddress("ssyabgpt@163.com");//邮件发送者的地址  
	        //设置接受用户  
	        String []ToAddress={"343785939@qq.com"}; 
	        mailinfo.setToAddress(ToAddress);  
	        mailinfo.setSubject("您发布的专题已完成");  
	        mailinfo.setContent("您发布的专题《"+dto.getSpecialName()+"》已完成,敬请登录平台打分及提出宝贵建议");//网页内容  
	        SendMail sm=new SendMail();  
	        sm.sendTextMail(mailinfo);
	        return JSONReturn.buildSuccess("修改成功");
		}else if (special.getState()==3) {
			if (teAccount.getAcctNickname().equals(dto.getSpecialName())) {
				
			}
			return JSONReturn.buildSuccess("3");
		}else {
			return JSONReturn.buildSuccess("修改成功");
		}
	}
	
	@Transactional
	public JSONReturn deleteSpecial(Integer specId, String acctName) {
		TeAccount teAccount = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, acctName);
		TeSpecial special=specialDao.findUniqueByProperty("id", specId);
		if (acctName.equals("admin")||StringUtils.isNotEmpty(teAccount.getAcctNickname())&&teAccount.getAcctNickname().equals(special.getEditors())) {
			specialDao.delete(special);
			return JSONReturn.buildSuccess("删除成功!");
		}else {
			return JSONReturn.buildFailure("没有删除权限!");
		}
		
	}*/
}
