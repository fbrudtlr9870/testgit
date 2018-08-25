<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="" name="pageTitle"/>
</jsp:include>
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

	<input type="text" name="boardTitle" id="boardTitle" value="${board.boardTitle }" class="form-control" required/>
	<input type="text" name="boardWriter" id="boardWriter"  class="form-control" value="${board.boardWriter }" readonly required/>
	<!-- 파일 -->
	<c:forEach var="v" items="${attachList }" varStatus="vs">
		  <button type="button" class="btn btn-block btn-outline-success" onclick="fileDownload('${v.originalFileName}','${v.renamedFileName}');">첨부파일${vs.count} - ${v.originalFileName }</button>
	</c:forEach>
	<br />
	<textarea name="boardContent" id="boardContent" class="form-control" cols="30" rows="10" required>${board.boardContent }</textarea>
	<br />
	<input type="submit" class="btn btn-outline-success" value="저장" />
	
</div>
<script>
function fileDownload(oName,rName){
	//한글파일명, 특수문자가 포함되있을 경우 대비
	oName = encodeURIComponent(oName);
	location.href="${pageContext.request.contextPath}/board/boardDownload.do?oName="+oName+"&rName="+rName;
}
</script>
	
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>