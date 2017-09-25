package com.example.dev.styleomega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Customer;
import com.example.dev.styleomega.Model.Database;

public class RegisterActivity extends Navigation {
    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private EditText telephone;
    private EditText address;
    private EditText password;
    private EditText re_password;
    private EditText question;
    private EditText answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        onCreation();

        email = (EditText) findViewById(R.id.et_email);
        firstName = (EditText) findViewById(R.id.et_firstName);
        lastName = (EditText) findViewById(R.id.et_lastName);
        telephone = (EditText) findViewById(R.id.et_tel);
        address = (EditText) findViewById(R.id.et_address);
        password = (EditText) findViewById(R.id.et_pswd1);
        re_password = (EditText) findViewById(R.id.et_pswd3);
        question = (EditText) findViewById(R.id.et_question);
        answer = (EditText) findViewById(R.id.tv_question);

    }

    public void btnCreateAccount_click(View view) {

        String mail = email.getText().toString();
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String telNo = telephone.getText().toString();
        String add = address.getText().toString();
        String pswd = password.getText().toString();
        String re_pswd = re_password.getText().toString();
        String quest = question.getText().toString();
        String answr = answer.getText().toString();

        if (mail.trim().equals("")) {
            email.setError("Enter Email Address");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Invalid Email Format");
        } else if (fName.trim().equals("")) {
            firstName.setError("Enter First Name");
        } else if (lName.trim().equals("")) {
            lastName.setError("Enter Last Name");
        } else if (add.trim().equals("")) {
            address.setError("Enter Address");
        } else if (pswd.trim().equals("")) {
            password.setError("Enter Password");
        } else if (re_pswd.trim().equals("")) {
            re_password.setError("Re-enter Password");
        } else if (quest.trim().equals("")) {
            question.setError("Enter question");
        } else if (answr.trim().equals("")) {
            answer.setError("Enter answer");
        } else {

            if (!password.getText().toString().equals(re_password.getText().toString())) {
                re_password.setError("Passwords do not match");
            } else {

                Customer.db = new Database(this);
                String registerMsg = Customer.registration(fName, lName, pswd, telNo, mail, add, quest, answr);
                if (registerMsg.equals("Email has already been used to create an account!")) {
                    email.setError(registerMsg);
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", mail);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), registerMsg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        }

    }

}
