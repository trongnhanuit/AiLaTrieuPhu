<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Đăng nhập - Đăng kí</title>
<link rel="stylesheet" href="../css/jquery-ui.css">
<link rel="stylesheet" href="../css/style.css">
<link rel="shortcut icon" type="image/png" href="../media/logo.png"/>
<script src="../js/jquery-1.11.1.js"></script>
<script src="../js/jquery-ui.js"></script>
<script>
$(function() {
  $( "#tabs" ).tabs();
});
</script>
<script type="text/javascript">  
          $(document).ready(function(){  
              $(".username").change(function(){ 
                  var username = $(this).val();  
                  if(username.length >= 4){  
                      $(".status").html("<img src='../media/loading.gif'><font color=gray> Kiểm tra khả dụng...</font>");  
                       $.ajax({  
                          type: "POST",  
                          url: "http://localhost:8080/AiLaTrieuPhu/CheckAvailability",  
                          data: "username="+ username,  
                          success: function(msg){  
							  if(msg.substring(0,2)==="OK")
								  $(".status").html("<font color=\"green\"><b>"+username+"</b> có thể sử dụng</font>");
							  else
								  {
                                  $(".status").html("<font color=\"red\"><b>"+username+"</b> đã được đăng kí</font>");
                                  $(".username").val("");
								  } 
                          }, 
                      });   
                  }  
                  else{    
                      $(".status").html("<font color=red>Tên đăng nhập phải nhiều hơn <b>4</b> kí tự.</font>");  
                  }  
                    
              });  
          });  
        </script>
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
    <form method="post" action="http://localhost:8080/AiLaTrieuPhu/LoginController">
    	<label for="usernamel">Tên đăng nhập:</label>
    	<br/>
    	<input type="text" name="usernamel" id="usernamel" required/>
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
    	<input type="text" name="govermentid" id="govermentid" required/>
    	<br/>
    	<input type="submit" value="Đăng kí">
  	</form>
  </div>
</div>
</div>
</div>
</body>
</html>