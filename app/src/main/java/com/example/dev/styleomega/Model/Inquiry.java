package com.example.dev.styleomega.Model;
import java.util.ArrayList;
import android.database.Cursor;

/**
 * Created by Dev on 8/19/2017.
 */

public class Inquiry {
    public static Database db;

    public static void post(String productID, String email, String inquiry) {

        String inquiryID;
        String select = "SELECT SUBSTR((inquiryID),2,5)+1 AS ID FROM inquiry ORDER BY SUBSTR((inquiryID),2,5)+1 DESC LIMIT 1";
        Cursor c = db.runSQLSelect(select);
        if (c.getCount() > 0) {
            c.moveToNext();
            inquiryID = "I" + c.getString(0);
        } else {
            inquiryID = "I10000";
        }
        c.close();
        String insert = "INSERT INTO inquiry (inquiryID, productID,email,inquiry) VALUES ('" + inquiryID + "','" + productID + "','" + email + "','" + inquiry + "')";
        db.runSQLUpdate(insert);
    }


    public static ArrayList viewProductInquiry(String productID) {

        ArrayList<inquiryDetails> a = new ArrayList<>();

        String query = "SELECT * FROM inquiry WHERE productID='" + productID + "' AND responses IS NOT null";
        Cursor c = db.runSQLSelect(query);
        if (c.moveToFirst()) {
            do {
                inquiryDetails d = new inquiryDetails();
                d.setInquiry(c.getString(2));
                d.setResponses(c.getString(3));
                a.add(d);
            }
            while (c.moveToNext());
            c.close();
        }
        return a;
    }

    public static ArrayList myInquiries(String email) {

        ArrayList<inquiryDetails> a = new ArrayList<>();

        String query = "SELECT * FROM inquiry WHERE email='" + email + "' AND responses IS NOT NULL";
        Cursor c = db.runSQLSelect(query);
        if (c.moveToFirst()) {
            do {
                String query1 = "SELECT * FROM product WHERE productID='" + c.getString(0) + "'";
                Cursor c1 = db.runSQLSelect(query1);
                if (c1.moveToFirst()) {
                    do {
                        inquiryDetails d = new inquiryDetails();
                        d.setInquiry(c.getString(2));
                        d.setResponses(c.getString(3));
                        d.setName(c1.getString(1));
                        d.setImage(c1.getString(5));

                        a.add(d);
                    }
                    while (c1.moveToNext());
                    c1.close();
                }
            } while (c.moveToNext());
            c.close();
        }
        return a;
    }
}
