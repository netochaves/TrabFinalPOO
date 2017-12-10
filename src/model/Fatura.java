package model;

import DAO.FaturaDAO;
import controle.AdministradoraCartaoDeCredito;
import excecoes.CartaoInexistente;
import excecoes.CartaoInvalido;
import excecoes.DataInvalida;

public class Fatura {
    private int mes;
    private int ano;
    private int cartao;
    private double valorTotal;

    public Fatura(int mes, int ano, int cartao) {
        this.mes = mes;
        this.ano = ano;
        this.cartao = cartao;
        this.valorTotal = 0;
    }
    public Fatura(int mes, int ano){
        this.mes =  mes;
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public int getCartao() {
        return cartao;
    }

    public void setValor(double valorTotal) {
        this.valorTotal = valorTotal;
        FaturaDAO.setValor(valorTotal, this);
    }

    public double getValor() {
        return valorTotal;
    }
    
    public double valorDeCompras(int cartao,int mes, int ano) throws CartaoInexistente, DataInvalida, CartaoInvalido {
    	double s=0;
    	AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
    	adm.valorDeCompras(mes, ano, cartao);
    	return s;
    }
}
