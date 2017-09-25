package com.example.dev.styleomega.Model;

import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Dev on 8/27/2017.
 */

public class Purchases {
    public static Database db;

    public static ArrayList receipt(String receiptID) {

        ArrayList<String> information = new ArrayList<>();

        String select = "SELECT * FROM sales WHERE receiptID='" + receiptID + "'";
        Cursor c = db.runSQLSelect(select);

        if (c.moveToFirst()) {
            do {
                information.add(c.getString(0));
                information.add(c.getString(1));
                information.add(c.getString(2));
                information.add(c.getString(3));
            }
            while (c.moveToNext());
            c.close();
        }
        return information;
    }



    public static void delete (String email){
        String delete="DELETE FROM shoppingcart WHERE email='"+email+"'";
        db.runSQLUpdate(delete);
    }

    public static String generateID (){

        String receiptID;
        String select ="SELECT SUBSTR((receiptID),2,4)+1 AS ID FROM sales ORDER BY SUBSTR((receiptID),2,4)+1 DESC LIMIT 1";
        Cursor c = db.runSQLSelect(select);
        if(c.getCount() > 0){
            c.moveToNext();
            receiptID = "R" + c.getString(0);
        }else{
            receiptID = "R1000";
        }
        c.close();
        return receiptID;
    }


    public static String insert (String receiptID, String email){

        String query ="SELECT * FROM shoppingcart WHERE email='"+email+"'";
        Cursor c = db.runSQLSelect(query);

        double totalPrice=0.0;
        if(c.getCount() > 0) {
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToNext();

                double total = c.getDouble(0);
                totalPrice = (totalPrice + total);
            }
        }
        c.close();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        String insert="INSERT INTO sales(receiptID,email,totalPrice,date) VALUES('"+receiptID+"','"+email+"'," +
                "'"+totalPrice+"','"+date+"')";
        db.runSQLUpdate(insert);
        return receiptID;
    }

    public static String insert1 (String receiptID, String email,String productID){

        String query ="SELECT * FROM product WHERE productID='"+productID+"'";
        Cursor c = db.runSQLSelect(query);
        int price=0;
        if(c.getCount() > 0) {
                c.moveToNext();
                price = (int) c.getDouble(4);
            c.close();
        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        String insert="INSERT INTO sales(receiptID,email,totalPrice,date) VALUES('"+receiptID+"','"+email+"'," +
                "'"+price+"','"+date+"')";
        db.runSQLUpdate(insert);
        return receiptID;
    }

    public static void update(String email){

        String query ="SELECT * FROM shoppingcart WHERE email='"+email+"'";
        Cursor c = db.runSQLSelect(query);
        if (c.getCount() > 0){
            c.moveToNext();
            int qty=c.getInt(1);
            String productID=c.getString(3);
            String size=c.getString(4);

            String query1="UPDATE size SET qty=qty-"+qty+" WHERE size='"+size+"' AND productID='"+productID+"'";
            db.runSQLUpdate(query1);
        }
        c.close();
    }

    public static void update1(String size, String productID ){
            String query1="UPDATE size SET qty=qty-"+1+" WHERE size='"+size+"' AND productID='"+productID+"'";
            db.runSQLUpdate(query1);;
    }
}
