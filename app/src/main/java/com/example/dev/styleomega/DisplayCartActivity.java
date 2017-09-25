package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.ShoppingCart;
import com.example.dev.styleomega.Model.productDetails;

public class DisplayCartActivity extends Navigation {
    String qty;
    String size;
    String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cart);
        onCreation();
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("MY CART");

        LinearLayout linearLayoutMain = (LinearLayout)findViewById(R.id.ll);

        ShoppingCart.db = new Database(this);
        ArrayList<productDetails> display = ShoppingCart.displayCart(user);
        if(display.size()>0){
            for(Iterator i = display.iterator(); i.hasNext();){
                final productDetails p = (productDetails) i.next();

                LinearLayout productImage = new LinearLayout(this);
                productImage.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,670);
                layoutParams1.setMargins(0,0,0,0);
                productImage.setLayoutParams(layoutParams1);

                ImageView image = new ImageView(this);
                int images = this.getResources().getIdentifier(p.getImage(), "drawable", "com.example.dev.styleomega");
                image.setImageResource(images);
                image.setLayoutParams(new LinearLayout.LayoutParams(357, 500));
                image.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayout productDetails = new LinearLayout(this);
                productDetails.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(30,0,0,0);
                productDetails.setLayoutParams(layoutParams);

                TextView productName = new TextView(this);
                productName.setText(p.getName());

                TextView productTotal = new TextView(this);
                productTotal.setText(p.getPrice() + " * " + p.getQty() + " = " + p.getTotalPrice() + " LKR");

                TextView productSize = new TextView(this);
                productSize.setText("Size: " + p.getSize());

                TextView productQty = new TextView(this);
                productQty.setText("Qty: ");

                ShoppingCart.db = new Database(this);
                size = p.getSize();
                productID = p.getProductID();
                ArrayList<String> s = ShoppingCart.getProductQty(productID,size);

                final NumberPicker np = new NumberPicker(this);
                np.setLayoutParams(new LinearLayout.LayoutParams(100, 400));
                np.setMinValue(1);
                np.setMaxValue(Integer.parseInt(s.get(0)));
                np.setWrapSelectorWheel(false);
                np.setValue(Integer.parseInt(p.getQty()));

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
                        qty= p.getQty();
                        deleteCart(v);
                    }
                });

                Button update = new Button(this);
                update.setText("Update");
                update.setLayoutParams(new LinearLayout.LayoutParams(180, 80));
                update.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        productID = p.getProductID();
                        size = p.getSize();
                        qty = String.valueOf(np.getValue());

                        updateCart(v);
                    }
                });

                productDetails.addView(productName);
                productDetails.addView(productTotal);
                productDetails.addView(productSize);
                productDetails.addView(productQty);
                productDetails.addView(np);
                productButtons.addView(update);
                productButtons.addView(delete);
                productDetails.addView(productButtons);
                productImage.addView(image);
                productImage.addView(productDetails);
                linearLayoutMain.addView(productImage);
            }

            int grandTotal=0;
            ShoppingCart.db = new Database(this);
            ArrayList<productDetails> info = ShoppingCart.getTotalInfo(user);
            for (Iterator it = info.iterator (); it.hasNext (); ) {
                productDetails details = (productDetails) it.next();

                grandTotal+= Integer.parseInt(details.getTotalPrice());
            }

            TextView total = new TextView(this);
            total.setTextSize(20);
            total.setTypeface(null, Typeface.BOLD);
            total.setGravity(Gravity.CENTER);
            total.setText("SUBTOTAL : " +  grandTotal + "LKR");

            Button checkout = new Button(this);
            checkout.setText("Check out");
            checkout.setTextSize(16);
            checkout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    checkout(v);
                }
            });
            linearLayoutMain.addView(total);
            linearLayoutMain.addView(checkout);
        }
        else {
            TextView productName = new TextView(this);
            productName.setText("YOUR CART IS EMPTY");
            linearLayoutMain.addView(productName);
        }

    }

    public void deleteCart (View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        ShoppingCart.db = new Database(this);
        ShoppingCart.removeCart(user, productID, size, qty);
        Toast.makeText(getApplicationContext(), "The product has been removed from your cart", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,DisplayCartActivity.class);
        startActivity(intent);
    }

    public void checkout(View view){
        Intent intent = new Intent(this,PlaceOrderActivity.class);
        startActivity(intent);
    }

    public void updateCart(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user",null);
        ShoppingCart.db = new Database(this);
        ShoppingCart.updateQty(productID,email,qty, size);
        Toast.makeText(getApplicationContext(), "The product has been updated", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,DisplayCartActivity.class);
        startActivity(intent);
    }


}
