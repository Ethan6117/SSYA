package com.empl.mgr.service;

import com.empl.mgr.support.JSONReturn;

public abstract interface SpecialScoreService {

	//public abstract JSONReturn findSpecialList(int page, String searchValue,String area,String state,String strTime,String endTime);
	//public JSONReturn findSpecialCount(int page, String searchValue,String area,String state,String strTime,String endTime);
	public abstract Object saveSpecialScoreInfo(String specId,String score, String opinion,String infoimg);
	public abstract JSONReturn findSpecialScoreById(Integer specId);
	//public abstract JSONReturn modifySpecialInfo(SpecialListDto dto, String acctName);
	//public abstract JSONReturn deleteSpecial(Integer specId, String acctName);
}
