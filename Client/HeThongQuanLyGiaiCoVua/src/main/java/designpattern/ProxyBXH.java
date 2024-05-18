package designpattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import model.TKKyThu;

public class ProxyBXH {
	private Map<Integer, ArrayList<TKKyThu>> dsBXHTheoVongDau;
	
	public ProxyBXH() {
		dsBXHTheoVongDau = new HashMap<>();
	}
	
	public ProxyBXH(Map<Integer, ArrayList<TKKyThu>> dsBXHTheoVongDau) {
		super();
		this.dsBXHTheoVongDau = dsBXHTheoVongDau;
	}
	public ArrayList<TKKyThu> layBXH(int idVongDau) {
		if(dsBXHTheoVongDau.containsKey(idVongDau)) {
			System.out.println("Lay cu :" + idVongDau);
			return dsBXHTheoVongDau.get(idVongDau);
		}
		else {
			try {
				//lay bxh tu api
				RestTemplate restTemplate = new RestTemplate();
				String apiUrl = "http://localhost:8080/lenLich/BXH?id=" + idVongDau;
				ResponseEntity<ArrayList<TKKyThu>> responseEntity2 = restTemplate.exchange(
					    apiUrl,
					    HttpMethod.GET,
					    null,
					    new ParameterizedTypeReference<ArrayList<TKKyThu>>() {}
					);
				ArrayList<TKKyThu> dsTKKyThu = responseEntity2.getBody();
				//them bxh
				themBXH(idVongDau, dsTKKyThu);
				System.out.println("Lay moi :" + idVongDau);
				return dsTKKyThu;
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}
	}
	public void themBXH(int idVongDau, ArrayList<TKKyThu> BXH) {
		if(!dsBXHTheoVongDau.containsKey(idVongDau)) {
			dsBXHTheoVongDau.put(idVongDau, BXH);
		}
		else {
			dsBXHTheoVongDau.replace(idVongDau, BXH);
		}
	}
	public void xoaBXH(int idVongDau) {
		if(dsBXHTheoVongDau.containsKey(idVongDau)) {
			dsBXHTheoVongDau.remove(idVongDau);
		}
	}
}
