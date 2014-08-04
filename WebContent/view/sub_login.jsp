<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/sub_login.css">
<script type="text/javascript" src="../js/lightbox/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/lightbox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
<script type="text/javascript" src="../js/lightbox/source/jquery.fancybox.js?v=2.1.5"></script>
<link rel="stylesheet" type="text/css" href="../js/lightbox/source/jquery.fancybox.css" media="screen" />
<title>Signin</title>
<script>
    $(document).ready(function() {
    $("#choosePosition").fancybox({
            maxWidth	: 1300,
            maxHeight	: 600,
            fitToView	: false,
            width		: '83%',
            height		: '50%',
            autoSize	: false,
            closeClick	: false,
            openEffect	: 'none',
            closeEffect	: 'none',
            afterClose  : function() {
                var pos=$("#pos").val();
                if (pos!="")
                {
                	$("#pos").val("");
            		location.href = "audience.jsp?pos="+pos;
            	}
                else
                    alert("Sao còn chưa chọn? Nhanh tay bạn nhé. Coi chừng hết chỗ bây giờ :)");
                }
                
    });
});
</script>
</head>
<body>
	<input type="hidden" id="pos" value="">
	<a class="btn" id="choosePosition" data-fancybox-type="iframe" href="choosePosition.jsp">Tôi là khán giả!</a>
</body>
</html>