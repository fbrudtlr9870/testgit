<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시글작성" name="pageTitle"/>
</jsp:include>

<script>
function validate(){
	
	return true;
}

$(function(){
	$("[name=upFile]").on("change",function(){
		//var fileName = $(this).val();
		var fileName = $(this).prop("files")[0].name;
		
		$(this).next(".custom-file-label").html(fileName);
	});
});
</script>

<style>
/* 부트스르랩라벨명 정렬 */
div#board-container label.custom-file-label{text-align:left;}
div#board-container{
	width:400px;
	margin:0 auto;
	text-align:center;
}
div#board-container input{margin-bottom:15px;}
</style>

<div id="board-container">
	<form action="boardFormEnd.do" name="boardFrm" method="post" enctype="multipart/form-data" onsubmit="return validate();">
		<input type="text" name="boardTitle" id="boardTitle" placeholder="제목" class="form-control" required/>
		<input type="text" name="boardWriter" id="boardWriter"  class="form-control" value="${memberLoggedIn.userId }" readonly required/>
		<!-- 파일 -->
		<div class="input-group mb-3" style="padding:0px">
		  <div class="input-group-prepend" style="padding:0px">
		    <span class="input-group-text">첨부파일1</span>
		  </div>
		  <div class="custom-file">
		    <input type="file" class="custom-file-input" name="upFile" id="upFile1">
		    <label class="custom-file-label" for="upFile1">파일을 선택하세요</label>
		  </div>
		</div>
		<div class="input-group mb-3" style="padding:0px">
		  <div class="input-group-prepend" style="padding:0px">
		    <span class="input-group-text">첨부파일2</span>
		  </div>
		  <div class="custom-file">
		    <input type="file" class="custom-file-input" name="upFile" id="upFile2">
		    <label class="custom-file-label" for="upFile2">파일을 선택하세요</label>
		  </div>
		</div>
		<textarea name="boardContent" id="boardContent" class="form-control" cols="30" rows="10" placeholder="내용" required></textarea>
		<br />
		<input type="submit" class="btn btn-outline-success" value="저장" />
		
	</form>
</div>
	
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>