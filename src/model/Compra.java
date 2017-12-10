package model;

import java.util.Date;

public class Compra {
    private Date data;
    private int estabelecimento;
    private double valor;
    private int parcelas;
    private int cartao;

    public Compra(int cartao,Date data, int estabelecimento, double valor, int parcelas) {
        this.data = data;
        this.estabelecimento = estabelecimento;
        this.valor = valor;
        this.parcelas = parcelas;
        this.cartao = cartao;
    }

    public Date getData() {
        return data;
    }

    public int getEstabelecimento() {
        return estabelecimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getParcelas() {
        return parcelas;
    }

    public int getCartao() { return cartao; }
}
