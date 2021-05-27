<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String result = (String)request.getAttribute("result");
		if(result == null){
	%>
			${id} 님이 ${rId} 님에게 ${money } 송금완료하여 ${TotalMoney } 남았습니다.
	<%		
		} else {
	%>
			<p>${result }</p>
	<%		
		}
	%>
	<%@ include file="HomeMenu.jsp" %>
</body>
</html>