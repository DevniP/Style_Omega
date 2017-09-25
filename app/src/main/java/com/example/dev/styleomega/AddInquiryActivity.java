package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.Inquiry;

public class AddInquiryActivity extends Navigation {
    private EditText inquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inquiry);
        onCreation();
        inquiry = (EditText) findViewById(R.id.tv_inquiryBox);
    }

    public void addInquiry(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        String productID = sharedPreferences.getString("productID",null);

        final String in = inquiry.getText().toString();

        if(in.trim().equals("")){
            inquiry.setError("Please enter your inquiry");
        }else{
            Inquiry.db = new Database(this);
            Inquiry.post(productID,user,in);
                String msg= "Thank you! We will get back to you soon";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ProductInformationActivity.class);
                startActivity(intent);

        }

    }
}
