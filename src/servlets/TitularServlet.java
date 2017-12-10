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
import excecoes.CpfInvalido;
import excecoes.SalarioInvalido;
import model.Cliente;
@WebServlet("/Titular")
public class TitularServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String forward ="";
		String action = req.getParameter("action");
		if(action==null) {
			forward = "/Titular.jsp";
			req.setAttribute("titulares", ClientesDAO.getAllTitulares());
		}
		else if(action.equalsIgnoreCase("remover")) {
			int cpf = Integer.parseInt(req.getParameter("cpf"));
			ClientesDAO.removerTitular(cpf);
			forward = "/Titular.jsp";
			req.setAttribute("titulares", ClientesDAO.getAllTitulares());
		}
        req.getRequestDispatcher(forward).forward(req, resp);

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		ArrayList<Cliente> titulares=new ArrayList<>();
		ArrayList<String> messages = new ArrayList<>();
		if(action.equalsIgnoreCase("pesquisar")) {
			String nome = req.getParameter("pesquisa");
			titulares=ClientesDAO.pesquisarTitularNome(nome);
		}else if(action.equalsIgnoreCase("inserir")) {
			int cpf = Integer.parseInt(req.getParameter("cpf"));
			String nome = req.getParameter("nome");
			double limite = Double.parseDouble(req.getParameter("limite"));
			int cartao = cpf;
			String endereco = req.getParameter("endereco");
			String cidade = req.getParameter("cidade");
			String estado = req.getParameter("estado");
			
			try {
				AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
				adm.cadastrarCliente(cpf, nome, limite, endereco, cidade, estado);
			}catch (ClienteJaCadastrado e) {
				String msg = "Este CPF já está cadastrado.";
				messages.add(msg);
			} catch (CpfInvalido e) {
				String msg = "Número de CPF invalido";
				messages.add(msg);
			} catch (SalarioInvalido e) {
				String msg = "Valor do limite invalido";
				messages.add(msg);
			}
			titulares=ClientesDAO.getAllTitulares();
		}else if(action.equalsIgnoreCase("alterar")) {
			int cpf = Integer.parseInt(req.getParameter("cpf"));
			String nome = req.getParameter("nome");
			String endereco = req.getParameter("endereco");
			String cidade = req.getParameter("cidade");
			String estado = req.getParameter("estado");
			
			Cliente c = new Cliente();
			c.setCpf(cpf);
			c.setNome(nome);
			c.setEndereco(endereco);
			c.setCidade(cidade);
			c.setEstado(estado);
			
			ClientesDAO.alterarTitular(c);
			titulares=ClientesDAO.getAllTitulares();
		}
		req.setAttribute("messages", messages);
        req.setAttribute("titulares", titulares);
        req.getRequestDispatcher("/Titular.jsp").forward(req, resp);
	}

}
