<%@page import="model.NguoiDung"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../openPage.jsp"%>
<title>Insert title here</title>
<link rel="stylesheet" href="../styles.css">
</head>
<%
	NguoiDung nd = (NguoiDung) session.getAttribute("nguoiDung");
	if(nd == null){
		response.sendRedirect("GDDangNhap.jsp?err=timeout");
	}
	else{
%>
<body>
<header><h1>Chọn BXH</h1></header>
<button onclick="openPage('GDBXHSauTungVongDau.jsp')">BXH sau từng vòng đấu</button>
<button onclick="openPage('../GDTrangChu.jsp')" class="back-button">Quay lại</button>
</body>
</html>
<%
	}
%>