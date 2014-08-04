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
	
	// HELP04
	if (message.data.indexOf("REQUEST help04")==0|| message.data.indexOf("REQUEST help03")==0)
		{
			$("#"+message.data.replace("REQUEST ","")).attr("id",message.data.replace("REQUEST ","")+"used");
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
		$(".c2r1").html('<a id="showChart" data-fancybox-type="iframe" href="showChart.jsp?data='+message.data.replace("RESPONSE help03: ","")+'"></a>'+$(".c2r1").html());
		$( "#showChart" ).trigger("click");
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
				
				// Reset everything
				second=10;
				$("#answerforhelp03").val("NO");
				$("#answerforhelp04").val("NO");
				$(".c2c4").html("");
			}
	}
}

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