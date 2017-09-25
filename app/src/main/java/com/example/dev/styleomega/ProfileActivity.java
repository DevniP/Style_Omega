package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;

import java.util.ArrayList;

public class ProfileActivity extends Navigation {

    private TextView firstName;
    private TextView lastName;
    private TextView tel;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        onCreation();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);

        Customer.db = new Database(this);
        ArrayList<String> customer = Customer.getAccountInformation(user);

        firstName = (TextView) findViewById(R.id.tv_firstName);
        lastName = (TextView) findViewById(R.id.tv_lastName);
        tel = (TextView) findViewById(R.id.tv_tel);
        address = (TextView)findViewById(R.id.tv_address);

        firstName.setText(customer.get(0));
        lastName.setText(customer.get(1));
        tel.setText(customer.get(2));
        address.setText(customer.get(4));
    }

    public void editProfileLink(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void resetPasswordLink(View view){
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    public void viewInquiriesLink(View view){
        Intent intent = new Intent(this, MyInquiryActivity.class);
        startActivity(intent);
    }

    public void btnSignOut_click(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", "");
        editor.commit();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
