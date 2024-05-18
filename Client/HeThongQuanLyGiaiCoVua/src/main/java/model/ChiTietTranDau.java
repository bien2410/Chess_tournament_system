package model;

public class ChiTietTranDau {
	private int id;
	private String mauCo;
	private float diem;
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
	public float getDiem() {
		return diem;
	}
	public void setDiem(float diem) {
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
	
	
}
