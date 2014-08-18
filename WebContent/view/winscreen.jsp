<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<div style="position: absolute; z-index: -1;">
		<img src="../media/win.png" style="width: 600px;">
	</div>
	<div style="
	    width: 600px;
	    font-size: 100px;
	    font-weight: bold;
	    color: red;
	    padding-top: 200px;
	    text-align: center;
	    text-shadow: 0 0 0.2em #000, 0 0 0.2em #000;"><%=request.getParameter("value") %>đ</div>
</body>
</html>