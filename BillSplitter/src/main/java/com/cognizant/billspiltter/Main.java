package com.cognizant.billspiltter;
import com.cognizant.billsplitter.controller.BillController;


import java.util.Scanner;

public class Main {
	
	public static void main( String[] args ) throws Exception
    {
    	//Scanner Instantiate
    	Scanner sc = new Scanner(System.in);
    	
    	//Display Object
    	BillController ui = new BillController();
    	
    	try {
    	boolean flag = true;
    	while(flag) {
    	
    		//Bill Splitter Heading 
	    	ui.getHeadingDisplay();
	    	//Bill Splitter Operation Options
	    	ui.getHeadingOptionsDisplay();
	    	
	    	
	    	//Taking Input For Operation
	    	System.out.println("Enter Your Choice : ");
	    	int ch = sc.nextInt();
	    	System.out.println("\n|********************************|\n");
	    	
	    	switch(ch) {
		    	
		    	case 1:{
		    		ui.getSignupDisplay();
		    		continue;
		    	}
		    	case 2:{
		    		ui.getBillDisplay();
		    		continue;
		    	}
		    	case 3:{
		    		ui.getAddBillDisplay();
		    		continue;
		    	}
		    	case 4:{
		    		ui.getDeleteBillDisplay();
		    		continue;
		    	}
		    	case 5:{
		    		ui.getUpdateBillDisplay();
		    		continue;
		    	}
		    	case 6:{
		    		ui.getBillDisplaySort();
		    		continue;
		    	}
		    	case 7:{
		    		System.out.println("Exiting....");
		    		flag = false;
		    		break;
		    	}
		    	default : ui.getWrongInputDisplay();
	    	}
    	}
    	
    	
    	//Closing Scanner
    	sc.close();
    	}catch(Exception e) {
    		System.out.println("Error : "+e.getMessage()+"\n");
    	}
    }
}
