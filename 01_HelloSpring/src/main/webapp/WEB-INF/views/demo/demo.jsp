<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Demo" name="pageTitle"/>
</jsp:include>
<style>
div#demo-container{
	width:40%;
	padding:15px;
	margin:0 auto;
	border:1px solid lightgray;
	border-radius:10px;
}
</style>
<div id="demo-container">
	<form id="devFrm">
	<!-- 이름 -->
	  <div class="form-group row">
	    <label for="devName" class="col-sm-2 col-form-label">이름</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="devName" name="devName">
	    </div>
	  </div>
	  <!-- 나이 -->
	  <div class="form-group row">
	    <label for="devAge" class="col-sm-2 col-form-label">나이</label>
	    <div class="col-sm-10">
	      <input type="number" class="form-control" id="devAge" name="devAge">
	    </div>
	  </div>
	  <!-- 이메일 -->
	  <div class="form-group row">
	    <label for="devEmail" class="col-sm-2 col-form-label">이메일</label>
	    <div class="col-sm-10">
	      <input type="email" class="form-control" id="devEmail" name="devEmail">
	    </div>
	  </div>
	  <!-- 개발언어 -->
	  <div class="form-group row">
	    <label class="col-sm-2 col-form-label">개발언어</label>
	    <div class="col-sm-10">
		    <!-- Java -->
		     <div class="form-check form-check-inline">
			  <input class="form-check-input" type="checkbox" id="Java" value="Java" name="devLang">
			  <label class="form-check-label" for="Java">Java</label>
			</div>
			<!-- Oracle -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="checkbox" id="Oracle" value="Oracle" name="devLang">
			  <label class="form-check-label" for="Oracle">Oracle</label>
			</div>
			<!-- Javascript -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="checkbox" id="Javascript" value="Javascript" name="devLang">
			  <label class="form-check-label" for="Javascript">Javascript</label>
			</div>
	    </div>
	  </div>
	</form>
	<!-- 전송버튼 -->
	<div class="list-group">
		<button type="button" onclick="demo1();" class="list-group-item list-group-item-action">파라미터핸들링-HttpServletRequest</button>
		<button type="button" onclick="demo2();" class="list-group-item list-group-item-action">파라미터핸들링-@RequestParam</button>
		<button type="button" onclick="demo3();" class="list-group-item list-group-item-action">파라미터핸들링-커맨드객체 VO</button>
		<button type="button" onclick="insertDev();" class="list-group-item list-group-item-action">insertDev()</button>
		<button type="button" onclick="selectDevList();" class="list-group-item list-group-item-action">selectDevList()</button>
	</div>
</div>
<script>
function demo1(){
   $("#devFrm").attr("action","${pageContext.request.contextPath}/demo/demo1.do");
   $("#devFrm").submit();
}
function demo2(){
   $("#devFrm").attr("action","${pageContext.request.contextPath}/demo/demo2.do");
   $("#devFrm").submit();
}
function demo3(){
   $("#devFrm").attr("action","${pageContext.request.contextPath}/demo/demo3.do");
   $("#devFrm").submit();
}
function insertDev(){
   $("#devFrm").attr("action","${pageContext.request.contextPath}/demo/insertDev.do");
   $("#devFrm").attr("method","post");
   $("#devFrm").submit();
}
function selectDevList(){
   $("#devFrm").attr("action","${pageContext.request.contextPath}/demo/selectDevList.do");
}
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>