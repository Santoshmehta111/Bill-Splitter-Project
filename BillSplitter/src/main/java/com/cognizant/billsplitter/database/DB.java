package com.cognizant.billsplitter.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB{
		private static Connection conn = null;
		
		public static Connection getDb(){
			try {
				String url = "jdbc:mysql://localhost:3306/santosh";
				String user = "root";
				String pass = "root";
				
				conn = DriverManager.getConnection(url,user,pass);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return conn;
		}
}