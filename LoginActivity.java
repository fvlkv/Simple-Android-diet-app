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



public class LoginActivity extends AppCompatActivity {

    Connection db;
    String kalory;
    TextView diet;
    TextView d1;
    TextView d2;
    TextView d3;
    TextView d4;
    TextView d5;
    TextView d6;
    TextView d7;
    String day1=null;
    String day2=null;
    String day3=null;
    String day4=null;
    String day5=null;
    String day6=null;
    String day7=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=DB();
        setContentView(R.layout.activity_login2);
        diet = findViewById(R.id.diet);
        d1 = findViewById(R.id.d1);
        d2 = findViewById(R.id.d2);
        d3 = findViewById(R.id.d3);
        d4 = findViewById(R.id.d4);
        d5 = findViewById(R.id.d5);
        d6 = findViewById(R.id.d6);
        d7 = findViewById(R.id.d7);
        LoadData();
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
                NavToMyProfile();
                return true;
            case R.id.editdiet:
               NavToDietChoser();
                return true;
            case R.id.logout:
                try{
                    db.close();
                }catch(Exception ex){ex.printStackTrace();}
                NavToMainActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    void NavToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    void NavToDietChoser(){
        Intent intent = new Intent(LoginActivity.this, DietChoser.class);
        startActivityForResult(intent,1);
    }
    void NavToMyProfile(){
        Intent intent = new Intent(LoginActivity.this, MyProfile.class);
        startActivity(intent);
    }



    void DataBaseCheckDiet( String log){
        try{

            if(db!=null){

                String query1="SELECT kalory from users WHERE login='"+log+"'";
                Statement st1 = db.createStatement();
                ResultSet rs1 = st1.executeQuery(query1);
                while(rs1.next()) {
                    kalory = rs1.getString(1);
                 }
            }
        }catch(Exception e){
                Toast.makeText(this, "Problem z połączeniem!", Toast.LENGTH_SHORT).show();
            }
    }
    void DataBaseGetDiet(String kal){
        try{

            if(db!=null){

                String query2 ="SELECT d1,d2,d3,d4,d5,d6,d7 from dieta WHERE kalorycznosc='"+kal+"'";
                Statement st2 = db.createStatement();
                ResultSet rs2 = st2.executeQuery(query2);
                while(rs2.next()){
                    day1=rs2.getString(1);
                    day2=rs2.getString(2);
                    day3=rs2.getString(3);
                    day4=rs2.getString(4);
                    day5=rs2.getString(5);
                    day6=rs2.getString(6);
                    day7=rs2.getString(7);
                }
            }
        }catch(Exception e){
            Toast.makeText(this, "Problem z połączeniem!", Toast.LENGTH_SHORT).show();
        }
    }
    public static String formatString(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                output.append("\n");
            }
            output.append(c);
        }
        return output.toString();
    }
    void LoadData(){
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String log = sh.getString("login", "");
        DataBaseCheckDiet(log);
        if(kalory==null){
            diet.setText("Wybierz swoją dietę w edytorze diety dostępnym w menu");
        }else{
            diet.setText(kalory+"kcal");
            DataBaseGetDiet(kalory);
            d1.setText("\nDzień 1:\n"+formatString(day1));
            d2.setText("\nDzień 2:\n"+formatString(day2));
            d3.setText("\nDzień 3:\n"+formatString(day3));
            d4.setText("\nDzień 4:\n"+formatString(day4));
            d5.setText("\nDzień 5:\n"+formatString(day5));
            d6.setText("\nDzień 6:\n"+formatString(day6));
            d7.setText("\nDzień 7:\n"+formatString(day7));
        }
    }
}