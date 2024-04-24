package com.cognizant.billsplitter.controller;

import com.cognizant.billsplitter.bean.Bill;
import com.cognizant.billsplitter.bean.User;
import com.cognizant.billsplitter.dao.BillDAO;
import com.cognizant.billsplitter.dao.UserDAO;
import com.cognizant.billsplitter.exceptions.MessageException;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BillController {
	public static String USER_NAME = "";
	public static long USER_MOBILE = 0;
	
	Scanner sc = new Scanner(System.in);
	//Design Display
	public void getHeadingDisplay() {
    	System.out.println("|------------------------------|");
    	System.out.println("|----    SpliWise Bill     ----|");
    	System.out.println("|------------------------------|");
	}
	public void getHeadingOptionsDisplay() {
		System.out.println(" ");
		System.out.println("1.Create Account ");
    	System.out.println("2.Show Bills");
    	System.out.println("3.Add Bills");
    	System.out.println("4.Delete Bill");
    	System.out.println("5.Settle Bill");
    	System.out.println("6.Show Bill By Date");
    	System.out.println("7.Exit");
    	System.out.println(" ");
	}
	public void getWrongInputDisplay() {
		System.out.println("Wrong Input!....Please Try again.\n\n\n");
	}
	
	

	//Splitter Function
	public String splitter(float amt,String p_names,String payer,boolean status) {
		if(status) {
			return "Amount Setteled";
		}else {
			String names[] = p_names.split(",");
			float disAmt = amt/names.length;
			
			String res = "";
			for(int i=0;i<names.length;i++) {
				try {
					ResultSet rs = userDao.retreiveSingleUser(Long.parseLong(names[i]));
					String nameofperson = "";
					while(rs.next()) {
						nameofperson = rs.getString("user_name");
					}
					
					if(!names[i].equals(payer)) {
						res += nameofperson.toUpperCase() +" = "+disAmt+", ";
					}else {
						res += nameofperson.toUpperCase() +" = 0, ";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			
			return res;
		}
	}
	
	//User Operation Display
	UserDAO userDao = new UserDAO();
	
	//Bill Operation Display
	BillDAO billSplit = new BillDAO();
	
	public void getLoginDisplay() {
		
		while(true) {
		System.out.println("Enter Your Mobile Number : ");
		long mob = sc.nextLong();
		
		try {
			ResultSet res = userDao.validUser(mob);
			if(res.next()) {
				USER_NAME = res.getString("user_name");
				USER_MOBILE = mob;
				System.out.println("Hi, "+USER_NAME+". Welcome\n\n");
				break;
			}else {
				System.out.println("Invalid Mobile Number or You are Account doesn't exixst\n\n");
			}
		}catch(Exception e) {
			System.out.println("ERROR : "+e.getMessage());
		}}
		
	}
	public void getSignupDisplay() {
		
		long mobile = 0;
		
		
		while(true) {
			System.out.println("Enter Your Mobile(10-digit) : ");
			mobile = sc.nextLong();
			if(Long.toString(mobile).matches("^[1-9][0-9]{9}$")) {
				break;
			}else {
				System.out.println("Mobile number shoud be in format!!\n");
			}
		}
		
		
			System.out.println("Enter Your Name : ");
			String name = sc.next();
			
			User userobj = new User();
			userobj.setName(name);
			userobj.setMobile(mobile);
			
			boolean res = userDao.addUser(userobj);
			if(res){
				System.out.println("Thank You, Account Created Sucessfully !\n\n");
			
		}
	}
	
	
	
	
	public void getBillDisplay() {
		if(USER_MOBILE == 0) {
			getLoginDisplay();
		}
		
		try {
			ResultSet resCount = billSplit.retreiveBill(USER_MOBILE);
			int size = 0;
			while(resCount.next()) {
				size++;
			}
			if(size > 0) {
				ResultSet rs = billSplit.retreiveBill(USER_MOBILE);
			
				int sno = 1;
				while (rs.next()) {
					 
		            int id = rs.getInt("bill_id");
		            String name = rs.getString("bill_name");
		            int nop = rs.getInt("no_of_person");
		            String persons = rs.getString("person_names");
		            
		            String paidby = "";
		            ResultSet prs = userDao.retreiveSingleUser(Long.parseLong(rs.getString("paid_by")));
					while(prs.next()) {
						paidby = prs.getString("user_name");
					}
		            paidby = paidby.toUpperCase();
		            
		            float amt =  rs.getFloat("amount") ;
		            boolean settle = rs.getBoolean("settle_status");
		            String date = rs.getString("date_of_bill");
		            String msg = splitter(amt,persons,rs.getString("paid_by"),settle);
		            
		            
		            System.out.println("----- Bill List "+sno+" -----\n\nId: "+id + "\nDate: " + date
		                               + "\nName: " + name.toUpperCase()+"\nPaid By: "+paidby+"\nAmount: "+amt+"\nNo Of Persons: "+(nop+1)+"\nSplitter: "+msg+"\n");
		            sno++;
		        }}else {
		        	System.out.println("No Bills Found");
		        }
				
				System.out.println("\n|********************************|\n");
		}catch(Exception e) {
			System.out.println("Sorry System Is Running With Some INTERNAL Issue\n");
		}
		
	}
	public void getAddBillDisplay() throws Exception {
		if(USER_MOBILE == 0) {
			getLoginDisplay();
		}
		
		//Variables
		String b_name = "";
		int personNo = 0;
		long paidBy = 0;
		String b_date = "";
		
		//Bill Object Creation
		Bill bill = new Bill();
		
		//Taking Input From User
		try {
		System.out.println("Enter Name For Bill : ");
		b_name = sc.next();
		
		System.out.println("Enter Number Of Persons : ");
		int nop = sc.nextInt();
		//Validation number of person
		if(nop>6 ||nop<1) {
			throw new MessageException("Number of persons is not in range");
		}
		
		
		//Fetch Persons and List out it
		System.out.println("Select "+nop+" persons from the list : ");
		List<Long> personArrMobile = new ArrayList<Long>();
		List<String> personArrName = new ArrayList<String>();
		List<Long> payerArrMobile  =new ArrayList<Long>();
		List<String> payerArrName  =new ArrayList<String>();
		
		
		ResultSet personRes = userDao.retreiveUser();
		int sno = 1;
		try {
			while(personRes.next()) {
				if(personRes.getLong("user_mobile")!=USER_MOBILE) {
				personArrMobile.add(personRes.getLong("user_mobile"));
				personArrName.add(personRes.getString("user_name"));
				System.out.println(sno+". "+personRes.getString("user_name")+" ("+personRes.getLong("user_mobile")+")");
				sno++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		int listSize = personArrName.size();
		
		
		String personsName = "";
		String personsMobile = "";
		
		
		int check = 0;
		while(check < nop )	{
			System.out.println("Enter Your Option -"+(check+1)+" : ");
			int opno = sc.nextInt();
			
			if(opno < listSize+1 && opno >=1){
				personsName +=personArrName.get(opno - 1)+",";
				personsMobile +=personArrMobile.get(opno - 1)+",";
				
				payerArrMobile.add(personArrMobile.get(opno - 1));
				payerArrName.add(personArrName.get(opno - 1));
				
				check++;
			}else {
				System.out.println("Wrong Input");
			}
			
		}
		personsName +=USER_NAME+",";
		personsMobile +=USER_MOBILE+",";
		
		System.out.println("\nEnter Bill Amount : ");
		float amt = sc.nextFloat();
		
		///Payer
				System.out.println("\nPaid By : ");
				for(int i=0;i<payerArrName.size();i++) {
					System.out.println(i+1+".By "+payerArrName.get(i).toUpperCase());			
				}
				System.out.println((payerArrName.size()+1)+".By "+USER_NAME+" (You)");			
				
				while(true) {
					System.out.println("Enter : ");
					personNo = sc.nextInt();
					
					if(personNo<1 || personNo>(payerArrName.size()+1)) {
						System.out.println("Wrong option choosed !...Try Again");
					}else if(personNo == payerArrName.size()+1) {
						paidBy = USER_MOBILE; 
						break;
					}else{
						paidBy = payerArrMobile.get(personNo - 1);
						break;
						
					}
				}
		
		
		
	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
			try {
				System.out.println("Enter Bill Date (YYYY-MM-DD) : ");
				b_date = sc.next();
				date = format.parse(b_date);
				b_date = format.format(date);
			}catch(Exception e) {
				System.out.println("Invalid Date Format");
			}
		
		
		
		//Set the values to Bill Object
		bill.setBillName(b_name);
		bill.setNoOfPerson(nop);
		bill.setPersonsName(personsMobile);
		bill.setPaidBy(""+paidBy+"");
		bill.setDateOfBill(b_date);
		bill.setAmount(amt);
		bill.setUserId(USER_MOBILE);
		
		//Inserting into database
		boolean res = billSplit.addBill(bill);
		
		if(res) {
			System.out.println("\nBill Added Successfully.");
			System.out.println("\n|********************************|\n");
		}else {
			System.out.println("\nSomething went wrong.");	
			System.out.println("\n|********************************|\n");
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("\nInput Error : "+e.getMessage()+"\n");
		}
	}
	public void getDeleteBillDisplay() {
		
		
		
		System.out.println("Enter the Bill ID : ");
		int bId = sc.nextInt();
		
		System.out.println("Confirm (Y/N) :");
		String c = sc.next();
		
		if(c.equalsIgnoreCase("yes") || c.equalsIgnoreCase("y")) {
			boolean res = billSplit.deleteBill(bId,USER_MOBILE);
			
			if(res) {
				System.out.println("\nDeleted Successfully!\n\n");
			}else {
				System.out.println("\nBill Id: "+bId+", is not exist, OR You are trying to delete someone else bill.\n\n");	
			}
		}else if(c.equalsIgnoreCase("no") || c.equalsIgnoreCase("n")) {
			System.out.println("\nAborted...\n\n");
		}else {
			System.out.println("\nWrong Input.\n\n");
		}
		
	}
	public void getUpdateBillDisplay() {
		
		
		System.out.println("Enter Bill Id : ");
		int bId = sc.nextInt();
		
		System.out.println("Confirm (Y/N) :");
		String c = sc.next();
		
		if(c.equalsIgnoreCase("yes") || c.equalsIgnoreCase("y")) {
			boolean res = billSplit.updateBill(bId,USER_MOBILE);
			if(res) {
				System.out.print("Bill Id : "+bId+" is Settled\n\n");
			}else {
				System.out.println("\nBill Id: "+bId+", is not exist, OR You are trying to settle someone else bill\n\n");
			}
		}else if(c.equalsIgnoreCase("no") || c.equalsIgnoreCase("n")) {
			System.out.println("\nAborted...\n\n");
		}else {
			System.out.println("\nWrong Input.\n\n");
		}
		
		
	}
	public void getBillDisplaySort() {
		
		try {
			ResultSet resCount = billSplit.retreiveBillByDate(USER_MOBILE);
			int size = 0;
			while(resCount.next()) {
				size++;
			}
			if(size > 0) {
			ResultSet rs = billSplit.retreiveBillByDate(USER_MOBILE);
		
			int sno = 1;
			while (rs.next()) {
				 
	            int id = rs.getInt("bill_id");
	            String name = rs.getString("bill_name");
	            int nop = rs.getInt("no_of_person");
	            String persons = rs.getString("person_names");
	           
	            
	            String paidby = "";
	            ResultSet prs = userDao.retreiveSingleUser(Long.parseLong(rs.getString("paid_by")));
				while(prs.next()) {
					paidby = prs.getString("user_name");
				}
	            paidby = paidby.toUpperCase();
	            
	            float amt =  rs.getFloat("amount");
	            boolean settle = rs.getBoolean("settle_status");
	            String date = rs.getString("date_of_bill");
	            String msg = splitter(amt,persons,rs.getString("paid_by"),settle);
	            
	            
	            System.out.println("----- Bill List "+sno+" -----\n\nId: "+id + "\nDate: " + date
	                               + "\nName: " + name.toUpperCase()+"\nPaid By: "+paidby+"\nAmount: "+amt+"\nNo Of Persons: "+(nop+1)+"\nSplitter: "+msg+"\n");
	            sno++;
	        }}else {
	        	System.out.println("No Bills Found");
	        }
			
			System.out.println("\n|********************************|\n");
	}catch(Exception e) {
		System.out.println("Sorry System Is Running With Some INTERNAL Issue\n");
	}
	}
	

	
}
