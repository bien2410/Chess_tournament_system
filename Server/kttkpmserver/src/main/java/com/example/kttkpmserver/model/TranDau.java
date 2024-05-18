package com.example.kttkpmserver.model;

import java.util.ArrayList;

import com.example.kttkpmserver.designpattern.Component;

public class TranDau implements Component{
	private int id;
	private ArrayList<ChiTietTranDau> dsChiTietTranDau;
	private VongDau vongDau;
	public TranDau() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<ChiTietTranDau> getDsChiTietTranDau() {
		return dsChiTietTranDau;
	}
	public void setDsChiTietTranDau(ArrayList<ChiTietTranDau> dsChiTietTranDau) {
		this.dsChiTietTranDau = dsChiTietTranDau;
	}
	
	public VongDau getVongDau() {
		return vongDau;
	}
	public void setVongDau(VongDau vongDau) {
		this.vongDau = vongDau;
	}
	@Override
	public String toString() {
		return "TranDau [id=" + id + ", dsChiTietTranDau=" + dsChiTietTranDau + "]";
	}
	@Override
	public boolean daXong() {
		// TODO Auto-generated method stub
		for(ChiTietTranDau chiTietTranDau : dsChiTietTranDau) {
			if(!chiTietTranDau.daXong()) return false;
		}
		return true;
	}
	
	
}
