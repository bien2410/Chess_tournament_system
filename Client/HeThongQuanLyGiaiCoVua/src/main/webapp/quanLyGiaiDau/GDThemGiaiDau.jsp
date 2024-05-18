<%@page import="org.springframework.http.ResponseEntity"%>
<%@page import="org.springframework.web.client.RestTemplate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.GiaiDau"%>
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
		response.sendRedirect("../GDDangNhap.jsp?err=timeout");
	}
	else{
		if(request.getParameter("action") == null){
%>
<body>
<header><h1>Thêm giải đấu</h1></header>
<form>
<table>
<tr>
	<th>Tên giải đấu</th>
	<td><input type="text" name="txtTen" required></td>
</tr>
<tr>
	<th>Địa điểm</th>
	<td><input type="text" name="txtDiaDiem" required></td>
</tr>
<tr>
	<th>Ngày bắt đầu</th>
	<td><input type="text" name="txtNgayBatDau" required></td>
</tr>
<tr>
	<th>Ngày kết thúc</th>
	<td><input type="text" name="txtNgayKetThuc" required></td>
</tr>
<tr>
	<th>Mô tả</th>
	<td><input type="text" name="txtMoTa"></td>
</tr>
<tr>
	<td><button type="submit" name="action" value="add">Thêm</td>
</tr>
</table>
</form>
<%
	}
	else if(request.getParameter("action").equals("add")){
		GiaiDau giaiDau = new GiaiDau();
		boolean result = false;
		boolean checkDate = true;
		try{			
			giaiDau.setTen(request.getParameter("txtTen"));
			giaiDau.setDiaDiem(request.getParameter("txtDiaDiem"));
			giaiDau.setNgayBatDau(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtNgayBatDau")));
			giaiDau.setNgayKetThuc(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtNgayKetThuc")));
			giaiDau.setMoTa(request.getParameter("txtMoTa"));
			if(giaiDau.getNgayBatDau().getTime() > giaiDau.getNgayKetThuc().getTime()){
				checkDate = false;
				%>
				<script type="text/javascript">
			        alert("Ngày bắt đầu lớn hơn ngày kết thúc.\nVui lòng nhập lại");	
			        history.back();
			    </script> 
				<%		
			}
			else{
				RestTemplate restTemplate = new RestTemplate();
				String apiUrl = "http://localhost:8080/giaiDau";
				ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(apiUrl, giaiDau, Boolean.class);
				result = responseEntity.getBody();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			result = false;
			checkDate = false;
			%>
			<script type="text/javascript">
		        alert("Nhập ngày không chính xác.\nVui lòng nhập lại theo định dạng dd/MM/yyyy");	
		        history.back();
		    </script> 
			<%		
		}		
		if(result){
			%>
			<script type="text/javascript">
		        alert("Thêm giải đấu thành công");	
		        openPage('GDQuanLyGiaiDau.jsp');
		    </script> 
			<%			
		}
		else if(checkDate){
			%>
			<script type="text/javascript">
		        alert("Thêm giải đấu thất bại");
		        history.back();		
		    </script> 
			<%
		}
	}
}%>
<button onclick="openPage('GDQuanLyGiaiDau.jsp')" class="back-button">Quay lại</button>
</body>
</html>