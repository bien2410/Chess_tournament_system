package com.example.kttkpmserver.designpattern;

import java.util.ArrayList;

public class CareTaker {
	
	private ArrayList<Memento> dsMemento;

	public CareTaker() {
		super();
		this.dsMemento = new ArrayList<>();
	}
	
	public void add(Memento memento) {
		dsMemento.add(memento);
	}
	
	public Memento get(int index) {
		if(dsMemento.size() <= index) return null;
		return dsMemento.get(index);
	}
	
	public Memento getLast() {
		return dsMemento.get(dsMemento.size() - 1);
	}
	
	public ArrayList<ArrayList<Integer>> getDsKetQua() {
		ArrayList<ArrayList<Integer>> results = new ArrayList<>();
		for(Memento m : dsMemento) {
			results.add(m.getKetQua());
		}
		return results;
	}
	
	
}
