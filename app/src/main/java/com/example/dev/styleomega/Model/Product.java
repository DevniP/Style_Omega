package com.example.dev.styleomega.Model;
import android.database.Cursor;
import java.util.ArrayList;

/**
 * Created by Dev on 8/18/2017.
 */

public class Product {

    public static Database db;

    public static ArrayList search (String name){
        ArrayList<productDetails> a = new ArrayList<>();

        String query ="SELECT * FROM product WHERE name LIKE '"+name+"%' OR gender LIKE '"+name+"%' OR type " +
                "LIKE '"+name+"%' OR style LIKE '"+name+"%' COLLATE NOCASE";

        Cursor c = db.runSQLSelect(query);
        if(c.moveToFirst()) {
            do {
                productDetails d = new productDetails();
                d.setProductID(c.getString(0));
                d.setName(c.getString(1));
                d.setPrice(c.getString(4));
                d.setImage(c.getString(5));
                a.add(d);
            }
            while(c.moveToNext());
            c.close();
        }
       return a;
    }

    public static ArrayList displayProducts(String type, String gender, String style){
        ArrayList<productDetails> a = new ArrayList<>();

        String query ="SELECT * FROM product WHERE type='"+type+"' AND gender='"+gender+"' AND style='"+style+"'";
        Cursor c = db.runSQLSelect(query);
        if(c.moveToFirst()) {
        do {
            String query1 = "SELECT * FROM size WHERE productID='" + c.getString(0) + "'";
            Cursor c1 = db.runSQLSelect(query1);

            int totalQauntity = 0;
            if(c1.moveToFirst()) {
                    int qauntity = Integer.parseInt(c1.getString(1));
                    totalQauntity = (qauntity + totalQauntity);

                    if (totalQauntity > 0) {
                        productDetails d = new productDetails();
                        d.setProductID(c.getString(0));
                        d.setName(c.getString(1));
                        d.setPrice(c.getString(4));
                        d.setImage(c.getString(5));
                        a.add(d);
                    }
            }
            c1.close();
        } while (c.moveToNext());
            c.close();
        }
        return a;
    }

    public static ArrayList info(String productID){

        ArrayList<productDetails> a = new ArrayList<>();

        String select ="SELECT * FROM product WHERE productID='"+productID+"'";
        Cursor c = db.runSQLSelect(select);

        if (c.getCount()>0){
            productDetails d = new productDetails();
            c.moveToNext();
            d.setProductID(c.getString(0));
            d.setName(c.getString(1));
            d.setPrice(c.getString(4));
            d.setImage(c.getString(5));
            d.setDescription(c.getString(6));
            a.add(d);
            c.close();
        }
        return a;
    }
}
