<%@page import="designpattern.ProxyBXH"%>
<%@page import="model.TranDau"%>
<%@page import="model.KyThu"%>
<%@page import="org.springframework.core.ParameterizedTypeReference"%>
<%@page import="org.springframework.http.HttpMethod"%>
<%@page import="model.TKKyThu"%>
<%@page import="model.VongDau"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.springframework.http.ResponseEntity"%>
<%@page import="org.springframework.web.client.RestTemplate"%>
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
	GiaiDau giaiDau = (GiaiDau) session.getAttribute("giaiDau");
	ProxyBXH proxyBXH = (ProxyBXH) session.getAttribute("proxyBXH");
	if(nd == null || giaiDau == null || proxyBXH == null){
		response.sendRedirect("../GDDangNhap.jsp?err=timeout");
	}
	else{
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://localhost:8080/lenLich/vongDau?id=" + giaiDau.getId();
		try{
			ResponseEntity<VongDau> responseEntity = restTemplate.getForEntity(apiUrl, VongDau.class);
			VongDau vongDau = responseEntity.getBody();
			if(vongDau == null){
				%>
				<script type="text/javascript">
			        alert("Giải đấu không có vòng đấu để lên lịch!\nVui lòng quay lại");
			        history.back();
			    </script> 
				<%
			}
			else{
				/* String apiUrl2 = "http://localhost:8080/lenLich/BXH?id=" + vongDau.getId();
				ResponseEntity<ArrayList<TKKyThu>> responseEntity2 = restTemplate.exchange(
					    apiUrl2,
					    HttpMethod.GET,
					    null,
					    new ParameterizedTypeReference<ArrayList<TKKyThu>>() {}
					);
				ArrayList<TKKyThu> dsTKKyThu = responseEntity2.getBody(); */
				ArrayList<TKKyThu> dsTKKyThu = proxyBXH.layBXH(vongDau.getId() - 1);
%>
<body>
<header><h1>Xếp cặp đấu</h1></header>
<h5>Vòng đấu: <%=vongDau.getTen() %></h5>
<h5>Thời gian: <%=new SimpleDateFormat("dd/MM/yyyy").format(vongDau.getThoiGian())%></h5>
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
		<tr>
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
<br>
<%
			String action = request.getParameter("action");
			if(action == null){
				if(vongDau.getDsTranDau() != null){
%>
<table>
<tr>
	<th>TT</th>
	<th>Kỳ thủ 1(Quân đen)</th>
	<th></th>
	<th>Kỳ thủ 2(Quân trắng)</th>
</tr>
<%
	for(int i = 0; i < vongDau.getDsTranDau().size(); i++){
		KyThu kyThu1 = vongDau.getDsTranDau().get(i).getDsChiTietTranDau().get(0).getKyThu();
		KyThu kyThu2 = vongDau.getDsTranDau().get(i).getDsChiTietTranDau().get(1).getKyThu();
		%>
		<tr>
			<td><%=i+1%></td>
			<td><%=kyThu1.getTen() + "(ID = " + kyThu1.getId() + " )"%></td>
			<td>VS</td>
			<td><%=kyThu2.getTen() + "(ID = " + kyThu2.getId() + " )"%></td>
		</tr>
	<%
	}
%>
</table>
<%					
				}
				else{
%>
<form>
	<button type="submit" name="action" value="xepcap">Xếp cặp</button>
</form>
<%	
				}
			}
			else if(action.equals("xepcap") || action.equals("truoc") || action.equals("sau")){
				Integer ttBangXepCap = (Integer) session.getAttribute("ttBangXepCap");
				if(ttBangXepCap == null){
					ttBangXepCap = 0;
				}
				if(action.equals("truoc")) ttBangXepCap -= 1;
				if(action.equals("sau")) ttBangXepCap += 1;
				String apiUrl3 = "http://localhost:8080/lenLich/xepCap?id=" + vongDau.getId() + "&index=" + ttBangXepCap;
				ResponseEntity<ArrayList<TranDau>> responseEntity3 = restTemplate.exchange(
					    apiUrl3,
					    HttpMethod.GET,
					    null,
					    new ParameterizedTypeReference<ArrayList<TranDau>>() {}
					);
				ArrayList<TranDau> dsTranDau = responseEntity3.getBody();
				int status = responseEntity3.getStatusCodeValue();
				if(status == 202) ttBangXepCap -= 1;
				vongDau.setDsTranDau(dsTranDau);
				session.setAttribute("vongDau", vongDau);
				session.setAttribute("ttBangXepCap", ttBangXepCap);
%>
<table>
<tr>
	<th>TT</th>
	<th>Kỳ thủ 1(Quân đen)</th>
	<th></th>
	<th>Kỳ thủ 2(Quân trắng)</th>
</tr>
<%
	for(int i = 0; i < dsTranDau.size(); i++){
		KyThu kyThu1 = dsTranDau.get(i).getDsChiTietTranDau().get(0).getKyThu();
		KyThu kyThu2 = dsTranDau.get(i).getDsChiTietTranDau().get(1).getKyThu();
		%>
		<tr>
			<td><%=i+1%></td>
			<td><%=kyThu1.getTen() + "(ID = " + kyThu1.getId() + " )"%></td>
			<td>VS</td>
			<td><%=kyThu2.getTen() + "(ID = " + kyThu2.getId() + " )"%></td>
		</tr>
	<%
	}
%>
</table>
<br>
<form>
	<% if(ttBangXepCap != 0){%>
	<button type="submit" name="action" value="truoc">Trước</button>
	<%}%>
	<% if(status != 202){%>
	<button type="submit" name="action" value="sau">Sau</button>
	<%}%>
	<br>
	<button type="submit" name="action" value="xacnhan">Xác nhận</button>
</form>
<%
			}
			else if(action.equals("xacnhan")){
				session.removeAttribute("proxyBXH");
				boolean result = false;
				String apiUrl4 = "http://localhost:8080/lenLich";
				VongDau vongDauMoi = (VongDau) session.getAttribute("vongDau");
				ResponseEntity<Boolean> responseEntity4 = restTemplate.postForEntity(apiUrl4, vongDauMoi, Boolean.class);
				result = responseEntity4.getBody();
				if(result){
					%>
					<script type="text/javascript">
				        alert("Lên lịch thi đấu thành công");	
				        openPage('GDLenLichThiDau.jsp');
				    </script> 
					<%			
				}
				else{
					%>
					<script type="text/javascript">
				        alert("Lên lịch thi đấu thất bại");
				        history.back();		
				    </script> 
					<%
				}
			}
			}
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
<button onclick="openPage('GDLenLichThiDau.jsp')" class="back-button">Quay lại</button>
</body>
</html>