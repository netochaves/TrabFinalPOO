package DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/trabalhoFinal?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection()    {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL,USER,PASS);
        }catch (ClassNotFoundException | SQLException e){
           throw new RuntimeException("Erro na Conexao: "+e.getMessage());
        }
    }

    public static void closeConnection(java.sql.Connection con){
        try {
            if(con != null) con.close();
        }catch (SQLException e){
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE,"Closing ConnectionFactory",e);
        }
    }
    public static void closeConnection(java.sql.Connection con, PreparedStatement stmt){
        closeConnection(con);
        try {
            if(stmt != null)stmt.close();
        }catch (SQLException e){
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE,"Closing statement",e);
        }
    }
    public static            void closeConnection(java.sql.Connection con, PreparedStatement stmt, ResultSet rst){
        closeConnection(con,stmt);
        try {
            if(rst != null)rst.close();
        }catch (SQLException e){
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE,"Closing resultSet",e);
        }
    }
}
