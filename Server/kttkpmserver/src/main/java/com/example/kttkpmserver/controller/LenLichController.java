package com.example.kttkpmserver.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kttkpmserver.dao.GiaiDauDAO;
import com.example.kttkpmserver.dao.TKKyThuDAO;
import com.example.kttkpmserver.dao.TranDauDAO;
import com.example.kttkpmserver.dao.VongDauDAO;
import com.example.kttkpmserver.designpattern.CareTaker;
import com.example.kttkpmserver.model.GiaiDau;
import com.example.kttkpmserver.model.TKKyThu;
import com.example.kttkpmserver.model.TranDau;
import com.example.kttkpmserver.model.VongDau;

@RestController
@RequestMapping("/lenLich")
public class LenLichController {
	@Autowired
	private GiaiDauDAO giaiDauDAO;
	@Autowired
	private VongDauDAO vongDauDAO;
	@Autowired
	private TranDauDAO tranDauDAO;
	@Autowired
	private TKKyThuDAO tkKyThuDAO;
	
	private VongDau vongDau = new VongDau();
	private CareTaker careTaker;
	
	@GetMapping("/giaiDau")
	public ResponseEntity<ArrayList<GiaiDau>> timKiemGiaiDau(@RequestParam(value = "ten", required = false) String ten,
																@RequestParam(value = "ngayBatDau", required = false) String ngayBatDau,
																@RequestParam(value = "ngayKetThuc", required = false) String ngayKetThuc){
		ArrayList<GiaiDau> dsGiaiDau = new ArrayList<>();
		try {			
			dsGiaiDau = giaiDauDAO.timKiemGiaiDau(ten, ngayBatDau, ngayKetThuc);
		} 
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(dsGiaiDau, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(dsGiaiDau, HttpStatus.OK);
	}
	@GetMapping("/vongDau")
	public ResponseEntity<VongDau> layVongDauHienTai(@RequestParam(value = "id", required = true) int idGiaiDau){
		VongDau vongDau = new VongDau();
		try {			
			vongDau = vongDauDAO.layVongDauHienTai(idGiaiDau);
		} 
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(vongDau, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(vongDau, HttpStatus.OK);
	}
	@GetMapping("/BXH")
	public ResponseEntity<ArrayList<TKKyThu>> layBXH(@RequestParam(value = "id", required = true) int idVongDau){
		ArrayList<TKKyThu> dsTKKyThu = new ArrayList<>();
		try {
			dsTKKyThu = tkKyThuDAO.thongKeBXH(idVongDau);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(dsTKKyThu, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(dsTKKyThu, HttpStatus.OK);
	}
	
	@GetMapping("/xepCap")
	public ResponseEntity<ArrayList<TranDau>> xepCap(@RequestParam(value = "id", required = true) int idVongDau,
														@RequestParam(value = "index", required = true) int index){
		System.out.println(index);
		if(vongDau.getId() != idVongDau) {
			vongDau.setId(idVongDau);
			careTaker = new CareTaker();
		}
		if(careTaker.get(index) != null) {
			vongDau.layDsTranDauTuMemento(careTaker.get(index));
			return new ResponseEntity<>(vongDau.getDsTranDau(), HttpStatus.OK);
		}
		ArrayList<TranDau> dsTranDau = new ArrayList<>();
		try {			
			dsTranDau = tranDauDAO.xepCap(idVongDau, careTaker.getDsKetQua());
			vongDau.setDsTranDau(dsTranDau);
			careTaker.add(vongDau.luuDsTranDauVaoMemento());
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			vongDau.layDsTranDauTuMemento(careTaker.get(index));
			return new ResponseEntity<>(vongDau.getDsTranDau(), HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(dsTranDau, HttpStatus.OK);
	}
	@PostMapping()
	public ResponseEntity<Boolean> xepCapDau(@RequestBody VongDau vongDau){
		boolean result = vongDauDAO.luuVongDau(vongDau);
		if(result) return new ResponseEntity<>(result, HttpStatus.OK);
		else return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
}
