package DAO;

import model.Cliente;
import model.Fatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FaturaDAO {

    public static void inserir(Fatura f){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO fatura (cartao,valor,mes,ano)VALUES(?,?,?,?)");
            stmt.setInt(1,f.getCartao());
            stmt.setDouble(2,f.getValor());
            stmt.setInt(3,f.getMes());
            stmt.setInt(4,f.getAno());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
    public static Fatura pesquisar(int mes,int ano,int cartao){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        Fatura f = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM fatura WHERE mes = ? AND ano = ? AND cartao = ?");
            stmt.setInt(1,mes);
            stmt.setInt(2,ano);
            stmt.setInt(3, cartao);
            ResultSet rst = stmt.executeQuery();
            if (rst.next()){
                f = new Fatura(rst.getInt("mes"),rst.getInt("ano"),rst.getInt("cartao"));
                f.setValor(rst.getDouble("valor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return f;
    }
    public static void setValor(double valor, Fatura f) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE fatura SET valor = ? WHERE mes = ? AND ano = ?");
            stmt.setDouble(1, valor);
            stmt.setInt(2, f.getMes());
            stmt.setInt(3, f.getAno());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    }
    public static ArrayList<Fatura> getAll(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ArrayList<Fatura> faturas = new ArrayList<>();
        Fatura f = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM fatura");
            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                f = new Fatura(rst.getInt("mes"),rst.getInt("ano"),rst.getInt("cartao"));
                faturas.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return faturas;
    }
    public static void limpar(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM fatura WHERE 1");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }


}
