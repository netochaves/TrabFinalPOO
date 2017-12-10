package model;

public class Pagamento {
    private int cartao;
    private double valor;
    private int mes;
    private int ano;
    private boolean isPago;

    public Pagamento(int cartao, int ano, int mes,double valor) {
        this.cartao = cartao;
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.setPago(false);
    }

    public int getCartao() {
        return cartao;
    }

    public void setCartao(int cartao) {
        this.cartao = cartao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isPago() {
        return isPago;
    }

    public void setPago(boolean isPago) {
        this.isPago = isPago;
    }
}
