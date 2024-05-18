<%@page import="designpattern.ProxyBXH"%>
<%@page import="model.NguoiDung"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../openPage.jsp"%>
<title>Insert title here</title>
<link rel="stylesheet" href="styles.css">
</head>
<%
	NguoiDung nd = (NguoiDung) session.getAttribute("nguoiDung");
	if(nd == null){
		response.sendRedirect("GDDangNhap.jsp?err=timeout");
	}
	else{
		
		ProxyBXH proxyBXH = (ProxyBXH) session.getAttribute("proxyBXH");
		if(proxyBXH == null){ 
			proxyBXH = new ProxyBXH();
			session.setAttribute("proxyBXH", proxyBXH);
		}
%>
<body>
<header><h1>Trang chủ</h1></header>
<h5><%=nd.getHoTen()%></h5>
<h5><%=nd.getVaiTro()%></h5>
<button onclick="openPage('quanLyGiaiDau/GDQuanLyGiaiDau.jsp')">Quản lý giải đấu</button>
<button onclick="openPage('lenLich/GDLenLichThiDau.jsp')">Lên lịch thi đấu cho từng vòng đấu</button>
<button onclick="openPage('BXH/GDChonBXH.jsp')">Xem BXH</button>	
</body>
</html>
<%
}%>