package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;

import java.util.ArrayList;

public class EditProfileActivity extends Navigation {

    private EditText firstName;
    private EditText lastName;
    private EditText tel;
    private EditText address;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        onCreation();
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("user",null);

        ArrayList<String> customer = Customer.getAccountInformation(user);

        firstName = (EditText) findViewById(R.id.et_firstName);
        lastName = (EditText) findViewById(R.id.et_lastName);
        tel = (EditText) findViewById(R.id.et_tel);
        address = (EditText)findViewById(R.id.et_address);

        firstName.setText(customer.get(0));
        lastName.setText(customer.get(1));
        tel.setText(customer.get(2));
        address.setText(customer.get(4));
    }

    public void btnUpdateProfile_click(View view) {

        final String fName = firstName.getText().toString();
        final String lName = lastName.getText().toString();
        final String telephone = tel.getText().toString();
        final String add = address.getText().toString();

        if( fName.trim().equals("")){
            firstName.setError("Enter First Name");
        }else if (lName.trim().equals("")) {
            lastName.setError("Enter Last Name");
        }else if (add.trim().equals("")) {
            address.setError("Enter Address");
        }else{
            Customer.db = new Database(this);

            Customer.editCustomerInformation(fName,lName,user, telephone,add);
            Toast.makeText(getApplicationContext(), "Your account has been updated!" , Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }

    }

}
