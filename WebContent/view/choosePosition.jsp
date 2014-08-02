<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="controller.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<link href="../css/choosePosition.css" rel="stylesheet" type="text/css">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
    $(".userOff").click(function(){
    	parent.$("#pos").val($(this).attr('id').replace("user-",""));
    	parent.$.fancybox.close();
    });
    });
</script>
</head>
<body>
	<div class="title">CHỌN NGAY VỊ TRÍ THUẬN LỢI NÀO</div>
	<c:forEach var="i" begin="31" end="50">
		<c:if test='<%=WSServer.getSessionRecord(String.valueOf(pageContext.getAttribute("i")))==null%>'>
			<div class="userOff" id="user-<c:out value="${i}" />"><div class="numUser"><c:out value="${i}"/></div></div>
		</c:if>
		<c:if test='<%=WSServer.getSessionRecord(String.valueOf(pageContext.getAttribute("i")))!=null%>'>
			<div class="userOn" id="user-<c:out value="${i}" />"><div class="umale" id="sex-<c:out value="${i}"/>"></div><div class="numUser"><c:out value="${i}"/></div></div>
		</c:if>        	
	</c:forEach>
	<c:forEach var="i" begin="11" end="30">
		<c:if test='<%=WSServer.getSessionRecord(String.valueOf(pageContext.getAttribute("i")))==null%>'>
			<div class="userOff" id="user-<c:out value="${i}" />"><div class="numUser"><c:out value="${i}"/></div></div>
		</c:if>
		<c:if test='<%=WSServer.getSessionRecord(String.valueOf(pageContext.getAttribute("i")))!=null%>'>
			<div class="userOn" id="user-<c:out value="${i}" />"><div class="umale" id="sex-<c:out value="${i}"/>"></div><div class="numUser"><c:out value="${i}"/></div></div>
		</c:if>        	
	</c:forEach>
</body>
</html>