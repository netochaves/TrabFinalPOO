package DAO;

import model.Dependente;
import model.Estabelecimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excecoes.EstabelecimentoJaCadastrado;

public class EstabelecimentoDAO {

    public static void inserir(Estabelecimento e) throws EstabelecimentoJaCadastrado{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        if(pesquisar(e.getCnpj())!=null) throw new EstabelecimentoJaCadastrado();
        try {
            stmt = con.prepareStatement("INSERT INTO estabelecimento (cnpj,nome,parcelas,endereco,cidade,estado,email,telefone)VALUES(?,?,?,?,?,?,?,?)");
            stmt.setInt(1,e.getCnpj());
            stmt.setString(2,e.getNome());
            stmt.setInt(3,e.getParcelas());
            stmt.setString(4, e.getEndereco());
            stmt.setString(5, e.getCidade());
            stmt.setString(6, e.getEstado());
            stmt.setString(7, e.getEmail());
            stmt.setString(8, e.getTelefone());
            stmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public static Estabelecimento pesquisar(int cnpj){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        Estabelecimento est = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM estabelecimento WHERE cnpj = ?");
            stmt.setInt(1,cnpj);
            ResultSet rst = stmt.executeQuery();
            if(rst.next()){
                 est = new Estabelecimento(rst.getInt("cnpj"),rst.getString("nome"),rst.getInt("parcelas"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getString("email"),rst.getString("telefone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return est;
    }
    public static ArrayList<Estabelecimento> getAll(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ArrayList<Estabelecimento> estabelecimentos = new ArrayList<>();
        Estabelecimento est = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM estabelecimento");
            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                est = new Estabelecimento(rst.getInt("cnpj"),rst.getString("nome"),rst.getInt("parcelas"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getString("email"),rst.getString("telefone"));
                estabelecimentos.add(est);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return estabelecimentos;
    }
    public static void remover(int cnpj){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM estabelecimento WHERE cnpj = ?");
            stmt.setInt(1,cnpj);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
    public static void alterar(Estabelecimento est) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
			stmt=con.prepareStatement("UPDATE estabelecimento SET nome=?, endereco=?,estado=?,email=?,telefone=?,parcelas=?,cidade=? WHERE cnpj = ?");
			stmt.setString(1,est.getNome());
			stmt.setString(2,est.getEndereco());
			stmt.setString(3,est.getEstado());
			stmt.setString(4,est.getEmail());
			stmt.setString(5,est.getTelefone());
			stmt.setInt(6, est.getParcelas());
			stmt.setString(7, est.getCidade());
			stmt.setInt(8, est.getCnpj());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
    public static ArrayList<Estabelecimento> pesquisarEstabelecimentoNome(String nome) {
      	 Connection con = ConnectionFactory.getConnection();
           PreparedStatement stmt = null;
           ResultSet rst =null;
           ArrayList<Estabelecimento> estabelecimentos = new ArrayList<>();
           try {
   			stmt=con.prepareStatement("SELECT * FROM estabelecimento WHERE nome LIKE ?");
   			stmt.setString(1, '%' + nome + '%');
   			rst = stmt.executeQuery();
   			while(rst.next()) {
   				Estabelecimento est = new Estabelecimento(rst.getInt("cnpj"),rst.getString("nome"),rst.getInt("parcelas"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getString("email"),rst.getString("telefone"));
                  estabelecimentos.add(est);
   			}
   		} catch (SQLException e) {
   			e.printStackTrace();
   		}
          return estabelecimentos;
      }
    public static void limpar() {
    	 Connection con = ConnectionFactory.getConnection();
         PreparedStatement stmt = null;
         try {
             stmt = con.prepareStatement("DELETE FROM estabelecimento WHERE 1");
             stmt.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
         }
    }

}
