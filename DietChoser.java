package com.example.projekt;

import static com.example.projekt.DataBaseConnection.DB;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class DietChoser extends AppCompatActivity {

    Connection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_choser);
        MaterialButton red1 = findViewById(R.id.redukcja1);
        MaterialButton red2 = findViewById(R.id.redukcja2);
        MaterialButton red3 = findViewById(R.id.redukcja3);
        MaterialButton mas1 = findViewById(R.id.masa1);
        MaterialButton mas2 = findViewById(R.id.masa2);
        MaterialButton mas3 = findViewById(R.id.masa3);
        MaterialButton back = findViewById(R.id.back);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String log = sh.getString("login", "");
        String k = sh.getString("kalory","");

        red1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseGetDiet("1600",log);
                Toast.makeText(DietChoser.this, "Dane zostały zaktualizowane", Toast.LENGTH_SHORT).show();
            }
        });
        red2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseGetDiet("1800",log);
                Toast.makeText(DietChoser.this, "Dane zostały zaktualizowane", Toast.LENGTH_SHORT).show();
            }
        });
        red3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseGetDiet("2000",log);
                Toast.makeText(DietChoser.this, "Dane zostały zaktualizowane", Toast.LENGTH_SHORT).show();
            }
        });
        mas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseGetDiet("2500",log);
                Toast.makeText(DietChoser.this, "Dane zostały zaktualizowane", Toast.LENGTH_SHORT).show();
            }
        });
        mas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseGetDiet("3000",log);
                Toast.makeText(DietChoser.this, "Dane zostały zaktualizowane", Toast.LENGTH_SHORT).show();
            }
        });
        mas3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseGetDiet("3500",log);
                Toast.makeText(DietChoser.this, "Dane zostały zaktualizowane", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DietChoser.this, LoginActivity.class);
               startActivity(intent);
               finish();
            }
        });
    }
    void DataBaseGetDiet(String kal, String log){
        try{
            db=DB();
            if(db!=null){

                    String query ="UPDATE diety.users SET kalory='"+kal+"' WHERE login='"+log+"'";
                    PreparedStatement stm = db.prepareStatement(query);
                    stm.executeUpdate();

            }
        }catch(Exception e){
            Toast.makeText(this, "Problem z połączeniem!", Toast.LENGTH_SHORT).show();
        }
    }


}