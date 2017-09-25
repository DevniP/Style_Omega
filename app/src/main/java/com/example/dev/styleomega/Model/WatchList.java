package com.example.dev.styleomega.Model;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Dev on 9/13/2017.
 */

public class WatchList {
    public static Database db;

    public static String addToList(String email,String productID, String size){

        String msg = "";
        String select= "SELECT * FROM watchlist WHERE email='"+email+"' AND productID='"+productID+"'  AND size='" + size + "'";
        Cursor c = db.runSQLSelect(select);
        if (c.getCount()>0) {
            msg = "The product already exists in your list";

        } else {
            String insert= "INSERT INTO watchlist (email, productID, size) VALUES ('"+email+"','"+productID+"','"+size+"')";
            db.runSQLUpdate(insert);
            msg="The product has been added to your list";
        }
        c.close();
        return msg;
    }

    public static void removeItem(String productID, String email, String size){

        String delete ="DELETE FROM watchlist WHERE email='"+email+"' AND productID='"+productID+"' AND size='"+size+"'";
        db.runSQLUpdate(delete);
    }


    public static ArrayList displayWatchList (String email) {

        ArrayList<productDetails> a = new ArrayList<>();

        String select = "SELECT * FROM watchlist WHERE email='" + email + "'";
        Cursor c = db.runSQLSelect(select);

        if (c.moveToFirst()) {
            do {
                productDetails d = new productDetails();
                String query2 = "SELECT * FROM size WHERE productID='" + c.getString(1) + "' AND size='" + c.getString(2) + "' ";
                Cursor c2 = db.runSQLSelect(query2);

                if (c2.moveToFirst()) {

                        int quantity = c2.getInt(1);

                        if (quantity <= 0) {
                            String delete = "DELETE FROM watchlist WHERE productID='" + c2.getString(0) + "' AND size='" + c2.getString(2) + "'";
                            db.runSQLUpdate(delete);
                        } else {
                            String query = "SELECT * FROM product WHERE productID='" + c.getString(1) + "'";
                            Cursor c1 = db.runSQLSelect(query);
                            if (c1.getCount() > 0) {
                                c1.moveToNext();
                                d.setProductID(c.getString(1));
                                d.setName(c1.getString(1));
                                d.setPrice(c1.getString(4));
                                d.setImage(c1.getString(5));
                                d.setSize(c.getString(2));
                            }
                            a.add(d);
                        }

                    while (c2.moveToNext());
                    c2.close();
                }
            }
            while (c.moveToNext());
            c.close();
        }
        return a;
    }

}
