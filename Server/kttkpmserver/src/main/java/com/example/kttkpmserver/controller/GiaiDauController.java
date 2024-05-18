package com.example.kttkpmserver.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kttkpmserver.dao.GiaiDauDAO;
import com.example.kttkpmserver.model.GiaiDau;

@RestController
@RequestMapping("/giaiDau")
public class GiaiDauController {
	@Autowired
	private GiaiDauDAO giaiDauDAO;
	
	@GetMapping()
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
	
	@PostMapping()
	public ResponseEntity<Boolean> themGiaiDau(@RequestBody GiaiDau giaiDau){
		boolean result = giaiDauDAO.themGiaiDau(giaiDau);
		if(result) return new ResponseEntity<>(result, HttpStatus.OK);
		else return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping()
	public ResponseEntity<Boolean> suaGiaiDau(@RequestBody GiaiDau giaiDau){
		boolean result = giaiDauDAO.suaGiaiDau(giaiDau);
		if(result) return new ResponseEntity<>(result, HttpStatus.OK);
		else return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> xoaGiaiDau(@PathVariable int id){
		boolean result = giaiDauDAO.xoaGiaiDau(id);
		if(result) return new ResponseEntity<>(result, HttpStatus.OK);
		else return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
}
