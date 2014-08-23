  $(document).ready(function(){  
	  $(".username").change(function(){ 
      var username = $(this).val();  
      if(username.length >= 4){
          $(".status").html("<img src='../media/loading.gif'><font color=gray> Kiá»ƒm tra kháº£ dá»¥ng...</font>");  
           $.ajax({  
              type: "POST",  
              url: "http://localhost:8080/AiLaTrieuPhu/CheckUser",  
              data: "username="+ username,  
              success: function(msg){
				  if(msg.substring(0,2)==="OK")
					  $(".status").html("<font color=\"green\"><b>"+username+"</b> cÃ³ thá»ƒ sá»­ dá»¥ng</font>");
				  else
					  {
                      $(".status").html("<font color=\"red\"><b>"+username+"</b> Ä‘Ã£ Ä‘Æ°á»£c Ä‘Äƒng kÃ­</font>");
                      $(".username").val("");
					  } 
              }, 
          });   
      }  
      else{    
          $(".status").html("<font color=red>TÃªn Ä‘Äƒng nháº­p pháº£i nhiá»�u hÆ¡n <b>4</b> kÃ­ tá»±.</font>");  
          }  
            
      });  
  });  
  
  //Check Goverment ID
  $(document).ready(function(){  
      $(".govermentid").change(function(){ 
      var govermentid = $(this).val();  
      if(govermentid.length == 9){
          $(".status2").html("<img src='../media/loading.gif'><font color=gray> Kiá»ƒm tra kháº£ dá»¥ng...</font>");  
           $.ajax({  
              type: "POST",  
              url: "http://localhost:8080/AiLaTrieuPhu/CheckGID",  
              data: "govermentid="+ govermentid,  
              success: function(msg){
				  if(msg.substring(0,2)==="OK")
					  $(".status2").html("<font color=\"green\">Sá»‘ CMND <b>"+govermentid+"</b> cÃ³ thá»ƒ sá»­ dá»¥ng</font>");
				  else
					  {
                      $(".status2").html("<font color=\"red\">Sá»‘ CMND <b>"+govermentid+"</b> Ä‘Ã£ Ä‘Æ°á»£c Ä‘Äƒng kÃ­</font>");
                      $(".govermentid").val("");
					  } 
              }, 
          });   
      }  
      else{    
          $(".status2").html("<font color=red>Sá»‘ CMND pháº£i Ä‘á»§ <b>9</b> sá»‘. Vui lÃ²ng nháº­p láº¡i</font>");  
          }  
            
      });  
  }); 