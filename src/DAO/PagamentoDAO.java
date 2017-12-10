package DAO;

import model.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PagamentoDAO {

    public static void inserir(Pagamento p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO pagamento (cartao,mes,ano,valor)VALUES(?,?,?,?)");
            stmt.setInt(1,p.getCartao());
            stmt.setInt(2,p.getMes());
            stmt.setInt(3,p.getAno());
            stmt.setDouble(4,p.getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Pagamento> getPagamentosFromMonth(int mes,int ano){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ArrayList<Pagamento> pagamentos = null;
        Pagamento p;
        try {
            stmt = con.prepareStatement("SELECT * FROM pagamento WHERE mes = ? AND ano = ?");
            stmt.setInt(1,mes);
            stmt.setInt(2,ano);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                p = new Pagamento(rst.getInt("cartao"),rst.getInt("mes"),rst.getInt("ano"),rst.getDouble("valor"));
                pagamentos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagamentos;
    }
    public static ArrayList<Pagamento> getAll(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        Pagamento p;
        try {
            stmt = con.prepareStatement("SELECT * FROM pagamento");
            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                p = new Pagamento(rst.getInt("cartao"),rst.getInt("ano"),rst.getInt("mes"),rst.getDouble("valor"));
                pagamentos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagamentos;
    }
    public static void setPago(Pagamento p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE pagamento SET isPago = ? WHERE mes = ? AND ano = ?");
            stmt.setBoolean(1,true);
            stmt.setInt(2,p.getMes());
            stmt.setInt(3,p.getAno());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void limpar(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM pagamento WHERE 1");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
}
