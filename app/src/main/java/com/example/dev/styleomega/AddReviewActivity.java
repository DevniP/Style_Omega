package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.Review;

public class AddReviewActivity extends Navigation {
    private EditText review;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        onCreation();

        review = (EditText) findViewById(R.id.tv_reviewBox);
        rating = (RatingBar) findViewById(R.id.ratingBar);
    }

    public void addReview(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        String productID = sharedPreferences.getString("productID",null);

        final String in = review.getText().toString();
        final String star = String.valueOf(rating.getRating());

        if(in.trim().equals("")){
            review.setError("Please enter your review");
        }else{
            Review.db = new Database(this);
            Review.post(user, productID, in, star);

            String msg= "Thank you!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,ProductInformationActivity.class);
            startActivity(intent);

        }

    }
}
