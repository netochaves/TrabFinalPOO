package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.PagamentoDAO;
import controle.AdministradoraCartaoDeCredito;
import excecoes.CartaoInexistente;
import excecoes.CartaoInvalido;
import excecoes.DataInvalida;
import excecoes.FaturaInexistente;
import excecoes.PagamentoInvalido;
import excecoes.ValorInvalido;
import model.Pagamento;

@WebServlet("/Pagamento")
public class PagamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public PagamentoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cartao=Integer.parseInt(request.getParameter("cartao"));
		int mes = Integer.parseInt(request.getParameter("mes"));
		int ano = Integer.parseInt(request.getParameter("ano"));
		
		request.setAttribute("pagamentos",PagamentoDAO.getPagamentos(cartao, mes, ano));
		request.getRequestDispatcher("/Pagamento.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String>messages=new ArrayList<>();
		double valor = Double.parseDouble(request.getParameter("valor"));
		String data = request.getParameter("data");
		int cartao = Integer.parseInt(request.getParameter("cartao"));
		String []s= data.split("/");
		int mes=Integer.parseInt(s[0]);
		int ano=Integer.parseInt(s[1]);
		AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
		System.out.println(mes+" "+ano);
		
		try {
			adm.registrarPagamento(cartao, mes, ano, valor);
		} catch (CartaoInexistente e) {
			String msg = "Cartão de usuario não encontrado";
			messages.add(msg);
			e.printStackTrace();
		} catch (PagamentoInvalido e) {
			String msg = "Valor de pagamento inferior a 10% da fatura ou maior que o valor total";
			messages.add(msg);
			e.printStackTrace();
		} catch (CartaoInvalido e) {
			String msg = "Número de cartão invalido";
			messages.add(msg);
			e.printStackTrace();
		} catch (ValorInvalido e) {
			String msg = "Valor de pagamento invalido";
			messages.add(msg);
			e.printStackTrace();
		} catch (DataInvalida e) {
			e.printStackTrace();
		} catch (FaturaInexistente e) {
			e.printStackTrace();
		}
		request.setAttribute("messages", messages);
		request.getRequestDispatcher("/Pagamento.jsp").forward(request, response);
	}

}
