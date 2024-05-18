<%@page import="model.ChiTietTranDau"%>
<%@page import="org.springframework.core.ParameterizedTypeReference"%>
<%@page import="model.TranDau"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.KyThu"%>
<%@page import="org.springframework.http.HttpMethod"%>
<%@page import="org.springframework.http.ResponseEntity"%>
<%@page import="org.springframework.http.HttpEntity"%>
<%@page import="org.springframework.http.MediaType"%>
<%@page import="org.springframework.http.HttpHeaders"%>
<%@page import="org.springframework.web.client.RestTemplate"%>
<%@page import="model.VongDau"%>
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
	VongDau vongDau = (VongDau) session.getAttribute("vongDau");
	KyThu kyThu = (KyThu) session.getAttribute("kyThu");
	if(nd == null || vongDau == null || kyThu == null){
		response.sendRedirect("../GDDangNhap.jsp?err=timeout");
	}
	else{
		try{
			RestTemplate restTemplate = new RestTemplate();
			String apiUrl = "http://localhost:8080/BXH/lichSuDau?idVongDau=" + vongDau.getId() + "&idKyThu=" + kyThu.getId();
			ResponseEntity<ArrayList<TranDau>> responseEntity = restTemplate.exchange(
				    apiUrl,
				    HttpMethod.GET,
				    null,
				    new ParameterizedTypeReference<ArrayList<TranDau>>() {}
				);
			ArrayList<TranDau> dsTranDau = responseEntity.getBody();
%>
<body>
<header><h1>Chi tiết kỳ thủ</h1></header>
<h5>ID: <%=kyThu.getId() %></h5>
<h5>Họ tên: <%=kyThu.getTen() %></h5>
<table>
<tr>
	<th>TT</th>
	<th>Vòng đấu</th>
	<th>Kết quả</th>
	<th>Đối thủ</th>
	<th>Màu cờ</th>
	<th>Tổng điểm</th>
</tr>
<%
	float diem = 0;
	for(int i = 0; i < dsTranDau.size(); i++){
		TranDau tranDau = dsTranDau.get(i);
		String tenVongDau = tranDau.getVongDau().getTen();
		ChiTietTranDau chiTietTranDau1 = new ChiTietTranDau();
		ChiTietTranDau chiTietTranDau2 = new ChiTietTranDau();
		if(tranDau.getDsChiTietTranDau().get(0).getKyThu().getId() == kyThu.getId()){
			chiTietTranDau1 = tranDau.getDsChiTietTranDau().get(0);
			chiTietTranDau2 = tranDau.getDsChiTietTranDau().get(1);
		}
		else{
			chiTietTranDau1 = tranDau.getDsChiTietTranDau().get(1);
			chiTietTranDau2 = tranDau.getDsChiTietTranDau().get(0);
		}
		String ketQua;
		diem += chiTietTranDau1.getDiem();
		if(chiTietTranDau1.getDiem() == 0){
			ketQua = "thua";
		}
		else if(chiTietTranDau1.getDiem() == 0.5){
			ketQua = "hoa";
		}
		else{
			ketQua = "thang";
		}
		String mauCo = chiTietTranDau1.getMauCo();
		String doiThu = chiTietTranDau2.getKyThu().getTen() + "(ID = " + chiTietTranDau2.getKyThu().getId() + " )";
		%>
		<tr>
			<td><%=i+1%></td>
			<td><%=tenVongDau%></td>
			<td><%=ketQua%></td>
			<td><%=doiThu%></td>
			<td><%=mauCo%></td>
			<td><%=diem%></td>
		</tr>
	<%
	}
%>
</table>
<%
		}
		catch(Exception e){
			e.printStackTrace();
			%>
			<script type="text/javascript">
		        alert("Có lỗi xảy ra!\nVui lòng quay lại");
		        history.back();
		    </script> 
			<%
		}
	}
%>

<button onclick="openPage('GDXemBXH.jsp')" class="back-button">Quay lại</button>
</body>
</html>