<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="controller.WSServer"%>
<!DOCTYPE html>
<html>
<head>
<title>Đăng nhập - Đăng kí</title>
<link rel="stylesheet" href="../css/login.css">
<link rel="shortcut icon" type="image/png" href="../media/logo.png"/>
<script src="../js/jquery-1.11.1.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/login.js"></script>
<script>
$(function() {
  $( "#tabs" ).tabs();
  $("#audience").load("sub_login.jsp"); 
});
</script>
<% 
boolean isFirstTime=WSServer.isFirstTime;
if (WSServer.isFirstTime)
	WSServer.isFirstTime=false;%>
<script>
$(document).ready(function(){
	var isFirst="<%= isFirstTime%>";
	if (isFirst=="true")
	{
	  $.removeCookie("func00", {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	  $.removeCookie("func01", {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	  $.removeCookie("func02", {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	  $.removeCookie("func03", {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	  $.cookie("func00", 0, {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	  $.cookie("func01", 0, {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	  $.cookie("func02", 0, {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	  $.cookie("func03", 0, {path: 'http://localhost:8080/AiLaTrieuPhu/view/mc.jsp'});
	}
});
</script>
<%! String error;%>
<% error="";
Cookie[] cookies=request.getCookies();
if (cookies!=null)
    for(Cookie cookie:cookies)
        if (cookie.getName().equals("error"))
            error=URLDecoder.decode(cookie.getValue(), "UTF-8");
%>
</head>
<body>
<div class="wrapper">
<div class="container">
<center><img src="../media/logo.png"/></center>	
<div id="tabs">
  <ul>
    <li><a href="#login">Đăng nhập</a></li>
    <li><a href="#register">Đăng kí</a></li>
  </ul>
  <div id="login">
    <form method="POST" action="http://localhost:8080/AiLaTrieuPhu/LoginController">
    	<label for="usernamel">Tên đăng nhập:</label>
    	<br/>
    	<input type="text" name="usernamel" id="usernamel" required/>
    	<div class="error"><%= (String) error %></div>
    	<br/>
    	<label for="password">Mật khẩu:</label>
    	<br/>
    	<input type="password" name="password" id="password" required />
    	<br/>
    	<br/>
    	<input type="submit" value="Đăng nhập">
    </form>
  </div>
  <div id="register">
  	<form method="post" action="http://localhost:8080/AiLaTrieuPhu/SignupController">
  		<label for="playername">Họ và tên:</label><br/>
    	<input type="text" name="playername" id="playername" required />
    	<br/>
    	<label for="username">Tài khoản:</label><br/>
    	<input type="text" class="username" name="username" id="username" pattern=".{3,}" required/><span class="status"></span>  
    	<br/>
    	<label for="password">Mật khẩu:</label><br/>
    	<input type="password" name="password" id="password" required title="Mật khẩu phải ít nhất 6 kí tự, có chứa chữ hoa/ chữ thường và số" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}"   this.setCustomValidity(this.validity.patternMismatch ? this.title : '');
  			if(this.checkValidity()) form.pwd2.pattern = this.value;"/>
		<br/>
		<label for="repassword"> Nhập lại mật khẩu:</label><br/>
		<input title="Hãy nhập lại mật khẩu vừa nhập ở trên chính xác" type="password" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="repassword" onchange="
		  this.setCustomValidity(this.validity.patternMismatch ? this.title : '');">
    	<br/>
    	<label for="sex">Giới tính:</label><br/>
    	<select name="sex" id="sex" required>
    		<option value="Male">Nam</option>
    		<option value="Female">Nữ</option>
    	</select>
    	<br/>
    	<label for="birthday">Năm sinh:</label><br/>
    	<input type="number" name="birthday" id="birthday" min="1950" max="2000" size="6" required/>
    	<br/>
    	<label for="address">Địa chỉ:</label><br/>
    	<input type="text" name="address" id="address" pattern=".{3,44}" required/>
    	<br/>
    	<label for="govermentid">Số CMND:</label><br/>
    	<input type="text" class="govermentid" name="govermentid" id="govermentid" required/><span class="status2"></span> 
    	<br/>
    	<input type="submit" value="Đăng kí">
  	</form>
  </div>
</div>
</div>
<div id="audience"></div>
</div>
</body>
</html>