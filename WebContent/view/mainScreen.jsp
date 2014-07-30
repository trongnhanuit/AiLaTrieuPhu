<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/mainScreen.css" rel="stylesheet" type="text/css">
<title>NHV - Ai là triệu phú</title>
</head>
<body>
	<div class="c1">
		<div class="c11">
				<c:forEach var="i" begin="30" end="49">
		            <div class="userOff" id="user-<c:out value="${i}" />">
		        		 <div class="umale" id="sex-<c:out value="${i}"/>"></div>
		           		 <div class="numUser"><c:out value="${i}"/></div>
		       		 </div>
	          </c:forEach>
	          <c:forEach var="i" begin="10" end="29">
		            <div class="userOff" id="user-<c:out value="${i}" />">
		        		 <div class="umale" id="sex-<c:out value="${i}"/>"></div>
		           		 <div class="numUser"><c:out value="${i}"/></div>
		       		 </div>
	          </c:forEach>
		</div>
		<div class="c12">
			<c:forEach var="i" begin="0" end="9">
	            <div class="userOff" id="user-<c:out value="${i}" />">
	        		 <div class="umale" id="sex-<c:out value="${i}"/>"></div>
	           		 <div class="numUser"><c:out value="${i}"/></div>
	       		 </div>
		   	</c:forEach>
		</div>
	</div>
	<div class="c2">
		<div class="c2l">
			<div class="c2l1"></div>
			<div class="c2l2"></div>
		</div> 
		<div class="c2c"></div> 
		<div class="c2r">
			<div class="c2r1"></div>
			<div class="c2r2"></div>
		</div> 
	</div>
</body>
</html>