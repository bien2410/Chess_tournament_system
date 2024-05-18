package com.example.kttkpmserver.model;

import com.example.kttkpmserver.designpattern.Component;

public class ChiTietTranDau implements Component{
	private int id;
	private String mauCo;
	private Float diem;
	private KyThu kyThu;
	public ChiTietTranDau() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMauCo() {
		return mauCo;
	}
	public void setMauCo(String mauCo) {
		this.mauCo = mauCo;
	}
	public Float getDiem() {
		return diem;
	}
	public void setDiem(Float diem) {
		this.diem = diem;
	}
	public KyThu getKyThu() {
		return kyThu;
	}
	public void setKyThu(KyThu kyThu) {
		this.kyThu = kyThu;
	}
	@Override
	public String toString() {
		return "ChiTietTranDau [id=" + id + ", mauCo=" + mauCo + ", diem=" + diem + ", kyThu=" + kyThu + "]";
	}
	@Override
	public boolean daXong() {
		// TODO Auto-generated method stub
		if(diem == null) return false;
		return true;
	}
	
	
}
