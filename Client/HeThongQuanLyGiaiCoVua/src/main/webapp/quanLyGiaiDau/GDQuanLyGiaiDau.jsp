<%@page import="org.springframework.core.ParameterizedTypeReference"%>
<%@page import="org.springframework.http.HttpMethod"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference"%>
<%@page import="model.GiaiDau"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.springframework.http.ResponseEntity"%>
<%@page import="org.springframework.web.util.UriComponentsBuilder"%>
<%@page import="org.springframework.web.client.RestTemplate"%>
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
		if(request.getParameter("index") == null){
			String ten = (String) request.getParameter("txtTen");
			String ngayBatDau = (String) request.getParameter("txtNgayBatDau");
			String ngayKetThuc = (String) request.getParameter("txtNgayKetThuc");
			RestTemplate restTemplate = new RestTemplate();
			String apiUrl = "http://localhost:8080/giaiDau";
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
											.queryParam("ten", ten)
											.queryParam("ngayBatDau", ngayBatDau)
											.queryParam("ngayKetThuc", ngayKetThuc);
			try{
				ResponseEntity<ArrayList<GiaiDau>> responseEntity = restTemplate.exchange(
					    builder.toUriString(),
					    HttpMethod.GET,
					    null,
					    new ParameterizedTypeReference<ArrayList<GiaiDau>>() {}
					);
				ArrayList<GiaiDau> dsGiaiDau = responseEntity.getBody();
				int statusCode = responseEntity.getStatusCodeValue();
				if(statusCode != 200){
					%>
					<script type="text/javascript">
				        alert("Nhập ngày không chính xác.\nVui lòng nhập lại theo định dạng dd/MM/yyyy");
				        history.back();
				    </script> 
					<%
				}
				else{
					session.setAttribute("dsGiaiDau", dsGiaiDau);
				
%>
<body>
<header><h1>Quản lý giải đấu</h1></header>
<form method="post" action="GDQuanLyGiaiDau.jsp">
<table>
	<tr>
	<td>Tên giải đấu: </td>
	<td><input type="text" name="txtTen" value="<%=(ten == null) ? "" : ten%>"></td>
	</tr>
	<tr>
	<td>Ngày bắt đầu: </td>
	<td><input type="text" name="txtNgayBatDau" value="<%=(ngayBatDau == null) ? "" : ngayBatDau%>"></td>
	</tr>
	<tr>
	<td>Ngày kết thúc: </td>
	<td><input type="text" name="txtNgayKetThuc" value="<%=(ngayKetThuc == null) ? "" : ngayKetThuc%>"></td>
	<!--chuyen text thanh date-->
	</tr>
	<tr>
	<td><button type="submit">Tìm</td>
	</tr>
</table>
</form>
<table>
<tr>
	<th>TT</th>
	<th>Tên giải đấu</th>
	<th>Địa điểm</th>
	<th>Ngày bắt đầu</th>
	<th>Ngày kết thúc</th>
</tr>
<%
		for(int i = 0; i < dsGiaiDau.size(); i++){	
			String start = new SimpleDateFormat("dd/MM/yyyy").format(dsGiaiDau.get(i).getNgayBatDau());
			String finish = new SimpleDateFormat("dd/MM/yyyy").format(dsGiaiDau.get(i).getNgayKetThuc());
			%>
			<tr onclick="openPage('GDQuanLyGiaiDau.jsp?index=<%=i%>')" style="cursor: pointer;">
				<td><%=i+1%></td>
				<td><%=dsGiaiDau.get(i).getTen()%></td>
				<td><%=dsGiaiDau.get(i).getDiaDiem()%></td>
				<td><%=start%></td>
				<td><%=finish%></td>
			</tr>
			<%
		}
	}
}
		catch(Exception e){
			e.printStackTrace();
			%>
			<script type="text/javascript">
		        alert("Nhập ngày không chính xác.\nVui lòng nhập lại theo định dạng dd/MM/yyyy");
		        history.back();
		    </script> 
			<%
		}
	}
		else{
			try{
				int index = Integer.parseInt(request.getParameter("index"));
				ArrayList<GiaiDau> dsGiaiDau = (ArrayList<GiaiDau>) session.getAttribute("dsGiaiDau");
				GiaiDau giaiDau = dsGiaiDau.get(index);
				session.setAttribute("giaiDau", giaiDau);
				response.sendRedirect("GDChiTietGiaiDau.jsp");
			}
			catch(Exception e){
				response.sendRedirect("../GDDangNhap.jsp?err=timeout");
			}
		}
}
%>

</table>
<button onclick="openPage('GDThemGiaiDau.jsp')">Thêm giải đấu</button>
<button onclick="openPage('../GDTrangChu.jsp')" class="back-button">Quay lại</button>
</body>
</html>