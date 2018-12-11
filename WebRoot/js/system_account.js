var moduleCode = '04001';
var curAcctName = "";

function initFun() {
	if(secure.find){
		dialog = BootstrapDialog.loading();
		findListInfo();
	}
	if(secure.add)
		$('button.add-btn').removeClass('hide');
	if(!secure.add)
		$('button.add-btn').remove();
}
$(function() {
	$('.search-select, .dropdown-menu').on('click', function(e) {
		$target = $(e.target);
		var searchBtn = $('button.search-btn');
		searchBtn.text($target.text());
		searchBtn.append("<span class='caret'></span>");
		searchBtn.attr('name', $target.attr('name'));
	});
	$('select.department').change(function(e){
		findPosition($(this).val());
	});
	$('select.mdoifyDepartment').change(function(e){
		findMdoifyPosition($(this).val());
	});
});
/*
 * 获取数据列表
 * . [K]
 */
function findListInfo() {
	$.post('mgr/account/findAccountList', {
		page : page,
		searchValue : $('input.searchInput').val()
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data)) return;
		var tbody = $('tbody.tbody').empty();
		$.each(data.body, function(i, v) {
			$("<tr></tr>")
			.append($("<td></td>").append(v.id))
			.append($("<td></td>").append(v.name))
			.append($("<td></td>").append(v.nickname))
			.append($("<td></td>").append(v.time))
			.append($("<td></td>").append(v.creator))
			.append($("<td></td>").append(analyzeBtns(v)))
			.appendTo(tbody);
		});
	}, 'json');
	findAccountPage();
}
/*
 * 获取数据分页
 * . [K]
 */
function findAccountPage() {
	$.post('mgr/account/findAccountPage', {
		page : page,
		searchValue : $('input.searchInput').val()
	}, function(data) {
		$.analysisPage(data.body);
	}, 'json');
}
/*
 * 解析操作按钮
 * . [K]
 */
function analyzeBtns(v){
	var btns = "";
	btns += secure.modify ? "<button type='button' class='btn btn-primary btn-xs' onclick='showModifyBox("+v.id+")'><span class='glyphicon glyphicon-pencil'></span>修改</button>" : "" ;
	btns += secure.modify ? "<button type='button' class='btn btn-info btn-xs' onclick='initPassword("+v.id+")'><span class='glyphicon glyphicon-cog'></span>初始密码</button>" : "" ;
	btns += secure.modify && !v.initAccount ? "<button class='btn btn-success btn-xs' type='button' onclick='roleMgr(\""+v.name+"\")'><span class='glyphicon glyphicon glyphicon-star'></span>管理</button>" : "";
	btns += secure.del && !v.initAccount ? "<button type='button' class='btn btn-danger btn-xs' onclick='hintDelete("+v.id+")'><span class='glyphicon glyphicon-remove'></span>删除</button>" : "" ;
	return btns;
}
/*
 * 显示添加窗口
 * . [K]
 */
function showAddBox(){
	$('.empty').removeClass('empty');
	$('input.acctName').val("");
	$('input.acctNick').val("");
	$('input.acctPass').val("");
	findDepartment();
	BootstrapDialog.showModel($('div.add-box'));
}
/*
 * 添加账户信息
 * . [K]
 */
var account = {};
function addAccount(){
	$.isSubmit = true;
	account.username = $.verifyForm($('input.acctName'), true);
	account.nickname = $.verifyForm($('input.acctNick'), true);
	account.password = $.verifyForm($('input.acctPass'), true);
	account.email = $.verifyForm($('input.email'), false);
	account.mobile = $.verifyForm($('input.mobile'), false);
	account.telephone = $.verifyForm($('input.telephone'), false);
	account.department = $.verifyForm($('select.department'), false);
	account.position = $.verifyForm($('select.position'), false);
	if(!$.isSubmit) reutrn;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/account/addAccount', {
		user : account.username,
		nick : account.nickname,
		pass : account.password,
		email: account.email,
		mobile:account.mobile,
		telephone:account.telephone,
		department:account.department,
		position:account.position
	}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		BootstrapDialog.hideModel($('div.add-box'));
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
	}, 'json');
}
/*
 * 提示并删除帐号列表
 * . [K]
 */
function hintDelete(id){
	if(!id) return;
	BootstrapDialog.confirm("请确定是否删除该帐号?", function(result){
		if(!result) return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/account/delAccount', {id:id}, function(data){
			dialog.close();
			if(!$.isSuccess(data)) return;
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
		});
	});
}
/*
 * 提示并确认初始密码为123456
 * . [K]
 */
function initPassword(id){
	if(!id) return;
	BootstrapDialog.confirm("请确定是否将该账户密码重置为 <b style='color:red;'>123456</b>", function(result){
		if(!result) return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/account/initPassword', {id:id}, function(data){
			dialog.close();
			if(!$.isSuccess(data)) return; 
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
 * 显示编辑窗口
 * . [K]
 */
function showModifyBox(id){
	$('.empty').removeClass('empty');
	if(!id) return;
	account.id = id;
	dialog = BootstrapDialog.loading();
	$.getJSON('mgr/account/findAccountById', {id:id}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		var info = data.body.info;
		$('input.modifyAcctName').val(info.name);
		$('input.mdoifyAcctNick').val(info.nickname);
		$('input.mdoifyEmail').val(info.email);
		$('input.mdoifyMobile').val(info.mobile);
		$('input.mdoifyTelephone').val(info.telephone);
		$.analyzeDepartmentOrPosition(info.department, $('select.mdoifyDepartment').empty().append('<option value=0>请选择部门</option>'), data.body.departments); // 部门
		$.analyzeDepartmentOrPosition(info.position, $('select.mdoifyPosition').empty().append('<option value=0>请选择职位</option>'), data.body.positions); // 职位
		BootstrapDialog.showModel($('div.modify-box'));
	});
}
/*
 * 编辑帐户信息
 * . [K]
 */
function mdoifyAccount(){
	if(!account.id) return;
	$.isSubmit = true;
	account.nickname = $.verifyForm($('input.mdoifyAcctNick'), true);
	account.mobile = $.verifyForm($('input.mdoifyMobile'), false);
	account.telephone = $.verifyForm($('input.mdoifyTelephone'), false);
	account.department = $.verifyForm($('select.mdoifyDepartment'), false);
	account.position = $.verifyForm($('select.mdoifyPosition'), false);
	account.email = $.verifyForm($('input.mdoifyEmail'), false);
	if(!$.isSubmit) return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/account/modifyNickname', {
		id : account.id,
		nickname : account.nickname,
		email : account.email,
		mobile:account.mobile,
		telephone:account.telephone,
		department:account.department,
		position:account.position
	}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
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
 * 遍历并显示角色列表
 * . [K]
 */
function roleMgr(acctName){
	if(!acctName) return;
	var tbody = $('table.module-table').empty();
	curAcctName = acctName;
	dialog = BootstrapDialog.loading();
	$.getJSON('mgr/account/findRole', {acctName : acctName}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			$("<tr class='acctRole'></tr>")
			.append($("<td></td>").append($("<label></label>").append(findRoleCheckBox(v) + v.roleName))) 
			.appendTo(tbody);
		});
		BootstrapDialog.showModel($('div.acct-role-module-box'));
	});
}
function findRoleCheckBox(v){
	return "<input type='checkbox' "+$.findChecked(v.opt)+" onclick='setAccountRole(this,"+v.id+")' code='"+v.id+"' />";
}
/*
 * 为当前帐户设置角色
 */
function setAccountRole(obj, id){
	if(!id) return;
	$.post('mgr/account/addAccountRole', {
		id : id, 
		account : curAcctName, 
		add : $(obj).is(':checked')
	}, function(data){
		if(!$.isSuccess(data)) return;
	}, 'json');
}

/*
 * 获取部门列表
 * . [K]
 */
function findDepartment(){
	$.getJSON('./mgr/findDepartment', function(data){
		var departmentList = $('select.department').empty().append("<option value=0>请选择部门</option>");
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			$('<option value='+v.id+'></option>').append(v.name).appendTo(departmentList);
		});
	});
}
/*
 * 根据部门ID, 获取职位列表
 * . [K]
 */
function findPosition(deptId){
	var positionList = $('select.position').empty().append("<option value=0>请选择职位</option>");
	if(deptId < 1) return;
	$.getJSON('./mgr/findPositionByDeptId', {deptId : deptId}, function(data){
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i, v){
			$('<option value='+v.id+'></option>').append(v.name).appendTo(positionList);
		});
	});
}
/*
 * 根据部门ID, 获取职位列表
 * . [K]
 */
function findMdoifyPosition(deptId){
	var positionList = $('select.mdoifyPosition').empty().append("<option value=0>请选择职位</option>");
	if(deptId < 1) return;
	$.getJSON('./mgr/findPositionByDeptId', {deptId : deptId}, function(data){
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i, v){
			$('<option value='+v.id+'></option>').append(v.name).appendTo(positionList);
		});
	});
}
//解析部门
$.analyzeDepartmentOrPosition = function(current, eml, data){
	if(!data) return;
	$.each(data, function(i,v){
		$('<option value='+v.id+' '+$.findOpeion(v.id, current)+'></option>')
		.append(v.name).appendTo(eml);
	});
};