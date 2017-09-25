package com.example.dev.styleomega;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dev.styleomega.Model.inquiryDetails;
import com.example.dev.styleomega.Model.Inquiry;
import com.example.dev.styleomega.Model.Database;

import java.util.ArrayList;
import java.util.Iterator;

public class MyInquiryActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inquiry);
        onCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("MY INQUIRIES");

        LinearLayout linearLayoutMain = (LinearLayout)findViewById(R.id.ll);

        Inquiry.db = new Database(this);
        ArrayList<inquiryDetails> myInquiry = Inquiry.myInquiries(user);

        if(myInquiry.size()>0) {

            for (Iterator in = myInquiry.iterator(); in.hasNext(); ) {
                inquiryDetails d = (inquiryDetails) in.next();

                LinearLayout productLayout = new LinearLayout(this);
                productLayout.setOrientation(LinearLayout.HORIZONTAL);
                productLayout.setPadding(0, 50, 0, 10);

                ImageView productImage = new ImageView(this);
                int image = this.getResources().getIdentifier(d.getImage(), "drawable", "com.example.dev.styleomega");
                productImage.setImageResource(image);
                productImage.setLayoutParams(new LinearLayout.LayoutParams(300, 400));
                productImage.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayout productDetails = new LinearLayout(this);
                productDetails.setOrientation(LinearLayout.VERTICAL);
                productDetails.setPadding(30,0,20,0);

                TextView productName = new TextView(this);
                productName.setText(d.getInquiry() + "\n");

                TextView productName1 = new TextView(this);
                productName1.setText( d.getResponses());
                productName1.setTypeface(null, Typeface.BOLD);

                productLayout.addView(productImage);
                productDetails.addView(productName);
                productDetails.addView(productName1);
                productLayout.addView(productDetails);
                linearLayoutMain.addView(productLayout);
            }
        }
        else{
            TextView productName = new TextView(this);
            productName.setText("NO INQUIRIES");
            linearLayoutMain.addView(productName);
        }

    }
}
