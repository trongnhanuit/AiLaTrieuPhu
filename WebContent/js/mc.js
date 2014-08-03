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
	if (message.data.indexOf("REQUEST help04")==0)
		$("#help04").attr("id","help04used");
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
};
ws.onclose = function(){
	alert("Bạn đã mất kết nối. Vui lòng chờ trong khi chúng tôi đang cố gắng kết nối lại!");
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
};