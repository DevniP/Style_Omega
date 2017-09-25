package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.Product;
import com.example.dev.styleomega.Model.Inquiry;
import com.example.dev.styleomega.Model.Review;
import com.example.dev.styleomega.Model.ShoppingCart;
import com.example.dev.styleomega.Model.WatchList;
import com.example.dev.styleomega.Model.productDetails;
import com.example.dev.styleomega.Model.inquiryDetails;
import com.example.dev.styleomega.Model.reviewDetails;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductInformationActivity extends Navigation {
    String size;
    Spinner spinner;
    String prices;
    public static final String EXTRA_PRICE = "com.example.Application.PRICE";
    public static final String EXTRA_SIZE = "com.example.Application.SIZE";
    public static final String EXTRA_PRODUCTID = "com.example.Application.PRODUCTID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        onCreation();
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String productID = sharedPreferences.getString("productID",null);

        Customer.db = new Database(this);
        ArrayList<String> pInfo = Product.info(productID);

        for (Iterator it = pInfo.iterator(); it.hasNext(); ) {
            final productDetails pd = (productDetails) it.next();

            TextView title = (TextView) findViewById(R.id.tv_name);
            title.setText(pd.getName().toUpperCase());
            title.setTypeface(null, Typeface.BOLD);

            ImageView image = (ImageView) findViewById(R.id.iv_Image);
            int id = this.getResources().getIdentifier(pd.getImage(), "drawable", "com.example.dev.styleomega");
            image.setImageResource(id);

            TextView price = (TextView) findViewById(R.id.tv_price);
            price.setText(pd.getPrice() + " LKR");
            price.setTypeface(null, Typeface.BOLD);

            TextView desc = (TextView) findViewById(R.id.tv_desc);
            desc.setText("\n" + pd.getDescription());
            desc.setTextSize(17);


            TextView size = (TextView) findViewById(R.id.tv_size);

            ShoppingCart.db = new Database(this);

            size.setText("\n Available sizes: ");
            size.setTextSize(16);

            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayList<String> s = ShoppingCart.getProductSizes(productID);
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            Button buy = (Button) findViewById(R.id.btn_purchase);
            ViewGroup.MarginLayoutParams marginParams4 = (ViewGroup.MarginLayoutParams) buy.getLayoutParams();
            marginParams4.setMargins(50, 30, 10, 0);
            buy.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    prices = pd.getPrice();
                    buy(v);
                }
            });

            Button shoppingButton = (Button) findViewById(R.id.btn_addCart);
            ViewGroup.MarginLayoutParams marginParams3 = (ViewGroup.MarginLayoutParams) shoppingButton.getLayoutParams();
            marginParams3.setMargins(50, 30, 10, 0);
            shoppingButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    shopping(v);
                }
            });

            Button watchlistButton = (Button) findViewById(R.id.btn_addList);
            ViewGroup.MarginLayoutParams marginParams2 = (ViewGroup.MarginLayoutParams) watchlistButton.getLayoutParams();
            marginParams2.setMargins(50, 30, 10, 0);
            watchlistButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    watchlist(v);
                }
            });

            Button shareButton = (Button) findViewById(R.id.btn_Share);
            ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) shareButton.getLayoutParams();
            marginParams1.setMargins(50, 30, 10, 0);
            shareButton.setTag(pd.getName());
            shareButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    share(v);
                }
            });

            Inquiry.db = new Database(this);
            ArrayList<inquiryDetails> productInquiry = Inquiry.viewProductInquiry(productID);
            if(productInquiry.size()>0) {

                LinearLayout ll_inquiry = (LinearLayout)findViewById(R.id.ll_inquiry);

                TextView inquiryHeading = new TextView(this);
                inquiryHeading.setText("INQUIRIES");
                inquiryHeading.setTypeface(null, Typeface.BOLD);
                inquiryHeading.setTextSize(20);

                LinearLayout linearLayout = new LinearLayout(this);
                for (Iterator in = productInquiry.iterator(); in.hasNext(); ) {
                    inquiryDetails d = (inquiryDetails) in.next();
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.setPadding(10,30,0,30);

                    TextView tv_inquiry = new TextView(this);
                    tv_inquiry.setText(d.getInquiry());
                    TextView tv_response = new TextView(this);
                    tv_response.setText(d.getResponses() + "\n");
                    tv_response.setTypeface(null, Typeface.BOLD);
                    linearLayout.addView(tv_inquiry);
                    linearLayout.addView(tv_response);
                }

                ll_inquiry.addView(inquiryHeading);
                ll_inquiry.addView(linearLayout);
            }

            Button inquiryButton = (Button) findViewById(R.id.btn_AddInquiry);
            inquiryButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    inquiry(v);
                }
            });

            Review.db = new Database(this);
            ArrayList<reviewDetails> productReview = Review.productReview(productID);
            if(productInquiry.size()>0) {

                LinearLayout ll_review = (LinearLayout)findViewById(R.id.ll_review);

                TextView reviewHeading = new TextView(this);
                reviewHeading.setText("REVIEWS");
                reviewHeading.setTypeface(null, Typeface.BOLD);
                reviewHeading.setTextSize(20);

                LinearLayout linearLay = new LinearLayout(this);
                for (Iterator in = productReview.iterator(); in.hasNext(); ) {
                    reviewDetails d = (reviewDetails) in.next();

                    linearLay.setOrientation(LinearLayout.VERTICAL);
                    linearLay.setPadding(10,10,0,60);

                    TextView tv_user = new TextView(this);
                    tv_user.setText("\n" + d.getEmail());
                    TextView tv_txt = new TextView(this);
                    tv_txt.setText(d.getReviewText());
                    tv_txt.setTypeface(null, Typeface.BOLD);

                    RatingBar rating = new RatingBar(this, null, android.R.attr.ratingBarStyleIndicator);
                    rating.setRating(Float.parseFloat(d.getRating()));
                    rating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);

                    linearLay.addView(tv_user);
                    linearLay.addView(tv_txt);
                    linearLay.addView(rating);

                }

                ll_review.addView(reviewHeading);
                ll_review.addView(linearLay);
            }

            Button reviewButton = (Button) findViewById(R.id.btn_addReview);
            reviewButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    review(v);
                }
            });
        }
    }



    public void shopping(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            String msg = "Please login to add product to the shopping cart";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            LoginButton(view);

        }else{
            String productID = sharedPreferences.getString("productID", null);
            ShoppingCart.db = new Database(this);
            size=spinner.getSelectedItem().toString();
            String msg = ShoppingCart.addCart(user, productID, size);

            if(msg.equals("The product already exists in your cart")){
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ProductInformationActivity.class);
                startActivity(intent);
            }
        }
    }

    public void buy(View view){

        Intent intent = new Intent(this,BuyProductActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String productID = sharedPreferences.getString("productID",null);
        intent.putExtra(EXTRA_PRODUCTID, productID);
        intent.putExtra(EXTRA_PRICE, prices);
        size=spinner.getSelectedItem().toString();
        intent.putExtra(EXTRA_SIZE, size);
        startActivity(intent);
    }


   /* public void watchlist(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            String msg = "Please login to add product to the wish list";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            LoginButton(view);

        }else{
            String productID = sharedPreferences.getString("productID", null);
            WatchList.db = new Database(this);
            size=spinner.getSelectedItem().toString();
            String msg = WatchList.addToList(user, productID, size);

            if(msg.equals("The product already exists in your list")){
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ProductInformationActivity.class);
                startActivity(intent);
            }
        }
    }*/

    public void watchlist(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            String msg = "Please login to add product to the wish list";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            LoginButton(view);

        }else{
            String productID = sharedPreferences.getString("productID", null);
            WatchList.db = new Database(this);
            size=spinner.getSelectedItem().toString();

            String msg = WatchList.addToList(user, productID, size);

            if(msg.equals("The product already exists in your list")){
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ProductInformationActivity.class);
                startActivity(intent);
            }
        }
    }

    public void inquiry(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            String msg = "Please login to add product inquiry";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            LoginButton(view);

        }else{
            Intent intent = new Intent(this, AddInquiryActivity.class);
            startActivity(intent);
        }
    }

    public void review(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            String msg = "Please login to add product review";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            LoginButton(view);

        }else{
            Intent intent = new Intent(this, AddReviewActivity.class);
            startActivity(intent);
        }
    }

    //https://code.tutsplus.com/tutorials/android-sdk-implement-a-share-intent--mobile-8433
    public void share (View view){
        String productName = view.getTag().toString();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "StyleOmega now has " + productName + ". Visit StyleOmega for more details!";
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
