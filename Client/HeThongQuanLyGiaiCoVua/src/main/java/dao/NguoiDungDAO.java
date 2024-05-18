package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.NguoiDung;

public class NguoiDungDAO extends DAO{
	public boolean kiemTraDangNhap(NguoiDung nguoiDung) {
		boolean result = false;
		String sql = "SELECT * FROM tblnguoidung WHERE taiKhoan = ? and matKhau = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nguoiDung.getTaiKhoan());
			ps.setString(2, nguoiDung.getMatKhau());
			ResultSet rs = ps.executeQuery();			
			if(rs.next()) {
				String vaiTro = rs.getString("vaiTro");
				if(vaiTro == null || !vaiTro.equals("quan ly")) return false;
				nguoiDung.setId(rs.getInt("id"));
				nguoiDung.setHoTen(rs.getString("hoTen"));
				nguoiDung.setVaiTro(vaiTro);
				result = true;				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
