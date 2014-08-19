// Biến thời gian cho vòng trả lời nhanh
var msecond, mtimerinterval;
// Bien tan so cho o do chay tao cam giac hoi hop vong tra loi nhanh
var count, f=4, timerinterval, currentcolor, stoppos;

var ws = new WebSocket("ws://localhost:8080/AiLaTrieuPhu/servertest");
//Connected to socket server, get @param message.
ws.onopen = function(){
	var pos=$.urlParam('pos');
	$("#pos").val(pos);
	ws.send(pos);
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
	
	//HELP
	// Dung chung cho 4 help de danh dau da su dung
	if (message.data.indexOf("REQUEST help0")==0)
		$("#"+message.data.replace("REQUEST ","")).attr("id",message.data.replace("REQUEST ","")+"used");
	//HELP04
	if (message.data.indexOf("REQUEST help04")==0 || message.data.indexOf("REQUEST help03")==0)
	{
		$("#answerfor"+message.data.replace("REQUEST ","")).val("YES");
		if (message.data.indexOf("REQUEST help04")==0)
			$(".c2c4").html("Hiện tại người chơi chính đang sử dụng quyền trợ giúp \"Tư vấn tại chỗ\". Hãy trợ giúp ngay bằng cách chọn đáp án mà bạn biết là đúng. Lưu ý: không bắt buộc chọn đáp án nếu như bạn không chắn chắc với đáp án của mình bạn nhé!");
		else
			$(".c2c4").html("Hiện tại người chơi chính đang sử dụng quyền trợ giúp \"Hỏi ý kiến khán giả\". Hãy trợ giúp ngay bằng cách chọn đáp án mà bạn cho là đúng.");
	}
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
	// Server gui cau hoi
	if (message.data.indexOf("QUICK ROUND QUESTION: ")==0)
	{
		var arr=message.data.replace("QUICK ROUND QUESTION: ","").split("@@@");
		$(".c2c1").html(arr[0]);
		$("#answera").html(arr[1]);
		$("#answerb").html(arr[2]);
		$("#answerc").html(arr[3]);
		$("#answerd").html(arr[4]);
		$("#answerforquickround").val("");
		$(".c2c4").html("Hãy click chọn đáp án lần lượt theo thứ tự bạn cho là đúng.");
		mtimerinterval=setInterval(mtimer,10), msecond=2000;
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
		var arr=message.data.replace("RESPONSE NEXT QUESTION: ","").split("@@@");
		$(".c2c1").html(arr[0]);
		$("#answera").html(arr[1]);
		$("#answerb").html(arr[2]);
		$("#answerc").html(arr[3]);
		$("#answerd").html(arr[4]);
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
	}
	
	// Thong bao nguoi choi chinh chien thang
	if (message.data.indexOf("MAINPLAYER WON: ")==0)
	{
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="resultcreen.jsp?value='+message.data.replace("MAINPLAYER WON: ","")+'&result=win"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
	}
	
	// Thong bao nguoi choi chinh thua cuoc
	if (message.data.indexOf("MAINPLAYER FAILED: ")==0)
	{
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="resultcreen.jsp?value='+message.data.replace("MAINPLAYER FAILED: ","")+'&result=failed"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
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
	if (message.data.indexOf("Reload: ")==0)
	{
		//var decNumber = Number(message.data.replace("Reload: ",""));
		var decNumber = Number("1");
		var binaryNumber = decNumber.toString(2).toUpperCase();
		var stringValue = binaryNumber.toString();
		
		if(stringValue.length<2)
			stringValue = "000"+stringValue;
		else if(stringValue.length<3)
			stringValue = "00"+stringValue;
		else if(stringValue.length<4)
			stringValue = "0"+stringValue;
	}
};
ws.onclose = function(){
	alert("Bạn đã mất kết nối. Vui lòng kết nối lại bạn nhé!");
	ws.close();
};

$(function(){
    $(".answer").click(function(){
    	// Only accecpt answer when be requested (answerforhelp04!="NO" or answerforhelp03!="NO")
    	if ($("#answerforhelp04").val()!="NO" || $("#answerforhelp03").val()!="NO")
		{
    		// restore to default background color before set new color for user's choise
    		$("#answera").css('background','#804000');
    		$("#answerb").css('background','#804000');
    		$("#answerc").css('background','#804000');
    		$("#answerd").css('background','#804000');
    		
    		// set new color for user's choise
    		$(this).css('background','red');
    		
    		if ($("#answerforhelp04").val()!="NO")
    			$("#answerforhelp04").val($( this ).attr('id').replace("answer",""));
    		else
    			$("#answerforhelp03").val($( this ).attr('id').replace("answer",""));
		}
    	
    	// Xử lý nhận đáp án cho vòng trả lời nhanh
    	if ($("#answerforquickround").val()!="NO")
		{
    		$(this).css('background','red');
    		$("#answerforquickround").val($("#answerforquickround").val()+$( this ).attr('id').replace("answer",""));
    		// Trả lời đủ 4 ký tự -> gửi đáp án ngay
    		if ($("#answerforquickround").val().length==4)
			{
    			ws.send("QUICK ROUND ANSWER: "+$("#answerforquickround").val()+";"+ msecond);
    			clearInterval(mtimerinterval);
    			$("#answerforquickround").val("NO");
				$(".c2c4").html("");
			}		
		}
    });
    });

// timer to submit answer
setInterval(timer,1000), second=10;
function timer()
{
	if ($("#answerforhelp04").val()!="NO"||$("#answerforhelp03").val()!="NO")
	{
		// Show time
		if ($("#answerforhelp04").val()!="NO")
			$(".c2c4").html("Hiện tại người chơi chính đang sử dụng quyền trợ giúp \"Tư vấn tại chỗ\". Hãy trợ giúp ngay bằng cách chọn đáp án mà bạn biết là đúng. Lưu ý: không bắt buộc chọn đáp án nếu như bạn không chắn chắc với đáp án của mình bạn nhé!. Thời gian còn lại: "+second);
		else
			$(".c2c4").html("Hiện tại người chơi chính đang sử dụng quyền trợ giúp \"Hỏi ý kiến khán giả\". Hãy trợ giúp ngay bằng cách chọn đáp án mà bạn cho là đúng.Thời gian còn lại: "+second);
		
		second--;
		
		// timeout
		if (second==-1)
			{
				// Choose value to send.
				if ($("#answerforhelp04").val()!="NO"&&$("#answerforhelp04").val()!="YES")
					ws.send("RESPONSE help04: "+$("#answerforhelp04").val());
				if ($("#answerforhelp03").val()!="NO"&&$("#answerforhelp03").val()!="YES")
					ws.send("RESPONSE help03: "+$("#answerforhelp03").val());
				
				// reset everything
				second=10;
				$("#answerforhelp03").val("NO");
				$("#answerforhelp04").val("NO");
				$(".c2c4").html("");
			}
	}
}

function mtimer()
{

	// Show time
	if (msecond%100==0)
		$(".c2c4").html("Hãy click chọn đáp án lần lượt theo thứ tự bạn cho là đúng. Thời gian còn lại: "+msecond/100);
	
	msecond--;
	
	// timeout
	if (msecond<=-1)
		{
			// Chỉ gởi khi có đáp án
			if ($("#answerforquickround").val()!="NO"&&$("#answerforquickround").val()!="")
				ws.send("QUICK ROUND ANSWER: "+$("#answerforquickround").val()+";"+ msecond);
			
			// Reset everything
			msecond=2000;
			$("#answerforquickround").val("NO");
			$(".c2c4").html("");
			clearInterval(mtimerinterval);
			return;
		}
}

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
	{
		clearInterval(timerinterval);
		if (pos==$("#pos").val())
			window.location.href = 'http://localhost:8080/AiLaTrieuPhu/view/mainplayer.jsp';
	}
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
    
    $.urlParam = function(name){
        var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
        return results[1] || 0;
    };
});

$(function(){
    $("#btnSend").click(function(){
        var s=$("#txtcontent").val();
        ws.send(s);
    });
    });
