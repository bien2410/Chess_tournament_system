package com.example.kttkpmserver.designpattern;

import java.util.ArrayList;

import com.example.kttkpmserver.model.TranDau;
import com.example.kttkpmserver.model.VongDau;

public class Memento {
	private ArrayList<TranDau> dsTranDau;

	public Memento(ArrayList<TranDau> dsTranDau) {
		super();
		this.dsTranDau = dsTranDau;
	}

	public ArrayList<TranDau> getDsTranDau() {
		return dsTranDau;
	}

	public ArrayList<Integer> getKetQua(){
		ArrayList<Integer> result = new ArrayList<>();
		for(TranDau tranDau : dsTranDau) {
			result.add(tranDau.getDsChiTietTranDau().get(0).getKyThu().getId());
			result.add(tranDau.getDsChiTietTranDau().get(1).getKyThu().getId());
		}
		return result;
	}
}
