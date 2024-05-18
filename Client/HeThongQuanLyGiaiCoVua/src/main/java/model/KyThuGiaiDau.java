package model;


public class KyThuGiaiDau {
	private int id;
	private KyThu kyThu;
	public KyThuGiaiDau() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public KyThu getKyThu() {
		return kyThu;
	}
	public void setKyThu(KyThu kyThu) {
		this.kyThu = kyThu;
	}
	@Override
	public String toString() {
		return "KyThuGiaiDau [id=" + id + ", kyThu=" + kyThu + "]";
	}
	
	
	
}
