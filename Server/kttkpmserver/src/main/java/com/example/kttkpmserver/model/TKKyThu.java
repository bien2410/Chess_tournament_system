package com.example.kttkpmserver.model;

public class TKKyThu implements Comparable<TKKyThu>{
	private KyThu kyThu;
	private float tongDiem;
	private int soTranThang;
	private float tongDiemDoiThu;
	private int soTranCamQuanDen;
	public TKKyThu() {
		super();
		kyThu = new KyThu();
		this.tongDiemDoiThu = 0;
	}
	public float getTongDiem() {
		return tongDiem;
	}
	public void setTongDiem(float tongDiem) {
		this.tongDiem = tongDiem;
	}
	public int getSoTranThang() {
		return soTranThang;
	}
	public void setSoTranThang(int soTranThang) {
		this.soTranThang = soTranThang;
	}
	public float getTongDiemDoiThu() {
		return tongDiemDoiThu;
	}
	public void setTongDiemDoiThu(float tongDiemDoiThu) {
		this.tongDiemDoiThu = tongDiemDoiThu;
	}
	public int getSoTranCamQuanDen() {
		return soTranCamQuanDen;
	}
	public void setSoTranCamQuanDen(int soTranCamQuanDen) {
		this.soTranCamQuanDen = soTranCamQuanDen;
	}
	public void congTongDiemDoiThu(float diem) {
		this.tongDiemDoiThu += diem;
	}
	
	public KyThu getKyThu() {
		return kyThu;
	}
	public void setKyThu(KyThu kyThu) {
		this.kyThu = kyThu;
	}
	@Override
	public int compareTo(TKKyThu o) {
		// TODO Auto-generated method stub
		if(this.tongDiem > o.tongDiem) return -1;
		if(this.tongDiem < o.tongDiem) return 1;
		if(this.soTranThang > o.soTranThang) return -1;
		if(this.soTranThang < o.soTranThang) return 1;
		if(this.tongDiemDoiThu > o.tongDiemDoiThu) return -1;
		if(this.tongDiemDoiThu < o.tongDiemDoiThu) return 1;
		if(this.soTranCamQuanDen > o.soTranCamQuanDen) return -1;
		if(this.soTranCamQuanDen < o.soTranCamQuanDen) return 1;
		return 0;
	}
	@Override
	public String toString() {
		return "\nId=" + kyThu.getId() + ", Ten=" + kyThu.getTen() + "\nTKKyThu [tongDiem=" + tongDiem + ", soTranThang=" + soTranThang + ", tongDiemDoiThu=" + tongDiemDoiThu
				+ ", soTranCamQuanDen=" + soTranCamQuanDen + "]";
	}
	
	
}
