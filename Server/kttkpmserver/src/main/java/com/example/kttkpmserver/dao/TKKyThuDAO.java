package com.example.kttkpmserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.example.kttkpmserver.model.TKKyThu;
import com.example.kttkpmserver.model.VongDau;

@Repository
public class TKKyThuDAO{
	public ArrayList<TKKyThu> thongKeBXH(int idVongDau){
		Connection con = DAO.getInstance().getConnection();
		System.out.println("layBXH");
		ArrayList<TKKyThu> dsTKKyThu = new ArrayList<>();
		String sql = "SELECT idGiaiDau FROM tblvongdau WHERE id = ?";
		String sql2 = "SELECT id, ten FROM tblkythu WHERE id IN (SELECT idKyThu FROM tblKyThuGiaiDau WHERE idGiaiDau = ?)";
		String sql3;
		sql3 = "SELECT mauCo, diem, idTranDau FROM tblchitiettrandau WHERE idKyThu = ? AND idTranDau IN (SELECT id FROM tbltrandau WHERE idVongDau IN (SELECT id FROM tblvongdau WHERE idGiaiDau = ? AND id <= ?))";
		String sql4 = "SELECT idKyThu FROM tblchitiettrandau WHERE idTranDau = ? AND idKyThu != ?";
		Map<Integer, TKKyThu> mapTKKyThu = new HashMap<>();
		Map<Integer, Set<Integer>> dsIdDoiThu = new HashMap<>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idVongDau);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				PreparedStatement ps2 = con.prepareStatement(sql2);
				int idGiaiDau = rs.getInt("idGiaiDau");
				ps2.setInt(1, idGiaiDau);
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()) {
					int idKyThu = rs2.getInt("id");
					TKKyThu tkKyThu = new TKKyThu();
					tkKyThu.getKyThu().setId(idKyThu);
					tkKyThu.getKyThu().setTen(rs2.getString("ten"));
					PreparedStatement ps3 = con.prepareStatement(sql3);
					ps3.setInt(1, idKyThu);
					ps3.setInt(2, idGiaiDau);
					ps3.setInt(3, idVongDau);
					ResultSet rs3 = ps3.executeQuery();
					int soTranCamQuanDen = 0;
					int soTranThang = 0;
					float diem = 0;
					dsIdDoiThu.put(idKyThu, new HashSet<>());
					while(rs3.next()) {
						float diemCong = rs3.getFloat("diem");
						if(rs3.getString("mauCo").equals("den")) soTranCamQuanDen++;
						if(diemCong == 1) soTranThang++;
						diem += diemCong;
						int idTranDau = rs3.getInt("idTranDau");
						PreparedStatement ps4 = con.prepareStatement(sql4);
						ps4.setInt(1, idTranDau);
						ps4.setInt(2, idKyThu);
						ResultSet rs4 = ps4.executeQuery();
						if(rs4.next()) {
							dsIdDoiThu.get(idKyThu).add(rs4.getInt("idKyThu"));
						}
					}
					tkKyThu.setTongDiem(diem);
					tkKyThu.setSoTranCamQuanDen(soTranCamQuanDen);
					tkKyThu.setSoTranThang(soTranThang);
					mapTKKyThu.put(idKyThu, tkKyThu);
				}
				for(Integer i : dsIdDoiThu.keySet()) {
					for(Integer j : dsIdDoiThu.get(i)) {
						mapTKKyThu.get(i).congTongDiemDoiThu(mapTKKyThu.get(j).getTongDiem());
					}
					dsTKKyThu.add(mapTKKyThu.get(i));
				}
				Collections.sort(dsTKKyThu);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsTKKyThu;
	}
}
