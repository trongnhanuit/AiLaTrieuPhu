// JavaScript Document
function Check0()
{
	$(".midbutton").hide("fast");
	$("#Login").show("fast");
	$("#lbKQLi").show("fast");
	$(".btnBack1").show("fast");
}
function Check2(user)
{
	$(".midbutton").hide("fast");
	$("#Login").show("fast");
	$("#lbKQLi1").show("fast");
	$("#name_login").val(user);
	$(".btnBack1").show("fast");
}
function btnSignUpclick()
{
	var KTSU = $('#KTSU').val();
	if(KTSU=="")
	{
		$("#Login").hide("fast");
		$("#FormSU").show("slow");
		$(".btnSignIn").hide("fast");
		$("#KTSU").val("1");
		$(".btnBack").show("fast");
	}
	else if(KTSU=="1")
	{
		Xuli();
	}
	
}
function btnSignInclick()
{
	var KTLI=$('#KTLI').val();
	if(KTLI=="")
		{
			$(".btnSignUp").hide("fast");
			$(".btnSignIn").show("fast");
			$("#FormSU").hide("slow");
			$("#Login").show("fast");
			$(".btnSignIn").hide("fast");
			$(".btnBack1").show("fast");
		}
}
//SignUp click
function checkduplicate()
{
	var pass1 = $('#passSU').val();
	var pass2 = $('#re-passSU').val();
	if(pass1!=pass2)
		{
			$("#lbRe-Pass").show("fast");
			$('#re-passSU').val("");
			$('#kt').val("0");
		}
	else
		{
			$("#lbRe-Pass").hide("fast");
			$('#kt').val("1");
		}
		
}
function checkuser()
{
	var username = $('#nameSU').val();
	$.ajax({
		url: 'CheckUserController',
		type: 'post',
		data: "username=" + username,
		success:function(data){
			if(data==1)
			{
				$("#lbKQ").show("fast");
				$('#kt').val("0");
			}
			else
			{
				$("#lbKQ").hide("fast");
				$('#kt').val("1");
			}
				
		},
		 error: function(xhr, status, error){
		       alert('request failed');
		    }
		});
	
}
function Xuli()
{
	var KTSU = $('#KTSU').val();
	if(KTSU=="1")
		{
		var kt=$('#kt').val();
		if(kt==""||kt=="0")
			$("#lbkt").show("fast");
		else
			{
			$("#lbkt").hide("fast");
			var username = $('#nameSU').val();
			var pass = $('#passSU').val();
			var name_show = $('#firstname').val() +" "+ $('#lastname').val();
			var sex = $('input[name="sex"]:checked').val();
			var Date = $('day').val() + "/" + $('month').val() + "/" + $('year').val();
			$.ajax({
				url: 'SignUpController',
				type: 'post',
				data: "username=" + username + "&pass=" + pass +"&name_show="+name_show+"&sex="+sex+"&Date="+Date,
				success:function(data){
					if(data.redirect)
						{
							window.location.href = data.redirect;
						}
					if(data=="1")
						{
							$('#name_login').val("");
							$('#pass_login').val("");
							$("#FormSU").hide("fast");
							$(".midbutton").hide("fast");
							$("#lbKQLi").hide("fast");
							$("#lbKQLi1").hide("fast");
							$(".btnBack1").show("fast");
							$("#Login").show("fast");
						}
					else
						{
							alert("Them that bai");
						}
				},
				 error: function(xhr, status, error){
				       alert('request failed');
				    }
				});
		}
		}
}


//Login Click
function SignIn()
{
	$("#lbKQ").hide("fast");
	$("#lbKQ1").hide("fast");
	var name = $('#name_login').val();
	var pass = $('#pass_login').val();
	$.ajax({
		
		url: 'LoginController',
		type: 'post',
		data: "name=" + name + "&pass=" + pass,
		success:function(data){
			if(data=="1")
			{
				window.location.href = "WEB-INF/views/user.jsp"; 
			}
			if(data=="0")
			{
				$("#lbKQ").show("fast");
				$("#name_login").val("");
				$("#pass_login").val("");
			}
			else if(data=="2")
				{
				$("#lbKQ").hide("fast");
				$("#lbKQ1").show("fast");
				$("#pass_login").val("");
				}
				
		},
		 error: function(xhr, status, error){
		       alert('request failed');
		    }
	});
}
function btnBack()
{
	$(".btnSignUp").show("fast");
	$(".btnSignIn").show("fast");
	$(".midbutton").show("fast");
	$("#KTSU").val("");
	$("#Login").hide("fast");
	$("#FormSU").hide("fast");
	$(".btnBack").hide("fast");
	$(".btnBack1").hide("fast");
}

