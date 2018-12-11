package com.empl.mgr.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.empl.mgr.annotation.SecureValid;
import com.empl.mgr.constant.MethodType;
import com.empl.mgr.controller.support.AbstractController;
import com.empl.mgr.dto.SpecialListDto;
import com.empl.mgr.model.TeSpecialDesign;
import com.empl.mgr.service.SpecialService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "special")
public class SpecialController extends AbstractController {

	@Autowired
	private SpecialService service;

	@ResponseBody
	@RequestMapping(value = "exportExcel")
	public JSONReturn exportExcel(@RequestParam String searchValue, @RequestParam String area,
			@RequestParam String state, @RequestParam String strTime, @RequestParam String endTime,
			HttpServletResponse response) throws UnsupportedEncodingException {
		if(null!=area && !area.equals("")){
			area=URLDecoder.decode(area, "utf-8");
		}
		if(null!=searchValue && !searchValue.equals("")){
			searchValue=URLDecoder.decode(searchValue, "utf-8");
		}
		if(null!=state && !state.equals("")){
			state=URLDecoder.decode(state, "utf-8");
		}
		if(null!=strTime && !strTime.equals("")){
			strTime=URLDecoder.decode(strTime, "utf-8");
		}
		if(null!=endTime && !endTime.equals("")){
			endTime=URLDecoder.decode(endTime, "utf-8");
		}
		return service.exportExcel(searchValue, area, state, strTime, endTime, response);
	}

	@ResponseBody
	@RequestMapping(value = "findSpecialTable")
	public JSONReturn findSpecialTable(String addTime, String state) {
		return service.findSpecialTable(addTime, state);
	}

	@ResponseBody
	@RequestMapping(value = "findSpecialList")
	@SecureValid(code = "02001", desc = "获取专题列表", type = MethodType.FIND)
	public JSONReturn findSpecialList(@RequestParam int page, @RequestParam String searchValue,
			@RequestParam String area, @RequestParam String state, @RequestParam String strTime,
			@RequestParam String endTime, HttpSession httpSession) {
		return service.findSpecialList(page, searchValue, area, state, strTime, endTime);
	}

	@ResponseBody
	@RequestMapping(value = "findSpecialCount")
	@SecureValid(code = "02001", desc = "获取专题分页", type = MethodType.FIND)
	public JSONReturn findSpecialCount(@RequestParam int page, @RequestParam String searchValue,
			@RequestParam String area, @RequestParam String state, @RequestParam String strTime,
			@RequestParam String endTime) {
		return service.findSpecialCount(page, searchValue, area, state, strTime, endTime);
	}

	@ResponseBody
	@RequestMapping(value = "saveSpecialInfo")
	@SecureValid(code = "02002", desc = "保存专题", type = MethodType.ADD)
	public JSONReturn saveSpecialInfo(@RequestParam String data, HttpSession httpSession) {
		SpecialListDto dto = (SpecialListDto) JSONObject.toBean(JSONObject.fromObject(data), SpecialListDto.class);
		return service.saveSpecialInfo(dto, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "findSpecialById")
	@SecureValid(code = "02001", desc = "根据ID号,获取专题信息", type = MethodType.FIND)
	public JSONReturn findSpecialById(@RequestParam Integer specId) {
		return service.findSpecialById(specId);
	}

	@ResponseBody
	@RequestMapping(value = "modifySpecialInfo")
	@SecureValid(code = { "02001", "02002" }, desc = "修改专题信息", type = MethodType.MODIFY)
	public JSONReturn modifySpecialInfo(@RequestParam String data, HttpSession httpSession) {
		SpecialListDto dto = (SpecialListDto) JSONObject.toBean(JSONObject.fromObject(data), SpecialListDto.class);
		return service.modifySpecialInfo(dto, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "deleteSpecial")
	@SecureValid(code = "02001", desc = "删除专题", type = MethodType.DELETE)
	public JSONReturn deleteSpecial(@RequestParam Integer specId, HttpSession httpSession) {
		return service.deleteSpecial(specId, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "uploadEnclosure")
	public Object uploadEnclosure(@RequestParam(value = "enclosure", required = false) MultipartFile file,
			HttpServletRequest request) {
		if (file.getSize() > 0) {
			String path = request.getSession().getServletContext().getRealPath("upload");
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));// 取文件格式后缀名
			if (!type.equals(".zip") && !type.equals(".rar")) {
				return JSONReturn.buildSuccess("格式不正确");
			}
			String fileName = new Date().getTime() + type;
			// File targetFile = new File(path, fileName);
			// if(!targetFile.exists()){
			// targetFile.mkdirs();
			// }
			// 保存
			try {
				SaveFileFromInputStream(file.getInputStream(), path, fileName);
				// file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return JSONReturn.buildSuccess(fileName);
		}
		return JSONReturn.buildSuccess("未选择文件");
	}

	@RequestMapping(value = "downloadEnclosure")
	public ResponseEntity<byte[]> downloadEnclosure(@RequestParam String filename, HttpServletRequest request)
			throws Exception {
		// 获取项目根目录
		String ctxPath = request.getSession().getServletContext().getRealPath("upload");
		// 获取下载文件露肩
		String downLoadPath = ctxPath + "\\" + filename;
		File file = new File(downLoadPath);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", filename);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "uploadDesign")
	public void uploadDesign(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("imgdesc") String imgdesc, @RequestParam("speId") String speId, HttpServletRequest request,
			HttpServletResponse response) {
		if (file.getSize() > 0) {
			String path = request.getSession().getServletContext().getRealPath("upload");
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
			String fileName = new Date().getTime() + type;
			// File targetFile = new File(path, fileName);
			// if(!targetFile.exists()){
			// targetFile.mkdirs();
			// }
			// 保存
			try {
				// file.transferTo(targetFile);
				SaveFileFromInputStream(file.getInputStream(), path, fileName);
				service.saveSpecialDesgin(Integer.valueOf(speId), fileName, imgdesc);
				response.getWriter().write("true");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("initDesign")
	public void initDesign(@RequestParam() Integer speId, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<TeSpecialDesign> specialDesigns = service.findSpecialDesignsById(speId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", request.getContextPath() + "/upload/");
		map.put("data", specialDesigns);
		JSONArray mapjson = JSONArray.fromObject(map);
		response.getWriter().write(mapjson.toString());
	}

	public void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

}
