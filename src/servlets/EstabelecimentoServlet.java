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
import DAO.EstabelecimentoDAO;
import controle.AdministradoraCartaoDeCredito;
import excecoes.ClienteJaCadastrado;
import excecoes.ClienteNaoEncontrado;
import excecoes.CnpjInvalido;
import excecoes.CpfInvalido;
import excecoes.EstabelecimentoJaCadastrado;
import excecoes.LimiteExcedido;
import excecoes.LimiteInvalido;
import excecoes.ParcelamentoInvalido;
import model.Cliente;
import model.Dependente;
import model.Estabelecimento;

@WebServlet("/Estabelecimento")
public class EstabelecimentoServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String forward ="";
		String action = req.getParameter("action");
		if(action==null) {
			forward = "/Estabelecimento.jsp";
			req.setAttribute("estabelecimentos", EstabelecimentoDAO.getAll());
		}
		else if(action.equalsIgnoreCase("remover")) {
			int cnpj = Integer.parseInt(req.getParameter("cnpj"));
			EstabelecimentoDAO.remover(cnpj);			
			forward = "/Estabelecimento.jsp";
			req.setAttribute("estabelecimentos", EstabelecimentoDAO.getAll());
		}
        req.getRequestDispatcher(forward).forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		String action = req.getParameter("action");
		ArrayList<Estabelecimento> estabelecimentos=new ArrayList<>();
		ArrayList<String> messages = new ArrayList<>();
		if(action.equalsIgnoreCase("pesquisar")) {
			String nome = req.getParameter("pesquisa");
			estabelecimentos=EstabelecimentoDAO.pesquisarEstabelecimentoNome(nome);
		}else if(action.equalsIgnoreCase("inserir")) {
			int cnpj = Integer.parseInt(req.getParameter("cnpj"));
			String nome = req.getParameter("nome");
			String endereco = req.getParameter("endereco");
			String cidade = req.getParameter("cidade");
			String estado = req.getParameter("estado");
			String email = req.getParameter("email");
			String telefone = req.getParameter("telefone");
			int parcelas = Integer.parseInt(req.getParameter("parcelas"));
			System.out.println(estado);
			try {
				AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
				adm.cadastrarEstabelecimento(cnpj, nome, parcelas,endereco,cidade,estado,email,telefone);
			} catch (EstabelecimentoJaCadastrado e) {
				String msg = "Este CNPJ já está cadastrado";
				messages.add(msg);
			} catch (CnpjInvalido e) {
				String msg = "Número de CNPJ invalido";
				messages.add(msg);
			} catch (ParcelamentoInvalido e) {
				String msg = "Número de parcelas invalido";
				messages.add(msg);
			}
			estabelecimentos = EstabelecimentoDAO.getAll();
		}else if(action.equalsIgnoreCase("alterar")) {
			int cnpj = Integer.parseInt(req.getParameter("cnpj"));
			String nome = req.getParameter("nome");
			String endereco = req.getParameter("endereco");
			String cidade = req.getParameter("cidade");
			String estado = req.getParameter("estado");
			String email = req.getParameter("email");
			String telefone = req.getParameter("telefone");
			int parcelas = Integer.parseInt(req.getParameter("parcelas"));
			
			Estabelecimento c = new Estabelecimento(cnpj, nome, parcelas, endereco, cidade, estado, email, telefone);
			
			EstabelecimentoDAO.alterar(c);
			estabelecimentos = EstabelecimentoDAO.getAll();
		}
		req.setAttribute("messages", messages);
        req.setAttribute("estabelecimentos", estabelecimentos);
        req.getRequestDispatcher("/Estabelecimento.jsp").forward(req, resp);
	}
}
