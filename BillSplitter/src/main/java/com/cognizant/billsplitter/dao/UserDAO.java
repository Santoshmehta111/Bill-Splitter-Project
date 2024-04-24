package com.cognizant.billsplitter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cognizant.billsplitter.bean.User;
import com.cognizant.billsplitter.database.DB;

public class UserDAO {
	public static Connection con = DB.getDb();
	public ResultSet retreiveSingleUser(long mob) {
		try {
			String query = "SELECT user_mobile,user_name FROM user WHERE user_mobile = "+mob;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
			return rs;
		}catch(Exception e) {
			return null;
		}
		
	}
	public ResultSet retreiveUser() {
		try {
			String query = "SELECT user_mobile,user_name FROM user";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
			return rs;
		}catch(Exception e) {
			return null;
		}
	}
	
	public boolean addUser(User obj) {
		try {
			
			String query = "INSERT INTO user (user_mobile,user_name) VALUES (?,?)";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setLong(1, obj.getMobile());
			ps.setString(2, obj.getName());
			int rowAffected = ps.executeUpdate();
			
			return rowAffected > 0;
	
		}catch(Exception e) {
			System.out.println("User Already Exist With This Mobile Number.\n");
			return false;
		}
	}

	public ResultSet validUser(long mob) {
		
		try {

			String query = "SELECT * FROM user WHERE user_mobile = "+mob;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
			return rs;
		}catch(Exception e) {
			return null;
		}
	}


}
