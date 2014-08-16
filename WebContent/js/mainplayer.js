// Bien thoi gian tra loi cau hoi
var count, timerinterval;

var ws = new WebSocket("ws://localhost:8080/AiLaTrieuPhu/servertest");
//Connected to socket server, get @param message.
ws.onopen = function(){
	ws.send("00");
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
	if (message.data.indexOf("RESPONSE help04: ")==0)
	{
		$("#user-"+message.data.replace("RESPONSE help04: ","")).attr('class', 'userHelp');
		$("#choiselist").val("");
		$(".c2c4").html("Hãy chọn 3 người chơi bạn muốn tham khảo đáp án!");
	}
	if (message.data.indexOf("RESULT help04: ")==0)	
	{
		var arr=message.data.replace('RESULT help04: ','').split(';');
		for (var i=0; i<3; i++)
		{
			$("#user-"+arr[i]).attr('class', 'userChoise');
			$("#user-"+arr[i]).html('<div class="numUser">'+arr[i]+'</div><div class="ans">'+arr[3+i].toUpperCase()+'</div>');
		}		
	}
	
	// HELP03
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
	
	//HELP02
	if (message.data.indexOf("RESPONSE help02: ")==0)
	{
		$(".c2r1").html('<a id="showChart" data-fancybox-type="iframe" href="help02.jsp?data='+message.data.replace("RESPONSE help02: ","")+'"></a>');
		$( "#showChart" ).trigger("click");
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
		$('#ansKey').val("");
		count=10;
		timerinterval=setInterval(questionTimer,1000); 
	}
	// Nhan dap an
	if (message.data.indexOf("QUESTION RESULT: ")==0)
	{
		var arr=+message.data.replace("QUESTION RESULT: ","").split(";");
		$("#answer"+arr[1].toLowerCase()).css('background','yellow');
	}
		
};
ws.onclose = function(){
	alert("Bạn đã mất kết nối. Vui lòng kết nối lại bạn nhé!");
	ws.close();
};

$.urlParam = function(name){
    var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
};

function questionTimer()
{
	// Hien thi thoi gian
	$(".c2c4").html("Thời gian còn lại: "+count);
	// Neu la vong cuoi thi kt de dung lai
	if (count==0)
	{
		clearInterval(timerinterval);
		ws.send("FINAL ANSWER QUESTION: "+$('#ansKey').val());
		$('#ansKey').val("NO");
	}
		
	count--;
}

// Answer click
$(function(){
    $(".answer").click(function(){
    	// Only accecpt answer when be requested (ansKey!="NO")
    	if ($("#ansKey").val()!="NO")
		{
    		// restore to default background color before set new color for user's choise
    		$("#answera").css('background','#804000');
    		$("#answerb").css('background','#804000');
    		$("#answerc").css('background','#804000');
    		$("#answerd").css('background','#804000');
    		
    		// set new color for user's choise
    		$(this).css('background','red');
    		
    		$("#ansKey").val($( this ).attr('id').replace("answer",""));
    		ws.send("TEMP ANSWER QUESTION: "+$("#ansKey").val());
		}
    });
    });

// Help click
$(function(){
    $(".help").click(function(){
    	// Only excuse if mainplayer hasn't used this help.
    	if ($( this ).attr('id').indexOf("used")==-1)
    	{
    		ws.send("REQUEST "+$( this ).attr('id'));
        	$( this ).attr("id",$( this ).attr('id')+"used");
    	}
    });
    
    // NEU SUA LA PHAI SUA HET 3 HAM===============================================================
    $(".userOn").click(function(){
		// Chi xu ly khi chua chon du 3 nguoi
		if ($("#choiselist").val().length<8 && $(this).attr("class")=="userHelp")
		{
			// neu la nguoi dau tien thi khong co ;
			if ($("#choiselist").val().length==0)
				$("#choiselist").val($(this).attr('id').replace("user-",""));
			else
				$("#choiselist").val($("#choiselist").val()+";"+$(this).attr('id').replace("user-",""));
			
			//Doi mau
			$(this).attr('class', 'userChoise');
			
			//neu du 3 nguoi thi gui
			if ($("#choiselist").val().length==8)
				ws.send("CHOISE LIST help04: "+$("#choiselist").val());
		}	
	});
    $(".userOff").click(function(){
		// Chi xu ly khi chua chon du 3 nguoi
		if ($("#choiselist").val().length<8 && $(this).attr("class")=="userHelp")
		{
			// neu la nguoi dau tien thi khong co ;
			if ($("#choiselist").val().length==0)
				$("#choiselist").val($(this).attr('id').replace("user-",""));
			else
				$("#choiselist").val($("#choiselist").val()+";"+$(this).attr('id').replace("user-",""));
			
			//Doi mau
			$(this).attr('class', 'userChoise');
			
			//neu du 3 nguoi thi gui
			if ($("#choiselist").val().length==8)
				ws.send("CHOISE LIST help04: "+$("#choiselist").val());
		}	
	});
    $(".userHelp").click(function(){
		// Chi xu ly khi chua chon du 3 nguoi
		if ($("#choiselist").val().length<8 && $(this).attr("class")=="userHelp")
		{
			// neu la nguoi dau tien thi khong co ;
			if ($("#choiselist").val().length==0)
				$("#choiselist").val($(this).attr('id').replace("user-",""));
			else
				$("#choiselist").val($("#choiselist").val()+";"+$(this).attr('id').replace("user-",""));
			
			//Doi mau
			$(this).attr('class', 'userChoise');
			
			//neu du 3 nguoi thi gui
			if ($("#choiselist").val().length==8)
				ws.send("CHOISE LIST help04: "+$("#choiselist").val());
		}	
	});
    //=========================================================================================================== 
    });

// Fancybox for help03
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
