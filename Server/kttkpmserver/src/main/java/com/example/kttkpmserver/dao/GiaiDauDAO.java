package com.example.kttkpmserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.example.kttkpmserver.model.GiaiDau;
import com.example.kttkpmserver.model.KyThu;
import com.example.kttkpmserver.model.KyThuGiaiDau;
import com.example.kttkpmserver.model.VongDau;

@Repository
public class GiaiDauDAO{
	public ArrayList<GiaiDau> timKiemGiaiDau(String ten, String ngayBatDau, String ngayKetThuc) throws ParseException{
		Connection con = DAO.getInstance().getConnection();
		ArrayList<GiaiDau> dsGiaiDau = new ArrayList<>();
		String sql = "SELECT * FROM tblgiaidau WHERE ten LIKE ?";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date start = null;
		Date finish = null;
		
		if (ngayBatDau != null && !ngayBatDau.isBlank()) {
			sql += " and ngayBatDau >= ?";
			start = sdf.parse(ngayBatDau);
		}
		if (ngayKetThuc != null && !ngayKetThuc.isBlank()) {
			sql += " and ngayKetThuc <= ?";
			finish = sdf.parse(ngayKetThuc);
		}

		if(ten == null || ten.isBlank()) ten = "";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + ten + "%"); 
			if(start != null && finish != null) {
				ps.setDate(2, new java.sql.Date(start.getTime()));
				ps.setDate(3, new java.sql.Date(finish.getTime()));
			}
			else {
				if(start != null) ps.setDate(2, new java.sql.Date(start.getTime()));
				if(finish != null) ps.setDate(2, new java.sql.Date(finish.getTime()));
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GiaiDau giaiDau = new GiaiDau();
				giaiDau.setId(rs.getInt("id"));
				giaiDau.setTen(rs.getString("ten"));
				giaiDau.setDiaDiem(rs.getString("diaDiem"));
				giaiDau.setNgayBatDau(rs.getDate("ngayBatDau"));
				giaiDau.setNgayKetThuc(rs.getDate("ngayKetThuc"));
				giaiDau.setMoTa(rs.getString("moTa"));
				dsGiaiDau.add(giaiDau);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsGiaiDau;
	}
	
	public boolean themGiaiDau(GiaiDau giaiDau) {
		Connection con = DAO.getInstance().getConnection();
		String sql = "INSERT INTO tblgiaidau (ten, diaDiem, ngayBatDau, ngayKetThuc, moTa) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, giaiDau.getTen());
			ps.setString(2, giaiDau.getDiaDiem());
			ps.setDate(3, new java.sql.Date(giaiDau.getNgayBatDau().getTime()));
			ps.setDate(4, new java.sql.Date(giaiDau.getNgayKetThuc().getTime()));
			ps.setString(5, giaiDau.getMoTa());
			ps.execute();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean suaGiaiDau(GiaiDau giaiDau) {
		Connection con = DAO.getInstance().getConnection();
		int result;
		String sql = "UPDATE tblgiaidau SET ten = ?, diaDiem = ?, ngayBatDau = ?, ngayKetThuc = ?, moTa = ? WHERE id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, giaiDau.getTen());
			ps.setString(2, giaiDau.getDiaDiem());
			ps.setDate(3, new java.sql.Date(giaiDau.getNgayBatDau().getTime()));
			ps.setDate(4, new java.sql.Date(giaiDau.getNgayKetThuc().getTime()));
			ps.setString(5, giaiDau.getMoTa());
			ps.setInt(6, giaiDau.getId());
			result = ps.executeUpdate();
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return (result == 0) ? false : true;
	}
	public boolean xoaGiaiDau(int id) {
		Connection con = DAO.getInstance().getConnection();
		int result;
		String sql = "DELETE FROM tblgiaidau WHERE id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return (result == 0) ? false : true;
	}
}
