<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body style="margin:0px;">
<% String imgsrc="../media/win.png";
	if (request.getParameter("result").equals("failed"))
		imgsrc="../media/failed.png";%>
	<div style="position: absolute; z-index: -1;">
		<img src=<%=imgsrc %> style="width: 600px; height: 318px;">
	</div>
	<div style="
	    width: 600px;
	    font-size: 100px;
	    font-weight: bold;
	    color: red;
	    padding-top: 200px;
	    text-align: center;
	    text-shadow: 0 0 0.2em #000, 0 0 0.2em #000;"><%=request.getParameter("value") %>đ</div>
	<div style="
    color: red;
    font-size: 20px;
    font-weight: bold;
    text-align: center;
    width: 600px;"> HÃY SẴN SÀNG BẮT ĐẦU VÒNG CHƠI MỚI NGAY NÀO!</div>
</body>
</html>