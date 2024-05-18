<%@page import="designpattern.ProxyBXH"%>
<%@page import="org.springframework.core.ParameterizedTypeReference"%>
<%@page import="model.TKKyThu"%>
<%@page import="java.util.ArrayList"%>
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
	ProxyBXH proxyBXH = (ProxyBXH) session.getAttribute("proxyBXH");
	if(nd == null || vongDau == null || proxyBXH == null){
		response.sendRedirect("../GDDangNhap.jsp?err=timeout");
	}
	else{
		if(request.getParameter("index") == null){
			/* RestTemplate restTemplate = new RestTemplate();
			String apiUrl = "http://localhost:8080/BXH?id=" + vongDau.getId();
			 */
			try{
				/* ResponseEntity<ArrayList<TKKyThu>> responseEntity = restTemplate.exchange(
					    apiUrl,
					    HttpMethod.GET,
					    null,
					    new ParameterizedTypeReference<ArrayList<TKKyThu>>() {}
					);
				ArrayList<TKKyThu> dsTKKyThu = responseEntity.getBody();  */
				ArrayList<TKKyThu> dsTKKyThu = proxyBXH.layBXH(vongDau.getId());
				session.setAttribute("dsTKKyThu", dsTKKyThu); 
%>

<body>
<header><h1>Xem BXH</h1></header>
<table>
<tr>
	<th>TT</th>
	<th>Id</th>
	<th>Tên kỳ thủ</th>
	<th>Số điểm</th>
	<th>Số trận thắng</th>
	<th>Tổng điểm đối thủ</th>
	<th>Số trận cầm quân đen</th>
</tr>
<%
	for(int i = 0; i < dsTKKyThu.size(); i++){
		
		%>
		<tr onclick="openPage('GDXemBXH.jsp?index=<%=i%>')" style="cursor: pointer;">
			<td><%=i+1%></td>
			<td><%=dsTKKyThu.get(i).getKyThu().getId()%></td>
			<td><%=dsTKKyThu.get(i).getKyThu().getTen()%></td>
			<td><%=dsTKKyThu.get(i).getTongDiem()%></td>
			<td><%=dsTKKyThu.get(i).getSoTranThang()%></td>
			<td><%=dsTKKyThu.get(i).getTongDiemDoiThu()%></td>
			<td><%=dsTKKyThu.get(i).getSoTranCamQuanDen()%></td>
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
			        alert("Có lỗi xảy ra!\nVui lòng quay lại");
			        history.back();
			    </script> 
				<%
			}
		}
		else{
			try{
				int index = Integer.parseInt(request.getParameter("index"));
				ArrayList<TKKyThu> dsTKKyThu = (ArrayList<TKKyThu>) session.getAttribute("dsTKKyThu");
				TKKyThu kyThu = dsTKKyThu.get(index);
				session.removeAttribute("dsTKKyThu");
				session.setAttribute("kyThu", kyThu.getKyThu());
				response.sendRedirect("GDChiTietKyThu.jsp");
			}
			catch(Exception e){
				response.sendRedirect("../GDDangNhap.jsp?err=timeout");
			}
		}
	}
%>
<button onclick="openPage('GDChonVongDau.jsp')" class="back-button">Quay lại</button>
</body>
</html>