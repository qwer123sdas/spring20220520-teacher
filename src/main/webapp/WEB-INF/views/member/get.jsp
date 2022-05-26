<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--font-awesome  -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!--bootstrap  -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" integrity="sha512-GQGU0fMMi238uA+a/bdWJfpUGKUkBdgfFdgBm72SUQ6BeyWjoY/ton0tEjH+OSH9iP4Dfh+7HM0I9f5eR0L/4w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!--Jquery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
<!-- bootstrap - JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>Insert title here</title>
<script>
	$(document).ready(function(){
		// 암호, 암호확인 일치여부
		let passwordCheck = true;
		// 이메일 중복 확인 여부
		let emailCheck = true;
		// 닉네임 중복 확인 여부
		let nickNameCheck = true;
		
		// 수정버튼 활성화 함수
		const enableModifySubmit = function(){
			if(passwordCheck  && emailCheck && nickNameCheck){
				$('#submitModifyButton1').removeAttr("disabled");
			}else{
				$('#submitModifyButton1').attr("disabled", "");
			}
		};
		
		// 기존 이메일 & 닉네임
		const oldEmail = $('#emailInput').val();
		const oldNickName = $('#nickNameInput').val();
		
		// 이메일 input 값 변경 시 이메일 중복버튼 활성화
		$('#emailInput').keyup(function(){
			const newEmail = $('#emailInput').val();
			
			if(oldEmail === newEmail){
				$('#email-input-button').attr("disabled", "");
				$('#emailMessage').text("예전 이메일과 같습니다.");
				emailCheck = true;
			}else{
				$('#email-input-button').removeAttr('disabled');
				emailCheck = false;
			}
			enableModifySubmit();
			
		});
		// 닉네임 input 값 변경 시 이메일 중복버튼 활성화
		$('#nickNameInput').keyup(function(){
			const newNickName = $('#nickNameInput').val();
			
			if(oldNickName === newNickName){
				$('#nickName-input-button').attr("disabled", "");
				$('#nickNameMessage').text("예전 닉네임과 같습니다.");
				nickNameCheck = true;
			}else{
				$('#nickName-input-button').removeAttr('disabled');
				nickNameCheck = false;
			}
			enableModifySubmit();
		});
		
		// 이메일 중복버튼 클릭시 ajax 요청 발생
		$('#email-input-button').click(function(e){
			e.preventDefault();
			const data = {
					email : $('#emailInput').val()
			}
			
			$.ajax({
				url : '${appRoot}/member/check',
				type : 'GET',
				data : data,
				success : function(data){
					switch(data){
					case "ok" : 
						$('#emailMessage').text("사용 가능한 이메일 입니다.");
						emailCheck = true;
						break;
					case "notOk" : 
						$('#emailMessage').text("사용 불가능한 이메일 입니다.");
						emailCheck = false;
						break;
					}
				},
				complete : function(){
					enableModifySubmit();
				}
			});
		});
		// 닉네임 중복버튼 클릭시 ajax 요청 발생
		$('#nickName-input-button').click(function(e){
			e.preventDefault();
			const data = {
					nickName : $('#nickNameInput').val()
			}
			$.ajax({
				url : '${appRoot}/member/check',
				type : 'GET',
				data : data,
				success : function(data){
					switch(data){
					case "ok" : 
						$('#nickNameMessage').text("사용 가능한 닉네임 입니다.");
						nickNameCheck = true;
						break;
					case "notOk" : 
						$('#nickNameMessage').text("사용 불가능한 닉네임 입니다.");
						nickNameCheck = false;
						break;
					}
				},
				complete : function(){
					enableModifySubmit();
				}
			});
		});
		
		// 암호, 암호확인 요소 값 변경시
		$('#passwordInput1, #passwordInput2').keyup(function(){
			const pw1 = $('#passwordInput1').val();
			const pw2 = $('#passwordInput2').val();
			
			if(pw1 === pw2){
				$('#passwordMessage').text("패스워드가 일치합니다.");
				passwordCheck = true;
			}else{
				$('#passwordMessage').text("패스워드가 일치하지 않습니다.");
				passwordCheck = false;
			}
			enableModifySubmit();
		});
		
		// 수정 submit버튼("modifySubmitButton2") 클릭 시
		$('#modifySubmitButton2').click(function(e){
			e.preventDefault();
			const form2 = $('#form2');
			
			// input 옮기기
			form2.find("[name=password]").val($('#passwordInput1').val());
			form2.find("[name=email]").val($('#emailInput').val());
			form2.find("[name=nickName]").val($('#nickNameInput').val());
			
			// submit
			form2.submit();
		});
	});
</script>


</head>
<body>
	<my:navBar current="memberList"></my:navBar>
	<div>
		<p>${message }</p>
	</div>
	<div>
	아이디 : <input type="text" value="${member.id }" readonly /> <br />
	비밀번호 : <input id="passwordInput1" type="text" value=""/> <br />
	암호확인 : <input id="passwordInput2" type="text" value=""/> <br />
	<p id="passwordMessage"></p>
	이메일 : <input id="emailInput" type="text" value="${member.email }"/> <button id="email-input-button" disabled>이메일중복확인</button> <br />
	<p id="emailMessage"></p>
	닉네임 : <input id="nickNameInput" type="text" value="${member.nickName }"/> <button id="nickName-input-button" disabled>닉네임중복확인</button><br />
	<p id="nickNameMessage"></p>
	가입일시 : <input type="datetime-local" value="${member.inserted }" readonly /> <br />
	</div>
	
	<!-- 요구사항  
	1. 이메일 input 변경 발생 시 이메일 중복 확인 버튼 활성화
	2. 닉네임 input에 변경 발생 시 닉네임중복확인버튼 활성화
	3. 암호/암호확인 일치. 이메일 중복확인 완료, 닉네임 중복확인 완료 시에만
	    수정버튼 활성화
	-->
	
	<div>
		<button id="submitModifyButton1" data-bs-toggle = "modal" data-bs-target="#modal2" disabled>수정</button>
		<button data-bs-toggle="modal" data-bs-target="#modal1">삭제</button>
	</div>
	
	<!-- 탈퇴 암호 확인 Modal -->
	<div class="modal fade" id="modal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	      	<form id="form1" action="${appRoot }/member/remove" method="post">
	      		<input type="hidden" value="${member.id }" name="id"/>
	        	암호 : <input type="text" name="password"/>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button form="form1" type="submit" class="btn btn-danger">탈퇴</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 수정 , 기존 암호 확인 Modal -->
	<div class="modal fade" id="modal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel2">Modal title</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	      	<form id="form2" action="${appRoot }/member/modify" method="post">
	      		<input type="hidden" value="${member.id }" name="id"/>
	      		<input type="hidden" name="password"/>
	      		<input type="hidden" name="email"/>
	      		<input type="hidden" name="nickName"/>
	        	기존 암호 : <input type="text" name="oldPassword"/>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button id="modifySubmitButton2" form="form1" type="submit" class="btn btn-primary">수정</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>