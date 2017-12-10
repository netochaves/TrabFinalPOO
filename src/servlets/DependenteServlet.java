package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClientesDAO;
import controle.AdministradoraCartaoDeCredito;
import excecoes.ClienteJaCadastrado;
import excecoes.ClienteNaoEncontrado;
import excecoes.CpfInvalido;
import excecoes.LimiteExcedido;
import excecoes.LimiteInvalido;
import model.Cliente;
import model.Dependente;
@WebServlet("/Dependente")
public class DependenteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		ArrayList<Dependente> dependentes=new ArrayList<>();
		if(action==null) {
		dependentes=ClientesDAO.getAllDependentes();
		}else if(action.equalsIgnoreCase("remover")) {
			int cpf = Integer.parseInt(req.getParameter("cpf"));
			ClientesDAO.removerDependente(cpf);
			dependentes = ClientesDAO.getAllDependentes();
		}else if(action.equalsIgnoreCase("listarDependentes")) {
			int cpfTitular = Integer.parseInt(req.getParameter("cpfTitular"));
			dependentes = ClientesDAO.getDependentes(cpfTitular);
		}
		
		req.setAttribute("dependentes", dependentes);
		req.getRequestDispatcher("/Dependente.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		ArrayList<Dependente> dependentes=new ArrayList<>();
		ArrayList<String> messages = new ArrayList<>();
		if(action.equalsIgnoreCase("pesquisar")) {
			String nome = req.getParameter("pesquisa");
			dependentes=ClientesDAO.pesquisarDependenteNome(nome);
		}else if(action.equalsIgnoreCase("inserir")) {
			int cpf = Integer.parseInt(req.getParameter("cpf"));
			String nome = req.getParameter("nome");
			double limite = Double.parseDouble(req.getParameter("limite"));
			int cpfTitular = Integer.parseInt(req.getParameter("cpfTitular"));
			int cartao = cpf;
			String endereco = req.getParameter("endereco");
			String cidade = req.getParameter("cidade");
			String estado = req.getParameter("estado");
			
			try {
				AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
				adm.cadastrarDependente(cpf, nome, cpfTitular, limite, endereco, cidade, estado);
			}catch (ClienteJaCadastrado e) {
				String msg = "Este CPF já está cadastrado.";
				messages.add(msg);
			} catch (ClienteNaoEncontrado e) {
				String msg = "Este titular não existe";
				messages.add(msg);
			} catch (LimiteInvalido e) {
				String msg = "Valor do limite invalido.";
				messages.add(msg);
			} catch (CpfInvalido e) {
				String msg = "Número de cpf invalido";
				messages.add(msg);
			} catch (LimiteExcedido e) {
				String msg = "Limite do titular excedido";
				messages.add(msg);
			}
			dependentes=ClientesDAO.getAllDependentes();
		}else if(action.equalsIgnoreCase("alterar")) {
			int cpf = Integer.parseInt(req.getParameter("cpf"));
			int cpfTitular = Integer.parseInt(req.getParameter("cpfTitular"));
			String nome = req.getParameter("nome");
			String endereco = req.getParameter("endereco");
			String cidade = req.getParameter("cidade");
			String estado = req.getParameter("estado");
			
			Dependente c = new Dependente();
			c.setCpf(cpf);
			c.setNome(nome);
			c.setEndereco(endereco);
			c.setCidade(cidade);
			c.setEstado(estado);
			
			ClientesDAO.alterarDependente(c);
			dependentes=ClientesDAO.getAllDependentes();
		}
		req.setAttribute("messages", messages);
        req.setAttribute("dependentes", dependentes);
		req.getRequestDispatcher("/Dependente.jsp").forward(req, resp);
	}
}
