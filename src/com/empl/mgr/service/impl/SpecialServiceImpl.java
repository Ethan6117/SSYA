package com.empl.mgr.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.constant.TimeFormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dao.SpecialDao;
import com.empl.mgr.dao.SpecialDesignDao;
import com.empl.mgr.dao.SpecialScoreDao;
import com.empl.mgr.dto.SpecialListDto;
import com.empl.mgr.field.TeAccountField;
import com.empl.mgr.model.TeAccount;
import com.empl.mgr.model.TeSpecial;
import com.empl.mgr.model.TeSpecialDesign;
import com.empl.mgr.model.TeSpecialScore;
import com.empl.mgr.service.SpecialService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.CompareUtil;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.MailInfo;
import com.empl.mgr.utils.PageUtils;
import com.empl.mgr.utils.SendMail;

@Scope
@Service
@Transactional(readOnly = true)
public class SpecialServiceImpl implements SpecialService {

	@Autowired
	private SpecialDao specialDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private SpecialScoreDao specialScoreDao;
	@Autowired
	private SpecialDesignDao specialDesignDao;

	public JSONReturn exportExcel(String searchValue, String area, String state, String strTime, String endTime,
			HttpServletResponse response) {
		List<SpecialListDto> dtoList = specialDao.findSpecialList(searchValue, area, state, strTime, endTime);
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

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("专题列表");
		sheet.setDefaultColumnWidth(15);
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		XSSFRow row = sheet.createRow(0);
		String headers[] = { "编号", "专题名称", "策划编辑", "区县", "计划上线时间", "实际完成时间", "参与人员", "设计用时", "前端用时", "后端用时", "总用时",
				"状态" };
		for (short i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		Iterator<SpecialListDto> it = dtoList.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			SpecialListDto t = (SpecialListDto) it.next();
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(t.getId().toString());
			cell = row.createCell(1);
			cell.setCellValue(t.getSpecialName().toString());
			cell = row.createCell(2);
			cell.setCellValue(t.getEditors().toString());
			cell = row.createCell(3);
			cell.setCellValue(t.getArea().toString());
			cell = row.createCell(4);
			cell.setCellValue(t.getStrStartTime().toString());
			cell = row.createCell(5);
			cell.setCellValue(t.getStrCompleteTime().toString());
			cell = row.createCell(6);
			cell.setCellValue(t.getSj().toString() + " " + t.getQd().toString() + " " + t.getHd().toString());
			cell = row.createCell(7);
			cell.setCellValue(t.getSjTime().toString());
			cell = row.createCell(8);
			cell.setCellValue(t.getQdTime().toString());
			cell = row.createCell(9);
			cell.setCellValue(t.getHdTime().toString());
			Float sum = Float.parseFloat(t.getSjTime().toString()) + Float.parseFloat(t.getQdTime().toString())
					+ Float.parseFloat(t.getHdTime().toString());
			cell = row.createCell(10);
			cell.setCellValue(sum);
			cell = row.createCell(11);
			cell.setCellValue(t.getStrState().toString());
		}
		try {
			String filename = java.net.URLEncoder.encode("专题列表.xlsx", "utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			OutputStream download = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			workbook.write(download);
			download.flush();
			download.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JSONReturn.buildSuccess("导出成功！");
	}

	public JSONReturn findSpecialTable(String addTime, String state) {
		List<SpecialListDto> dtoList = specialDao.findSpecialTable(addTime, state);
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");
		return JSONReturn.buildSuccess(dtoList);
	}

	public JSONReturn findSpecialList(int page, String searchValue, String area, String state, String strTime,
			String endTime) {
		// TODO Auto-generated method stub
		List<SpecialListDto> dtoList = specialDao.findSpecialList(page, searchValue, area, state, strTime, endTime);
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

	public JSONReturn findSpecialCount(int page, String searchValue, String area, String state, String strTime,
			String endTime) {
		// TODO Auto-generated method stub
		int count = specialDao.findSpecialCount(searchValue, area, state, strTime, endTime);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.PAGE_LIST));
	}

	@Transactional
	public JSONReturn saveSpecialInfo(SpecialListDto dto, String acctName) {
		// TODO Auto-generated method stub
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("专题保存失败!");
		TeSpecial special = new TeSpecial();
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
		MailInfo mailinfo = new MailInfo();
		// --设置邮件服务器开始
		mailinfo.setMailServerHost("smtp.163.com");
		mailinfo.setMailServerPort("25");
		mailinfo.setValidate(true);
		mailinfo.setUserName("ssyabgpt");
		mailinfo.setPassword("ssyabgpt163com");
		// --设置邮件服务器结束
		mailinfo.setFromAddress("ssyabgpt@163.com");// 邮件发送者的地址
		// 设置接受用户
		String[] ToAddress = { "904123287@qq.com", "1027762986@qq.com", "228615852@qq.com" };
		mailinfo.setToAddress(ToAddress);
		mailinfo.setSubject(dto.getEditors() + "发布的新的专题需求");
		mailinfo.setContent(dto.getEditors() + "发布了新的专题《" + dto.getSpecialName() + "》,请注意及时分配人员");// 网页内容
		SendMail sm = new SendMail();
		sm.sendTextMail(mailinfo);
		return JSONReturn.buildSuccess("添加成功!");
	}

	public JSONReturn findSpecialById(Integer specId) {
		TeSpecial special = specialDao.findUniqueByProperty("id", specId);
		if (CompareUtil.isEmpty(special))
			return JSONReturn.buildFailure("获取源数据失败!");
		return JSONReturn.buildSuccess(special);
	}

	@Transactional
	public JSONReturn modifySpecialInfo(SpecialListDto dto, String acctName) {
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("专题保存失败!");
		TeAccount teAccount = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, acctName);
		if (!acctName.equals("admin") && !teAccount.getAcctNickname().equals(dto.getEditors())
				&& !teAccount.getAcctNickname().equals(dto.getSj()) && !teAccount.getAcctNickname().equals(dto.getQd())
				&& !teAccount.getAcctNickname().equals(dto.getHd()) && teAccount.getPosition() != 2l
				&& teAccount.getPosition() != 3l) {
			return JSONReturn.buildFailure("修改失败,没有权限!");
		}
		TeSpecial special = specialDao.findUniqueByProperty("id", dto.getId());
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
		if (dto.getState() != null) {
			special.setState(dto.getState());
		}
		if (dto.getSjTime() != null) {
			special.setSjTime(dto.getSjTime());
		}
		if (dto.getQdTime() != null) {
			special.setQdTime(dto.getQdTime());
		}
		if (dto.getHdTime() != null) {
			special.setHdTime(dto.getHdTime());
		}
		if (StringUtils.isNotBlank(dto.getStrCompleteTime())) {
			special.setCompleteTime(DateTimeUtil.stringToDate(dto.getStrCompleteTime(), "yyyy-MM-dd"));
		}
		specialDao.save(special);
		if (special.getState() != null && special.getState() == 2) {
			MailInfo mailinfo = new MailInfo();
			// --设置邮件服务器开始
			mailinfo.setMailServerHost("smtp.163.com");
			mailinfo.setMailServerPort("25");
			mailinfo.setValidate(true);
			mailinfo.setUserName("ssyabgpt");
			mailinfo.setPassword("ssyabgpt163com");
			// --设置邮件服务器结束
			mailinfo.setFromAddress("ssyabgpt@163.com");// 邮件发送者的地址
			// 设置接受用户
			String[] ToAddress = { teAccount.getEmail() };
			mailinfo.setToAddress(ToAddress);
			mailinfo.setSubject("您发布的专题已完成");
			mailinfo.setContent("您发布的专题《" + dto.getSpecialName() + "》已完成,敬请登录平台打分及提出宝贵建议");// 网页内容
			SendMail sm = new SendMail();
			sm.sendTextMail(mailinfo);
			return JSONReturn.buildSuccess("修改成功");
		} else if (special.getState() == 3) {
			if (teAccount.getAcctNickname().equals(dto.getEditors())) {
				TeSpecialScore score = specialScoreDao.findUniqueByProperty("speId", special.getId());
				if (score == null) {
					return JSONReturn.buildSuccess(special.getId());
				}
			}
			return JSONReturn.buildSuccess("修改成功");
		} else {
			return JSONReturn.buildSuccess("修改成功");
		}
	}

	@Transactional
	public JSONReturn deleteSpecial(Integer specId, String acctName) {
		TeAccount teAccount = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, acctName);
		TeSpecial special = specialDao.findUniqueByProperty("id", specId);
		if (acctName.equals("admin") || StringUtils.isNotEmpty(teAccount.getAcctNickname())
				&& teAccount.getAcctNickname().equals(special.getEditors())) {
			specialDao.delete(special);
			return JSONReturn.buildSuccess("删除成功!");
		} else {
			return JSONReturn.buildFailure("没有删除权限!");
		}

	}

	@Transactional
	public JSONReturn saveSpecialDesgin(Integer speId, String url, String imgdesc) {
		TeSpecialDesign specialDesign = new TeSpecialDesign();
		specialDesign.setSpeId(speId);
		specialDesign.setDesignUrl(url);
		specialDesign.setImgdesc(imgdesc);
		specialDesignDao.save(specialDesign);
		return JSONReturn.buildSuccess("");
	}

	public List<TeSpecialDesign> findSpecialDesignsById(Integer speId) {
		return specialDesignDao.findByProperty("speId", speId);
	}
}
