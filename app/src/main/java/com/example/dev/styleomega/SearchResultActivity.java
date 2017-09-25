package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.Product;
import com.example.dev.styleomega.Model.productDetails;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchResultActivity extends Navigation {
    String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        onCreation();

        Intent intent = getIntent();
        productName = intent.getStringExtra(Navigation.EXTRA_NAME);

        Product.db = new Database(this);
        ArrayList<productDetails> s =  Product.search(productName);

        LinearLayout linearLayoutMain = (LinearLayout)findViewById(R.id.ll);

        if (s.size()>0) {

            for (Iterator it = s.iterator(); it.hasNext(); ) {
                productDetails pd = (productDetails) it.next();

                LinearLayout productLayout = new LinearLayout(this);
                productLayout.setOrientation(LinearLayout.HORIZONTAL);
                productLayout.setPadding(0, 30, 0, 0);
                productLayout.setTag(pd.getProductID());
                productLayout.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        itemLink(v);
                    }
                });

                ImageView productImage = new ImageView(this);
                int image = this.getResources().getIdentifier(pd.getImage(), "drawable", "com.example.dev.styleomega");
                productImage.setImageResource(image);
                productImage.setLayoutParams(new LinearLayout.LayoutParams(400, 500));
                productImage.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayout productDetails = new LinearLayout(this);
                productDetails.setOrientation(LinearLayout.VERTICAL);
                productDetails.setPadding(50,0,0,0);

                TextView productName = new TextView(this);
                productName.setText(pd.getName().toUpperCase());
                productName.setTypeface(null, Typeface.BOLD);
                productName.setTextSize(17);

                TextView itemPrice = new TextView(this);
                itemPrice.setText(pd.getPrice()+" LKR");
                itemPrice.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                productDetails.addView(productName);
                productDetails.addView(itemPrice);
                productLayout.addView(productImage);
                productLayout.addView(productDetails);
                linearLayoutMain.addView( productLayout);
            }
        }
    }

    public void itemLink(View view){
        String productID = view.getTag().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productID", productID);
        editor.commit();

        Intent intent = new Intent(this, ProductInformationActivity.class);
        startActivity(intent);
    }
}
