package com.example.dev.styleomega;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;

import java.util.ArrayList;

public class SecurityActivity extends Navigation {
    private TextView question;
    private EditText answer;
    String email;
    public static final String EXTRA_EMAIL  = "com.example.Application.EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        onCreation();

        Intent intent = getIntent();
        email = intent.getStringExtra(ForgotPasswordActivity.EXTRA_EMAIL);

        Customer.db = new Database(this);
        ArrayList<String> info = Customer.forgotPassword(email);

        question = (TextView) findViewById(R.id.tv_question);
        question.setText(info.get(0));
        question.setTypeface(null, Typeface.BOLD);
    }

    public void validate (View view) {

        answer = (EditText) findViewById(R.id.et_answer);
        String answr = answer.getText().toString();

        if (answr.trim().equals("")) {
            answer.setError("Please Enter Answer");
        } else {

            Customer.db = new Database(this);
            String msg = Customer.security(email,answr);
            if(msg.equals("valid")){
                Intent intent = new Intent(this, ResetForgotPasswordActivity.class);
                intent.putExtra(EXTRA_EMAIL, email);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ForgotPasswordActivity.class);
                startActivity(intent);
            }

        }
    }
}
