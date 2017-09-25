package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.ShoppingCart;
import com.example.dev.styleomega.Model.WatchList;
import com.example.dev.styleomega.Model.productDetails;

import java.util.ArrayList;
import java.util.Iterator;

public class DisplayWatchListActivity extends Navigation{
    String productID;
    String size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_watch_list);
        onCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("MY WISH LIST");
        LinearLayout linearLayoutMain = (LinearLayout)findViewById(R.id.ll);

        WatchList.db = new Database(this);
        ArrayList<productDetails> display = WatchList.displayWatchList(user);
        if(display.size()>0){
            for(Iterator i = display.iterator(); i.hasNext();){
                final productDetails p = (productDetails) i.next();

                LinearLayout productImage = new LinearLayout(this);
                productImage.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,670);
                layoutParams1.setMargins(0,0,0,0);
                productImage.setLayoutParams(layoutParams1);

                ImageView image = new ImageView(this);
                int images = this.getResources().getIdentifier(p.getImage(), "drawable", "com.example.dev.styleomega");
                image.setImageResource(images);
                image.setLayoutParams(new LinearLayout.LayoutParams(357, 500));
                image.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayout productDetails = new LinearLayout(this);
                productDetails.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(30,0,0,0);
                productDetails.setLayoutParams(layoutParams);

                TextView productName = new TextView(this);
                productName.setText(p.getName());

                TextView productTotal = new TextView(this);
                productTotal.setText(p.getPrice() + " LKR");

                TextView productSize = new TextView(this);
                productSize.setText("Size: " + p.getSize());


                LinearLayout productButtons = new LinearLayout(this);
                productButtons.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams2.setMargins(0,0,0,0);
                productButtons .setLayoutParams(layoutParams2);

                ImageView delete = new ImageView(this);
                delete.setLayoutParams(new LinearLayout.LayoutParams(80, 77));
                delete.setImageResource(R.drawable.bin);
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) delete.getLayoutParams();
                marginParams.setMargins(10, 0, 10, 0);

                delete.setScaleType(ImageView.ScaleType.FIT_XY);
                delete.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        productID = p.getProductID();
                        size = p.getSize();
                        deleteList(v);
                    }
                });

               Button addToCart = new Button(this);
                addToCart.setText("Cart");
                addToCart.setLayoutParams(new LinearLayout.LayoutParams(180, 80));
                addToCart.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        productID = p.getProductID();
                        size = p.getSize();
                        shopping(v);
                    }
                });

                productDetails.addView(productName);
                productDetails.addView(productTotal);
                productDetails.addView(productSize);
                productButtons.addView(addToCart);
                productButtons.addView(delete);
                productDetails.addView(productButtons);
                productImage.addView(image);
                productImage.addView(productDetails);
                linearLayoutMain.addView(productImage);
            }


        }
        else {
            TextView productName = new TextView(this);
            productName.setText("YOUR WISH LIST IS EMPTY");
            linearLayoutMain.addView(productName);
        }

    }

    public void deleteList (View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        WatchList.db = new Database(this);
        WatchList.removeItem(productID, user, size);
        Toast.makeText(getApplicationContext(), "The product has been removed from your list", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,DisplayWatchListActivity.class);
        startActivity(intent);
    }

    public void shopping(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user",null);
        ShoppingCart.db = new Database(this);
        String msg = ShoppingCart.addCart(email, productID, size);
        if(msg.equals("The product already exists in your list")){
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            deleteList(view);
        }
    }


}
