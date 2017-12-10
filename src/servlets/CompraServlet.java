package servlets;

import java.io.IOException;
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
		
		int cartao = Integer.parseInt(req.getParameter("cartao"));
		int parcelas = Integer.parseInt(req.getParameter("parcelas"));
		int estabelecimento = Integer.parseInt(req.getParameter("estabelecimento"));
		double valor = Double.parseDouble(req.getParameter("valor"));
		
		AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
		try {
			adm.registrarCompra(new Date(), cartao, estabelecimento, valor, parcelas);
		} catch (CartaoInexistente e) {

		} catch (LimiteExcedido e) {

		} catch (EstabelecimentoNaoCadastrado e) {

		} catch (ParcelamentoInvalido e) {

		} catch (DataInvalida e) {

		} catch (CartaoInvalido e) {

		} catch (EstabelecimentoInvalido e) {

		} catch (ValorInvalido e) {

		} catch (ClienteNaoEncontrado e) {

		} catch (FaturaJaExistente e) {

		}
		req.setAttribute("compras", CompraDAO.getAll());
		req.getRequestDispatcher("/Compra.jsp").forward(req, resp);
		
	}

}
