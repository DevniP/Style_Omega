package com.example.dev.styleomega;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;

public class ResetForgotPasswordActivity extends Navigation {

    private EditText newPassword;
    private EditText rnewPassword;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_forgot_password);
        onCreation();

        Intent intent = getIntent();
        email = intent.getStringExtra(SecurityActivity.EXTRA_EMAIL);

        newPassword = (EditText) findViewById(R.id.et_newPswd);
        rnewPassword = (EditText) findViewById(R.id.et_rnewpswd);
    }

    public void btnResetForgotPassword(View view) {

        String npswd = newPassword.getText().toString();
        String npswd1 = rnewPassword.getText().toString();

        if (npswd.trim().equals("")) {
            newPassword.setError("Enter New Password");
        } else if (npswd1.trim().equals("")) {
            rnewPassword.setError("Re-enter new password");
        } else {

            if (!newPassword.getText().toString().equals(rnewPassword.getText().toString())) {
                rnewPassword.setError("Passwords do not match");
            } else {
                Customer.db = new Database(this);
                Customer.resetForgotPassword(email, npswd);

                Toast.makeText(getApplicationContext(),"Your password has been re-set", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }

    }
}
