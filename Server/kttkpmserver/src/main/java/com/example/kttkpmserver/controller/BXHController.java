package com.example.kttkpmserver.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kttkpmserver.dao.GiaiDauDAO;
import com.example.kttkpmserver.dao.TKKyThuDAO;
import com.example.kttkpmserver.dao.TranDauDAO;
import com.example.kttkpmserver.dao.VongDauDAO;
import com.example.kttkpmserver.model.ChiTietTranDau;
import com.example.kttkpmserver.model.GiaiDau;
import com.example.kttkpmserver.model.TKKyThu;
import com.example.kttkpmserver.model.TranDau;
import com.example.kttkpmserver.model.VongDau;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/BXH")
public class BXHController {
	@Autowired
	private GiaiDauDAO giaiDauDAO;
	@Autowired
	private VongDauDAO vongDauDAO;
	@Autowired
	private TKKyThuDAO tkKyThuDAO;
	@Autowired 
	private TranDauDAO tranDauDAO;
	
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
	public ResponseEntity<ArrayList<VongDau>> layDsVongDau(@RequestParam(value = "id", required = true) int idGiaiDau){
		ArrayList<VongDau> dsVongDau = new ArrayList<>();
		try {			
			dsVongDau = vongDauDAO.layDsVongDau(idGiaiDau);
		} 
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(dsVongDau, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(dsVongDau, HttpStatus.OK);
	}
	@GetMapping()
	public ResponseEntity<ArrayList<TKKyThu>> layBXH(@RequestParam(value = "id", required = true) int idVongDau){
		ArrayList<TKKyThu> dsTKKyThu = new ArrayList<>();
		try {			
			dsTKKyThu = tkKyThuDAO.thongKeBXH(idVongDau);
		} 
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(dsTKKyThu, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(dsTKKyThu, HttpStatus.OK);
	}
	@GetMapping("/lichSuDau")
	public ResponseEntity<ArrayList<TranDau>> layChiTietKyThu(@RequestParam(value = "idVongDau", required = true) int idVongDau, 
																@RequestParam(value = "idKyThu", required = true) int idKyThu){
		ArrayList<TranDau> dsTranDau = new ArrayList<>();
		try {			
			dsTranDau = tranDauDAO.layLichSuDau(idKyThu, idVongDau);
		} 
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(dsTranDau, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(dsTranDau, HttpStatus.OK);
	}
}
