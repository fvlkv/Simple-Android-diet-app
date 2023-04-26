package com.example.projekt;

import static com.example.projekt.CryptoHelper.encrypt;
import static com.example.projekt.DataBaseConnection.DB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Connection db;
    String logResult;
    String passResult;
    String kalorycznosc=null;
    String encryptedPass=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmplog=username.getText().toString();
                String tmppass=password.getText().toString();
                encryptedPass=encrypt(tmppass);
                DataBaseLoginVerification(tmplog,encryptedPass);

                if(tmplog.isEmpty()||tmppass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Pola nie mogą być puste!", Toast.LENGTH_SHORT).show();
                }
                else if(tmplog.equals(logResult)&&encryptedPass.equals(passResult)){
                    navigateToSceondActivity();
                    SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sh.edit();
                    myEdit.putString("login",logResult);
                    myEdit.putString("kalory",kalorycznosc);
                    myEdit.commit();

                }else{
                    Toast.makeText(MainActivity.this, "Zły login lub hasło!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    void navigateToSceondActivity(){
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    void DataBaseLoginVerification(String log,String pass){
        try{
           db=DB();
            if(db!=null){
                String query ="SELECT login,pass,kalory from users WHERE login='"+log+"' AND pass='"+pass+"'";
                Statement st = db.createStatement();
                ResultSet rs1 = st.executeQuery(query);
                while(rs1.next()){
                    logResult=rs1.getString(1);
                    passResult=rs1.getString(2);
                    kalorycznosc=rs1.getString(3);
                }


            }
        }catch(Exception e){

        }

    }


}