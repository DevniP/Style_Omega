package com.example.dev.styleomega;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;

public class ForgotPasswordActivity extends Navigation {
    private EditText email;
    public static final String EXTRA_EMAIL  = "com.example.Application.EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        onCreation();

        TextView txt = (TextView) findViewById(R.id.tv_text);
        txt.setText("We can help you recover your password. \n" +
                "Please enter your email address and we will direct you to your security question.");

        email = (EditText) findViewById(R.id.et_email);
    }

    public void securityQ(View view) {

        final String emails = email.getText().toString();

        if (emails.trim().equals("")) {
            email.setError("Enter Your E-Mail Address");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("Invalid Email Format");
        } else {
            Customer.db = new Database(this);
            String msg = Customer.sec(emails);
            if(msg.equals("Valid")){
                Intent intent = new Intent(this, SecurityActivity.class);
                intent.putExtra(EXTRA_EMAIL, emails);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Entered email address is invalid", Toast.LENGTH_LONG).show();
                email.setText("");
            }
        }

    }
}
