<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>wmInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/linkBlue.css"/>
  <script>
    'use strict';
    
    function fCheck() {
    	let receiveId = myform.receiveId.value;
    	let title = myform.title.value;
    	let content = myform.content.value;
    	
    	if(receiveId.trim() == "") {
    		alert("받는 사람 아이디를 입력하세요");
    		myform.receive.focus();
    	}
    	else if(title.trim() == "") {
    		alert("메세지 제목을 입력하세요");
    		myform.title.focus();
    	}
    	else if(content.trim() == "") {
    		alert("메세지 내용을 입력하세요");
    		myform.content.focus();
    	}
    	else myform.submit();
    }
    
    // 주소록
    function jusorokView() {
    	$(".modal-header #cnt").html(${fn:length(mVos)});
  		let jusorok = '';
  		jusorok += '<table class="table table-hover">';
  		jusorok += '<tr class="table-dark text-dark text-center">';
  		jusorok += '<th>번호</th><th>아이디</th><th>성명</th><th>메일주소</th>';
  		jusorok += '</tr>';
  		jusorok += '<c:forEach var="vo" items="${mVos}" varStatus="st">';
  		jusorok += '<tr onclick="javascript:inputMidCheck(\'${vo.mid}\')" class="text-center">';
  		jusorok += '<td>${st.count}</td>';
  		jusorok += '<td>${vo.mid}</td>';
  		jusorok += '<td>${vo.name}</td>';
  		jusorok += '<td>${vo.email}</td>';
  		jusorok += '</tr>';
  		jusorok += '</c:forEach>';
  		jusorok += '';
  		jusorok += '</table>';
  		$(".modal-body #jusorok").html(jusorok);
    }
    
    // 주소 클릭하면 받는 아이디란에 선택된 아이디를 넣어준다.
    function inputMidCheck(mid) {
    	$("#receiveId").val(mid);
    	$(".btn-close").click();
    	$("#receiveId").attr("readonly", true);
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <form name="myform" method="post" action="wmInputOk">
    <table class="table table-bordered">
      <tr>
        <th>보내는사람</th>
        <td><input type="text" name="sendId" value="${sMid}" readonly class="form-control"/></td>
      </tr>
      <tr>
        <th>받는사람</th>
        <td>
          <div class="input-group">
            <input type="text" name="receiveId" id="receiveId" class="form-control"/>
            <input type="button" value="주소록" onclick="jusorokView()" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#myModal" />
          </div>
        </td>
      </tr>
      <tr>
        <th>메세지 제목</th>
        <td>
          <div class="input-group">
            <input type="text" name="title" id="title" placeholder="메세지 제목을 입력하세요" class="form-control"/>
          </div>
        </td>
      </tr>
      <tr>
        <th>메세지 내용</th>
        <td>
          <div class="input-group">
            <textarea rows="5" name="content" placeholder="메세지 내용을 입력하세요" class="form-control"></textarea>
          </div>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="button" value="메세지전송" onclick="fCheck()" class="btn btn-primary me-2"/>
          <input type="reset" value="다시쓰기" class="btn btn-info me-2"/>
          <input type="button" value="돌아가기" onclick="location.href='webMessage?mSw=1';" class="btn btn-warning"/>
        </td>
      </tr>
    </table>
  </form>
</div>

<!-- 주소록을 Modal로 출력하기 -->
<div class="modal fade" id="myModal">
	<div class="modal-dialog modal-dialog-centered">
	  <div class="modal-content" style="width:600px">
	  	<div class="modal-header" style="width:600px">
	  		<h4 class="modal-title">☆ 주 소 록 ☆(건수:<span id="cnt"></span>)</h4>
	  		<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	  	</div>
	  	<div class="modal-body" style="width:600px;height:400px;overflow:auto;">
	  		<span id="jusorok"></span>
	  	</div>
	  	<div class="modal-footer" style="width:600px">
	  		<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
	  	</div>
	  </div>
	</div>
</div>

<p><br/></p>
</body>
</html>