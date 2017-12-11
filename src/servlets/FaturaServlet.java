package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClientesDAO;
import DAO.FaturaDAO;
import controle.AdministradoraCartaoDeCredito;
import excecoes.CartaoInexistente;
import excecoes.CartaoInvalido;
import excecoes.DataInvalida;
import excecoes.FaturaInexistente;
import excecoes.FaturaJaExistente;
import model.Cliente;
import model.Fatura;

@WebServlet("/Fatura")
public class FaturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FaturaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/Fatura.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("acao");
		ArrayList<String> messages = new ArrayList<>();
		if(action.equalsIgnoreCase("faturar")) {
			String data = request.getParameter("data");
			String []s=data.split("/");
			int mes = Integer.parseInt(s[0]);
			int ano = Integer.parseInt(s[1]);
			
			AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
			ArrayList<Cliente> clientes = ClientesDAO.getAllClientes();
			
			for (Cliente c : clientes) {
				try {
					adm.faturar(c.getNumCartao(), mes, ano);
				} catch (CartaoInexistente e) {
					e.printStackTrace();
				} catch (FaturaInexistente e) {
					e.printStackTrace();
				} catch (DataInvalida e) {
					e.printStackTrace();
				} catch (CartaoInvalido e) {
					e.printStackTrace();
				} catch (FaturaJaExistente e) {
					String msg = "Esta fatura já existe";
					messages.add(msg);
				}
			}
		}else if(action.equalsIgnoreCase("pesquisar")) {
			int cartao = Integer.parseInt(request.getParameter("pesquisa"));
			request.setAttribute("faturas", FaturaDAO.getAllCartao(cartao));
		}
		request.setAttribute("messages", messages);
		request.getRequestDispatcher("/Fatura.jsp").forward(request, response);
	}

}
