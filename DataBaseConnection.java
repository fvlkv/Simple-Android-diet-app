package com.example.projekt;

import android.os.StrictMode;


import java.sql.Connection;
import java.sql.DriverManager;


public class DataBaseConnection {

    private static final String URL="jdbc:mysql://192.168.21.19:3306/diety?useUnicode=yes&characterEncoding=utf-8";
    private static final String user="aplikacja_back";
    private static final String password="J234re5GT4s2agAv";


    public static Connection DB(){
        Connection con=null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(URL,user,password);

        }catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
