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
	
	if (message.data.indexOf("MAINPLAYER SIGNED IN")==0)
	{
		$(".c2r13").html('<div class="btn" id="ads">QUẢNG CÁO</div>');
		$(".c2r12").html('<div class="btn" id="nextquestion">BẮT ĐẦU CÂU HỎI MỚI</div>');
	}
		
	//HELP
	// Dung chung cho 4 help de danh dau da su dung
	if (message.data.indexOf("REQUEST help0")==0)
		$("#"+message.data.replace("REQUEST ","")).attr("id",message.data.replace("REQUEST ","")+"used");
	// HELP04
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
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="showChart.jsp?data='+message.data.replace("RESPONSE help03: ","")+'"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
	}
	
	//HELP01
	if (message.data.indexOf("RESPONSE help01: ")==0)
	{
		var arr=message.data.replace('RESPONSE help01: ','').split(';');
		$("#answer"+arr[0]).css('background','black');
		$("#answer"+arr[1]).css('background','black');
	}
	
	//HELP02
	if (message.data.indexOf("RESPONSE help02: ")==0)
	{
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="help02.jsp?data='+message.data.replace("RESPONSE help02: ","")+'"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
	}
	
	// VONG TRA LOI NHANH
	// Server bao du dieu kien tao vong tra loi nhanh
	if (message.data.indexOf("CREATE QUICK ROUND")==0)
		$(".c2r11").html('<div class="btn" id="createquickround">TẠO VÒNG CHƠI MỚI</div>');
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
	
	//CAU HOI MOI
	// Nhan cau hoi moi
	if (message.data.indexOf("RESPONSE NEXT QUESTION: ")==0)
	{
		$("#answera").css('background','#804000');
		$("#answerb").css('background','#804000');
		$("#answerc").css('background','#804000');
		$("#answerd").css('background','#804000');
		var arr=message.data.replace("RESPONSE NEXT QUESTION: ","").split("@@@");
		$(".c2c1").html(arr[0]);
		$("#answera").html(arr[1]);
		$("#answerb").html(arr[2]);
		$("#answerc").html(arr[3]);
		$("#answerd").html(arr[4]);
		count=11;
		timerinterval=setInterval(questionTimer,1000); 
	}
	// Nhận Temp answer tu nguoi choi chinh
	if (message.data.indexOf("TEMP ANSWER QUESTION: ")==0)
	{
		$("#answera").css('background','#804000');
		$("#answerb").css('background','#804000');
		$("#answerc").css('background','#804000');
		$("#answerd").css('background','#804000');
		
		// set new color for user's choise
		$("#answer"+message.data.replace("TEMP ANSWER QUESTION: ","")).css('background','red');
	}
	// Nhan dap an
	if (message.data.indexOf("QUESTION RESULT: ")==0)
	{
		$("#answera").css('background','#804000');
		$("#answerb").css('background','#804000');
		$("#answerc").css('background','#804000');
		$("#answerd").css('background','#804000');
		
		var arr=message.data.replace("QUESTION RESULT: ","").split(";");
		$("#answer"+arr[0]).css('background','red');
		$("#answer"+arr[1].toLowerCase()).css('background','yellow');
		if(arr[0]==arr[1].toLowerCase())
		{
			$(".c2r10").html('<div class="btn" id="pause">TẠM DỪNG</div>'); 
			$(".c2r13").html('<div class="btn" id="ads">QUẢNG CÁO</div>');
			$(".c2r12").html('<div class="btn" id="nextquestion">BẮT ĐẦU CÂU HỎI MỚI</div>');
		}
	}
	
	// Thong bao nguoi choi chinh chien thang
	if (message.data.indexOf("MAINPLAYER WON: ")==0)
	{
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="resultcreen.jsp?value='+message.data.replace("MAINPLAYER WON: ","").split(";")[0]+'&result=win"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
		// An cac nut qua cau hoi
		$(".c2r10").html('<div class="btnOff" id="pause">TẠM DỪNG</div>'); 
		$(".c2r13").html('<div class="btnOff" id="ads">QUẢNG CÁO</div>');
		$(".c2r12").html('<div class="btnOff" id="nextquestion">BẮT ĐẦU CÂU HỎI MỚI</div>');
		//Hien nut tao vong choi moi neu du nguoi
		if (message.data.replace("MAINPLAYER WON: ","").split(";")[1]=="1")
			$(".c2r11").html('<div class="btn" id="createquickround">TẠO VÒNG CHƠI MỚI</div>');
	}
	
	// Thong bao nguoi choi chinh thua cuoc
	if (message.data.indexOf("MAINPLAYER FAILED: ")==0)
	{
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="resultcreen.jsp?value='+message.data.replace("MAINPLAYER FAILED: ","").split(";")[0]+'&result=failed"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
		//Hien nut tao vong choi moi neu du nguoi
		if (message.data.replace("MAINPLAYER FAILED: ","").split(";")[1]=="1")
			$(".c2r11").html('<div class="btn" id="createquickround">TẠO VÒNG CHƠI MỚI</div>');
	}
	
	//Hiện Quảng cáo
	if (message.data.indexOf("RESPONSE ADS: ")==0)
	{
		var res =message.data.replace("RESPONSE ADS: ","");
		$(".container").css('display','block');
		$(".container").css('background','rgba(0,0,0,0.9)');
		$(".container").css('padding','1% 25% 12%');
		$(".container").css('width','100%');
		$(".container").html("<video id =\"ads\" autoplay width=\"900\" height=\"600\"> <source src=\"../media/promo0"+res+".mp4\" type=\"video/mp4\"></video>");
		$('#ads').bind("ended", function(){ 
			$(".container").html(""); 
			$(".container").css('display','none');
		    });
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

function questionTimer()
{
	// Hien thi thoi gian
	$(".c2c4").html("Thời gian còn lại: "+count);
	// Neu la vong cuoi thi kt de dung lai
	if (count==0)
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

//Tạm dừng cuộc chơi
$(document).on("click", "#pause",function() 
{
    ws.send("REQUEST PAUSE");
    $(".c2r10").html('<div class="btnOff" id="pause">TẠM DỪNG</div>');  
    $(".c2r11").html('<div class="btnOff" id="createquickround">TẠO VÒNG CHƠI MỚI</div>');
    $(".c2r12").html('<div class="btnOff" id="nextquestion">BẮT ĐẦU CÂU HỎI MỚI</div>');
	$(".c2r13").html('<div class="btnOff" id="ads">QUẢNG CÁO</div>');
});
//Tạo vòng trả lời nhanh
$(document).on("click", "#createquickround",function() 
{
	$(".c2r11").html('<div class="btnOff" id="createquickround">TẠO VÒNG CHƠI MỚI</div>');
    ws.send("CREATE QUICK ROUND");
});
//Qua cau hoi moi
$(document).on("click", "#nextquestion",function() 
{
	$(".c2r10").html('<div class="btnOff" id="pause">TẠM DỪNG</div>'); 
	$(".c2r12").html('<div class="btnOff" id="nextquestion">BẮT ĐẦU CÂU HỎI MỚI</div>');
	$(".c2r13").html('<div class="btnOff" id="ads">QUẢNG CÁO</div>');
    ws.send("REQUEST NEXT QUESTION");
});

//Hiện quảng cáo
$(document).on("click", "#ads",function() 
{
	if($('#nextquestion').length)
		ws.send("REQUEST ADS");
	else
		alert("Không thể quảng cáo lúc này!");
});