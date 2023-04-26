package com.example.projekt;

import static com.example.projekt.CryptoHelper.encrypt;
import static com.example.projekt.DataBaseConnection.DB;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;



public class RegisterActivity extends AppCompatActivity {
    Connection db;
    String verification=null;
    String encryptedPass=null;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        EditText pass2 = (EditText) findViewById(R.id.passwordConfirm);
        MaterialButton regbtn = (MaterialButton) findViewById(R.id.signupbutton);


            regbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String log = username.getText().toString();
                    String password = pass.getText().toString();
                    String password2 = pass2.getText().toString();
                    String checkemail=email.getText().toString();
                    encryptedPass=encrypt(password);
                    db=DB();
                    DataBaseLoginVerification(log);


                    if(log.isEmpty()||password.isEmpty()||password2.isEmpty()||checkemail.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Pola nie mogą być puste!", Toast.LENGTH_SHORT).show();
                    }
                   else if(password2.equals(password)&&EMAIL_ADDRESS_PATTERN.matcher(checkemail).matches()) {
                       if(verification==null) {
                           DataBaseRegistry(log, encryptedPass, checkemail);
                           Toast.makeText(RegisterActivity.this, "Rejestracja przebiegła pomyślnie!", Toast.LENGTH_SHORT).show();
                           finish();
                       }else{
                           Toast.makeText(RegisterActivity.this, "Podany login jest zajęty!", Toast.LENGTH_SHORT).show();
                       }


                }else{
                        Toast.makeText(RegisterActivity.this, "Nieprawidłowe dane!", Toast.LENGTH_SHORT).show();
                }
                }
            });

    }
    void DataBaseRegistry(String log,String pass, String email){
        try{

            if(db!=null){

                    String query2 ="INSERT INTO diety.users (email, pass, login) VALUES ('"+email+"','"+pass+"','"+log+"')";
                    PreparedStatement stm = db.prepareStatement(query2);
                    stm.execute();
            }
        }catch(Exception e){
            Toast.makeText(this, "Database error", Toast.LENGTH_SHORT).show();
        }
    }
    void DataBaseLoginVerification(String log) {
        try {
            if (db != null) {
                String query1 = "SELECT login from users WHERE login='" + log + "'";
                Statement st = db.createStatement();
                ResultSet rs = st.executeQuery(query1);
                while (rs.next()) {
                    verification = rs.getString(1);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Database error", Toast.LENGTH_SHORT).show();
        }

    }


}