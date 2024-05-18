package com.example.kttkpmserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import com.example.kttkpmserver.model.ChiTietTranDau;
import com.example.kttkpmserver.model.KyThu;
import com.example.kttkpmserver.model.TranDau;
import com.example.kttkpmserver.model.VongDau;

@Repository
public class VongDauDAO{
	public VongDau layVongDauHienTai(int idGiaiDau) {
		Connection con = DAO.getInstance().getConnection();
		String sql = "SELECT * FROM tblvongdau WHERE idGiaiDau = ?";
		String sql2 = "SELECT * FROM tbltrandau wHERE idVongDau = ?";
		String sql3 = "SELECT * FROM tblchitiettrandau WHERE idTranDau = ?";
		String sql4 = "SELECT * FROM tblkythu WHERE id = ?";
		boolean check = false;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idGiaiDau);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				VongDau vongDau = new VongDau();
				int idVongDau = rs.getInt("id");
				vongDau.setId(idVongDau);
				vongDau.setTen(rs.getString("ten"));
				vongDau.setThoiGian(rs.getDate("thoiGian"));
				ArrayList<TranDau> dsTranDau = new ArrayList<>();
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setInt(1, idVongDau);
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()) {
					TranDau tranDau = new TranDau();
					int idTranDau = rs2.getInt("id");
					tranDau.setId(idTranDau);
					ArrayList<ChiTietTranDau> dsChiTietTranDau = new ArrayList<>();
					PreparedStatement ps3 = con.prepareStatement(sql3);
					ps3.setInt(1, idTranDau);
					ResultSet rs3 = ps3.executeQuery();
					while(rs3.next()) {
						ChiTietTranDau chiTietTranDau = new ChiTietTranDau();
						chiTietTranDau.setId(rs3.getInt("id"));
						chiTietTranDau.setMauCo(rs3.getString("mauCo"));
						float diem = rs3.getFloat("diem");
						if(rs3.wasNull()) check = true;
						chiTietTranDau.setDiem(diem);
						PreparedStatement ps4 = con.prepareStatement(sql4);
						ps4.setInt(1, rs3.getInt("idKyThu"));
						ResultSet rs4 = ps4.executeQuery();
						if(rs4.next()) {
							KyThu kyThu = new KyThu();
							kyThu.setId(rs4.getInt("id"));
							kyThu.setTen(rs4.getString("ten"));
							chiTietTranDau.setKyThu(kyThu);
						}
						dsChiTietTranDau.add(chiTietTranDau);
					}
					tranDau.setDsChiTietTranDau(dsChiTietTranDau);
					dsTranDau.add(tranDau);
				}
				if(dsTranDau.size() > 0) vongDau.setDsTranDau(dsTranDau);
				else return vongDau;
				if(check) return vongDau;
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	public boolean luuVongDau(VongDau vongDau) {
		Connection con = DAO.getInstance().getConnection();
		boolean result = true;
		String sql = "INSERT INTO tbltrandau (idVongDau) VALUES (?)";
		String sql2 = "INSERT INTO tblchitiettrandau (mauCo, idKyThu, idTranDau) VALUES(?, ?, ?)";
		try {
			con.setAutoCommit(false);
			for(TranDau tranDau : vongDau.getDsTranDau()) {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, vongDau.getId());
				ps.execute();
				ResultSet generatedKey = ps.getGeneratedKeys();
				if(generatedKey.next()) {
					int id = generatedKey.getInt(1);
					for(ChiTietTranDau chiTiet : tranDau.getDsChiTietTranDau()) {
						PreparedStatement ps2 = con.prepareStatement(sql2);
						ps2.setString(1, chiTiet.getMauCo());
						ps2.setInt(2, chiTiet.getKyThu().getId());
						ps2.setInt(3, id);
						ps2.execute();
					}
				}
			}
			con.commit();
		} 
		catch (Exception e) {
			// TODO: handle exception
			result = false;
			try {
				con.rollback();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			try {
				con.setAutoCommit(true);
			}
			catch (Exception ex) {
				// TODO: handle exception
				result = false;
				ex.printStackTrace();
			}
		}
		return result; 
	}

	public ArrayList<VongDau> layDsVongDau(int idGiaiDau){
		Connection con = DAO.getInstance().getConnection();
		ArrayList<VongDau> dsVongDau = new ArrayList<>();
		String sql = "SELECT * FROM tblvongdau WHERE idGiaiDau = ?";
		String sql2 = "SELECT id FROM tbltrandau WHERE idVongDau = ?";
		String sql3 = "SELECT diem FROM tblchitiettrandau WHERE idTranDau = ?";
		boolean check = false;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idGiaiDau);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				VongDau vongDau = new VongDau();
				int idVongDau = rs.getInt("id");
				vongDau.setId(idVongDau);
				vongDau.setTen(rs.getString("ten"));
				vongDau.setThoiGian(rs.getDate("thoiGian"));
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setInt(1, idVongDau);
				ResultSet rs2 = ps2.executeQuery();
				check = true;
				while(rs2.next()) {
					check = false;
					int idTranDau = rs2.getInt("id");
					PreparedStatement ps3 = con.prepareStatement(sql3);
					ps3.setInt(1, idTranDau);
					ResultSet rs3 = ps3.executeQuery();
					while(rs3.next()) {
						float diem = rs3.getFloat("diem");
						if(rs3.wasNull()) {
							check = true;
							break;
						}
					}
					if(check) break;
				}
				if(check) return dsVongDau;
				dsVongDau.add(vongDau);
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsVongDau;
	}
}
