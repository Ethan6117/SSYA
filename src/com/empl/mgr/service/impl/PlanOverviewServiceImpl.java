package com.empl.mgr.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.constant.TimeFormatConstant;
import com.empl.mgr.dao.PlanOverviewDao;
import com.empl.mgr.dto.PlanOverviewListDto;
import com.empl.mgr.model.TePlanOverview;
import com.empl.mgr.service.PlanOverviewService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.PageUtils;

@Scope
@Service
@Transactional(readOnly = true)
public class PlanOverviewServiceImpl implements PlanOverviewService {
	@Autowired
	private PlanOverviewDao planOverviewDao;

	@Transactional
	public JSONReturn findPlanOverviewList(int page, String year) {
		// TODO Auto-generated method stub
		List<PlanOverviewListDto> dtoList = planOverviewDao.findPlanOverviewList(page, year);
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");

		if(year==""){
			List<TePlanOverview> poList = new ArrayList<TePlanOverview>();
			Date date = DateTimeUtil.getCurrentTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(date);
			int currentWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
			int currentYear;
			if(StringUtils.isBlank(year)){
				int currentWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				if (currentWeek==0){
					currentWeek=7;
				}
				if(currentWeek>=5){
					currentWeekOfYear+=1;
					calendar.add(Calendar.DATE, 7);
				}
				calendar.add(Calendar.DATE, 7 - currentWeek);
				for (int i = 0;; i++) {
					if(currentWeekOfYear-i==0)
						currentWeekOfYear+=52;
					if (dtoList.get(i).getWeekOfYear() == currentWeekOfYear - i) {
						if (i > 0) {
							if (poList.size() > 1) {
								planOverviewDao.updateState(dtoList.get(i).getId(), 9);
								planOverviewDao.updateState(dtoList.get(i + 1).getId(), 9);
							} else if (poList.size() == 1) {
								planOverviewDao.updateState(dtoList.get(i).getId(), 8);
								planOverviewDao.updateState(dtoList.get(i + 1).getId(), 9);
							}
							for (TePlanOverview po : poList) {
								if (poList.indexOf(po) < poList.size()-2) {
									po.setState(9);
								} else if (poList.indexOf(po) == poList.size()-2) {
									po.setState(8);
								}
								planOverviewDao.save(po);
							}
							dtoList=planOverviewDao.findPlanOverviewList(page, year);
						}
						break;
					}
					Date sunDate = calendar.getTime();
					currentYear = calendar.get(Calendar.YEAR);
					calendar.add(Calendar.DATE, -6);
					Date monDate = calendar.getTime();
					PlanOverviewListDto newDto = new PlanOverviewListDto(null, currentYear, currentWeekOfYear - i,
							DateTimeUtil.conversionTime(monDate, TimeFormatConstant.YYYY_MM_DD) + "至"
									+ DateTimeUtil.conversionTime(sunDate, TimeFormatConstant.YYYY_MM_DD), 7);
					dtoList.add(i, newDto);
					poList.add(0, new TePlanOverview(null, currentYear, currentWeekOfYear - i,
									DateTimeUtil.conversionTime(monDate, TimeFormatConstant.YYYY_MM_DD) + "至"
											+ DateTimeUtil.conversionTime(sunDate, TimeFormatConstant.YYYY_MM_DD), 7));
					calendar.add(Calendar.DATE, -1);
				}
			}
		}

		for (PlanOverviewListDto dto : dtoList) {
			dto.setStrState(dto.setStateToStr(dto.getState()));
		}
		return JSONReturn.buildSuccess(dtoList);
	}
	
	public JSONReturn findPlanOverviewCount(int page, String year) {
		// TODO Auto-generated method stub
		int count = planOverviewDao.findPlanOverviewCount(year);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.PAGE_LIST));
	}
}
