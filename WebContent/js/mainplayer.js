// Bien thoi gian tra loi cau hoi
var count, timerinterval;

// Bien cho biet close fancybox xong co can redirect qua trang login k
var isNeed2Redirect=0;

// Bien cho biet close fancybox xong co can tinh gio khong
var isNeed2Count=0;

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
	// Nhan thong bao duoc them quyen help04
	if (message.data.indexOf("ADD HELP04")==0)
		$("#help04used").attr("id","help04");
		
	if (message.data.indexOf("RESPONSE help04: ")==0)
	{
		$("#user-"+message.data.replace("RESPONSE help04: ","")).attr('class', 'userHelp');
		$("#choiselist").val("");
		$(".c2c4").html("Hãy chọn 3 người chơi bạn muốn tham khảo đáp án!");
	}
	if (message.data.indexOf("RESULT help04: ")==0)	
	{
		// bat dau tinh gio lai
		count=10;
		timerinterval=setInterval(questionTimer,1000); 
		
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
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="showChart.jsp?data='+message.data.replace("RESPONSE help03: ","")+'"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
	}
	
	//HELP01
	if (message.data.indexOf("RESPONSE help01: ")==0)
	{
		// bat dau tinh gio lai
		count=10;
		timerinterval=setInterval(questionTimer,1000); 
		
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
	
	
	//CAU HOI MOI
	// Neu nhan ds user on thi phai set lai trang thai hien thi
	if (message.data.indexOf("USER ON LIST: ")==0)
	{
		var arr=message.data.replace("USER ON LIST: ","").split(";");
		for (var i=0; i<arr.length; i++)
		{
			$("#user-"+arr[i]).attr('class','userOn');
			$("#user-"+arr[i]).html('<div class="numUser">'+arr[i]+'</div><div class="umale" id="sex-'+arr[i]+'"></div>');
		}
	}
	
	// Nhan cau hoi moi
	if (message.data.indexOf("RESPONSE NEXT QUESTION: ")==0)
	{
		// Set lai mau cho cac o dap an
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
		$('#ansKey').val("");
		$("#q"+arr[5]).css('background','rgba(255,255,255,0.4)');
		for(var i=0; i<arr[5];i++)
			$("#q"+i).css('background','rgba(0,0,0,0.1)');
		count=10;
		timerinterval=setInterval(questionTimer,1000); 
	}
	// Nhan dap an
	if (message.data.indexOf("QUESTION RESULT: ")==0)
	{
		var arr=message.data.replace("QUESTION RESULT: ","").split(";");
		$("#answer"+arr[1].toLowerCase()).css('background','yellow');
	}
	
	// Thong bao nguoi choi chinh chien thang
	if (message.data.indexOf("MAINPLAYER WON: ")==0)
	{
		isNeed2Redirect=1;
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="resultcreen.jsp?value='+message.data.replace("MAINPLAYER WON: ","").split(";")[0]+'&result=win"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
	}
	
	// Thong bao nguoi choi chinh thua cuoc
	if (message.data.indexOf("MAINPLAYER FAILED: ")==0)
	{
		isNeed2Redirect=1;
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="resultcreen.jsp?value='+message.data.replace("MAINPLAYER FAILED: ","").split(";")[0]+'&result=failed"></a>'+$(".c2l2").html());
		$( "#showChart" ).trigger("click");
	}
	
	// Tam dung game
	if (message.data.indexOf("REQUEST PAUSE")==0)
	{
		isNeed2Redirect=1;
		$(".c2l2").html('<a id="showChart" data-fancybox-type="iframe" href="pausescreen.jsp"></a>'+$(".c2l2").html());
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

//Stop click
$(function(){
    $("#stopplaying").click(function(){
    	ws.send("REQUEST STOP PLAYING");
    	isNeed2Redirect=1;
    	clearInterval(timerinterval);
    });
});
// Help click
$(function(){
    $(".help").click(function(){
    	// Only excuse if mainplayer hasn't used this help.
    	if ($( this ).attr('id').indexOf("used")==-1)
    	{
    		isNeed2Count=1;
    		clearInterval(timerinterval);
    		
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
            closeEffect	: 'none',
            afterClose  : function() 
            {
            	if (isNeed2Redirect==1)
            		location.href = "login.jsp";
            	if (isNeed2Count==1)
        		{
            		// bat dau tinh gio lai
            		count=10;
            		timerinterval=setInterval(questionTimer,1000);
            		isNeed2Count==0;
            		ws.send("CLOSE HELP FANCYBOX");
        		}
            }
    });
});

$(function(){
    $("#btnSend").click(function(){
        var s=$("#txtcontent").val();
        ws.send(s);
    });
    });
