var pro = {};	// 项目所有信息
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
			$('div.new-title').text("项目信息");
			$.findProjectInfo(pro.id);
			return;
		}
	}
}

/*
 * 获取项目基本信息
 * . [K]
 */
$.findProjectInfo = function(proId){
	if(!proId) return;
	$.getJSON('./mgr/project/findProjectInfo',{proId:proId}, function(data){
		if(!$.isSuccess(data)) return;
		var info = data.body;
		$('td.projectName').append(info.projectName);
		$('td.client').append(info.client);
		$('td.partner').append(info.partner);
		$('td.linkman').append(info.linkman);
		$('td.phone').append(info.phone);
		/*var array=$("td.manager").find("option");
		var i;
		$.each(array, function(i, v) {
			if(v.value==info.manager)
				return;
		});
		if(i!=array.length)
			$('select.manager option:first').after("<option value='"+info.manager+"'>"+info.manager+"</option>");*/
		$('td.manager').append(info.manager);
		/*var participants=info.participants;
		var inputparticipants=[];
		$('input[class="participants"]').each(function(){
			inputparticipants.push($(this).val());
		});
		checkBoxGiveValue(participants,inputparticipants,"participants");*/
		$('td.participants').append(info.participants);
		$('td.domainName').append(info.domainName);
		$('td.serverAndLogin').append(info.serverAndLogin);
		$('td.path').append(info.path);
		$('td.summary').append(info.summary);
		$('td.boss').append(info.boss);
		$('td.state').append(info.strState);/*
		isState3=info.state;*/
	});
};

function back(){
	window.location.href="project_list.html"
}