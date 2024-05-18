package com.example.kttkpmserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	private static DAO instance = null;
	private Connection con = null;
	private DAO() {
		String dbUrl = "jdbc:mysql://localhost:3306/kttkpm?autoReconnect=true&useSSL=false";
		String dbClass = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(dbClass);
			con = DriverManager.getConnection(dbUrl, "root", "2410");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return con;
	}
	public static DAO getInstance() {
		if(instance == null) {
			instance = new DAO();
		}
		return instance;
	}
}
