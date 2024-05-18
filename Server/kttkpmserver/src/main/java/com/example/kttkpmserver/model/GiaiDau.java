package com.example.kttkpmserver.model;

import java.util.ArrayList;
import java.util.Date;

import com.example.kttkpmserver.designpattern.Component;

public class GiaiDau implements Component{
	private int id;
	private String ten;
	private String diaDiem;
	private Date ngayBatDau;
	private Date ngayKetThuc;
	private String moTa;
	private ArrayList<VongDau> dsVongDau;
	private ArrayList<KyThuGiaiDau> dsKyThuGiaiDau;
	public GiaiDau() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getDiaDiem() {
		return diaDiem;
	}
	public void setDiaDiem(String diaDiem) {
		this.diaDiem = diaDiem;
	}
	public Date getNgayBatDau() {
		return ngayBatDau;
	}
	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}
	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}
	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		if(moTa == null) this.moTa = "";
		else this.moTa = moTa;
	}
	
	public ArrayList<VongDau> getDsVongDau() {
		return dsVongDau;
	}
	public void setDsVongDau(ArrayList<VongDau> dsVongDau) {
		this.dsVongDau = dsVongDau;
	}
	public ArrayList<KyThuGiaiDau> getDsKyThuGiaiDau() {
		return dsKyThuGiaiDau;
	}
	public void setDsKyThuGiaiDau(ArrayList<KyThuGiaiDau> dsKyThuGiaiDau) {
		this.dsKyThuGiaiDau = dsKyThuGiaiDau;
	}
	@Override
	public String toString() {
		return "GiaiDau [id=" + id + ", ten=" + ten + ", diaDiem=" + diaDiem + ", ngayBatDau=" + ngayBatDau
				+ ", ngayKetThuc=" + ngayKetThuc + ", moTa=" + moTa + ", dsVongDau=" + dsVongDau + ", dsKyThuGiaiDau="
				+ dsKyThuGiaiDau + "]";
	}
	@Override
	public boolean daXong() {
		// TODO Auto-generated method stub
		for(VongDau vongDau : dsVongDau) {
			if(!vongDau.daXong()) return false;
		}
		return true;
	}
	
	
}
