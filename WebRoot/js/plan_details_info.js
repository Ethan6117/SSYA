var pro = {};	// 项目所有信息
var submitText = '提交信息失败, 请注意被标识为红色的部分!';

var moduleCode = $.getUrlParam('moduleCode');
moduleCode = !moduleCode ? '05001' : moduleCode;	// 获取url传递过来的模块编号, 默认为01001
pro.id = !$.getUrlParam('proId') ? 0 : $.getUrlParam('proId');	// 项目ID
// 动态生成url, 通过获取项目编号来决定
var url = !$.getUrlParam('proId') ? 'saveProjectInfo' : 'modifyProjectInfo';

var reg = {};	// 正则表达式
reg.identity = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;	// 身份证
reg.date = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/;	// 日期验证
reg.phone = /^1[3|4|5|8][0-9]\d{4,8}$/;	// 手机号码
reg.integer = /^[0-9]*[1-9][0-9]*$/;	// 正整数大于0
reg.double = /^\d+(\.\d+)?$/;	// 小数或整数, 含0
reg.number = /^[1-9]\d*|[0]{1,1}$/;	// 正整数, 含0

function initFun() {
	if(!secure.find)  
		$('div.edit-employees-box').remove();
	if(secure.find){
		$('div.edit-employees-box').removeClass('hide');
		if(pro.id){
			findName();
			addBreadcrumb('修改项目信息');
			$('div.new-title').text("修改项目信息");
			/*findAccountByRoleId(24); //获得所有项目经理
			var ids='22,23,24';
			findPeoByPosId(ids);// 获取所有技术人员
*/			setTimeout(function(){$.findProjectInfo(pro.id);},100);//0.1秒延迟 用于确保加载完人员列表
			return;
		}
		findName();
		$('div.new-title').text("新增项目信息");
		/*var ids='22,23,24';
		findPeoByPosId(ids);// 获取所有技术人员
		findAccountByRoleId(24); //获得所有项目经理
*/	}
}

function findName() {
	$.ajax({
		type:"POST",
		url:"mgr/account/findNameList",
		dataType:"json",
		success:function(data){
			$.each(data.body, function(j, v) {
					$(".manager").append("<option value='"+ v.nickname +"'>"+ v.nickname +"</option>");
			});
			
			$.each(data.body, function(j, v) {
				$("td.participants ul").append("<li>"+"<input class='participants' type='checkbox' value='"+v.nickname+"' />"+v.nickname+"\t"+"</li>");
			});
			
		}
	});
}
/*
 * 根据角色id获取账号信息
 */
function  findAccountByRoleId(current){
	$.responsible = $('select.responsible').empty().append('<option value="">请选择负责人</option>');
	$.getJSON('./mgr/project/findAccountByRoleId',{roleId:current}, function(data){
		$.analyzeresponsible(current, $.responsible, data.body);
	});
}

/*
 *获得技术人员 
 */
function findPeoByPosId(current){
	$.peo = $('.321');
	$.getJSON('./mgr/account/findAccountInPosId',{posIds:current}, function(data){
		$.analyzeDepartmentOrPosition(current, $.peo, data.body);
	});
}

/*
 *复选框绑定参数
 *author：tdx 
 */
function checkBoxGiveValue(dblist,htmllist,textarea){
	for(var i=0;i<htmllist.length;i++)
		if(dblist.indexOf(htmllist[i])!=-1)
			$('input[value="'+htmllist[i]+'"]').prop('checked',true);
}

/*
 * jQuery 扩展
 * . [K]
 */
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

(function($){
	//解析项目经理
	$.analyzeresponsible = function(current, eml, data){
		if(!data) return;
		$.each(data, function(i,v){
			$('<option value='+v+'></option>')
			.append(v).appendTo(eml);
		});
	};
	
	//解析技术人员
	$.analyzeDepartmentOrPosition=function(current, eml, data){
		if(!data) return;
		$.each(data, function(i,v){
			$('<input type="checkbox" class="participants" name="participants" value='+v.acctNickname+'>'+v.acctNickname+'</input>')
			.append(v.acctNickname).appendTo(eml);
		});
	};
	
})(jQuery);

(function($){
})(jQuery);

$(function(){
});
/*
 * 验证表单数据
 * . [K]
 */
function detectionForm(){
	$.isSubmit = true;	// 重置表单为可提交
	// 项目信息
	pro.projectName = $.input('.projectName', true, null);	// 项目名称
	pro.client = $.input('.client', false, null);	// 客户名称
	pro.partner = $.input('.partner', false, null);	// 员工头像
	pro.linkman = $.input('.linkman', false, null);	// 客户联系人
	pro.phone = $.input('.phone', false, reg.phone);	// 客户电话
	pro.manager = $.select('.manager', true, null);	// 我方负责人
	var participants =[];
	$('.participants:checked').each(function(){
		participants.push($(this).val());
	});
	pro.participants=participants.toString();	// 参与人员
	pro.domainName = $.input('.domainName', false, null);	// 访问方式
	pro.serverAndLogin = $.input('.serverAndLogin', false, null);
	pro.path = $.input('.path', false, null);
	pro.summary = $('.summary').val();	// 项目概述
	pro.boss = $.input('.boss', false, null);	// 主管领导
	pro.state = $.select('.state', true, reg.integer);	// 项目状态
	if(!$.isSubmit){
//			BootstrapDialog.msg(submitText, BootstrapDialog.TYPE_DANGER);
		var bdg=BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_DANGER,
			closable : true,
			message : submitText
		});
		setTimeout(function(){
			bdg.close();
		},1500);
	}
	return $.isSubmit;
};
/*
 * 保存项目信息
 * . [K]
 */
var isState3;
function saveOrModifyProjectInfo(){ 
	if (isState3==3)
	{
		var bdg=BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_DANGER,
			closable : true,
			message : "项目修改失败!",
			onhidden : function(dialogRef) {
				window.location.href = "project_list.html";
			}
		});
		setTimeout(function(){
			bdg.close();
		},1500);
	}
	else{
		detectionForm();
		if(!$.isSubmit) return;
		dialog = BootstrapDialog.isSubmitted();
		$.post("mgr/project/"+url,{data : JSON.stringify(pro)}, function(data){
			dialog.close();
			if(!$.isSuccess(data)) return;
			var bdg=BootstrapDialog.show({
				title:"提示信息",
				type:BootstrapDialog.TYPE_SUCCESS,
				closable : true,
				message : "项目保存成功!",
				onhidden : function(dialogRef) {
					window.location.href = "project_list.html";
				}
			});
			setTimeout(function(){
				bdg.close();
			},1500);
		}, 'json');
	}
};
/*
 * 获取项目基本信息
 * . [K]
 */
$.findProjectInfo = function(proId){
	if(!proId) return;
	$.getJSON('./mgr/project/findProjectInfo',{proId:proId}, function(data){
		if(!$.isSuccess(data)) return;
		var info = data.body;
		$('input.projectName').val(info.projectName);
		$('input.client').val(info.client);
		$('input.partner').val(info.partner);
		$('input.linkman').val(info.linkman);
		$('input.phone').val(info.phone);

		var array=$("select.manager").find("option");
		var i;
		$.each(array, function(i, v) {
			if(v.value==info.manager)
				return;
		});
		if(i!=array.length)
			$('select.manager option:first').after("<option value='"+info.manager+"'>"+info.manager+"</option>");
		$('select.manager').val(info.manager);
		
		var participants=info.participants;
		var inputparticipants=[];
		$('input[class="participants"]').each(function(){
			inputparticipants.push($(this).val());
		});
		checkBoxGiveValue(participants,inputparticipants,"participants");
		$('input.domainName').val(info.domainName);
		$('input.serverAndLogin').val(info.serverAndLogin);
		$('input.path').val(info.path);
		$('.summary').val(info.summary);
		$('input.boss').val(info.boss);
		$('select.state').val(info.state);
		isState3=info.state;
	});
};