<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <link type="text/css" rel="stylesheet" href="<%=path%>/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/css/bootstrap-dialog.min.css">
<link type="text/css" rel="stylesheet" href="<%=path%>/css/commone.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/bootstrap-dialog.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/bootstrap-pagy.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jspcommon.js"></script>
<script type="text/javascript">
	var moduleCode = getUrlParam('moduleCode');
	moduleCode = !moduleCode ? '01001' : moduleCode;	// 获取url传递过来的模块编号, 默认为01001
	findMenu(moduleCode);
	
	/*
	 * 修改帐户信息
	 * . [K]
	 */
	function mdoifyAccount(){
		//if(!account.id) return;
		//$.isSubmit = true;
		var accountid = $.verifyForm($('input.modifyAcctId'), true);
		var nickname = $.verifyForm($('input.mdoifyAcctNick'), true);
		var mobile = $.verifyForm($('input.mdoifyMobile'), false);
		var telephone = $.verifyForm($('input.mdoifyTelephone'), false);
		var email = $.verifyForm($('input.mdoifyEmail'), false);
		//if(!$.isSubmit) return;
		//dialog = BootstrapDialog.isSubmitted();
		$.post('account/modifyNickname', {
			id : accountid,
			nickname : nickname,
			email : email,
			mobile:mobile,
			telephone:telephone,
			department:"",
			position:""
		}, function(data){
			dialog.close();
			if(!$.isSuccess(data)) return;
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
</script>
<style>
.login-tr{ display: inline-block;
    margin-left: 31px;
}
</style>
<div class="nav navbar navbar-inverse navbar-fixed-top">
            <div class="container">
               <a href="<%=path%>/index.html" class="navbar-brand"><span class="glyphicon glyphicon-home"></span>&nbsp;首页</a>
               <ul class="nav navbar-nav" id="nav-box-ul"></ul>
               <ul class="nav navbar-nav pull-right">
               		<li><a href="javascript:void(0)" class="acctInfo" data-toggle="modal" ><span class="glyphicon glyphicon-user"></span>&nbsp;</a></li>
                	<li><a href="javascript:void(0)" onclick="exit()">注销</a></li>
               </ul>
            </div>
        </div>
<div class="container main hide">
	<div class='modal-header'><h4 class='modal-title'>修改个人资料</h4></div>
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header"><h4 class="modal-title">修改账户</h4></div>
			<div class="modal-body">
				<input type="hidden" class="form-control modifyAcctId" maxlength="64" value="${acctId}"/>
				<input type="text" class="form-control margin-top-15 mdoifyAcctNick" placeholder="姓名" maxlength="64" value="${nickName}" />
				<input type="text" class="form-control margin-top-15 mdoifyMobile" placeholder="手机号" maxlength="11" value="${mobile}" />
				<input type="text" class="form-control margin-top-15 mdoifyTelephone" placeholder="工作固话" maxlength="20" value="${telephone}" />
				<input type="text" class="form-control margin-top-15 mdoifyEmail" placeholder="邮箱" maxlength="50" value="${email}" />
			</div> 
			<div class="modal-footer">
				<!-- <button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
				<button type="button" class="btn btn-primary" onclick="mdoifyAccount()">修改</button>
			</div>
		</div>
	</div>
<footer>北京市三思益安科技有限责任公司 <a href="http://www.ssya.net" target="_blank">www.ssya.net</a></footer>
</div>
</html>
