package com.example.kttkpmserver.model;

import java.util.ArrayList;
import java.util.Date;

import com.example.kttkpmserver.designpattern.Component;
import com.example.kttkpmserver.designpattern.Memento;

public class VongDau implements Component{
	private int id;
	private String ten;
	private Date thoiGian;
	private ArrayList<TranDau> dsTranDau;
	private GiaiDau giaiDau;
	public VongDau() {
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
	public Date getThoiGian() {
		return thoiGian;
	}
	public void setThoiGian(Date thoiGian) {
		this.thoiGian = thoiGian;
	}
	public ArrayList<TranDau> getDsTranDau() {
		return dsTranDau;
	}
	public void setDsTranDau(ArrayList<TranDau> dsTranDau) {
		this.dsTranDau = dsTranDau;
	}
	public GiaiDau getGiaiDau() {
		return giaiDau;
	}
	public void setGiaiDau(GiaiDau giaiDau) {
		this.giaiDau = giaiDau;
	}
	@Override
	public String toString() {
		return "VongDau [id=" + id + ", ten=" + ten + ", thoiGian=" + thoiGian + ", dsTranDau=" + dsTranDau + "]";
	}
	@Override
	public boolean daXong() {
		// TODO Auto-generated method stub
		for(TranDau tranDau : dsTranDau) {
			if(!tranDau.daXong()) return false;
		}
		return true;
	}
		
	public Memento luuDsTranDauVaoMemento() {
		return new Memento(dsTranDau);
	}
	
	public void layDsTranDauTuMemento(Memento memento) {
		this.dsTranDau = memento.getDsTranDau();
	}
}
