<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--font-awesome  -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<!--bootstrap  -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css"
	integrity="sha512-GQGU0fMMi238uA+a/bdWJfpUGKUkBdgfFdgBm72SUQ6BeyWjoY/ton0tEjH+OSH9iP4Dfh+7HM0I9f5eR0L/4w=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<!--Jquery -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
	referrerpolicy="no-referrer"></script>
<!-- bootstrap - JS -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<title>Insert title here</title>
<script>
	$(document).ready(function() {
		// 중복, 암호 확인 변수
		let idOk = false;
		let pwOk = false;
		let emailOk = false;
		let nickName = false;

		// 아이디 중복체크
		$('#checkIdButton1').click(function(e) {
			e.preventDefault();
			$(this).attr("disabled", "");
			const data = {
				id : $('#form1').find('[name=id]').val()
			}
			idOk = false;
			$.ajax({
				url : '${appRoot}/member/check',
				type : 'GET',
				data : data,
				success : function(data) {
					switch (data) {
					case "ok":
						$('#idMessage1').text("사용 가능한 아이디 입니다.");
						idOk = true;
						break;
					case "notOk":
						$('#idMessage1').text("사용 불가능한 아이디 입니다.");
						break;
					}
				},
				error : function(data) {
					$('#idMessage1').text("중복확인 중 문제 발생. 다시 확인 해 주세요.");
				},
				complete : function() {
					$('#checkIdButton1').removeAttr("disabled");
					enableSubmit();
				}
			});
		});

		$('#checkEmailButton').click(function(e) {
			e.preventDefault();
			$(this).attr("disabled", "");
			const data = {
				email : $('#form1').find('[name=email]').val()
			}
			emailOk = false;
			$.ajax({
				url : '${appRoot}/member/check',
				type : 'GET',
				data : data,
				success : function(data) {
					switch (data) {
					case "ok":
						$('#emailMessage').text("사용 가능한 이메일 입니다.");
						emailOk = true;
						break;
					case "notOk":
						$('#emailMessage').text("사용 불가능한 이메일 입니다.");
						break;
					}
				},
				complete : function() {
					$('#checkEmailButton').removeAttr("disabled");
					enableSubmit();
				}
			});
		});

		$('#checkNickNameButton').click(function(e) {
			e.preventDefault();
			const data = {
				nickName : $('#form1').find('[name=nickName]').val()
			}
			nickNameOk = false;
			$.ajax({
				url : '${appRoot}/member/check',
				type : 'GET',
				data : data,
				success : function(data) {
					switch (data) {
					case "ok":
						$('#nickNameMessage').text("사용 가능한 이메일 입니다.");
						nickNameOk = true;
						break;
					case "notOk":
						$('#nickNameMessage').text("사용 불가능한 이메일 입니다.");
						break;
					}
				},
				complete : function() {
					enableSubmit();
				}
			})
		});

		// 패스워드 오타 확인
		$('#passwordInput1, #passwordInput2').keyup(function() {
			const pw1 = $('#passwordInput1').val();
			const pw2 = $('#passwordInput2').val();
			pwOk = false;
			if (pw1 === pw2) {
				$('#passwordMessage1').text("패스워드가 일치합니다.");
				pwOk = true;
			} else {
				$('#passwordMessage1').text("패스워드가 일치하지 않습니다.");
			}
		});

		// 회원가입 submit버튼 활성화 비활성화 함수
		const enableSubmit = function() {
			if (idOk && pwOk && emailOk && nickNameOk) {
				$('#submitButton1').removeAttr("disabled");
			} else {
				$('#submitButton1').attr("disabled", "");
			}
		}
	});
</script>
</head>

<body>
	<my:navBar current="signup"></my:navBar>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-12 col-lg-6">
				<h1>회원가입</h1>
				<form id="form1" action="${appRoot }/member/signup" method="post">
				
<!--input group  -->				
										
					<label for="idInput1" class="form-label">
					아이디
					</label>
					<div class="input-group">
						<input id="idInput1" class="form-control" type="text" name="id" />
						<button class="btn btn-secondary" id="checkIdButton1" type="button">아이디 중복확인</button>
					</div>
					<div class="form-text" id="idMessage1"></div>
					
					<label for="passwordInput1" class="form-label">
					비밀번호
					</label>
					<input class="form-control" id="passwordInput1" type="password" name="password" />
					
					<label for="passwordInput2" class="form-label">
					패스워드확인
					</label>
					<input class="form-control" id="passwordInput2" type="password" name="passwordConfirm" />
					
					<div class="form-text" id="passwordMessage1"></div>
					
					<label for="emailInput1" class="form-label">
					이메일
					</label>
					<div class="input-group">
						<input id="emailInput1" class="form-control" type="email" name="email" />
						<button class="btn btn-secondary" id="checkEmailButton">이메일 확인 버튼</button>
					</div>
					<div class="form-text" id="emailMessage"></div>
					
					<label for="nickNameInput1" class="form-label">
					닉네임
					</label>
					<div class="input-group">
						<input id="nickNameInput1" class="form-control" type="text" name="nickName" />
						<button class="btn btn-secondary" id="checkNickNameButton">닉네임 중복여부 확인</button>
					</div>
					<div class="form-text" id="nickNameMessage"></div>
					
					<button class="btn btn-primary"  id="submitButton1" disabled>회원가입</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>