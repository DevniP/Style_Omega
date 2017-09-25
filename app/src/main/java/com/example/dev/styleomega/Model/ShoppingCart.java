package com.example.dev.styleomega.Model;
import android.database.Cursor;
import java.util.ArrayList;
/**
 * Created by Dev on 8/19/2017.
 */

public class ShoppingCart {

    public static Database db;

    public static String addCart(String email,String productID, String size) {

        String msg = "";
        String query = "SELECT * FROM shoppingcart WHERE email='" + email + "' AND " +
                "productID='" + productID + "' AND size='" + size + "'";
        Cursor c = db.runSQLSelect(query);
        if (c.getCount()>0) {
            c.moveToNext();
            msg = "The product already exists in your cart";

        } else {

            String query1 = "SELECT * FROM product WHERE productID='" + productID + "'";
            Cursor c1 = db.runSQLSelect(query1);

            if (c1.getCount()>0) {
                c1.moveToNext();
               // double totalPrice = (1) * (c1.getDouble(4));
                double totalPrice = (c1.getDouble(4));

                String query2 = "INSERT INTO shoppingcart(email,productID,qty,totalPrice,size) " +
                        "VALUES('" + email + "','" + productID + "','1','" + totalPrice + "','" + size + "')";
                db.runSQLUpdate(query2);
                msg="The product has been added to your cart";
            }
            c1.close();
        }
        c.close();
        return msg;
    }


    public static void updateQty(String productID,String email, String qty, String size) {

        String query = "SELECT * FROM product WHERE productID='" + productID + "'";
        Cursor c = db.runSQLSelect(query);

        if (c.getCount() > 0) {
            c.moveToNext();
            double price = Double.parseDouble(c.getString(4));
            double totalPrice = Integer.parseInt(qty) * (price);

            String query2 = "SELECT * FROM shoppingcart WHERE productID='" + productID + "' " +
                    "AND size='" + size + "' AND email='" + email + "'";
            Cursor c2 = db.runSQLSelect(query2);
            if (c2.getCount() > 0) {
                c2.moveToNext();

                String query1 = "UPDATE shoppingcart SET qty='" + qty + "', totalPrice='" + totalPrice + "' " +
                        "WHERE productID='" + c.getString(0) + "' AND size='" + c2.getString(4) + "'";
                db.runSQLUpdate(query1);
            }
            c2.close();
        }
        c.close();
    }


    public static void removeCart(String email, String productID, String size, String qty){

        String query ="DELETE FROM shoppingcart WHERE email='"+email+"' AND productID='"+productID+"' " +
                "AND size='"+size+"' AND qty='"+qty+"'";

        db.runSQLUpdate(query);
    }


    public static ArrayList getTotalInfo(String email){
        ArrayList<productDetails> a = new ArrayList<>();

        String select = "SELECT * FROM shoppingcart WHERE email='"+email+"'";
        Cursor c = db.runSQLSelect(select);

        if(c.moveToFirst()) {
            do{
                productDetails d = new productDetails();
                d.setProductID(c.getString(3));
                d.setTotalPrice(c.getString(0));
                a.add(d);
            }
            while (c.moveToNext());
            c.close();
        }
        return a;
    }

    public static ArrayList displayCart(String email) {
        ArrayList<productDetails> a = new ArrayList<>();

        String select = "SELECT * FROM shoppingcart WHERE email='" + email + "'";
        Cursor c = db.runSQLSelect(select);

        if (c.moveToFirst()) {
            do {
                productDetails d = new productDetails();
                String query2 = "SELECT * FROM size WHERE productID='" + c.getString(3) + "' " +
                        "AND size='" + c.getString(4) + "' ";
                Cursor c2 = db.runSQLSelect(query2);

                if (c2.moveToFirst()) {
                   // do {
                        int quantity = c2.getInt(1);

                        if (quantity <= 0) {
                            String delete = "DELETE FROM shoppingcart WHERE productID='" + c2.getString(0) + "' " +
                                    "AND size='" + c2.getString(2) + "'";
                            db.runSQLUpdate(delete);
                        } else {
                            String query = "SELECT * FROM product WHERE productID='" + c.getString(3) + "'";
                            Cursor c1 = db.runSQLSelect(query);
                            if (c1.getCount() > 0) {
                                c1.moveToNext();
                                d.setProductID(c.getString(3));
                                d.setName(c1.getString(1));
                                d.setPrice(c1.getString(4));
                                d.setTotalPrice(c.getString(0));
                                d.setImage(c1.getString(5));
                                d.setQty(c.getString(1));
                                d.setSize(c.getString(4));
                            }
                            a.add(d);
                        }
                   // }
                    while (c2.moveToNext());
                    c2.close();
                }
            }
            while (c.moveToNext());
            c.close();
        }
        return a;
    }



    public static ArrayList getProductSizes(String productID){
        ArrayList sizes = new ArrayList();
        String select = "SELECT * FROM size WHERE productID='"+productID+"' AND qty>0";
        Cursor c = db.runSQLSelect(select);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++) {
                c.moveToNext();
                sizes.add(c.getString(2));
            }
        }
        c.close();
        return sizes;
    }

    public static ArrayList getProductQty(String productID, String size){
        ArrayList qty = new ArrayList();
        String select = "SELECT * FROM size WHERE productID='"+productID+"' AND size='"+size+"' AND qty>0";
        Cursor c = db.runSQLSelect(select);
        if(c.getCount()>0){
            for(int i=0;i<c.getCount();i++) {
                c.moveToNext();
                qty.add(c.getString(1));
            }
        }
        c.close();
        return qty;
    }




}
