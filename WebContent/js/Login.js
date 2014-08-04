  $(document).ready(function(){  
      $(".username").change(function(){ 
      var username = $(this).val();  
      if(username.length >= 4){
          $(".status").html("<img src='../media/loading.gif'><font color=gray> Kiểm tra khả dụng...</font>");  
           $.ajax({  
              type: "POST",  
              url: "http://localhost:8080/AiLaTrieuPhu/CheckUser",  
              data: "username="+ username,  
              success: function(msg){
				  if(msg.substring(0,2)==="OK")
					  $(".status").html("<font color=\"green\"><b>"+username+"</b> có thể sử dụng</font>");
				  else
					  {
                      $(".status").html("<font color=\"red\"><b>"+username+"</b> đã được đăng kí</font>");
                      $(".username").val("");
					  } 
              }, 
          });   
      }  
      else{    
          $(".status").html("<font color=red>Tên đăng nhập phải nhiều hơn <b>4</b> kí tự.</font>");  
          }  
            
      });  
  });  
  
  //Check Goverment ID
  $(document).ready(function(){  
      $(".govermentid").change(function(){ 
      var govermentid = $(this).val();  
      if(govermentid.length == 9){
          $(".status2").html("<img src='../media/loading.gif'><font color=gray> Kiểm tra khả dụng...</font>");  
           $.ajax({  
              type: "POST",  
              url: "http://localhost:8080/AiLaTrieuPhu/CheckGID",  
              data: "govermentid="+ govermentid,  
              success: function(msg){
				  if(msg.substring(0,2)==="OK")
					  $(".status2").html("<font color=\"green\">Số CMND <b>"+govermentid+"</b> có thể sử dụng</font>");
				  else
					  {
                      $(".status2").html("<font color=\"red\">Số CMND <b>"+govermentid+"</b> đã được đăng kí</font>");
                      $(".govermentid").val("");
					  } 
              }, 
          });   
      }  
      else{    
          $(".status2").html("<font color=red>Số CMND phải đủ <b>9</b> số. Vui lòng nhập lại</font>");  
          }  
            
      });  
  }); 