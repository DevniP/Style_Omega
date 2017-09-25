package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.Purchases;

import java.util.ArrayList;

public class BuyProductActivity extends Navigation {
    private EditText address;
    private TextView totalPrice;
    private EditText securityNo;
    private EditText cardNo;
    private EditText date;
    private EditText name;
    String user;
    String price;
    String productID;
    String size;

    public static final String EXTRA_RECEIPTID = "com.example.Application.RECEIPTID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);
        onCreation();
        Intent intent = getIntent();
        //Return the intent that started this activity.
        price = intent.getStringExtra(ProductInformationActivity.EXTRA_PRICE);
        size = intent.getStringExtra(ProductInformationActivity.EXTRA_SIZE);
        productID = intent.getStringExtra(ProductInformationActivity.EXTRA_PRODUCTID);



        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("user", null);

        totalPrice = (TextView) findViewById(R.id.tv_total);
        totalPrice.setText("TOTAL " + price);
        totalPrice.setTypeface(null, Typeface.BOLD);
        totalPrice.setGravity(Gravity.LEFT);

        Customer.db = new Database(this);
        ArrayList<String> customer = Customer.getAccountInformation(user);
        address = (EditText) findViewById(R.id.et_address);
        address.setText(customer.get(4));

        securityNo = (EditText) findViewById(R.id.et_securityno);
        cardNo = (EditText) findViewById(R.id.et_cardno);
        date = (EditText) findViewById(R.id.et_date);
        name = (EditText) findViewById(R.id.et_name);
    }


    public void order (View view) {

        String secNo = securityNo.getText().toString();
        String card = cardNo.getText().toString();
        String add = address.getText().toString();
        String nameS = name.getText().toString();
        String dateS = date.getText().toString();

        if (add.trim().equals("")) {
            address.setError("Enter Your Address");
        } else if (nameS.trim().equals("")) {
            name.setError("Enter Card Name");
        } else if (card.trim().equals("")) {
            cardNo.setError("Enter Card Number");
        } else if (dateS.trim().equals("")) {
            date.setError("Enter Card Expiry Date");
        } else if (secNo.trim().equals("")) {
            securityNo.setError("Enter Card Security Number");
        } else if(secNo.length()!=3) {
            securityNo.setError("Invalid Security Number");
        }else if(card.length()!=16) {
            cardNo.setError("Invalid Card Number");
        } else{

            Purchases.db = new Database(this);
            String receiptID = Purchases.generateID();
            Purchases.insert1(receiptID, user, productID);
            Purchases.update1(size, productID);

            Intent intent = new Intent(this, ReceiptActivity.class);
            intent.putExtra(EXTRA_RECEIPTID, receiptID);
            startActivity(intent);

        }

    }
}
