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

</head>
<body>

	<my:navBar current="memberList"></my:navBar>
	<div class="container">
		<div class="row">
			<div class="col-12">
			
				<h1>회원목록</h1>
				
				
				<table class="table">
					<thead>
						<tr>
							<th>id</th>
							<th>password</th>
							<th>email</th>
							<th>nickName</th>
							<th>inserted</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${memberList }" var="list">
							<tr>
								<td>
									<c:url value="/member/get" var="getMemberUrl">
										<c:param name="id" value="${list.id }"></c:param>
									</c:url>
									<a href="${getMemberUrl }"> ${list.id } </a>
								</td>
								<td>${list.password }</td>
								<td>${list.email }</td>
								<td>${list.nickName }</td>
								<td>${list.inserted }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				
			</div>
		</div>
	</div>

</body>
</html>