package com.example.dev.styleomega;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev.styleomega.Model.Database;
import com.example.dev.styleomega.Model.Product;
import com.example.dev.styleomega.Model.ShoppingCart;
import com.example.dev.styleomega.Model.productDetails;

import java.util.ArrayList;

/**
 * Created by Dev on 8/19/2017.
 */

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    EditText productName;
    public static final String EXTRA_TYPE  = "com.example.Application.TYPE";
    public static final String EXTRA_GENDER  = "com.example.Application.GENDER";
    public static final String EXTRA_STYLE  = "com.example.Application.STYLE";
    public static final String EXTRA_NAME = "com.example.Application.NAME";

    public void onCreation(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_women) {
            Intent intent = new Intent(getApplicationContext(), WomenCategoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_men) {
            Intent intent = new Intent(getApplicationContext(), MenCategoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_kids) {
            Intent intent = new Intent(getApplicationContext(), KidsCategoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(getApplicationContext(), ContactUsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LoginButton(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }


    public void direct (View view){

        String tag = view.getTag().toString();
        String type = tag.split(",")[0];
        String gender = tag.split(",")[1];
        String style = tag.split(",")[2];

        Intent intent = new Intent(this, productListActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_GENDER, gender);
        intent.putExtra(EXTRA_STYLE, style);
        startActivity(intent);
    }

    public void displayCarts(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            String msg = "Please login to open your shopping cart";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            LoginButton(view);

        }else{
            Intent intent = new Intent(this,DisplayCartActivity.class);
            startActivity(intent);
        }
    }

    public void displayList(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user",null);
        if(user==""){
            String msg = "Please login to open your wish list";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            LoginButton(view);

        }else{
            Intent intent = new Intent(this,DisplayWatchListActivity.class);
            startActivity(intent);
        }
    }

    public void searchProduct(View view) {
        productName = (EditText) findViewById(R.id.editText2);
        final String pName = productName.getText().toString();

        if (pName.trim().equals("")) {
            productName.setError(Html.fromHtml("<font color='#F0FFF0'>Enter Product Name</font>"));
        } else {
            Product.db = new Database(this);
            ArrayList<productDetails> s =  Product.search(pName);
            if(s.isEmpty() || s.size()<0){
                Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.putExtra(EXTRA_NAME, pName);
                startActivity(intent);
            }
        }

    }


}
