<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<script>
	$(document).ready(function(){
		$("#button1").click(function(){
			$.ajax({url : "/spr2/ex03/sub03"});
		});
		
		$('#button2').click(function(){
			$.ajax({url : "/spr2/ex03/sub04"});
		});
		
		$('#button3').click(function(){
			$.ajax({
				type : 'GET',
				url : '/spr2/ex03/sub05'
			});
		});
		
		$('#button4').click(function(){
			$.ajax({
				type : 'POST',
				url : '/spr2/ex03/sub06'
			});
		});
		
		$('#button5').click(function(){
			$.ajax({
				type : 'delete',
				url : '/spr2/ex03/sub07'
			});
		});
		
		$('#button6').click(function(){
			$.ajax({
				type : 'PUT',
				url : '/spr2/ex03/sub08'
			});
		});
	});

</script>

<title>Insert title here</title>
</head>
<body>
	<button id="button1">ajax 요청 보내기</button>
	
	<br />
	
	<!--/spr2/ex03/sub04로 요청 보내기  -->
	<button id="button2">요청보내기 2</button>
	
	<br />
	
	<!-- spr2/ex03/sub05 get방식 -->
	<button id="button3">get방식으로 요청 보내기</button>
	
	<br />
	
	<button id="button4">post방식으로요청 보내기</button>
	
	<br />
	
	<!--/spr2/ex03/sub07 delete방식  -->
	<button id="button5"> delete방식 요청 보내기</button>
	
	<br />
	
	<!-- /spr2/ex03/sub08 put 방식 요청 보내기  -->
	<button id="button6"> put 방식 요청 보내기</button>
</body>
</html>