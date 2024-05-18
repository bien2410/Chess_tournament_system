<%@page import="org.springframework.http.HttpEntity"%>
<%@page import="org.springframework.http.MediaType"%>
<%@page import="org.springframework.http.HttpHeaders"%>
<%@page import="org.springframework.http.ResponseEntity"%>
<%@page import="org.springframework.http.HttpMethod"%>
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
	GiaiDau giaiDau = (GiaiDau)session.getAttribute("giaiDau");
	if(nd == null || giaiDau == null){
		response.sendRedirect("../GDDangNhap.jsp?err=timeout");
	}
	else{
		String action = request.getParameter("action");	
		if(action == null){
%>
<body>
<header><h1>Chi tiết giải đấu</h1></header>
<form>
<table>
<tr style="display: none;">
<td><input type="text" name="txtId" value="<%=giaiDau.getId()%>" required></td>
</tr>
<tr>
	<th>Tên giải đấu</th>
	<td><input type="text" name="txtTen" value="<%=giaiDau.getTen()%>" required></td>
</tr>
<tr>
	<th>Địa điểm</th>
	<td><input type="text" name="txtDiaDiem" value="<%=giaiDau.getDiaDiem()%>" required></td>
</tr>
<tr>
	<th>Ngày bắt đầu</th>
	<td><input type="text" name="txtNgayBatDau" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(giaiDau.getNgayBatDau())%>" required></td>
</tr>
<tr>
	<th>Ngày kết thúc</th>
	<td><input type="text" name="txtNgayKetThuc" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(giaiDau.getNgayKetThuc())%>" required></td>
</tr>
<tr>
	<th>Mô tả</th>
	<td><input type="text" name="txtMoTa" value="<%=(giaiDau.getMoTa() == null) ? "" : giaiDau.getMoTa()%>"></td>
</tr>
<tr>
	<td><button type="submit" name="action" value="update">Lưu thông tin</td>
	<td><button type="submit" name="action" value="delete">Xóa giải đấu</td>
</tr>
</table>
</form>
<button onclick="openPage('GDQuanLyGiaiDau.jsp')" class="back-button">Quay lại</button>
</body>
</html>
<%}
		else if(action.equals("delete")){
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://localhost:8080/giaiDau/" + request.getParameter("txtId");
		ResponseEntity<Boolean> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.DELETE, null, Boolean.class);
		boolean result = responseEntity.getBody();
		if(result){
			%>
			<script type="text/javascript">
		        alert("Xóa giải đấu thành công");			       			      
		        openPage('GDQuanLyGiaiDau.jsp');
		    </script> 
			<%
		}
		else{
			%>
			<script type="text/javascript">
		        alert("Xóa giải đấu thất bại");
		        history.back();		
		    </script> 
			<%
		}
	}
	else if(action.equals("update")){
		boolean result = false;
		boolean checkDate = true;
		try{
			giaiDau.setId(Integer.parseInt(request.getParameter("txtId")));
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
				HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);
		        HttpEntity<GiaiDau> requestEntity = new HttpEntity<>(giaiDau, headers);
				ResponseEntity<Boolean> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.PUT, requestEntity, Boolean.class);
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
		        alert("Lưu giải đấu thành công");	
		        openPage('GDQuanLyGiaiDau.jsp');
		    </script> 
			<%			
		}
		else if(checkDate){
			%>
			<script type="text/javascript">
		        alert("Lưu giải đấu thất bại");
		        history.back();		
		    </script> 
			<%
		}
	}
}
%>