package model;

import model.Cliente;

public class Dependente extends Cliente {

    private int cpfTitular;
    public Dependente(int cpf, String nome, double limite,int cpfTitular, int numCartao,String endereco, String cidade, String estado, double debito, double saldo) {
        super(cpf, nome, limite, numCartao,endereco,cidade,estado,debito,saldo);
        this.cpfTitular = cpfTitular;
    }
    public Dependente(int cpf, String nome, double limite,int cpfTitular, int numCartao) {
    	 super(cpf, nome, limite, numCartao);
         this.cpfTitular = cpfTitular;
    }

    public Dependente() {	}
	public int getTitularCpf(){
        return cpfTitular;
    }
}
