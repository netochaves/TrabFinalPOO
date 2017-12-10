package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CompraDAO;
import controle.AdministradoraCartaoDeCredito;
import excecoes.CartaoInexistente;
import excecoes.CartaoInvalido;
import excecoes.ClienteNaoEncontrado;
import excecoes.DataInvalida;
import excecoes.EstabelecimentoInvalido;
import excecoes.EstabelecimentoNaoCadastrado;
import excecoes.FaturaJaExistente;
import excecoes.LimiteExcedido;
import excecoes.ParcelamentoInvalido;
import excecoes.ValorInvalido;

@WebServlet("/Compra")
public class CompraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CompraServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/Compra.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<String> messages = new ArrayList<>();
		int cartao = Integer.parseInt(req.getParameter("cartao"));
		int parcelas = Integer.parseInt(req.getParameter("parcelas"));
		int estabelecimento = Integer.parseInt(req.getParameter("estabelecimento"));
		double valor = Double.parseDouble(req.getParameter("valor"));
	
		String data = req.getParameter("data");
		String []s = data.split("/");
		int mes = Integer.parseInt(s[1]);
		int dia = Integer.parseInt(s[0]);
		int ano = Integer.parseInt(s[2]);
		if(mes==12) {mes-=1;}
		ano-=1900;
		Date date=new Date(ano,mes,dia);
		
		AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
		try {
			adm.registrarCompra(date, cartao, estabelecimento, valor, parcelas);
		} catch (CartaoInexistente e) {
			e.printStackTrace();
		} catch (LimiteExcedido e) {
			String msg = "Limite insuficiente para compra";
			messages.add(msg);
		} catch (EstabelecimentoNaoCadastrado e) {
			String msg = "Este estabelecimento não existe";
			messages.add(msg);
		} catch (ParcelamentoInvalido e) {
			String msg = "Número de parcelas invalido";
			messages.add(msg);
		} catch (DataInvalida e) {
			e.printStackTrace();
		} catch (CartaoInvalido e) {
			String msg = "Número de cartão invalido";
			messages.add(msg);
		} catch (EstabelecimentoInvalido e) {
			String msg = "Número de estabelecimento invalido";
			messages.add(msg);
		} catch (ValorInvalido e) {
			String msg = "Valor invalido.";
			messages.add(msg);
		} catch (ClienteNaoEncontrado e) {
			String msg = "Este cliente não existe";
			messages.add(msg);
		} catch (FaturaJaExistente e) {
			e.printStackTrace();
		}
		req.setAttribute("compras", CompraDAO.getAll());
		req.getRequestDispatcher("/Compra.jsp").forward(req, resp);
		
	}

}
