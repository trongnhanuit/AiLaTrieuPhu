// Bien tan so cho o do chay tao cam giac hoi hop vong tra loi nhanh
var count, f=4, timerinterval, currentcolor, stoppos;

var ws = new WebSocket("ws://localhost:8080/AiLaTrieuPhu/servertest");
//Connected to socket server, get @param message.
ws.onopen = function(){
	ws.send("-1");
	$("#status").html("Send request to Server...<br/>");
};
	// Receive data from server
ws.onmessage = function(message)
{
	$("#status").html($("#status").html()+message.data);
	if (message.data.indexOf("AUDIENCE IN: ")==0)
	{
		$("#user-"+message.data.replace("AUDIENCE IN: ","")).attr('class', 'userOn');
		$("#user-"+message.data.replace("AUDIENCE IN: ","")).html($("#user-"+message.data.replace("AUDIENCE IN: ","")).html()+'<div class="umale" id="sex-'+message.data.replace("AUDIENCE IN: ","")+'"></div>');
	}
	if (message.data.indexOf("AUDIENCE OUT: ")==0)
	{
		$("#user-"+message.data.replace("AUDIENCE OUT: ","")).attr('class', 'userOff');
		$("#user-"+message.data.replace("AUDIENCE OUT: ","")).html('<div class="numUser">'+message.data.replace("AUDIENCE OUT: ","")+'</div>');
	}	
	
	// HELP04
	if (message.data.indexOf("REQUEST help04")==0 || message.data.indexOf("REQUEST help03")==0)
		$("#"+message.data.replace("REQUEST ","")).attr("id",message.data.replace("REQUEST ","")+"used");
	if (message.data.indexOf("RESPONSE help04: ")==0)
		$("#user-"+message.data.replace("RESPONSE help04: ","")).attr('class', 'userHelp');	
	if (message.data.indexOf("RESULT help04: ")==0)	
	{
		var arr=message.data.replace('RESULT help04: ','').split(';');
		for (var i=0; i<3; i++)
		{
			$("#user-"+arr[i]).attr('class', 'userChoise');
			$("#user-"+arr[i]).html('<div class="numUser">'+arr[i]+'</div><div class="ans">'+arr[3+i].toUpperCase()+'</div>');
		}
	}
	
	//HELP03
	if (message.data.indexOf("RESPONSE help03: ")==0)
	{
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="showChart.jsp?data='+message.data.replace("RESPONSE help03: ","")+'"></a>'+$(".c2r1").html());
		$( "#showChart" ).trigger("click");
	}
	
	//HELP01
	if (message.data.indexOf("RESPONSE help01: ")==0)
	{
		var arr=message.data.replace('RESPONSE help01: ','').split(';');
		$("#answer"+arr[0]).css('background','black');
		$("#answer"+arr[1]).css('background','black');
	}
	
	// VONG TRA LOI NHANH
	// Server bao du dieu kien tao vong tra loi nhanh
	if (message.data.indexOf("CREATE QUICK ROUND")==0)
		$(".c2r1").html($(".c2r1").html()+'<div class="btn" id="createquickround">TẠO VÒNG CHƠI MỚI</div>');
	//Server gui cau hoi
	if (message.data.indexOf("QUICK ROUND QUESTION: ")==0)
	{
		var arr=message.data.replace("QUICK ROUND QUESTION: ","").split("@@@");
		$(".c2c1").html(arr[0]);
		$("#answera").html(arr[1]);
		$("#answerb").html(arr[2]);
		$("#answerc").html(arr[3]);
		$("#answerd").html(arr[4]);
	}
	// Server gui ket qua nguoi duoc chon
	if (message.data.indexOf("RESULT ROUND QUESTION: ")==0)
	{
		var arr=message.data.replace("RESULT ROUND QUESTION: ","").split(";");
		for (var i=0; i<arr.length; i++)
			$("#user-"+arr[i]).attr('class', 'userHelp');
		// Chay 5 vong
		stoppos=arr[0];
		count=5*10;
		timerinterval=setInterval(runAroundTimer,1000/f); 
	}
		
};
ws.onclose = function(){
	alert("Bạn đã mất kết nối. Vui lòng kết nối lại bạn nhé!");
	ws.close();
};

function runAroundTimer()
{
	// Tim vi tri can doi mau va vi tri can reset mau
	var num=count%10+1;
	var pos="";
	if (num==10)
		pos="10";
	else
		pos="0"+num;
	var next="";
	if (num==10)
		next="01";
	else
		if (num==9)
			next="10";
		else
			next="0"+(num+1);
	$("#user-"+next).attr('class', currentcolor);
	currentcolor=$("#user-"+pos).attr("class");
	$("#user-"+pos).attr('class', 'userChoise');
	
	// Neu la vong cuoi thi kt de dung lai
	if (count<10 && stoppos==pos)
		clearInterval(timerinterval);
	count--;
}

//Fancybox for help03
$(document).ready(function() {
    $("#showChart").fancybox({
            maxWidth	: 600,
            maxHeight	: 600,
            fitToView	: false,
            width		: '50%',
            height		: '60%',
            autoSize	: false,
            closeClick	: false,
            openEffect	: 'none',
            closeEffect	: 'none'
    });
});


$(function(){
    $("#btnSend").click(function(){
        var s=$("#txtcontent").val();
        ws.send(s);
    });
    });

//Tạo vòng trả lời nhanh
$(document).on("click", "#createquickround",function() 
{
	$(".c2r1").empty();
    ws.send("CREATE QUICK ROUND");
});