package com.example.dev.styleomega.Model;
import java.util.ArrayList;
import android.database.Cursor;

/**
 * Created by Dev on 8/19/2017.
 */

public class Review {

    public static Database db;

    public static void post(String email, String productID, String reviewText, String star){

        String reviewID;
        String query = "SELECT SUBSTR((reviewID),2,5)+1 AS ID FROM review ORDER BY SUBSTR((reviewID),2,5)+1 DESC LIMIT 1";
        Cursor c = db.runSQLSelect(query);

        if (c.getCount() > 0) {
            c.moveToNext();
            reviewID = "R" + c.getString(0);
        }
        else{
            reviewID = "R10000";
        }
        c.close();
        String query2 = "INSERT INTO review (reviewID, email, productID, reviewText, rating) VALUES " +
                "('"+reviewID+"','"+email+"', '"+productID+"', '"+reviewText+"',"+star+")";
        db.runSQLUpdate(query2);
    }


    public static ArrayList productReview(String productID) {
        ArrayList<reviewDetails> a = new ArrayList<>();

        String query = "SELECT * FROM review WHERE productID='" + productID + "'";
        Cursor c = db.runSQLSelect(query);

        if (c.moveToFirst()) {
            do {
                reviewDetails h = new reviewDetails();
                h.setEmail(c.getString(1));
                h.setReviewText(c.getString(3));
                h.setRating(c.getString(4));

                a.add(h);
            }
            while (c.moveToNext());
            c.close();
        }
        return a;
    }
}
