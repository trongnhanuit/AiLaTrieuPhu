<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="controller.*" %>
<%@page import="model.*" %>
<%@page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/mainScreen.css" rel="stylesheet" type="text/css">
<title>MC - AI LÀ TRIỆU PHÚ</title>
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/mc.js"></script>
<script type="text/javascript" src="../js/lightbox/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/lightbox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
<script type="text/javascript" src="../js/lightbox/source/jquery.fancybox.js?v=2.1.5"></script>
<script type="text/javascript" src="../js/jquery.cookie.js"></script>
<link rel="stylesheet" type="text/css" href="../js/lightbox/source/jquery.fancybox.css" media="screen" />
<%
	String help="1110";
	int countQuestion=0;
	List <Round> rounds=Function.select(Round.class,"status=0");
	if (!rounds.isEmpty())
	{
		Round currentRound=rounds.get(0);
		help=Integer.toBinaryString(currentRound.getHelp());
		for (int i=0; i<4-help.length(); i++)
			help="0"+help;
		countQuestion=currentRound.getQuestionlist().split("@").length;
	}
%>

</head>
<body>
<div class="container"></div>
<div class="wrapper">
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
			<c:forEach var="i" begin="1" end="9">
				<c:if test='<%=WSServer.getSessionRecord("0"+String.valueOf(pageContext.getAttribute("i")))==null%>'>
					<div class="userOff" id="user-0<c:out value="${i}" />"><div class="numUser">0<c:out value="${i}"/></div></div>
				</c:if>
				<c:if test='<%=WSServer.getSessionRecord("0"+String.valueOf(pageContext.getAttribute("i")))!=null%>'>
					<div class="userOn" id="user-0<c:out value="${i}" />"><div class="umale" id="sex-0<c:out value="${i}"/>"></div><div class="numUser">0<c:out value="${i}"/></div></div>
				</c:if>        	
			</c:forEach>
			
		   	<c:if test='<%=WSServer.getSessionRecord("10")==null%>'>
				<div class="userOff" id="user-10"><div class="numUser">10</div></div>
			</c:if>
			<c:if test='<%=WSServer.getSessionRecord("10")!=null%>'>
				<div class="userOn" id="user-10"><div class="umale" id="sex-10"></div><div class="numUser">10</div></div>
			</c:if> 
		</div>
	</div>
	<div class="c2">
		<div class="c2l">
			<div class="c2l1">
				<div class="c2l1title">TRỢ GIÚP</div>
				<div class="c2l1content">
					<% 
						for (int i=0; i<4; i++)
							if (help.charAt(3-i)=='1')
								out.write("<div class=\"help\" id=\"help0"+(i+1)+"\"></div>");
							else
								out.write("<div class=\"help\" id=\"help0"+(i+1)+"used\"></div>");
					%>
				</div>
			</div>
			<div class="c2l2"></div>
		</div> 
		<div class="c2c">
			<div class="c2c1"><%=WSServer.currentQuestionContent %></div>
			<div class="c2c2">
				<div class="answer" id="answera"><%=WSServer.currentQuestionansA %></div>
				<div class="answer" id="answerb"><%=WSServer.currentQuestionansB %></div>
			</div>
			<div class="c2c3">
				<div class="answer" id="answerc"><%=WSServer.currentQuestionansC %></div>
				<div class="answer" id="answerd"><%=WSServer.currentQuestionansD %></div>
			</div>
			<div class="c2c4"></div>
			<div class="c2c5">
				Enter value to send: <input type="text" id="txtcontent" value="test"> <br/>
				<input type="button" value="send" id="btnSend"><br/>
				Status: <div id="status"></div>
			</div>
		</div> 
		<div class="c2r">
			<div class="c2r1"></div>
<script>
$(document).ready(function(){
	var s="<div class=\"c2r10\">";
	if ($.cookie("func00")==0)
		s=s+"<div class=\"btnOff\" id=\"pause\">TẠM DỪNG</div>";
	else
		s=s+"<div class=\"btn\" id=\"pause\">TẠM DỪNG</div>";
		
	if ($.cookie("func01")==0)
		s=s+"</div><div class=\"c2r11\"><div class=\"btnOff\" id=\"createquickround\">TẠO VÒNG CHƠI MỚI</div>";
	else
		s=s+"</div><div class=\"c2r11\"><div class=\"btn\" id=\"createquickround\">TẠO VÒNG CHƠI MỚI</div>";

	if ($.cookie("func02")==0)
		s=s+"</div><div class=\"c2r12\"><div class=\"btnOff\" id=\"nextquestion\">BẮT ĐẦU CÂU HỎI MỚI</div>";
	else
		s=s+"</div><div class=\"c2r12\"><div class=\"btn\" id=\"nextquestion\">BẮT ĐẦU CÂU HỎI MỚI</div>";

	if ($.cookie("func03")==0)
		s=s+"</div><div class=\"c2r13\"><div class=\"btnOff\" id=\"ads\">QUẢNG CÁO</div></div>";
	else
		s=s+"</div><div class=\"c2r13\"><div class=\"btn\" id=\"ads\">QUẢNG CÁO</div></div>";
	$(".c2r1").html(s);
});
</script>
			<div class="c2r2"></div>
		</div> 
	</div>
	</div>
</body>
</html>