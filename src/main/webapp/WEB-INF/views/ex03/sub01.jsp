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
		
		$('#button7').click(function(){
			$.ajax({
				type : 'GET',
				url : '/spr2/ex03/sub09',
				data : {
					title : "epl",
					writer : "son"
				}  //  자바스크립트 객체(﻿PlainObject)
			});
		});
		
		$('#button8').click(function(){
			$.ajax({
				type : 'POST',
				url : '/spr2/ex03/sub10',
				data : {
					name : "son",
					address : "ganam"
				}  //  자바스크립트 객체(﻿PlainObject)
			});
		});
		$('#button9').click(function(){
			$.ajax({
				type : 'POST',
				url : '/spr2/ex03/sub11',
				data : {
					Book : {
						title : "득점왕 되기",
						writer : "son"
					}
				}  
			});
		});
		$('#button10').click(function(){
			$.ajax({
				url : '/spr2/ex03/sub10',
				type : 'POST',
				data : "name=donald&address=london"
			})
		})
		
		$('#button11').click(function(){
			$.ajax({
				url : '/spr2/ex03/sub11',
				type : 'POST',
				data : "title=apple&writer=Harry"
			})
		})
		
		$('#button12').click(function(e){
			e.preventDefault();
			
			const dataString = $("#form1").serialize();
			
			$.ajax({
				url : '/spr2/ex03/sub10',
				type : 'POST',
				// 데이터 넣는 방법 2가지 : 스트링이나 js객체, 하지만 이를 도와주는 jquery가 serialize()
				data : dataString
				
			})
		})
		
		$('#button13').click(function(e){
			e.preventDefault();
			const dataString = $("#form2").serialize()
			$.ajax({
				url : '/spr2/ex03/sub11',
				type : 'POST',
				data : dataString
			})
		})
		
		$('#button14').click(function(e){
			$.ajax({
				url : '/spr2/ex03/sub12',
				type : 'POST',
				success : function(data){
					console.log("요청 성공!");
					console.log("받은 데이터", data)
				}
			})
		})
		
		$('#button15').click(function(){
			$.ajax({
				url : '/spr2/ex03/sub13',
				type : 'GET',
				success : function(data){
					$('#result1').text(data);
				}
			})
		})
		
		$('#button16').click(function(){
			$.ajax({
				url : '/spr2/ex03/sub14',
				type : 'GET',
				success : function(book){
					console.log(book);
					console.log(book.title);
					console.log(book.writer);
					$('#result2').text(book.title);
					$('#result3').text(book.writer);
				}
			})
		})
		
		$('#button17').click(function(){
			$.ajax({
				url : '/spr2/ex03/sub15',
				type : 'GET',
				success : function(data){
					console.log(data);
				}
			})
		})
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
	
	<br />
	
	<!-- /spr2/ex03/sub09 get방식으로 데이터 보내기 -->
	<p>서버로 데이터 보내기</p>
	<button id="button7">get방식으로 서버로 데이터보내기</button>
	
	<!-- name, address  -->
	<button id="button8">Post방식으로 데이터 보내기</button>
	
	<!-- spr2/ex03/sub11 post방식으로   -->
	<!-- Book객체 데이터 보내기 -->
	<button id="button9">post방식으로 데이터 보내기2</button>
	
	
	<!-- /spr2/ex03/sub10 post 방식으로 데이터보내기 == button8과 같음 -->
	<!-- 전송될 데이터는 name, address -->
	<button id="button10">post방식으로 데이터 보내기(encoded String)</button>
	
	<!-- spr2/ex03/sub11 post방식으로 -->
	<!-- title, writer  -->
	<button id="button11">Post방식으로 데이터 보내기</button>
	
	
	<hr />
	
	<!-- form 데이터 보내기  -->
	<p>폼 데이터 보내기</p>
	<form action="/spr2/ex03/sub10" id="form1" method="post">
		name : <input type="text"  name = "name"/>
		address : <input type="text"  name="address"/>
		<input id="button12" type="submit" value="전송" />
	</form>
	
	<hr />
	<!--button13이 클릭되면,  /spr2/ex03/sub11로 post방식 ajax요청 전송  -->
	<form id="form2">
		title : <input type="text" name="title" /> <br />
		writer <input type="text" name="writer" /> <br />
		<input type="submit" value="전송" id="button13"/>
	</form>
	
	<hr />
	<p>응답 처리하기</p>
	<!-- url : /spr2/ex03/sub12, type : post,  -->
	<button id="button14">응답처리하기</button>
	
	<hr />
	
	<button id="button15">로또번호 받기</button>
	<p>받은 번호 : 
		<span id="result1"></span>
	</p>
	
	<hr />
	<button id="button16">json 데이터 받기</button>
	<p>책 제목 : <span id="result2"></span></p>
	<p>책 저자 : <span id="result3"></span></p>
	
	<hr />
	<button id="button17">map to json</button>
	
	</body>
</html>