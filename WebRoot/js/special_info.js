var empl = {};	// 专题所有信息
var submitText = '提交信息失败, 请注意被标识为红色的部分!';
var moduleCode = $.getUrlParam('moduleCode');
moduleCode = !moduleCode ? '02002' : moduleCode;	// 获取url传递过来的模块编号, 默认为01001
empl.id = !$.getUrlParam('specId') ? 0 : $.getUrlParam('specId');	// 员工ID
// 动态生成url, 通过获取专题编号来决定
var url = !$.getUrlParam('specId') ? 'saveSpecialInfo' : 'modifySpecialInfo';

var reg = {};	// 正则表达式
reg.identity = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;	// 身份证
reg.date = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/;	// 日期验证
reg.phone = /^1[3|4|5|8][0-9]\d{4,8}$/;	// 手机号码
reg.integer = /^[0-9]*[1-9][0-9]*$/;	// 正整数大于0
reg.double = /^\d+(\.\d+)?$/;	// 小数或整数, 含0
reg.number = /^[1-9]\d*|[0]{1,1}$/;	// 正整数, 含0

function initFun() {
	if(!secure.find)  $('div.edit-employees-box').remove();
	if(secure.find){
		//dialog = BootstrapDialog.loading();
		$('div.edit-employees-box').removeClass('hide');
		if(empl.id){
			addBreadcrumb('修改专题');
			//appendTable();
			$('.guanli').css('display','blank');
			var sjs='2,19';
			findSjByPosId(sjs);// 获取所有设计人员
			var qds='1,3';
			findQdByPosId(qds);// 获取所有前端人员
			var ids='20,21,22,23,24';
			findHdByPosId(ids);// 获取所有后端人员
			setTimeout(function(){$.findSpecialInfo(empl.id);},100);//0.1秒延迟 用于确保加载完人员列表
			//$.findSpecialInfo(empl.id);
			return;
		}
		$('div.edit-employees-box').removeClass('hide');
		$('div.new-title').text("新增专题");
		$('input.editors').val($('#acctount').val());
		$('.download').css('display','none');
		$('.guanli').css('display','none');
		//addBreadcrumb('新增专题');
		//findDepartment(0); // 获取所有部门
		//findChoose(0); // 获取所有单选框
		//$.findAllProvince(); // 获取所有省份
	}
}

/*
 * 获取设计人员
 */
function  findSjByPosId(current){
	$.deparsj = $('select.empsj').empty().append('<option value="">请选择人员</option>');
	$.getJSON('./mgr/account/findAccountInPosId',{posIds:current}, function(data){
		$.analyzeDepartmentOrPosition(current, $.deparsj, data.body);
	});
}
/*
 * 获取前端人员
 */
function findQdByPosId(current){
	$.deparqd = $('select.empqd').empty().append('<option value="">请选择人员</option>');
	$.getJSON('./mgr/account/findAccountInPosId',{posIds:current}, function(data){
		$.analyzeDepartmentOrPosition(current, $.deparqd, data.body);
	});
}
/*
 * 获取后端人员
 */
function findHdByPosId(current){
	$.deparhd = $('select.emphd').empty().append('<option value="">请选择人员</option>');
	$.getJSON('./mgr/account/findAccountInPosId',{posIds:current}, function(data){
		$.analyzeDepartmentOrPosition(current, $.deparhd, data.body);
	});
}
//解析人员
$.analyzeDepartmentOrPosition = function(current, eml, data){
	if(!data) return;
	$.each(data, function(i,v){
		$('<option value='+v.acctNickname+' '+$.findOpeion(v.acctNickname, current)+'></option>')
		.append(v.acctNickname).appendTo(eml);
	});
};

//获得日期插件
function getWdatePicker(){
	WdatePicker();
}

//附件上传
function enclosureUp(){
    // 提交表单  
    $("#upload").ajaxSubmit({  
        url : "./mgr/special/uploadEnclosure", // 请求的url  
        type : "post", // 请求方式  
        dataType : "json", // 响应的数据类型  
        async :true, // 异步  
        success: function (data) {  
        	if(data.body!="格式不正确"&&data.body!="未选择文件"){
        		$(".enclosure").val(data.body);
        		alert("上传成功");
        	}else{
        		alert(data.body);
        	}
        },  
        error : function(){  
            alert("数据加载失败！");  
        }  
    });
}

//附件下载
function enclosuredownload(){
	var download=$(".enclosuredownload").val();
	window.open('./mgr/special/downloadEnclosure?filename='+download);
}

/*
 * 绑定完成日期
 */
function setCompleteTime(){
	if($('.state').val()==2){
		$('.completeTime').val(new Date().Format("yyyy-MM-dd"));
	}else if($('.state').val()==3){
		$('.completeTime').val($('#hiddenTime').val());
	}else{
		$('.completeTime').val("");
	}
}

/*
 * jQuery 扩展
 * . [K]
 */
function setReadonly(){
	$("#colorTest").attr("readonly","readonly");
}

function revokeReadonly(){
	$("#colorTest").removeAttr("readonly");
}

function needServer(){
	$("[name='serverContent']").attr("disabled",false);
}

function noNeedServer(){
	$("[name='serverContent']").attr("disabled","disabled");
}

//特殊赋值
function specialchk(){
	var pageStyle =[];
	$('input[name="pageStyle"]:checked').each(function(){
		pageStyle.push($(this).val());
	});
	if($.trim($('#pageStyle').val())!=null){
		pageStyle.push($('#pageStyle').val());
	}
	empl.pageStyle=pageStyle.toString();
	var mainColors=null;
	if($('#zd').is(':checked')){ 
		mainColors=$('#colorTest').val();
	}else{
		mainColors=$("input[name='mainColors']:checked").val();
	}
	empl.mainColors=mainColors.toString();
	var picture =[];
	$('input[name="picture"]:checked').each(function(){
		picture.push($(this).val());
	});
	if($.trim($('#picture').val())!=null){
		picture.push($('#picture').val());
	}
	empl.picture=picture.toString();
	var serverContent =[];
	if($('input[name="server"]:checked').val()=='1'){
		$('input[name="serverContent"]:checked').each(function(){
			serverContent.push($(this).val());
		});
		if($.trim($('#serverContent').val())!=null){
			serverContent.push($('#serverContent').val());
		}
	}
	empl.serverContent=serverContent.toString();
	empl.enclosure=$('.enclosure').val();
	empl.sj=$('.empsj').val();
	empl.qd=$('.empqd').val();
	empl.hd=$('.emphd').val();
	empl.other = $('.other').val();
	empl.sjTime=$('.sjTime').val();
	empl.qdTime=$('.qdTime').val();
	empl.hdTime=$('.hdTime').val();
	empl.state=$('.state').val();
	empl.strCompleteTime =$('.completeTime').val();
}
//验证文本框, 传入获取文本框元素的标识, 是否为空,  加正则匹配
$.input = function(className, empty, regular){
	$(className).parent().prev().removeClass('data-empty');
	var val = $.removeTrim($(className).val());
	if(!empty) return val;
	if(empty && !regular && val) return val;
	if(regular != null && regular.test(val)) return val;
	$.isSubmit = false;
	$(className).parent().prev().addClass('data-empty');
};
//获取单选按钮
$.radio = function(className, empty, regular){
	$(className).parent().parent().prev().removeClass('data-empty');
	var val = $(className).filter(':checked').val();
	if(!empty) return val;
	if(empty && !regular && val) return val;
	if(regular != null && regular.test(val)) return val;
	$.isSubmit = false;
	$(className).parent().parent().prev().addClass('data-empty');
};
//验证下拉列表
$.select = function(className, empty, regular){
	$(className).parent().prev().removeClass('data-empty');
	var val = $(className).val();
	if(!empty) return val;
	if(empty && !regular && val && val != 0) return val;
	if(regular != null && regular.test(val)) return val;
	$.isSubmit = false;
	$(className).parent().prev().addClass('data-empty');
};
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
};

/*
 * 验证表单数据
 * . [K]
 */
function detectionForm(){
	$.isSubmit = true;	// 重置表单为可提交
	// 专题信息
	empl.specialName = $.input('.specialName', true, null);	// 专题名称
	empl.editors = $.input('.editors', true, null);	// 策划编辑
	empl.area = $.select('.area', true, null);	// 区县
	empl.strStartTime = $.input('.startTime', true, reg.date);	// 策划上线时间
	empl.wcmVersion = $.radio('.wcmVersion', true, null);	// 搭建平台
	empl.pageDesign = $.radio('.pageDesign', true, reg.integer);	// 页面设计
	empl.flashDesign = $.radio('.flashDesign', true, reg.integer);  //flash动画设计
	empl.server = $.radio('.server', true, reg.integer);  //功能程序开发
	specialchk();
	if(!$.isSubmit)
		//BootstrapDialog.msg(submitText, BootstrapDialog.TYPE_DANGER);
		var bdg=BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_DANGER,
			closable : true,
			message : submitText
		});
		setTimeout(function(){
			bdg.close();
		},1500);
	return $.isSubmit;
};
/*
 * 返回单选按钮
 * . [K]
 */
function getLabelRadio(current, id, name, text){
	return $("<label class='label'></label>")
	.append($("<input type='radio' class='"+name+"' value='"+id+"' name='"+name+"' "+ $.findChecked(current == id) +" />"))
	.append($("<span class='label-text float-left'></span>").append(text));
}
/*
 *复选框绑定参数
 *author：tdx 
 */
function checkBoxGiveValue(dblist,htmllist,textarea){
	for(var i=0;i<dblist.length;i++){
		var a=0;
		for(var j=0;j<htmllist.length;j++){
			if(dblist[i]==htmllist[j]){
				$('input[value="'+htmllist[j]+'"]').prop('checked',true);
				a=1;
			}
		}
		if(dblist[i]!=null&&a==0){
			$('#'+textarea+'').val(dblist[i]);
		}
	}
}

/*
 * 保存员工信息
 * . [K]
 */
function saveOrModifySpecialInfo(){ 
	detectionForm();
	if(!$.isSubmit) return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('./mgr/special/'+url,{data : JSON.stringify(empl)}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		if(data.body=='修改成功'||data.body=='添加成功!'){
			var bfg=BootstrapDialog.show({
				title:"提示信息",
				type:BootstrapDialog.TYPE_SUCCESS,
				closable : true,
				message : "专题保存成功!",
				onhidden : function(dialogRef) {
					window.location.href = "specil_list.html";
				}
			});
			setTimeout(function(){
				bfg.close();
			},1500);
			
		}else{
			BootstrapDialog.show({
				title:"评分反馈",
				type:BootstrapDialog.TYPE_SUCCESS,
				closable : true,
				message : $('<div class="message" id="'+data.body+'"></div>').load('special_score.html'),
			});
		}
	}, 'json');
};
/*
 * 获取专题基本信息
 * . [K]
 */
$.findSpecialInfo = function(specId){
	if(!specId) return;
	$.getJSON('./mgr/special/findSpecialById',{specId:specId}, function(data){
		if(!$.isSuccess(data)) return;
		var info = data.body;
		$('#hiddenId').val(info.id);
		$('input.specialName').val(info.specialName);
		$('div.new-title').text("修改 \""+info.specialName+"\" 信息");
		$('input.editors').val(info.editors);
		$('select.area').val(info.area);
		var startTime = new Date(info.startTime);
		$('input.startTime').val(startTime.Format("yyyy-MM-dd"));
		$('input.wcmVersion').each(function(){
			if($(this).val()==info.wcmVersion){
				$(this).prop('checked',true);
			}
		});
		$('input.pageDesign').each(function(){
			if($(this).val()==info.pageDesign){
				$(this).prop('checked',true);
			}
		});
		var pageStyle=info.pageStyle.split(',');
		var inputpageStyle=[];
		$('input[name="pageStyle"]').each(function(){
			inputpageStyle.push($(this).val());
		});
		checkBoxGiveValue(pageStyle,inputpageStyle,"pageStyle");
		//主色调赋值
		$('input.mainColors').each(function(){
			var a=0;
			if($(this).val()==info.mainColors){
				$(this).prop('checked',true);
				a=1;
			}
			if($(this).val()!=null&&a==0){
				$('#zd').prop('checked',true);
				$('#colorTest').val(info.mainColors);
			}
		});
		$('input.flashDesign').each(function(){
			if($(this).val()==info.flashDesign){
				$(this).prop('checked',true);
			}
		});
		var picture=info.picture.split(',');
		var inputpageStyle=[];
		$('input[name="picture"]').each(function(){
			inputpageStyle.push($(this).val());
		});
		checkBoxGiveValue(picture,inputpageStyle,"picture");
		$('input.server').each(function(){
			if($(this).val()==info.server){
				$(this).prop('checked',true);
			}
		});
		var serverContent=info.serverContent.split(',');
		var inputpageStyle=[];
		$('input[name="serverContent"]').each(function(){
			inputpageStyle.push($(this).val());
		});
		needServer();
		checkBoxGiveValue(serverContent,inputpageStyle,"serverContent");
		if(info.enclosure==""){
			$(".download").css('display','none');
		}else{
			$(".upload").css('display','none');
			$(".enclosuredownload").val(info.enclosure);
		}
		$('textarea.other').val(info.other);
		$('select.empsj').val(info.sj);
		$('select.empqd').val(info.qd);
		$('select.emphd').val(info.hd);
		$('input.sjTime').val(info.sjTime);
		$('input.qdTime').val(info.qdTime);
		$('input.hdTime').val(info.hdTime);
		$('select.state').val(info.state);
		if(info.completeTime!=null){
			var completeTime = new Date(info.completeTime);
			$('input.completeTime').val(completeTime.Format("yyyy-MM-dd"));
			$('#hiddenTime').val(completeTime.Format("yyyy-MM-dd"));
		}
	});
};
