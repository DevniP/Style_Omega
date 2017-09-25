package com.example.dev.styleomega;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.Purchases;

public class ReceiptActivity extends Navigation {
    private TextView receiptNo;
    private TextView name;
    private TextView date;
    private TextView subTotal;
    private String receiptID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        onCreation();

        Intent intent = getIntent();
        receiptID = intent.getStringExtra(PlaceOrderActivity.EXTRA_RECEIPTID);


        Purchases.db = new Database(this);
        ArrayList<String> receipt = Purchases.receipt(receiptID);

        receiptNo = (TextView) findViewById(R.id.tv_receiptNo);
        name = (TextView) findViewById(R.id.tv_name);
        date = (TextView) findViewById(R.id.tv_date);
        subTotal = (TextView) findViewById(R.id.tv_subTotal);

        name.setText("Email: " + receipt.get(1));
        receiptNo.setText("Receipt No: " + receipt.get(0));
        date.setText("Date & Time: " + receipt.get(2));
        subTotal.setText("Sub Total: " + receipt.get(3));
    }
}
