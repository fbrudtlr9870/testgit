<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/common/header.jsp">
   <jsp:param value="회원등록" name="pageTitle"/>
</jsp:include>
<style>
div#enroll-container {
   width: 500px;
   margin: 0 auto;
   text-align: center;
}
/* 중복아이디체크 관련 */
div#userId-container{position:relative; padding:0px;}
div#userId-container span.guide{
	display:none;
	font-size:12px;
	position:absolute;
	top:12px;
	right:10px;
}
div#userId-container span.ok{color:green;}
div#userId-container span.error{color:red;}
</style>
<script>
$(function(){
	$("#password2").blur(function(){
		var p1 = $("#password_").val();
		var p2 = $(this).val();
		if(p1!=p2){
			alert("비밀번호 틀린데?");
			$("#password_").focus();			
		}
	});
	
	$("#userId_").on("keyup",function(){
		var userId = $(this).val().trim();
		if(userId.length<4){
			$(".guide").hide();
			$("#idDuplicateCheck").val(0);
			return;
		}
		
		$.ajax({
			url : "${pageContext.request.contextPath}/member/checkIdDuplicate.do", //checkIdDuplicate.do라고 상대주소를 적으면 /member/memberEnroll.do에서 현재 directory가 member임. 그래서 member에 있다고 생각. member밑의 /checkIdDuplicate.do라고 붙임
			data : {userId:userId},
			dataType:"json",
			success : function(data){
				console.log(data);
				if(data.isUsable==true){
					$(".guide.error").hide();
					$(".guide.ok").show();
					$("#idDuplicateCheck").val(1);
				}else{
					$(".guide.error").show();
					$(".guide.ok").hide();
					$("#idDuplicateCheck").val(0);
				}
			},
			error : function(jqxhr,textStatus,errorThrown){
				console.log("ajax실패");
				console.log(jqxhr);
				console.log(textStatus);
				console.log(errorThrown);
			}
			
		});
	});
	
});
/*
 * 유효성검사함수
 */
 function validate(){
	var userId = $("#userId_");
	if(userId.val().trim().length<4){
		alert("아이디 4글자 이상쓰렴");
		userId.focus();
		return false;
	}
	
	return true;
}
</script>
<div id="enroll-container">
   <form action="${pageContext.request.contextPath }/member/memberEnrollEnd.do" method="post" onsubmit="return validate();">
   <div id="userId-container">   
      <input type="text" class="form-control" name="userId" id="userId_" placeholder="아이디" required/> <br />
      <span class="guide ok">이 아이디는 사용가능합니다.</span>
      <span class="guide error">이 아이디는 사용할 수 없습니다.</span>
      <input type="hidden" id="idDuplicateCheck" value="0" />
   </div>
      <input type="password" class="form-control" name="password" id="password_" placeholder="패스워드" required/> <br />
      <input type="password" class="form-control" id="password2" placeholder="패스워드확인" required/> <br />
      <input type="text" class="form-control" name="userName" id="userName" placeholder="이름" required/> <br />
      <input type="number" class="form-control" name="age" id="age" placeholder="나이"/> <br />
      <input type="email" class="form-control" name="email" id="email" placeholder="이메일"/> <br />
      <input type="tel" class="form-control" name="phone" id="phone" maxlength="11" placeholder="전화번호" required/> <br />
      <input type="text" class="form-control" name="address" id="address" placeholder="주소"/> <br />
      <select class="form-control" name="gender" id="gender" required>
         <option value="" disabled selected>성별</option> 
         <option value="M">남</option> 
         <option value="F">여</option>
      </select>
      <div class="form-check-inline form-check">
         취미 : &nbsp;
         <input type="checkbox" class="form-check-input" value="운동" name="hobby" id="hobby1" />
         <label for="hobby1">운동</label> &nbsp;
         <input type="checkbox" class="form-check-input" value="등산" name="hobby" id="hobby2" />
         <label for="hobby2">등산</label> &nbsp;
         <input type="checkbox" class="form-check-input" value="독서" name="hobby" id="hobby3" />
         <label for="hobby3">독서</label> &nbsp;
         <input type="checkbox" class="form-check-input" value="게임" name="hobby" id="hobby4" />
         <label for="hobby4">게임</label> &nbsp;
         <input type="checkbox" class="form-check-input" value="여행" name="hobby" id="hobby5" />
         <label for="hobby5">여행</label>
      </div>
      <br />
      
      <input type="submit" value="가입" class="btn btn-outline-success" /> &nbsp;
   </form>
</div>   
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>