package com.example.kttkpmserver.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.example.kttkpmserver.model.ChiTietTranDau;
import com.example.kttkpmserver.model.KyThu;
import com.example.kttkpmserver.model.TKKyThu;
import com.example.kttkpmserver.model.TranDau;
import com.example.kttkpmserver.model.VongDau;

@Repository
public class TranDauDAO{
	private ArrayList<Integer> ans;
	private ArrayList<ArrayList<Integer>> dsKetQua; 
	public ArrayList<TranDau> xepCap(int idVongDau, ArrayList<ArrayList<Integer>> dsKetQua) throws Exception{
		Connection con = DAO.getInstance().getConnection();
		ArrayList<TKKyThu> dsTKKyThu = new ArrayList<>();
		String sql = "SELECT idGiaiDau FROM tblvongdau WHERE id = ?";
		String sql2 = "SELECT id, ten FROM tblkythu WHERE id IN (SELECT idKyThu FROM tblKyThuGiaiDau WHERE idGiaiDau = ?)";
		String sql3 = "SELECT mauCo, diem, idTranDau FROM tblchitiettrandau WHERE idKyThu = ? AND idTranDau IN (SELECT id FROM tbltrandau WHERE idVongDau IN (SELECT id FROM tblvongdau WHERE idGiaiDau = ? AND id < ?))";
		String sql4 = "SELECT idKyThu FROM tblchitiettrandau WHERE idTranDau = ? AND idKyThu != ?";
		Map<Integer, TKKyThu> mapTKKyThu = new HashMap<>();
		Map<Integer, Set<Integer>> dsIdDoiThu = new HashMap<>();
		Map<Integer, Boolean> daXepCap = new HashMap<>();
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
					daXepCap.put(idKyThu, false);
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
						if(rs3.getString("mauCo").equals("den")) soTranCamQuanDen++;
						float diemCong = rs3.getFloat("diem");
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
		ArrayList<Integer> kq = new ArrayList<>();
		ans = null;
		this.dsKetQua = dsKetQua;
//		for(int i = 0; i < dsKetQua.size(); i++) {
//			for(int j = 0; j < dsKetQua.get(i).size(); j++) {
//				System.out.print(dsKetQua.get(i).get(j) + " ");
//			}
//			System.out.println();
//		}
		QuayLui(dsTKKyThu, 0, dsTKKyThu.size(), daXepCap, dsIdDoiThu,kq);
		if(ans == null) {
			ans = new ArrayList<>();
			for(TKKyThu i : dsTKKyThu) {
				ans.add(i.getKyThu().getId());
			}
		}
		if(kq.contains(ans)) {
			throw new Exception();
		}
		ArrayList<TranDau> dsTranDau = new ArrayList<>();
		for(int i = 0; i < ans.size(); i += 2) {
			TranDau tranDau = new TranDau();
			ArrayList<ChiTietTranDau> dsChiTietTranDau = new ArrayList<>();
			TKKyThu kyThu1 = mapTKKyThu.get(ans.get(i));
			TKKyThu kyThu2 = mapTKKyThu.get(ans.get(i + 1));
			ChiTietTranDau ct1 = new ChiTietTranDau();
			ct1.setMauCo("den");
			ChiTietTranDau ct2 = new ChiTietTranDau();
			ct2.setMauCo("trang");
			if(kyThu1.getSoTranCamQuanDen() < kyThu2.getSoTranCamQuanDen()) {
				ct1.setKyThu(kyThu1.getKyThu());
				ct2.setKyThu(kyThu2.getKyThu());
			}
			else {
				ct1.setKyThu(kyThu2.getKyThu());
				ct2.setKyThu(kyThu1.getKyThu());
			}
			dsChiTietTranDau.add(ct1);
			dsChiTietTranDau.add(ct2);
			tranDau.setDsChiTietTranDau(dsChiTietTranDau);
			dsTranDau.add(tranDau);
		}
		return dsTranDau;
	}
	private void QuayLui(ArrayList<TKKyThu> dsTKKyThu, int i, int size, Map<Integer, Boolean> daXepCap, Map<Integer, Set<Integer>> dsIdDoiThu, ArrayList<Integer> kq) {
		// TODO Auto-generated method stub
		if(i >= size - 1) {
			if(ans == null ) {
				boolean check = true;
				for(ArrayList<Integer> arr : dsKetQua) {
					if(!check(arr, kq)) {
						check = false;
						break;
					}
				}
				if(check) {
					ans = new ArrayList<>(kq);
					for(int m = 0; m < ans.size(); m++) System.out.print(ans.get(m) + " ");
					System.out.println();
				}
			}
			return;
		}
		int idKt1 = dsTKKyThu.get(i).getKyThu().getId();
		if(daXepCap.get(idKt1)) QuayLui(dsTKKyThu, i + 1, size, daXepCap, dsIdDoiThu, kq);
		else {
			daXepCap.replace(idKt1, true);
			kq.add(idKt1);
			for(int j = i + 1; j < dsTKKyThu.size(); j++) {
				int idKt2 = dsTKKyThu.get(j).getKyThu().getId();
				if(daXepCap.get(idKt2)) continue;
				if(dsIdDoiThu.get(idKt1).contains(idKt2)) continue;
				daXepCap.replace(idKt2, true);
				kq.add(idKt2);
				QuayLui(dsTKKyThu, i + 1, size, daXepCap, dsIdDoiThu, kq);
				daXepCap.replace(idKt2, false);
				kq.remove(kq.size() - 1);
			}
			daXepCap.replace(idKt1, false);
			kq.remove(kq.size() - 1);
		}
	}
	private boolean check(ArrayList<Integer> a1, ArrayList<Integer> a2) {
		for(int i = 0; i < a1.size(); i += 2) {
			int x1, x2;
			if(a1.get(i) < a1.get(i + 1)) {
				x1 = a1.get(i);
				x2 = a1.get(i + 1);
			}
			else {
				x2 = a1.get(i);
				x1 = a1.get(i + 1);
			}
			int y1, y2;
			if(a2.get(i) < a2.get(i + 1)) {
				y1 = a2.get(i);
				y2 = a2.get(i + 1);
			}
			else {
				y2 = a2.get(i);
				y1 = a2.get(i + 1);
			}
			if(x1 != y1 || x2 != y2) {
				
				return true; 
			}
		}
		return false;
	}
	public ArrayList<TranDau> layLichSuDau(int idKyThu, int idVongDau){
		Connection con = DAO.getInstance().getConnection();
		ArrayList<TranDau> dsTranDau = new ArrayList<>();
		String sql = "SELECT idGiaiDau FROM tblvongdau WHERE id = ?";
		String sql2 = "SELECT * FROM tblvongdau WHERE idGiaiDau = ? AND id <= ?";
		String sql3 = "SELECT idTranDau FROM tblchitiettrandau WHERE idTranDau IN (SELECT id FROM tbltrandau WHERE idVongDau = ?) and idKyThu = ?";
		String sql4 = "SELECT * FROM tblchitiettrandau WHERE idTranDau = ?";
		String sql5 = "SELECT id, ten FROM tblkythu WHERE id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idVongDau);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int idGiaiDau = rs.getInt("idGiaiDau");
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setInt(1, idGiaiDau);
				ps2.setInt(2, idVongDau);
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()) {
					VongDau vongDau = new VongDau();
					vongDau.setId(rs2.getInt("id"));
					vongDau.setTen(rs2.getString("ten"));
					PreparedStatement ps3 = con.prepareStatement(sql3);
					ps3.setInt(1, vongDau.getId());
					ps3.setInt(2, idKyThu);
					ResultSet rs3 = ps3.executeQuery();
					if(rs3.next()) {
						TranDau tranDau = new TranDau();
						int idTranDau = rs3.getInt("idTranDau");
						tranDau.setId(idTranDau);
						PreparedStatement ps4 = con.prepareStatement(sql4);
						ps4.setInt(1, idTranDau);
						ResultSet rs4 = ps4.executeQuery();
						ArrayList<ChiTietTranDau> dsChiTietTranDau = new ArrayList<>();
						while(rs4.next()) {
							ChiTietTranDau chiTietTranDau = new ChiTietTranDau();
							chiTietTranDau.setDiem(rs4.getFloat("diem"));
							chiTietTranDau.setMauCo(rs4.getString("mauCo"));
							int idKT = rs4.getInt("idKyThu");
							if(idKT == idKyThu) {
								KyThu kyThu = new KyThu();
								kyThu.setId(idKyThu);
								chiTietTranDau.setKyThu(kyThu);
								chiTietTranDau.setKyThu(kyThu);
							}
							else {
								PreparedStatement ps5 = con.prepareStatement(sql5);
								ps5.setInt(1, idKT);
								ResultSet rs5 = ps5.executeQuery();
								if(rs5.next()) {
									KyThu kyThu = new KyThu();
									kyThu.setId(rs5.getInt("id"));
									kyThu.setTen(rs5.getString("ten"));
									chiTietTranDau.setKyThu(kyThu);
								}
							}
							dsChiTietTranDau.add(chiTietTranDau);
						}
						tranDau.setDsChiTietTranDau(dsChiTietTranDau);
						tranDau.setVongDau(vongDau);
						dsTranDau.add(tranDau);
					}
				}
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsTranDau;
	}
}
