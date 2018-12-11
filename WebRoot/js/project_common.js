var moduleCode = '05001';

function initFun() {
	if (secure.find) {
		dialog = BootstrapDialog.loading();
		findListInfo();
	}
	if (!secure.add) {
		$('button.add-btn').remove();
	}
	if (secure.add) {
		$('button.add-btn').removeClass('hide');
	}
}
/*
 * 获取项目列表 
 * . [K]
 */
function findListInfo() {
	$.post('mgr/project/findProjectList', {
		page : page,
		searchValue : $('.searchInput').val(),
		state : $('.state').val()
	}, function(data) {
		dialog.close();
		var tbody = $('tbody.tbody').empty();
		if (!$.isSuccess(data)) return;
		$.each(data.body, function(i, v) {
			$('<tr></tr>')
			.append($('<td></td>').append(++i))
			.append($('<td></td>').append(v.strState))
			.append($('<td></td>').append("<a href='project.html?moduleCode=05001&proId="+v.id+"'>"+v.projectName+"</a>"))
			.append($('<td></td>').append(v.client))
			.append($('<td></td>').append(v.manager))
			.append($('<td></td>').append(v.participants))
			.append($('<td></td>').append(analyzeBtns(v)))
			.appendTo(tbody);
		});
	}, 'json');
	$.post('mgr/project/findProjectCount', {
		page : page,
		searchValue : $('.searchInput').val(),
		state:$('.state').val()
	}, function(data) {
		$.analysisPage(data.body);
	}, 'json');
}
/*
 * 分析操作按钮 
 * . [K]
 */
function analyzeBtns(v) {
	var btns = "";
	var acct=$('a.acctInfo').text().substring(1);
	btns += secure.modify||v.manager==acct ? "<button type='button' class='btn btn-primary btn-xs' onclick='showModifyBox("+ v.id + ")'><span class='glyphicon glyphicon-pencil'></span>编辑</button>" : "";
	btns += secure.del||v.manager==acct ? "<button type='button' class='btn btn-danger btn-xs' onclick='hintDelete(" + v.id + ")'><span class='glyphicon glyphicon-remove'></span>删除</button>" : "";
	return btns;
}
/*
 * 显示部门员工列表, 选择经理 
 * . [K]
 */
function showManagerList(principal, deptId) {
	if (!deptId) return;
	dialog = BootstrapDialog.loading();
	departmentId = deptId;
	$.getJSON('mgr/department/findDeptEmplList', {deptId : deptId}, function(data) {
		dialog.close();
		if (!$.isSuccess(data)) return;
		var list = $('select.principal-list').empty().append('<option value=0>请选择...</option>');
		$.each(data.body, function(i, v) {
			$('<option value=' + v.id + '></option>').append(v.name).appendTo(list);
		});
		BootstrapDialog.showModel($('div.set-principal-box'));
	});
}
/*
 * 设置部门经理 
 * . [K]
 */
function setPrincipal() {
	$.getJSON('mgr/department/setPrincipal', {
		deptId : departmentId,
		emplId : $('select.principal-list').val()
	}, function(data) {
		if (!$.isSuccess(data)) return;
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
		BootstrapDialog.hideModel($('div.set-principal-box'));
		findListInfo();
	});
}
/*
 * 提示并确定删除项目信息 
 * . [K]
 */
function hintDelete(id) {
	if (!id) return;
	BootstrapDialog.confirm("请确认是否要删除该项目?", function(result) {
		if (!result) return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/project/deleteProject', {proId : id}, function(data) {
			dialog.close();
			if (!$.isSuccess(data)) return;
			findListInfo();
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
/*
 * 显示部门编辑窗口 
 * . [K]
 */
function showModifyBox(proId) {
	$('.empty').removeClass('empty');
	if (!proId) return;
	window.open('./project_new.html?moduleCode=05001&proId=' + proId);
}
/*
 * 修改部门信息
 * . [K]
 */
var dept = {};
function modifyDept(deptId) {
	if (!deptId) return;
	$.isSubmit = true;
	dept.deptId = deptId;
	dept.deptName = $.verifyForm($('input.modifyName'), true);
	dept.deptDesc = $.verifyForm($('textarea.modifyDesc'), false);
	if (!$.isSubmit) return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/department/modifyDepartment', {
		deptId : dept.deptId,
		name : dept.deptName,
		desc : dept.deptDesc
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data)) return;
		BootstrapDialog.hideModel($('div.modify-box'));
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
		findListInfo();
	}, 'json');
}
/*
 * 新窗口打开新建项目页面
 * . [K]
 */
function newInternshipProject() {
	window.open('./project_new.html?moduleCode=05001');
}
/*
 * 添加部门信息 
 * . [K]
 */
function addDepartment() {
	$.isSubmit = true;
	dept.deptName = $.verifyForm($('input.addName'), true);
	dept.deptDesc = $.verifyForm($('textarea.addDesc'), false);
	if (!$.isSubmit) return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/department/addDepartment', {name : dept.deptName,desc : dept.deptDesc}, function(data) {
		dialog.close();
		if (!$.isSuccess(data)) return;
		BootstrapDialog.hideModel($('div.add-box'));
		BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
		findListInfo();
	}, 'json');
}