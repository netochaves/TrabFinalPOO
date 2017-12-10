package DAO;

import model.Compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CompraDAO {

    public static void inserir(Compra c){
        Connection con = ConnectionFactory.getConnection();//Inicia a conexao
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO compra(mes,ano,dia,estabelecimento,valor,parcelas,cartao)VALUES(?,?,?,?,?,?,?)");
            stmt.setInt(1,c.getData().getMonth());
            stmt.setInt(2,c.getData().getYear());
            stmt.setInt(3,c.getData().getDate());
            stmt.setInt(4,c.getEstabelecimento());
            stmt.setDouble(5,c.getValor());
            stmt.setInt(6,c.getParcelas());
            stmt.setInt(7,c.getCartao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }

    public static ArrayList<Compra> getCompras(Date data){
        Connection con = ConnectionFactory.getConnection();//Inicia a conexao
        PreparedStatement stmt = null;
        Compra c = null;
        ArrayList<Compra> compras = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM compra WHERE mes = ? AND ano = ?");
            stmt.setInt(1,data.getMonth());
            stmt.setInt(2,data.getYear());
            ResultSet rst = stmt.executeQuery();
            if (rst.next()){
                c = new Compra(rst.getInt("cartao"),new Date(rst.getInt("ano"),rst.getInt("mes"),
                        rst.getInt("dia")),rst.getInt("estabelecimento")
                        ,rst.getDouble("valor"),rst.getInt("parcelas"));
                compras.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return compras;
    }
    public static ArrayList<Compra> getAll(){
        Connection con = ConnectionFactory.getConnection();//Inicia a conexao
        PreparedStatement stmt = null;
        Compra c = null;
        ArrayList<Compra> compras = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM compra");
            ResultSet rst = stmt.executeQuery();
            while (rst.next()){
                c = new Compra(rst.getInt("cartao"),new Date(rst.getInt("ano"),rst.getInt("mes"),
                        rst.getInt("dia")),rst.getInt("estabelecimento")
                        ,rst.getDouble("valor"),rst.getInt("parcelas"));
                compras.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return compras;
    }
    public static void limpar(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM compra WHERE 1");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
}
