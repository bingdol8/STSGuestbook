<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>

<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<link href="${path}/summernote/summernote.min.css" rel="stylesheet" />
<script src="${path}/summernote/summernote.min.js"></script>

<script>
/* function product_delete(){
	if(confirm("삭제하시겠습니까?")){
		document.form1.action="${path}/shop/product/delete.do";
		document.form1.submit();
	}
} */
$(document).ready(function() {
	  $('#description').summernote({
		  height: 300,
		  width: 800
	  });
	});
var _0x27a8=['1003866EURjnl','764603rJGxqW','154960WzrkZB','${path}/shop/product/delete.do','2wURzcy','546218cvXTkw','form1','270093pZETyT','1008932VImhVE','469467QPQcbw'];var _0x4836=function(_0x15ecd6,_0x166a7){_0x15ecd6=_0x15ecd6-0x18f;var _0x27a8fd=_0x27a8[_0x15ecd6];return _0x27a8fd;};(function(_0x3a6115,_0x42cb89){var _0x2c1ca6=_0x4836;while(!![]){try{var _0x2f9230=-parseInt(_0x2c1ca6(0x196))+parseInt(_0x2c1ca6(0x198))+parseInt(_0x2c1ca6(0x193))+-parseInt(_0x2c1ca6(0x18f))+-parseInt(_0x2c1ca6(0x192))+parseInt(_0x2c1ca6(0x190))+parseInt(_0x2c1ca6(0x191))*parseInt(_0x2c1ca6(0x195));if(_0x2f9230===_0x42cb89)break;else _0x3a6115['push'](_0x3a6115['shift']());}catch(_0x363041){_0x3a6115['push'](_0x3a6115['shift']());}}}(_0x27a8,0x8e363));function product_delete(){var _0xede66f=_0x4836;confirm('삭제하시겠습니까?')&&(document[_0xede66f(0x197)]['action']=_0xede66f(0x194),document['form1']['submit']());}
function product_update(){
	var product_name=document.form1.product_name.value;
	var price=document.form1.price.value;
	var description=document.form1.description.value;
	if(product_name==""){
		alert("상품명을 입력하세요.");
		document.form1.product_name.focus();
		return;
	}
	if(price==""){
		alert("가격을 입력하세요.");
		document.form1.price.focus();
		return;
	}
/* 	if(description==""){
		alert("상품설명을 입력하세요.");
		document.form1.description.focus();
		return;
	} */
	document.form1.action="${path}/shop/product/update.do";
	document.form1.submit();
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>상품정보 편집</h2>
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
<table>
	<tr>
		<td>상품명</td>
		<td><input type="text" name="product_name" value="${dto.product_name}"></td>
	</tr>
	<tr>
		<td>가격</td>
		<td><input type="text" name="price" value="${dto.price}"></td>
	</tr>
	<tr>
		<td>상품설명</td>
		<td><textarea rows="5" cols="60" id="description" name="description">${dto.description}</textarea>
			
		</td>
	</tr>
	<tr>
		<td>상품이미지</td>
		<td><img src="${path}/images/${dto.picture_url}" width="300px" height="300px">
			<br>
			<input type="file" name="file1">		
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="hidden" name="product_id" value="${dto.product_id}">
			<input type="button" value="수정" onclick="product_update()">
			<input type="button" value="삭제" onclick="product_delete()">
			<input type="button" value="목록"
			onclick="location.href='${path}/admin/product/list.do';">
		</td>
	</tr>
</table>
</form>
</body>
</html>