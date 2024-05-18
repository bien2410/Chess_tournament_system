package model;


import java.util.ArrayList;

public class TranDau {
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
	
	
}
