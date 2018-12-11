package com.empl.mgr.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.constant.TimeFormatConstant;
import com.empl.mgr.dao.PlanDao;
import com.empl.mgr.dto.PlanListDto;
import com.empl.mgr.dto.ProjectListDto;
import com.empl.mgr.model.TePlan;
import com.empl.mgr.service.PlanService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.CompareUtil;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.PageUtils;

@Scope
@Service
@Transactional(readOnly=true)
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanDao planDao;
	
	@Transactional
	public JSONReturn findPlanList(int page, String date) {
		// TODO Auto-generated method stub
		String lastWeek=new String();
		if(date.length()==5&&date.charAt(4)==1){
			Integer y=Integer.valueOf(date.substring(3, 4))-1;
			lastWeek=date.substring(0,3)+y.toString()+"52";
		}
		else{
			Integer w=Integer.valueOf(date.substring(5, 6))-1;
			lastWeek=date.substring(0,5)+w.toString();
		}
		List<PlanListDto> dtoList=planDao.findPlanList(page, date);
		if (CollectionUtils.isEmpty(dtoList)){
			dtoList=planDao.findPlanList(page, lastWeek);
			if (CollectionUtils.isEmpty(dtoList))
				return JSONReturn.buildFailure("未获取到上周数据!");
			for (PlanListDto dto : dtoList) {
				if(dto.getState()!=3){
					dto.setLastWeekPercent(dto.getPercent());
					dto.setPercent(null);
					dto.setStrEstimatedTime(DateTimeUtil.conversionTime(dto.getEstimatedTime(), TimeFormatConstant.YYYY_MM_DD));
					dto.setStrCompleteTime(DateTimeUtil.conversionTime(dto.getCompleteTime(), TimeFormatConstant.YYYY_MM_DD));
					savePlan(dto, date);
				}
			}
		}
		
		dtoList=planDao.findPlanList(page, date);
		int size=dtoList.size();
		for (int i=0;i<size;i++) {	
			PlanListDto dto=dtoList.get(i);
			dto.setStrState(dto.setStateToStr());
			dto.setStrEstimatedTime(DateTimeUtil.conversionTime(dto.getEstimatedTime(), TimeFormatConstant.YYYY_MM_DD));
			dto.setStrCompleteTime(DateTimeUtil.conversionTime(dto.getCompleteTime(), TimeFormatConstant.YYYY_MM_DD));
			
			if(dto.getState()==3){
				for(int j=i;j<dtoList.size()-1;j++){
					dtoList.set(j, dtoList.get(j+1));
				}
				dtoList.set(dtoList.size()-1, dto);
				i--;
				size-=1;
			}			
		}
		return JSONReturn.buildSuccess(dtoList);
	}

	public JSONReturn findPlanCount(int page, String date) {
		int count = planDao.findPlanCount(date);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.PAGE_LIST));
	}
	
	@Transactional
	public JSONReturn deletePlan(Integer pId) {
		planDao.deletePlan(pId);
		return JSONReturn.buildSuccess("删除成功!");
	}
	
	public JSONReturn findPlanById(Integer pId, String date) {
		PlanListDto dto = planDao.findPlanById(pId);
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("获取源数据失败!");
		dto.setStrState(dto.setStateToStr());
		dto.setStrEstimatedTime(DateTimeUtil.conversionTime(dto.getEstimatedTime(), TimeFormatConstant.YYYY_MM_DD));
		dto.setStrCompleteTime(DateTimeUtil.conversionTime(dto.getCompleteTime(), TimeFormatConstant.YYYY_MM_DD));
		return JSONReturn.buildSuccess(dto);
	}

	@Transactional
	public JSONReturn updatePlan(PlanListDto dto, String date) {
		// TODO Auto-generated method stub
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("项目保存失败!");
		TePlan plan=planDao.findUniqueByProperty("id", dto.getId());
		plan.setId(dto.getId());
		plan.setProjectName(dto.getProjectName());
		plan.setManager(dto.getManager());
		plan.setParticipants(dto.getParticipants());
		plan.setPercent(StringUtils.isNotBlank(dto.getPercent()) ? dto.getPercent() : null);
		plan.setState(dto.getState());
		plan.setProgress(dto.getProgress());
		plan.setParticipants(dto.getParticipants());
		plan.setEstimatedTime(StringUtils.isNotBlank(dto.getStrEstimatedTime()) ? DateTimeUtil.stringToDate(dto.getStrEstimatedTime(), TimeFormatConstant.YYYY_MM_DD) : null);
		plan.setCompleteTime(StringUtils.isNotBlank(dto.getStrCompleteTime()) ? DateTimeUtil.stringToDate(dto.getStrCompleteTime(), TimeFormatConstant.YYYY_MM_DD) : null);
		plan.setUpdateTime(DateTimeUtil.getCurrentTime());
		planDao.save(plan);
		return JSONReturn.buildSuccess("修改成功");
	}
	
	@Transactional
	public JSONReturn savePlan(PlanListDto dto, String date) {
		// TODO Auto-generated method stub
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("项目保存失败!");
		TePlan plan=new TePlan();
		plan.setId(dto.getId());
		plan.setProjectName(dto.getProjectName());
		plan.setManager(dto.getManager());
		plan.setParticipants(dto.getParticipants());
		plan.setLastWeekPercent(StringUtils.isNotBlank(dto.getLastWeekPercent()) ? dto.getLastWeekPercent() : null);
		plan.setPercent(StringUtils.isNotBlank(dto.getPercent()) ? dto.getPercent() : null);
		plan.setState(dto.getState());
		plan.setProgress(dto.getProgress());
		plan.setParticipants(dto.getParticipants());
		plan.setEstimatedTime(StringUtils.isNotBlank(dto.getStrEstimatedTime()) ? DateTimeUtil.stringToDate(dto.getStrEstimatedTime(), TimeFormatConstant.YYYY_MM_DD) : null);
		plan.setCompleteTime(StringUtils.isNotBlank(dto.getStrCompleteTime()) ? DateTimeUtil.stringToDate(dto.getStrCompleteTime(), TimeFormatConstant.YYYY_MM_DD) : null);
		plan.setAddTime(DateTimeUtil.getCurrentTime());
		plan.setDate(Integer.valueOf(date));
		plan.setdeleteTag(0);
		planDao.save(plan);
		return JSONReturn.buildSuccess("添加成功");
	}
	
	public JSONReturn findProjectName() {
		List<ProjectListDto> dtoList=planDao.findProjectName();
		return JSONReturn.buildSuccess(dtoList);
	}
}
