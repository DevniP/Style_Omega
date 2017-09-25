package com.example.dev.styleomega.Model;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.Properties;


/**
 * Created by Dev on 8/12/2017.
 */

public class Customer {

    public static Database db;

    public static String verify(String email , String password){
        String msg="";

        String verifySQL ="SELECT * FROM authentication WHERE email='"+email+"' AND password='"+password+"'";
        Cursor c = db.runSQLSelect(verifySQL);
        if(c.getCount()==1){
            c.moveToNext();
            msg = "Welcome " + email;
            c.close();
        }else{
            msg = "Invalid Email address or Password!";
        }
        return msg;
    }

    public static String security (String email, String answer){
        String msg="";
        String select = "SELECT * FROM authentication WHERE email='"+email+"' AND answer='"+answer+"' COLLATE NOCASE";
        Cursor c = db.runSQLSelect(select);
        if(c.getCount()>0){
            c.moveToNext();
            msg="valid";
            c.close();
        }
        else {
            msg= "The entered answer is invalid";
        }
        return msg;
    }

    public static String sec(String email){
        String msg="";
        String select = "SELECT * FROM authentication WHERE email='"+email+"'";
        Cursor c = db.runSQLSelect(select);
        if(c.getCount()==1){
            c.moveToNext();
            msg="Valid";
            c.close();
        }
        else {
            msg= "Invalid";
        }
        return msg;
    }

    public static String registration (String fName, String lName, String pwd, String tel, String email,
                                       String address, String question, String answer) {

        String msg="";
        String select = "SELECT * FROM authentication WHERE email='"+email+"'";
        Cursor c = db.runSQLSelect(select);
        if(c.getCount()>0){
            msg = "Email has already been used to create an account!";
            c.close();
        }else{
            String insert= "INSERT INTO authentication (FirstName, LastName, password, tel, " +
                    "email, address, question, answer) VALUES ('"+fName+"','"+lName+"','"+pwd+"','"+tel+"'," +
                    "'"+email+"','"+address+"','"+question+"','"+answer+"')";
            db.runSQLUpdate(insert);
            msg=fName + " you have successfully Registered with StyleOmega";
        }
        return msg;
    }

    public static String resetPassword (String email, String pwd, String pwd1, String pwd2) {

        String msg="";
        String select = "SELECT * from authentication WHERE email ='"+email+"' AND password='"+pwd+"' ";
        Cursor c = db.runSQLSelect(select);
        if(!(c.getCount() >0)){
            msg = "The entered current password do not match";
            c.close();
        }
        else if(pwd1.equals(pwd2)){
            String insert= "UPDATE authentication SET Password ='"+pwd1+"' WHERE email ='"+email+"'";
            db.runSQLUpdate(insert);
            msg="Your password has been changed";
        }
        return msg;
    }

    public static void resetForgotPassword (String email, String pwd1) {
            String insert= "UPDATE authentication SET Password ='"+pwd1+"' WHERE email ='"+email+"'";
            db.runSQLUpdate(insert);
    }

    public static ArrayList getAccountInformation(String email){
        ArrayList<String> information = new ArrayList<>();

        String select = "SELECT * FROM authentication WHERE email='"+email+"'";
        Cursor c = db.runSQLSelect(select);
        if(c.getCount()>0){
            c.moveToNext();
            information.add(c.getString(0));
            information.add(c.getString(1));
            information.add(c.getString(2));
            information.add(c.getString(3));
            information.add(c.getString(4));
            c.close();
        }
        return information;
    }

    public static void editCustomerInformation (String fName, String lName, String email, String tel,String address) {
        String insert = "UPDATE authentication SET FirstName='" + fName + "',LastName='" + lName + "'," +
                "tel='" + tel + "',address='" + address + "' WHERE email='" + email + "'";
        db.runSQLUpdate(insert);
    }

    public static ArrayList forgotPassword(String email) {
        ArrayList<String> information = new ArrayList<>();

            String select= "SELECT * FROM authentication WHERE email='"+email+"'";
            Cursor c = db.runSQLSelect(select);
            if(c.getCount()>0){
                c.moveToNext();
                information.add(c.getString(6));
                c.close();
            }
            return information;
    }




}

