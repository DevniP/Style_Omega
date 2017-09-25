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

public class LoginActivity extends Navigation{
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onCreation();
        email = (EditText) findViewById(R.id.et_enterEmail);
        password = (EditText)findViewById(R.id.et_enterPassword);
    }

    public void btnSignIn_click(View view){

        final String mail = email.getText().toString();
        final String pswd = password.getText().toString();

        if( mail.trim().equals("")){
            email.setError("Enter Email Address");
        }else if (pswd.trim().equals("")) {
            password.setError("Enter Password");
        }else{
            Customer.db = new Database(this);
            String msg = Customer.verify(mail,pswd);
            if(msg.equals("Invalid Email address or Password!")){
                password.setText("");
                password.setError(msg);

            }else{
                SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", mail);
                editor.commit();

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ProfileActivity.class);
                startActivity(intent);
            }
        }
    }

    public void createAccountButton(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void forgotPasswordLink(View view){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
