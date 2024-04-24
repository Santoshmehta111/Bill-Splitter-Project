package com.cognizant.billsplitter.dao;
import com.cognizant.billsplitter.bean.Bill;
import com.cognizant.billsplitter.database.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class BillDAO {
	public static Connection con = DB.getDb();
	public ResultSet retreiveBill(long mob) {
		try {
			
			String query = "SELECT * FROM bills where user_mobile = "+mob;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
			return rs;
		}catch(Exception e) {
			return null;
		}
	}
	public boolean addBill(Bill obj) {
		try {
			
			String query = "INSERT INTO bills (bill_name,no_of_person,person_names,paid_by,amount,date_of_bill,user_mobile) VALUES (?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, obj.getBillName());
			ps.setInt(2, obj.getNoOfPerson());
			ps.setString(3, obj.getPersonsName());
			ps.setString(4, obj.getPaidBy());
			ps.setFloat(5, obj.getAmount());
			ps.setString(6, obj.getDateOfBill());
			ps.setLong(7, obj.getUserId());
			
			int rowAffected = ps.executeUpdate();
			
			return rowAffected > 0;
	
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteBill(int id,long mob) {
		try {
			String query = "DELETE FROM bills WHERE bill_id = "+id+" AND user_mobile = "+mob;
            PreparedStatement ps = con.prepareStatement(query);
            
            int rowAffected = ps.executeUpdate();
			return rowAffected > 0;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean updateBill(int id,long mob) {
		try {
			String query = "UPDATE bills SET settle_status = 1 WHERE bill_id = "+id+" AND user_mobile = "+mob;
            PreparedStatement ps = con.prepareStatement(query);
            
            int res = ps.executeUpdate();
            return res>0;
            
		}catch(Exception e) {
			return false;
		}
	}
	public ResultSet retreiveBillByDate(long mob) {
		try {
			String query = "SELECT * FROM bills WHERE user_mobile = "+mob+" ORDER BY date_of_bill ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
			return rs;
		}catch(Exception e) {
			return null;
		}
	}
	
}
