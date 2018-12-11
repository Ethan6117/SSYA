package com.empl.mgr.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.empl.mgr.annotation.SecureValid;
import com.empl.mgr.constant.MethodType;
import com.empl.mgr.controller.support.AbstractController;
import com.empl.mgr.service.SpecialScoreService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "specialScore")
public class SpecialScoreController extends AbstractController {

	@Autowired
	private SpecialScoreService scoreService;
	
	@ResponseBody
	@RequestMapping(value="saveSpecialscoreInfo")
	public Object saveSpecialscoreInfo(@RequestParam String specId,@RequestParam String score,@RequestParam String opinion,
			@RequestParam(value = "infoimg", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String url="";
		if (file.getSize()>0) {
			String path = request.getSession().getServletContext().getRealPath("upload"); 
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			if (!type.equals(".jpg")&&!type.equals(".png")) {
				return JSONReturn.buildSuccess("格式不正确");
			}
	        String fileName = new Date().getTime()+type;  
	        File targetFile = new File(path, fileName);  
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }  
	        //保存  
	        try {  
	            file.transferTo(targetFile);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }
	        url=request.getContextPath()+"\\upload\\"+fileName;
		}
        return scoreService.saveSpecialScoreInfo(specId, score, opinion, url);
	}

	/*@ResponseBody
	@RequestMapping(value = "findSpecialList")
	@SecureValid(code = "02001", desc = "获取专题列表", type = MethodType.FIND)
	public JSONReturn findSpecialList(@RequestParam int page, @RequestParam String searchValue,
			@RequestParam String area,@RequestParam String state,
			@RequestParam String strTime,@RequestParam String endTime,
			HttpSession httpSession) {
		return service.findSpecialList(page, searchValue,area,state,strTime,endTime);
	}
	@ResponseBody
	@RequestMapping(value = "findSpecialCount")
	@SecureValid(code = "02001", desc = "获取专题分页", type = MethodType.FIND)
	public JSONReturn findSpecialCount(@RequestParam int page, @RequestParam String searchValue,
			@RequestParam String area,@RequestParam String state,
			@RequestParam String strTime,@RequestParam String endTime) {
		return service.findSpecialCount(page, searchValue,area,state,strTime,endTime);
	}
	
	@ResponseBody
	@RequestMapping(value = "saveSpecialInfo")
	@SecureValid(code = "02002", desc = "保存专题", type = MethodType.ADD)
	public JSONReturn saveSpecialInfo(@RequestParam String data, HttpSession httpSession) {
		SpecialListDto dto = (SpecialListDto) JSONObject
				.toBean(JSONObject.fromObject(data), SpecialListDto.class);
		return scoreService.saveSpecialInfo(dto, acctName(httpSession));
	}*/

	@ResponseBody
	@RequestMapping(value = "findSpecialScoreByspecId")
	@SecureValid(code = "02001", desc = "根据specID号,获取评分信息", type = MethodType.FIND)
	public JSONReturn findSpecialScoreByspecId(@RequestParam Integer specId) {
		return scoreService.findSpecialScoreById(specId);
	}
	
	/*
	@ResponseBody
	@RequestMapping(value = "modifySpecialInfo")
	@SecureValid(code = { "02001", "02002" }, desc = "修改专题信息", type = MethodType.MODIFY)
	public JSONReturn modifySpecialInfo(@RequestParam String data, HttpSession httpSession) {
		SpecialListDto dto = (SpecialListDto) JSONObject
				.toBean(JSONObject.fromObject(data), SpecialListDto.class);
		return service.modifySpecialInfo(dto, acctName(httpSession));
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteSpecial")
	@SecureValid(code = "02001", desc = "删除专题", type = MethodType.DELETE)
	public JSONReturn deleteSpecial(@RequestParam Integer specId, HttpSession httpSession) {
		return service.deleteSpecial(specId,acctName(httpSession));
	}*/
}
