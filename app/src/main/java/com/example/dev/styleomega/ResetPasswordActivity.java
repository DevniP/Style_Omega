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

public class ResetPasswordActivity extends Navigation {

    private EditText password;
    private EditText newPassword;
    private EditText rnewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        onCreation();

        password = (EditText) findViewById(R.id.et_currentPswd);
        newPassword = (EditText) findViewById(R.id.et_newPswd);
        rnewPassword = (EditText) findViewById(R.id.et_rnewpswd);
    }

    public void btnResetPassword_click(View view) {

        String cpswd = password.getText().toString();
        String npswd = newPassword.getText().toString();
        String npswd1 = rnewPassword.getText().toString();

        if (cpswd.trim().equals("")) {
            password.setError("Enter Current Password");
        } else if (npswd.trim().equals("")) {
            newPassword.setError("Enter New Password");
        } else if (npswd1.trim().equals("")) {
            rnewPassword.setError("Re-enter new password");
        } else {

            if (!newPassword.getText().toString().equals(rnewPassword.getText().toString())) {
                rnewPassword.setError("Passwords do not match");
            } else {
                Customer.db = new Database(this);
                SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("user",null);
                String msg = Customer.resetPassword(user, cpswd , npswd, npswd1);
                if (msg.equals("The entered current password do not match")) {
                    password.setError(msg);
                } else {

                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        }

    }
}
