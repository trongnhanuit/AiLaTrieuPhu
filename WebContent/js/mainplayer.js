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
};
ws.onclose = function(){
	alert("Bạn đã mất kết nối. Vui lòng chờ trong khi chúng tôi đang cố gắng kết nối lại!");
	ws.close();
};

$.urlParam = function(name){
    var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
};

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

// Choise Audience for help04
$(function(){
	
    });



$(function(){
    $("#btnSend").click(function(){
        var s=$("#txtcontent").val();
        ws.send(s);
    });
    });
