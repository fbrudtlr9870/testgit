<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/common/header.jsp">
   <jsp:param value="Board" name="pageTitle"/>
</jsp:include>

<style>
div#board-container{
   margin:0 auto;
   width:60%;
}
input#btn-add{float:right; margin:0 0 15px;}
</style>      

<div id="board-container">
	<span>총 ${totalCount }건의 게시물이 있습니다.</span><br />
	
	<input type="button" value="글쓰기" id="btn-add" class="btn btn-outline-success" onclick="location.href='boardForm.do'"/>
	
   <table class="table" id="table-board">
   <tr>
      <th>번호</th>
      <th>제목</th>
      <th>작성자</th>
      <th>내용</th>
      <th>작성일</th>
      <th>첨부파일</th>
      <th>조회수</th>
   </tr>
   <c:if test="${not empty list }">
   <c:forEach var="i" items="${list }" varStatus="vs">
          <tr>
             <td>${i["boardNo"]}</td>
             <td>${i["boardTitle"]}</td>
             <td>${i["boardWriter"]}</td>
             <td>${i["boardContent"]}</td>
             <td>${i["boardDate"]}</td>
             <td>
             	<c:if test="${i.fileCount>0 }">
             		${i.fileCount }
             	</c:if>
             	<c:if test="${i.fileCount<=0 }">
             	  	 ${i.fileCount }
             	</c:if>
             </td>
             <td>${i["boardReadCount"]}</td>
          </tr>
       </c:forEach>
   </c:if>
   
   <c:if test="${empty list }">
          <tr>
             <td colspan="6">데이터가 없습니다.</td>
          </tr>
   </c:if>
</table>
<!-- 페이지바 -->
<%
	int totalCount = Integer.parseInt(String.valueOf(request.getAttribute("totalCount")));
	int numperPage = Integer.parseInt(String.valueOf(request.getAttribute("numperPage")));
	int cPage = 1;
	try{
		cPage = Integer.parseInt(request.getParameter("cPage"));
	}catch(NumberFormatException e){
		
	}
%>
<%=com.kh.spring.common.util.Utils.getPageBar(totalCount,cPage,numperPage,"boardList.do") %>
</div>

</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>