package DAO;
import model.Cliente;
import model.Dependente;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excecoes.ClienteJaCadastrado;
import excecoes.ClienteNaoEncontrado;

public class ClientesDAO {
    public static void insert(Cliente c) throws ClienteJaCadastrado{
        Connection con = ConnectionFactory.getConnection();//Inicia a conexao
        PreparedStatement stmt = null;
        if(pesquisar(c.getCpf())!=null) throw new ClienteJaCadastrado();
        try {
            if(c instanceof Dependente){//Se for titular insere na tabela de clientes.
                stmt = con.prepareStatement("INSERT INTO dependente(cpf,nome,cartao,saldo,debito,cpfTitular,limite,endereco,cidade,estado)VALUES(?,?,?,?,?,?,?,?,?,?)");
                stmt.setInt(1,c.getCpf());
                stmt.setString(2,c.getNome());
                stmt.setInt(3,c.getNumCartao());
                stmt.setDouble(4,c.getSaldo1());
                stmt.setDouble(5,c.getDebito1());
                stmt.setInt(6,((Dependente) c).getTitularCpf());
                stmt.setDouble(7,c.getLimite1());
                stmt.setString(8, c.getEndereco());
                stmt.setString(9, c.getCidade());
                stmt.setString(10, c.getEstado());
            }else{
                stmt = con.prepareStatement("INSERT INTO titular(cpf,nome,cartao,saldo,debito,limite,endereco,cidade,estado)VALUES(?,?,?,?,?,?,?,?,?)");
                stmt.setInt(1,c.getCpf());
                stmt.setString(2,c.getNome());
                stmt.setInt(3,c.getNumCartao());
                stmt.setDouble(4,c.getSaldo1());
                stmt.setDouble(5,c.getDebito1());
                stmt.setDouble(6,c.getLimite1());
                stmt.setString(7, c.getEndereco());
                stmt.setString(8, c.getCidade());
                stmt.setString(9, c.getEstado());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
    
    public static Date getUltimaFatura(Cliente c) {
    	Connection con = ConnectionFactory.getConnection();
    	PreparedStatement stmt = null;
    	Date d = null;
    	try {
    		if(c instanceof Dependente) {
    			stmt = con.prepareStatement("SELECT * FROM dependente WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
    				 d = new Date(rst.getInt("anoLast"),rst.getInt("mesLast"),1);
    			}
    		}else {
    			stmt = con.prepareStatement("SELECT * FROM titular WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
    				 d = new Date(rst.getInt("anoLast"),rst.getInt("mesLast"),1);
    			}
    		}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    	return d;
    }
    
    public static void setUltimaFatura(Cliente c, Date date) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            if(c instanceof Dependente) {
                stmt = con.prepareStatement("UPDATE dependente SET mesLast = ?, anoLast = ? WHERE cpf = ?");
                stmt.setInt(1, date.getMonth());
                stmt.setInt(2, date.getYear());
                stmt.setInt(3, c.getCpf());
            }else{
                stmt = con.prepareStatement("UPDATE titular SET mesLast = ?, anoLast = ? WHERE cpf = ?");
                stmt.setInt(1, date.getMonth());
                stmt.setInt(2, date.getYear());
                stmt.setInt(3, c.getCpf());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    }
    
    public static void alterarSaldo(Cliente c, double valor){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            if(c instanceof Dependente) {
                stmt = con.prepareStatement("UPDATE dependente SET saldo = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }else{
                stmt = con.prepareStatement("UPDATE titular SET saldo = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    }
    public static void alterarDebito(Cliente c, double valor){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            if(c instanceof Dependente) {
                stmt = con.prepareStatement("UPDATE dependente SET debito = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }else{
                stmt = con.prepareStatement("UPDATE titular SET debito = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    }
    public static void alterarLimite(Cliente c, double valor){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            if(c instanceof Cliente) {
                stmt = con.prepareStatement("UPDATE dependente SET limite = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }else{
                stmt = con.prepareStatement("UPDATE titular SET limite = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    }
    public static Cliente pesquisar(int cpf){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        Cliente c = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM titular WHERE cpf = ?");
            stmt.setInt(1,cpf);
            ResultSet rst = stmt.executeQuery();
            if(rst.next()){
                c = new Cliente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("saldo"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
            }
            if(c==null) {
            	 stmt = con.prepareStatement("SELECT * FROM dependente WHERE cpf = ?");
                 stmt.setInt(1,cpf);
                 rst = stmt.executeQuery();
                 if(rst.next()){
                	 c = new Dependente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("limite"),rst.getInt("cpfTitular"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return c;
    }
    public static Double getSaldo(Cliente c) {
    	Connection con = ConnectionFactory.getConnection();
    	PreparedStatement stmt = null;
    	double valor = 0;
    	try {
    		if(c instanceof Dependente) {
    			stmt = con.prepareStatement("SELECT * FROM dependente WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
    				 valor = rst.getDouble("saldo");
    			}
    		}else {
    			stmt = con.prepareStatement("SELECT * FROM titular WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
   				 	valor = rst.getDouble("saldo");
    			}
    		}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    	return valor;
    }
    public static Double getDebito(Cliente c) {
    	Connection con = ConnectionFactory.getConnection();
    	PreparedStatement stmt = null;
    	double valor = 0;
    	try {
    		if(c instanceof Dependente) {
    			stmt = con.prepareStatement("SELECT * FROM dependente WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
    				 valor = rst.getDouble("debito");
    			}
    		}else {
    			stmt = con.prepareStatement("SELECT * FROM titular WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
   				 	valor = rst.getDouble("debito");
    			}
    		}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    	return valor;
    }
    
    public static void setTotal(Cliente c,double valor) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            if(c instanceof Cliente) {
                stmt = con.prepareStatement("UPDATE dependente SET total = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }else{
                stmt = con.prepareStatement("UPDATE titular SET total = ? WHERE cpf = ?");
                stmt.setDouble(1, valor);
                stmt.setInt(2, c.getCpf());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    }
    
    public static Double getTotal(Cliente c) {
    	Connection con = ConnectionFactory.getConnection();
    	PreparedStatement stmt = null;
    	double valor = 0;
    	try {
    		if(c instanceof Dependente) {
    			stmt = con.prepareStatement("SELECT * FROM dependente WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
    				 valor = rst.getDouble("total");
    			}
    		}else {
    			stmt = con.prepareStatement("SELECT * FROM titular WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
   				 	valor = rst.getDouble("total");
    			}
    		}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    	return valor;
    }
    
    public static Double getLimite(Cliente c) {
    	Connection con = ConnectionFactory.getConnection();
    	PreparedStatement stmt = null;
    	double valor = 0;
    	try {
    		if(c instanceof Dependente) {
    			stmt = con.prepareStatement("SELECT * FROM dependente WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
    				 valor = rst.getDouble("limite");
    			}
    		}else {
    			stmt = con.prepareStatement("SELECT * FROM titular WHERE cpf = ?");
    			stmt.setInt(1, c.getCpf());
    			ResultSet rst = stmt.executeQuery();
    			if(rst.next()) {
   				 	valor = rst.getDouble("limite");
    			}
    		}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            ConnectionFactory.closeConnection(con,stmt);
        }
    	return valor;
    }
    public static Cliente pesquisarCartao(int cpf) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        Cliente c = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM titular WHERE cartao = ?");
            stmt.setInt(1,cpf);
            ResultSet rst = stmt.executeQuery();
            if(rst.next()){
                c = new Cliente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("saldo"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
            }
            if(c==null) {
            	 stmt = con.prepareStatement("SELECT * FROM dependente WHERE cartao = ?");
                 stmt.setInt(1,cpf);
                 rst = stmt.executeQuery();
                 if(rst.next()){
                     Dependente d = new Dependente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("limite"),rst.getInt("cpfTitular"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return c;
    }
    public static ArrayList<Cliente> getAllTitulares(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM titular");
            rst = stmt.executeQuery();
            while(rst.next()){
                Cliente c = new Cliente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("saldo"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt,rst);//Fecha a conexao e o statement
        }
        return clientes;
    }
    public static ArrayList<Cliente> getAllClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM titular");
            rst = stmt.executeQuery();
            while(rst.next()){
                Cliente c = new Cliente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("saldo"),rst.getInt("cartao"));
                clientes.add(c);
            }
            stmt = con.prepareStatement("SELECT * FROM dependente");
            rst= stmt.executeQuery();
            while(rst.next()){
                Dependente d = new Dependente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("limite"),rst.getInt("cpfTitular"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
                clientes.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt,rst);//Fecha a conexao e o statement
        }
        return clientes;
    }
    public static ArrayList<Dependente> getAllDependentes(){
    	 ArrayList<Dependente> dependentes = new ArrayList<>();
         Connection con = ConnectionFactory.getConnection();
         PreparedStatement stmt = null;
         try {
             stmt = con.prepareStatement("SELECT * FROM dependente");
             ResultSet rst = stmt.executeQuery();
             while (rst.next()){
                 Dependente d = new Dependente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("limite"),rst.getInt("cpfTitular"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
                 dependentes.add(d);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
         }
         return dependentes;
    }
    public static ArrayList<Dependente> getDependentes(int cpf){
        ArrayList<Dependente> dependentes = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM dependente WHERE cpfTitular = ?");
            stmt.setInt(1,cpf);
            ResultSet rst = stmt.executeQuery();
            while (rst.next()){
                Dependente d = new Dependente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("limite"),rst.getInt("cpfTitular"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
                dependentes.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
        return dependentes;
    }
    
    public static void removerTitular(int cpf) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
			stmt = con.prepareStatement("DELETE FROM titular WHERE cpf = ?");
			stmt.setInt(1, cpf);
			stmt.executeUpdate();
			stmt = con.prepareStatement("DELETE FROM dependente WHERE cpfTitular = ?");
			stmt.setInt(1, cpf);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
    }
    
    public static void removerDependente(int cpf) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
			stmt = con.prepareStatement("DELETE FROM dependente WHERE cpf = ?");
			stmt.setInt(1, cpf);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
        
    }
    
    public static void alterarTitular(Cliente c) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
			stmt = con.prepareStatement("UPDATE titular SET nome = ?,endereco = ?,cidade = ?,estado = ? WHERE cpf = ?");
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getEndereco());
			stmt.setString(3, c.getCidade());
			stmt.setString(4, c.getEstado());
			stmt.setInt(5, c.getCpf());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
    }
    public static void alterarDependente(Dependente c) {
    	Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
			stmt = con.prepareStatement("UPDATE dependente SET nome = ?,endereco = ?,cidade = ?,estado = ? WHERE cpf = ?");
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getEndereco());
			stmt.setString(3, c.getCidade());
			stmt.setString(4, c.getEstado());
			stmt.setInt(5, c.getCpf());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
    }
    
    public static ArrayList<Cliente> pesquisarTitularNome(String nome) {
    	 Connection con = ConnectionFactory.getConnection();
         PreparedStatement stmt = null;
         ResultSet rst =null;
         ArrayList<Cliente> titulares = new ArrayList<>();
         Cliente t;
         try {
			stmt=con.prepareStatement("SELECT * FROM titular WHERE nome LIKE ?");
			stmt.setString(1, '%' + nome + '%');
			rst = stmt.executeQuery();
			while(rst.next()) {
                t = new Cliente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("saldo"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
                titulares.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return titulares;
    }
    public static ArrayList<Dependente> pesquisarDependenteNome(String nome) {
   	 Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rst =null;
        ArrayList<Dependente> dependentes = new ArrayList<>();
        try {
			stmt=con.prepareStatement("SELECT * FROM dependente WHERE nome LIKE ?");
			stmt.setString(1, '%' + nome + '%');
			rst = stmt.executeQuery();
			while(rst.next()) {
               Dependente d = new Dependente(rst.getInt("cpf"),rst.getString("nome"),rst.getDouble("limite"),rst.getInt("cpfTitular"),rst.getInt("cartao"),rst.getString("endereco"),rst.getString("cidade"),rst.getString("estado"),rst.getDouble("debito"),rst.getDouble("saldo"));
               dependentes.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
       return dependentes;
   }
    
    
    public static void limparTitulares(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM titular WHERE 1");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
    public static void limparDependentes(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM dependente WHERE 1");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con,stmt);//Fecha a conexao e o statement
        }
    }
}
