<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="controler.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/mainScreen.css" rel="stylesheet" type="text/css">
<title>NHV - Ai là triệu phú</title>
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
var ws = new WebSocket("ws://localhost:8080/AiLaTrieuPhu/servertest");
//Connected to socket server, get @param message.
ws.onopen = function(){
	$("#status").html("Send request to Server...<br/>");
};
	// Receive data from server
ws.onmessage = function(message)
{
	$("#status").html($("#status").html()+message.data);
	if (message.data.indexOf("AUDIENCE IN: ")==0)
		{
			$("#user-"+message.data.replace("AUDIENCE IN: ","")).attr('class', 'userOn');
			$("#user-"+message.data.replace("AUDIENCE IN: ","")).html($("#user-"+message.data.replace("AUDIENCE IN: ","")).html()+'<div class="umale" id="sex-'+message.data.replace("AUDIENCE OUT: ","")+'"></div>');
		}
	if (message.data.indexOf("AUDIENCE OUT: ")==0)
		{
			$("#user-"+message.data.replace("AUDIENCE OUT: ","")).attr('class', 'userOff');
			$("#user-"+message.data.replace("AUDIENCE OUT: ","")).html('<div class="numUser">'+message.data.replace("AUDIENCE OUT: ","")+'</div>');
		}		
		
};
ws.onclose = function(){
	ws.close();
};
$(function(){
    $("#btnSend").click(function(){
        var s=$("#txtcontent").val();
        ws.send(s);
    });
    });

$.urlParam = function(name){
    var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
}
</script>
</head>
<body>
	<div class="c1">
		<div class="c11">
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
		</div>
		<div class="c12">
			<c:forEach var="i" begin="1" end="10">
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
		<div class="c2c">
			Enter value to send: <input type="text" id="txtcontent" value="test"> <br/>
			<input type="button" value="send" id="btnSend"><br/>
			Status: <div id="status"></div>
		</div> 
		<div class="c2r">
			<div class="c2r1"></div>
			<div class="c2r2"></div>
		</div> 
	</div>
</body>
</html>