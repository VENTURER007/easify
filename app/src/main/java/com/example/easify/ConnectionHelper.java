package com.example.easify;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {


//        public static void main(String[] args) {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection conn = DriverManager.getConnection(
//                        "jdbc:mysql://aws.connect.psdb.cloud/easify?sslMode=VERIFY_IDENTITY",
//                        "nxfd229amx652555glxe",
//                        "pscale_pw_u3CON9LIHJ67YmaHTi30KAieK9Xfp67DKrChrJkHSjr");
//                System.out.println("get database name" + conn.getCatalog());
//                // Execute a simple query to check if the connection is successful
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT 1");
//                if (rs.next()) {
//                    System.out.println("Connection successful");
//                } else {
//                    System.out.println("Connection failed");
//                }
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        }


    public Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://aws.connect.psdb.cloud/easify?sslMode=VERIFY_IDENTITY",
                    "nxfd229amx652555glxe",
                    "pscale_pw_u3CON9LIHJ67YmaHTi30KAieK9Xfp67DKrChrJkHSjr");
            System.out.println("get database name" + conn.getCatalog());
            // Execute a simple query to check if the connection is successful
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");
            if (rs.next()) {
                System.out.println("Connection successful");
            } else {
                System.out.println("Connection failed");
            }

            return conn;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
//public class ConnectionHelper {
//private static final String URL = "jdbc:mysql://aws.connect.psdb.cloud/easify?sslMode=VERIFY_IDENTITY";
////private static final String URL = "jdbc:mysql://aws.connect.psdb.cloud:3306/?user=nxfd229amx652555glxe";
//
//    private static final String USER = "nxfd229amx652555glxe";
//    private static final String PASSWORD = "pscale_pw_u3CON9LIHJ67YmaHTi30KAieK9Xfp67DKrChrJkHSjr";
//    private static Connection conn = null;
//
//    public static Connection getConnection() throws SQLException {
//        if (conn == null) {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                conn = DriverManager.getConnection("jdbc:mysql://aws.connect.psdb.cloud:3306/easify?user=nxfd229amx652555glxe&password=pscale_pw_u3CON9LIHJ67YmaHTi30KAieK9Xfp67DKrChrJkHSjr");
//            } catch (ClassNotFoundException e) {
//                throw new SQLException("MySQL JDBC driver not found. "+e.getMessage());
//            }catch(Exception e){
//                e.printStackTrace();
//                Log.e("connection","Connection error");
//            }
//        }
//        System.out.println("Successful");
//        return conn;
//    }
//}

