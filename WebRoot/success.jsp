<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>找回密码</title>
  </head>
  <link type="text/css" rel="stylesheet" href="./css/bootstrap.min.css" />
  <link type="text/css" rel="stylesheet" href="./css/bootstrap-dialog.min.css">
  <link type="text/css" rel="stylesheet" href="./css/commone.css" />
  <script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="./js/bootstrap.min.js"></script>
  <script type="text/javascript" src="./js/bootstrap-dialog.min.js"></script>
  <script type="text/javascript" src="./js/common.js"></script>
  <script type="text/javascript">
  		function updatePass(){
  			$.isSubmit = true;
			var key = $.verifyForm($('input.key'), false);
			var userName = $.verifyForm($('input.userName'), true);
			var password = $.verifyForm($('input.password'), true);
			var passwordConfirm = $.verifyForm($('input.passwordConfirm'), true);
			if(!$.isSubmit) return;
			if(password != passwordConfirm){
				var bdg=BootstrapDialog.show({
					title:"提示信息",
					type:BootstrapDialog.TYPE_PRIMARY,
					closable : true,
					message :"再次密码输入不一致!"
				});
				setTimeout(function(){
					bdg.close();
				},1500);
				return;
			}
			dialog = BootstrapDialog.loading();
			$.post('./mgr/0/updatePass', {
				key : key,
				userName : userName,
				password : password,
			}, function(data) {
				alert(data.body);
				if (data.head) {
					window.location.href = 'login.html';
				return;
				}
			}, 'json');
  		}
  </script>  
  <body>
  <input type="hidden" class="key" value="${requestScope.key}">
  <table>
  <tr>
    <td>用户名：</td><td><input type="text" class="userName" value="${requestScope.userName}" readonly="readonly"></td>
   </tr>
   <tr>
    <td>新密码：</td><td><input type="password" class="password"></td>
    </tr>
   <tr>
    <td>确认密码：</td><td><input type="password" class="passwordConfirm"></td>
    </tr>
    <tr>
    <td colspan="1"><input type="button" value="提交" onclick="updatePass()"></td>
    </tr>
    </table>
  </body>
</html>
