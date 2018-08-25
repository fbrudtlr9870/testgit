<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Demo Result" name="pageTitle"/>
</jsp:include>
<style>
table#tbl-demo{
	width:50%;
	margin:0 auto;
}
</style>
<table class="table" id="tbl-demo">
	<tr>
		<th scope="col">이름</th>
		<td>${dev.devName }</td>
	</tr>
	<tr>
		<th scope="col">나이</th>
		<td>${dev.devAge }</td>
	</tr>
	<tr>
		<th scope="col">이메일</th>
		<td>${dev.devEmail }</td>
	</tr>
	<tr>
		<th scope="col">개발언어</th>
		<td>
		<c:forEach items="${dev.devLang }" var="lang" varStatus="vs">
			${!vs.first?",":"" }${lang } 
		</c:forEach>
		</td>
	</tr>
</table>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>