package com.empl.mgr.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.empl.mgr.dto.SpecialListDto;
import com.empl.mgr.model.TeSpecialDesign;
import com.empl.mgr.support.JSONReturn;

public abstract interface SpecialService {
	public abstract JSONReturn exportExcel(String searchValue, String area, String state, String strTime,
			String endTime, HttpServletResponse response);

	public abstract JSONReturn findSpecialTable(String addTime, String state);

	public abstract JSONReturn findSpecialList(int page, String searchValue, String area, String state, String strTime,
			String endTime);

	public JSONReturn findSpecialCount(int page, String searchValue, String area, String state, String strTime,
			String endTime);

	public abstract JSONReturn saveSpecialInfo(SpecialListDto dto, String acctName);

	public abstract JSONReturn findSpecialById(Integer specId);

	public abstract JSONReturn modifySpecialInfo(SpecialListDto dto, String acctName);

	public abstract JSONReturn deleteSpecial(Integer specId, String acctName);

	public abstract JSONReturn saveSpecialDesgin(Integer speId, String url, String imgdesc);

	public abstract List<TeSpecialDesign> findSpecialDesignsById(Integer speId);
}
