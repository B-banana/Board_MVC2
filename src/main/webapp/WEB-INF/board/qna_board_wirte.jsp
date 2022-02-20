<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
#writeForm {
	width: 500px;
	height: 610px;
	border: 1px solid red;
	margin: auto;
}

h2 {
	text-align: center;
}

table {
	margin: auto;
	width: 450px;
}

.td_left {
	width: 150px;
	background: orange;
	text-align: center;
}

.td_right {
	width: 300px;
	background: skyblue;
}

#commandCell {
	text-align: center;
}

</style>
</head>
<body>
	<!-- 게시판 등록 -->
	<section id="writeForm">
		<h2>게시판 글 등록</h2>
		<!-- multipart/form-data : 모든 문자를 인코딩하지 않음을 명시함.
		이 방식은 <form> 요소가 파일이나 이미지를 서버로 전송할 때 주로 사용함. -->
		<form action="boardWritePro.bo" method="post"
			enctype="multipart/form-data" name="boardform">
			<table>
				<tr>
					<td class="td_left"><label for="BOARD_NAME">글쓴이</label></td>
					<td class="td_right"><input type="text" name="BOARD_NAME" id="BOARD_NAME" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="BOARD_PASS">비밀번호</label></td>
					<td class="td_right"><input type="password" name="BOARD_PASS" id="BOARD_PASS" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="BOARD_SUBJECT">제 목</label></td>
					<td class="td_right"><input type="text" name="BOARD_SUBJECT" id="BOARD_SUBJECT" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="BOARD_FILE">파일첨부</label></td>
					<td class="td_right"><textarea name="BOARD_CONTENT" id="BOARD_CONTENT" cols="40" rows="15" required="required" ></textarea></td>
				</tr>
				<tr>
					<td class="td_left"><label for="BOARD_FILE">내 용</label></td>
					<td class="td_right"><input type="file" name="BOARD_FILE" id="BOARD_FILE" required="required"/></td>
				</tr>
				
			</table>
			
			<section id="commandCell">
				<input type="submit" value="등록" />&nbsp;&nbsp;
				<input type="reset" value="다시쓰기"/>
			</section>

		</form>
	</section>
	<!-- 게시판 등록 끝 -->
</body>
</html>