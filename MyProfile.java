package com.example.projekt;

import static com.example.projekt.DataBaseConnection.DB;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyProfile extends AppCompatActivity {
    Connection db;
    TextView email;
    TextView name;
    String e="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        name = findViewById(R.id.nazwa);
        email = findViewById(R.id.email);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String log = sh.getString("login", "");
        name.setText(log);
        DataBaseGetEmail(log);
        email.setText(e);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.profile:
                Toast.makeText(this, "Już tu jesteś", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.editdiet:
                NavToDietChoser();
                return true;
            case R.id.logout:
                NavToMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    void NavToMainActivity(){
        Intent intent = new Intent(MyProfile.this, MainActivity.class);
        startActivity(intent);
    }
    void NavToDietChoser(){
        Intent intent = new Intent(MyProfile.this, DietChoser.class);
        startActivity(intent);
    }
    void DataBaseGetEmail(String log) {
        try {
            db = DB();
            if (db != null) {
                String query = "SELECT email from users WHERE login='" + log + "';";
                Statement st = db.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    e=rs.getString(1);
                }


            }
        } catch (Exception e) {

        }
    }

}