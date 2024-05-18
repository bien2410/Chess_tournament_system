<%@page import="org.springframework.core.ParameterizedTypeReference"%>
<%@page import="org.springframework.http.HttpMethod"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.VongDau"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.springframework.http.ResponseEntity"%>
<%@page import="model.GiaiDau"%>
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
	GiaiDau giaiDau = (GiaiDau) session.getAttribute("giaiDau");
	if(nd == null || giaiDau == null){
		response.sendRedirect("../GDDangNhap.jsp?err=timeout");
	}
	else{
		if(request.getParameter("index") == null){
			RestTemplate restTemplate = new RestTemplate();
			String apiUrl = "http://localhost:8080/BXH/vongDau?id=" + giaiDau.getId();
			try{
				ResponseEntity<ArrayList<VongDau>> responseEntity = restTemplate.exchange(
					    apiUrl,
					    HttpMethod.GET,
					    null,
					    new ParameterizedTypeReference<ArrayList<VongDau>>() {}
					);
				ArrayList<VongDau> dsVongDau = responseEntity.getBody();
				session.setAttribute("dsVongDau", dsVongDau);
%>

<body>
<header><h1>Chọn vòng đấu</h1></header>
<h5>Giải đấu: <%=giaiDau.getTen() %></h5>
<table>
<tr>
	<th>TT</th>
	<th>Vòng</th>
	<th>Thời gian</th>
</tr>
<%
	for(int i = 0; i < dsVongDau.size(); i++){
		String thoiGian = new SimpleDateFormat("dd/MM/yyyy").format(dsVongDau.get(i).getThoiGian());
		%>
		<tr onclick="openPage('GDChonVongDau.jsp?index=<%=i%>')" style="cursor: pointer;">
			<td><%=i+1%></td>
			<td><%=dsVongDau.get(i).getTen()%></td>
			<td><%=thoiGian%></td>
		</tr>
	<%
	}
%>
</table>
<%			}
			catch(Exception e){
				e.printStackTrace();
				%>
				<script type="text/javascript">
			        alert("Chưa có vòng đấu nào diễn ra!\nVui lòng quay lại");
			        history.back();
			    </script> 
				<%
			}
		}
		else{
			try{
				int index = Integer.parseInt(request.getParameter("index"));
				ArrayList<VongDau> dsVongDau = (ArrayList<VongDau>) session.getAttribute("dsVongDau");
				VongDau vongDau = dsVongDau.get(index);
				session.setAttribute("vongDau", vongDau);
				response.sendRedirect("GDXemBXH.jsp");
			}
			catch(Exception e){
				response.sendRedirect("../GDDangNhap.jsp?err=timeout");
			}
		}
	}
%>
<button onclick="openPage('GDBXHSauTungVongDau.jsp')" class="back-button">Quay lại</button>
</body>
</html>