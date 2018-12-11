function findMenu(moduleCode) {
		$.getJSON('findMenu', function(data) {
			if(!$.isSuccess(data)) return;
			var nav = $('ul#nav-box-ul').empty();
			$.each(data.body, function(i, v) {
				if(!v.moduleLevel){
					$('<li class=\'dropdown\' name=\''+v.moduleCode+'\'></li>')
					.append($("<a href='dropdown-toggle' data-toggle='dropdown' href='javascript:void(0)'></a>")
					.append(v.moduleName+"<span class='caret'></span>"))
					.append(analyzeMenu(v.moduleCode, data.body))
					.appendTo(nav);
				}
			});
			findModuleParameter(moduleCode);
		});
	}

	// 获取传递的参数
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	};
	
	/*
 * 解析导航菜单
 * . [K]
 */
function analyzeMenu(code, data){
	var ul = '';
	ul += "<ul class='dropdown-menu'>";
	$.each(data, function(i,v){
		if(v.moduleSuperCode == code) ul += "<li><a href='../"+v.modulePage+"'>"+v.moduleName+"</a></li>"; 
	});
	ul += "</ul>";
	return ul;
}

/*
 * 退出登录
 * . [K]
 */
function exit(){
	BootstrapDialog.confirm("请确认是是否需要注销登录!", function(result){
		if(!result) return;
		window.location.href="../login.html";
		$.getJSON('exit', function(data){
			if(!$.isSuccess(data)) window.location.href="../login.html";
			window.location.href="../login.html"; 
		});
	});
}

$.isSubmit = true;	// 是否可提交
$.verifyForm = function(eml, isEmpty){
	eml.removeClass('empty');
	if(!isEmpty) return eml.val();
	var val = eml.val();
	if(val < 1 || val.length < 1){
		$.isSubmit = false;
		eml.addClass('empty');
	}
	return val;
};

BootstrapDialog.isSubmitted = function(){
	return BootstrapDialog.show({
		title : "正在提交",
		closable : false,
		message: "请稍等, 正在提交请求!"
    });
};

//判断返回数据的JSON头是成功还是失败
$.isSuccess = function(data) {
	if(data.head) return data.head;
	
	if(!data.body) return;
	
	if((data.body == 'PERMISSION_DENIED' || data.body == 'UNLOGIN') && dialog != null){
		
		dialog.close();
		if(data.body == 'UNLOGIN'){
			
			window.location.href="../login.html";
			var bdg=BootstrapDialog.show({
				title : "错误",
				type : BootstrapDialog.TYPE_DANGER,
				message : data.body,
				onhide : function(dialog){window.location.href="../login.html"; }
			});
			setTimeout(function(){
				bdg.close();
			},1500);
			return;
		}
	}
  if((data.body == 'PERMISSION_DENIED' || data.body == 'UNLOGIN')){
        window.location.href="../login.html";
	}
	var bdg=BootstrapDialog.show({
		title : "错误",
		type : BootstrapDialog.TYPE_DANGER,
		message : data.body
	});
	setTimeout(function(){
		bdg.close();
	},1500);
	return data.head;
};

function findModuleParameter(moduleCode) {
	if(!moduleCode) return;
	$.getJSON('findModuleParameter', {moduleCode : moduleCode}, function(data){
		if(!$.isSuccess(data)) return;
		$('a.acctInfo').append(data.body.acctount);
		$('#acctount').val(data.body.acctount);
		secure = data.body;
		if(!secure.find){
			$('div.main').remove();	// 删除页面主要元素
			//BootstrapDialog.msg("非法操作, 你没有当前页面的权限!", BootstrapDialog.TYPE_DANGER);
			var bdg=BootstrapDialog.show({
				title:"提示信息",
				type:BootstrapDialog.TYPE_DANGER,
				closable : true,
				message : "非法操作, 你没有当前页面的权限!"
			});
			setTimeout(function(){
				bdg.close();
			},1500);
			return;
		}
		$('div.main').removeClass('hide');
		if("0" != moduleCode){
			var obj = $('ol.breadcrumb').empty();
			obj.append($("<li></li>").append(data.body.superModuleName));
			obj.append($("<li ckass='active'></li>").append(data.body.moduleName));
			$('.navbar-nav').find('.dropdown[name='+data.body.code+']').addClass('active');
			
			document.title="三思益安 | "+data.body.moduleName + ' - ' + data.body.superModuleName+" [www..]";
		}
	});
}