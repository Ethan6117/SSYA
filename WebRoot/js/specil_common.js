var moduleCode = '02001';
var departmentId = 0;

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

function exportExcel() {
	var url = 'mgr/special/exportExcel?area=' + $('.area').val()
			+ '&searchValue=' + $('.searchInput').val() + '&state='
			+ $('.state').val() + '&strTime=' + $('.strTime').val()
			+ '&endTime=' + $('.endTime').val() + '';
	url = encodeURI(encodeURI(url));
	window.location.href = url;
}
/*
 * 获取专题列表 . [K]
 */
function findListInfo() {
	$.post('mgr/special/findSpecialList', {
		page : page,
		searchValue : $('.searchInput').val(),
		area : $('.area').val(),
		state : $('.state').val(),
		strTime : $('.strTime').val(),
		endTime : $('.endTime').val()
	}, function(data) {
		var sjSum = 0;
		var qdSum = 0;
		var hdSum = 0;
		var zSum = 0;
		dialog.close();
		var tbody = $('tbody.tbody').empty();
		if (!$.isSuccess(data))
			return;
		$.each(data.body, function(i, v) {
			$('<tr></tr>').append($('<td></td>').append(v.id))
				.append($('<td></td>').append(v.specialName))
				.append($('<td></td>').append(v.editors))
				.append($('<td></td>').append(v.area))
				.append($('<td></td>').append(v.strStartTime))
				.append($('<td></td>').append(v.strCompleteTime))
				.append($('<td></td>').append(v.sj + '&nbsp;' + v.qd + '&nbsp;' + v.hd))
				.append($('<td></td>').append(v.sjTime))
				.append($('<td></td>').append(v.qdTime))
				.append($('<td></td>').append(v.hdTime))
				.append($('<td></td>').append(parseFloat(v.sjTime) + parseFloat(v.qdTime) + parseFloat(v.hdTime)))
				.append($('<td></td>').append(v.strState))
				.append($('<td></td>').append(analyzeBtns(v))).appendTo(tbody);
			sjSum = sjSum + parseFloat(v.sjTime);
			qdSum = qdSum + parseFloat(v.qdTime);
			hdSum = hdSum + parseFloat(v.hdTime);
			zSum = zSum	+ (parseFloat(v.sjTime) + parseFloat(v.qdTime) + parseFloat(v.hdTime));
		});
		$('<tr></tr>').append($('<td></td>').append('总计'))
			.append($('<td></td>').append())
			.append($('<td></td>').append())
			.append($('<td></td>').append())
			.append($('<td></td>').append())
			.append($('<td></td>').append())
			.append($('<td></td>').append())
			.append($('<td></td>').append(sjSum.toFixed(1)))
			.append($('<td></td>').append(qdSum.toFixed(1)))
			.append($('<td></td>').append(hdSum.toFixed(1)))
			.append($('<td></td>').append(zSum.toFixed(1)))
			.append($('<td></td>').append())
			.append($('<td></td>').append()).appendTo(tbody);
	}, 'json');
	$.post('mgr/special/findSpecialCount', {
		page : page,
		searchValue : $('.searchInput').val(),
		area : $('.area').val(),
		state : $('.state').val(),
		strTime : $('.strTime').val(),
		endTime : $('.endTime').val()
	}, function(data) {
		$.analysisPage(data.body);
	}, 'json');
}
/*
 * 分析操作按钮 . [K]
 */
function analyzeBtns(v) {
	var btns = "";
	btns += secure.modify ? "<button type='button' class='btn btn-primary btn-xs' onclick='showModifyBox("
			+ v.id
			+ ")'><span class='glyphicon glyphicon-pencil'></span>编辑</button>"
			: "";

	btns += secure.del ? "<button type='button' class='btn btn-danger btn-xs' onclick='hintDelete("
			+ v.id
			+ ")'><span class='glyphicon glyphicon-remove'></span>删除</button>"
			: "";

	btns += secure.score ? "<button type='button' class='btn btn-success btn-xs' onclick='showScoreBox("
			+ v.id
			+ ")'><span class='glyphicon glyphicon-align-left'></span>评分</button>"
			: "";
	return btns;
}

/*
 * 提示并确定删除专题信息 . [K]
 */
function hintDelete(id) {
	if (!id)
		return;
	BootstrapDialog.confirm("请确认是否要删除该专题?", function(result) {
		if (!result)
			return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/special/deleteSpecial', {
			specId : id
		}, function(data) {
			dialog.close();
			if (!$.isSuccess(data))
				return;
			findListInfo();
			// BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
			var bdg = BootstrapDialog.show({
				title : "提示信息",
				type : BootstrapDialog.TYPE_SUCCESS,
				closable : true,
				message : data.body
			});
			setTimeout(function() {
				bdg.close();
			}, 1500);
		});
	});
}
/*
 * 显示专题编辑窗口 . [K]
 */
function showModifyBox(specId) {
	$('.empty').removeClass('empty');
	if (!specId)
		return;
	window.open('./specil_new.html?moduleCode=02001&specId=' + specId);
}

/*
 * 显示评分窗口 . [K]
 */
function showScoreBox(specId) {
	$('.empty').removeClass('empty');
	if (!specId)
		return;
	BootstrapDialog.show({
		title : "评分反馈",
		type : BootstrapDialog.TYPE_SUCCESS,
		closable : true,
		message : $('<div class="info" id="' + specId + '"></div>').load(
				'specialscore_info.html')
	});
}
/*
 * 修改部门信息 . [K]
 */
var dept = {};
function modifyDept(deptId) {
	if (!deptId)
		return;
	$.isSubmit = true;
	dept.deptId = deptId;
	dept.deptName = $.verifyForm($('input.modifyName'), true);
	dept.deptDesc = $.verifyForm($('textarea.modifyDesc'), false);
	if (!$.isSubmit)
		return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/department/modifyDepartment', {
		deptId : dept.deptId,
		name : dept.deptName,
		desc : dept.deptDesc
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data))
			return;
		BootstrapDialog.hideModel($('div.modify-box'));
		// BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
		var bdg = BootstrapDialog.show({
			title : "提示信息",
			type : BootstrapDialog.TYPE_SUCCESS,
			closable : true,
			message : data.body
		});
		setTimeout(function() {
			bdg.close();
		}, 1500);
		findListInfo();
	}, 'json');
}
/*
 * 新窗口打开新建员工信息页面 . [K]
 */
function newInternshipSpecil() {
	window.open('./specil_new.html?moduleCode=02002');
}
