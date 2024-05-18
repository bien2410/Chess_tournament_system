package model;


import java.util.ArrayList;

public class KyThu {
	private int id;
	private String ten;
	private float elo;
	private String email;
	private String sdt;
	public KyThu() {
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
	public float getElo() {
		return elo;
	}
	public void setElo(float elo) {
		this.elo = elo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	@Override
	public String toString() {
		return "KyThu [id=" + id + ", ten=" + ten + ", elo=" + elo + ", email=" + email + ", sdt=" + sdt + "]";
	}
	
}
