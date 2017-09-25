package com.example.dev.styleomega;

import android.os.Bundle;
import android.widget.TextView;

public class ContactUsActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        onCreation();

        TextView information = (TextView) findViewById(R.id.tv_info);
        information.setText("Style omega is one of Sri Lanka’s leading online fashion stores retailing ladies gents and kids clothing \n" +
                "accessories. \n" + "\n" +
                "The wide range of clothes available at style omega provides more choices, " +
                "ideas and opportunities to its customers in order to add more value to their personalities." + "\n" +
                "Experience a phenomenal fashion experience!");

        TextView open = (TextView) findViewById(R.id.tv_open);
        open.setText("We are open on\n" +
                "Monday to Friday: 10am - 9pm\n" +
                "Saturday and Sunday: 10am - 10pm");

        TextView location = (TextView) findViewById(R.id.tv_location);
        location.setText( "Our stores are located at,\n" +
                "Colombo\n" +
                "Kandy\n" +
                "Galle\n" + "\n" +
                "For futher support please contact a dedicated member \nof our team on\n" +
                "☎ 011-1243787\n" +
                "☎ 011-9868702\n" +
                "email: styleomega@gmail.com");
    }
}
