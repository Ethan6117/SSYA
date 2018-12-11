var moduleCode = $.getUrlParam('moduleCode');
moduleCode = !moduleCode ? '05002' : moduleCode;
var date = !$.getUrlParam('date') ? 0 : $.getUrlParam('date');
var state = !$.getUrlParam('state') ? 0 : $.getUrlParam('state');
var planState;

function initFun() {
	if (secure.find) {
		dialog = BootstrapDialog.loading();
		findPlanListInfo();
	}
	if (!secure.add||state==9||state==8) {
		$('button.add-btn').remove();
	}
	if (secure.add&&state!=9&&state!=8) {
		$('button.add-btn').removeClass('hide');
	}
}
/*
 * 获取计划列表 
 * . [K]
 */
function findPlanListInfo() {
	$.post('mgr/plan/findPlanList', {
		page : page,
		date : date
	}, function(data) {
		dialog.close();
		var tbody = $('tbody.tbody').empty();
		if (!$.isSuccess(data)) return;
		$.each(data.body, function(i, v) {
			$('<tr class='+ v.id +'></tr>')
				.append($("<td class='id'></td>").append(++i))
				.append($("<td class='projectName'></td>").append(v.projectName))
				.append($("<td class='manager'></td>").append(v.manager))
				.append($("<td class='participants partic-list'></td>").append(v.participants))
				.append($("<td class='lastWeekPercent'></td>").append(v.lastWeekPercent!=null ? v.lastWeekPercent+"%" : ""))
				.append($("<td class='percent'></td>").append(v.percent!=null ? v.percent+"%" : ""))
				.append($("<td class='strState'></td>").append(v.strState))
				.append($("<td class='progress2'></td>").append(v.progress))
				.append($("<td class='strEstimatedTime'></td>").append(v.strEstimatedTime))
				.append($("<td class='strCompleteTime'></td>").append(v.strCompleteTime))
				.append($("<td class='analyzeBtns'></td>").append(analyzeBtns(v)))
				.appendTo(tbody);
		});
	}, 'json');
	$.post('mgr/plan/findPlanCount', {
		page : page,
		date : "201841"
	}, function(data) {
		$.analysisPage(data.body);
	}, 'json');
}
/*
 * 列表操作按钮 
 * . [K]
 */
function analyzeBtns(v) {
	var btns = "";
	var acct=$('a.acctInfo').text().substring(1);
	btns += (secure.modify&&state!=9)||(v.manager==acct&&state!=9) ?
			"<button type='button' class='btn btn-primary btn-xs' onclick='clickModifyPlan(" + v.id + ")'><span class='glyphicon glyphicon-pencil'></span>编辑</button>" :
				"";
	btns += (secure.del&&state!=9)||(v.manager==acct&&state!=9) ? 
			"<button type='button' class='btn btn-danger btn-xs' onclick='hintDelete("+ v.id +")'><span class='glyphicon glyphicon-remove'></span>删除</button>" :
				"";
	return btns;
}

/*
 * 点击编辑按钮
*/
function clickModifyPlan(id){
	if (!id) return;
	$.getJSON('./mgr/plan/findPlanInfo', {
		pId : id,
		date : date
	}, function(data) {
		if(!$.isSuccess(data)) return;
		var v = data.body;
		$("."+id+" .projectName").empty().append("<select class='select projectName2' style='width: 120px;'><option value='"+ v.projectName +"'>"+ v.projectName +"</option></select>");
		$("."+id+" .manager").empty().append("<select class='select manager2' style='width: 65px;'><option value='"+ v.manager +"'>"+ v.manager +"</option></select>");
		$("."+id+" .participants").empty();
		findName(v);
		if (v.percent == null)
			v.percent = "";
		$("." + id + " .percent").empty().append("<input type='text' class='text percent2' value='"	+ v.percent + "' style='width: 30px;' />%");
		$("."+id+" .strState").empty().append("<select class='select strState2' style='width: 65px;'><option value='1'>进行中</option><option value='5'>启动</option><option value='6'>等待</option><option value='3'>完成</option></select>");
		$("."+id+" select.strState2").val(v.state);
		$("."+id+" .progress2").empty().append("<textarea class='form-control2 addDesc progress3' rows='3' maxlength='500'>"+ v.progress +"</textarea>");
		$("."+id+" .strEstimatedTime").empty().append("<input type='text' class='text date strEstimatedTime2' value='"+ v.strEstimatedTime +"' style='width: 90px;' onfocus='WdatePicker()' />");
		$("."+id+" .strCompleteTime").empty().append("<input type='text' class='text date strCompleteTime2' value='"+ v.strCompleteTime +"' style='width: 90px;' onfocus='WdatePicker()' />");
		$("."+id+" .analyzeBtns").empty().append("<button type='button' class='btn btn-primary btn-xs' onclick='updatePlanInfo("+ id +", "+ v.date +")'><span class='glyphicon glyphicon-pencil'></span>保存</button>");
		planState=v.state;
	});
}

/*
 * 点击添加
*/
function clickAddPlan(){
	$("<tr class='id'></tr>")
	.append($("<td class='id'></td>"))
	.append($("<td class='projectName'></td>").append("<select class='select projectName2' style='width: 120px;'><option value=''>请选择</option></select>"))
	.append($("<td class='manager'></td>").append("<select class='select manager2' style='width: 65px;'><option value=''>请选择</option></select>"))
	.append($("<td class='participants'></td>"))
	.append($("<td class='lastWeekPercent'></td>"))
	.append($("<td class='percent'></td>").append("<input type='text' class='text percent2' value='' style='width: 30px;' />%"))
	.append($("<td class='strState'></td>").append("<select class='select strState2' style='width: 65px;'><option value='1'>进行中</option><option value='5'>启动</option><option value='6'>等待</option><option value='3'>完成</option></select>"))
	.append($("<td class='progress2'></td>").append("<textarea class='form-control2 addDesc progress3' rows='3' maxlength='500'></textarea>"))
	.append($("<td class='strEstimatedTime'></td>").append("<input type='text' class='text date strEstimatedTime2' value='' style='width: 90px;'  onfocus='WdatePicker()' />"))
	.append($("<td class='strCompleteTime'></td>").append("<input type='text' class='text date strCompleteTime2' value='' style='width: 90px;' onfocus='WdatePicker()' />"))
	.append($("<td class='analyzeBtns'></td>").append("<button type='button' class='btn btn-primary btn-xs' onclick='updatePlanInfo(\"id\", "+ date +")'><span class='glyphicon glyphicon-pencil'></span>保存</button>"))
	.prependTo("tbody.tbody");
	findName()
}

/*
 * 获取账号名单
*/
function findName(b) {
	$.ajax({
		type:"GET",
		url:"mgr/account/findParticipantsList",
		dataType:"json",
		success:function(data){
			$.each(data.body, function(j, v) {
				if(b==null){					$
					$(".id .participants").append("<div class='input-name'>"+"<input class='participants2' type='checkbox' value='"+v.nickname+"' />"+v.nickname+"</div>");
				}
				else{
					$("."+ b.id +" .participants").append("<div class='input-name'>"+"<input class='participants2' type='checkbox' value='"+v.nickname+"' />"+v.nickname+"</div>");
					if(b.participants.indexOf(v.nickname)!=-1)
						$("."+ b.id +" input[value='"+ v.nickname +"']").prop('checked',true);
				}
			});
		}
	});
	
	$.ajax({
		type:"GET",
		url:"mgr/account/findManagerList",
		dataType:"json",
		success:function(data){
			$.each(data.body, function(j, v) {
				if(b==null){
					$(".id .manager .manager2").append("<option value='"+ v.nickname +"'>"+ v.nickname +"</option>");
				}
				else{
					if(b.manager!=v.nickname)
					$("."+ b.id +" .manager .manager2").append("<option value='"+ v.nickname +"'>"+ v.nickname +"</option>");
				}
			});
		}
	});
	
	$.ajax({
		type:"GET",
		url:"mgr/plan/findProjectName",
		dataType:"json",
		success:function(data){
			$.each(data.body, function(j, v) {
				if(b==null){
					$(".id .projectName .projectName2").append("<option value='"+ v.projectName +"'>"+ v.projectName +"</option>");
				}
				else{
					if(b.projectName!=v.projectName)
					$("."+ b.id +" .projectName .projectName2").append("<option value='"+ v.projectName +"'>"+ v.projectName +"</option>");
				}
			});
		}
	});
}

function hintDelete(id) {
	if (!id) return;
	BootstrapDialog.confirm("请确认是否要删除该项目?", function(result) {
		if (!result) return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/plan/deletePlan', {pId : id}, function(data) {
			dialog.close();
			if (!$.isSuccess(data)) return;
			findPlanListInfo();
			//BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
			var bdg=BootstrapDialog.show({
				title:"提示信息",
				type:BootstrapDialog.TYPE_SUCCESS,
				closable : true,
				message : data.body
			});
			setTimeout(function(){
				bdg.close();
			},1500);
		});
	});
}

(function($){
	// 验证文本框, 传入获取文本框元素的标识, 是否为空,  加正则匹配
	$.input = function(className, empty, regular){
		$(className).parent().prev().removeClass('data-empty');
		var val = $.removeTrim($(className).val());
		if(!empty) return val;
		if(empty && !regular && val) return val;
		if(regular != null && regular.test(val)) return val;
		$.isSubmit = false;
		$(className).parent().prev().addClass('data-empty');
	};
	// 验证下拉列表
	$.select = function(className, empty, regular){
		$(className).parent().prev().removeClass('data-empty');
		var val = $(className).val();
		if(!empty) return val;
		if(empty && !regular && val && val != 0) return val;
		if(regular != null && regular.test(val)) return val;
		$.isSubmit = false;
		$(className).parent().prev().addClass('data-empty');
	};
	// 获取单选按钮
	$.radio = function(className, empty, regular){
		$(className).parent().parent().prev().removeClass('data-empty');
		var val = $(className).filter(':checked').val();
		if(!empty) return val;
		if(empty && !regular && val) return val;
		if(regular != null && regular.test(val)) return val;
		$.isSubmit = false;
		$(className).parent().parent().prev().addClass('data-empty');
	};
})(jQuery);

var p={};
function detectionForm(id, date){
	$.isSubmit = true;	// 重置表单为可提交
	// 项目信息
	p.projectName = $.select('.'+ id +' .projectName2', true, null);	// 项目名称
	p.manager = $.select('.'+ id +' .manager2', true, null);			//负责人
	p.percent = $.input('.'+ id +' .percent2', false, null);
	p.state = $.select('.'+ id +' .strState2', false, null);
	p.progress = $.input('.'+ id +' .progress3', false, null);
	var participants =[];
	$('.'+ id +' .participants2:checked').each(function(){
		participants.push($(this).val());
	});
	p.participants=participants.toString();	// 参与人员
	p.strEstimatedTime = $.input('.'+ id +' .strEstimatedTime2', false, null);	// 访问方式
	p.strCompleteTime = $.input('.'+ id +' .strCompleteTime2', false, null);
	p.date = date;
	if(id=="id")
		id=null;
	p.id = id;
	if(!$.isSubmit){
		var bdg=BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_DANGER,
			closable : true,
			message : "加<span class='mandatory'>*</span>处必填！"
		});
		setTimeout(function(){
			bdg.close();
		},1500);
	}
	return $.isSubmit;
};

/*
 * 添加与修改
*/
function updatePlanInfo(id, Week){
	if (planState==3)
	{
		var bdg=BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_DANGER,
			closable : true,
			message : "项目修改失败!",
			onhidden : function(dialogRef) {
				window.location.href = "plan.html?moduleCode=05002&date=" + date + "&state=" + state;
			}
		});
		setTimeout(function(){
			bdg.close();
		},1500);
	}
	else{
		detectionForm(id, Week);
		if(!$.isSubmit) return;
		dialog = BootstrapDialog.isSubmitted();
		var url="updatePlan";
		if(id=="id")
			url="addPlan";
		$.post("mgr/plan/"+url, {
			data : JSON.stringify(p),
			date : date
		}, function(data){
			dialog.close();
			if(!$.isSuccess(data)) return;
			var bdg=BootstrapDialog.show({
				title:"提示信息",
				type:BootstrapDialog.TYPE_SUCCESS,
				closable : true,
				message : "项目保存成功!",
				onhidden : function(dialogRef) {
					window.location.href = "plan.html?moduleCode=05002&date=" + date + "&state=" + state;
				}
			});
			setTimeout(function(){
				bdg.close();
			},1500);
		}, 'json');
	}
};