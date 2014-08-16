<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	var count = 30;
	setInterval(timer, 1000);
	function timer()
	{
	  count=count-1;
	  document.getElementById("timer").innerHTML=count;
	  switch(count)
	  {
	  case 29: document.getElementById("chat").innerHTML+="<br /><b>Tôi:</b> Bạn nghe rõ tôi đọc câu hỏi nhé!"; break;
	  case 28: document.getElementById("chat").innerHTML+="<br /><b>Người thân: </b> Tôi đang nghe và sẵn sàng đây "; break;
	  case 26: document.getElementById("chat").innerHTML+="<br /><b>Tôi: </b> Câu hỏi là hsdhjsdvhfsvhf "; break;
	  case 23: document.getElementById("chat").innerHTML+="<br /><b>Người thân: </b>"+document.getElementById("ansr").innerHTML; break;
	  case 22: document.getElementById("chat").innerHTML+="<br /><b>Tôi: </b> Cám ơn bạn nhiều nhé"; break;
	  }
	  if (count <= 0)
	  {
		  document.getElementById("timer").innerHTML="Hết giờ";
	  }
	}
</script>
<style>
#ansr
{display:none;}
</style>
</head>
<body>
<span id="timer"></span>
<div id="ansr">
	<% if(request.getParameter("data").length()==1)
			out.println("À, câu này theo tôi biết đáp án chắc chắn là "+request.getParameter("data").toUpperCase()+" đó bạn!");
		else
			out.println("Xin lỗi bạn! Tôi không biết đáp án câu này rồi");
	%></div>
<div id="chat"></div>
</body>
</html>