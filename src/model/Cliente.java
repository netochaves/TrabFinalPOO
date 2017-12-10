package model;

import java.util.ArrayList;
import java.util.Date;

import DAO.ClientesDAO;

public class Cliente {
    private int cpf;
    private String nome;
    private double limite;
    private int numCartao;
    private double saldo;
    private ArrayList<Dependente> dependentes = new ArrayList<>();
    private ArrayList<Compra> compras = new ArrayList<>();
    private Date ultimaFatura;
    private double Total;
    private double compraDependentes;
    private double debito;
    private String endereco;
	private String cidade;
    private String estado;


    public Cliente(int cpf, String nome, double limite, int numCartao) {
        this.cpf = cpf;
        this.nome = nome;
        this.limite = limite;
        this.numCartao = numCartao;
        this.saldo = 0;
        this.debito = 0;
    }
    
    public Cliente(int cpf, String nome, double limite, int numCartao,String endereco,String cidade,String estado,double debito,double saldo) {
        this.cpf = cpf;
        this.nome = nome;
        this.limite = limite;
        this.numCartao = numCartao;
        this.endereco = endereco;
        this.cidade = cidade;
        this.debito = debito;
        this.estado = estado;
        this.saldo = saldo;
    }

    public Cliente() {}

	public int getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public double getLimite() {
        return ClientesDAO.getLimite(this);
    }

    public void addDependente(Dependente d){
        this.dependentes.add(d);
    }

    public ArrayList<Dependente> getDependentes() {
        return ClientesDAO.getDependentes(this.getCpf());
    }

    public int getNumCartao() { return numCartao; }

    public void setLimite(double limite) { ClientesDAO.alterarLimite(this, limite); }

    public Date getUltimaFatura() { return ClientesDAO.getUltimaFatura(this); }

    public void setUltimaFatura(Date ultimaFatura) { ClientesDAO.setUltimaFatura(this, ultimaFatura);}

    public void setTotal(double total) { ClientesDAO.setTotal(this, total); }

    public double getTotal() { return ClientesDAO.getTotal(this); }

    public void setSaldo(double saldo) { ClientesDAO.alterarSaldo(this, saldo); }

    public double getSaldo() { return ClientesDAO.getSaldo(this); }

    public double getCompraDependentes() {
        return compraDependentes;
    }

    public double CompraDependentes() {
        double s = 0;
        for (Dependente d:dependentes) {
            s+=d.getTotal();
        }
        return s;
    }
    public double limiteDosDependentes() {
    	double s = 0;
    	for(Dependente d : ClientesDAO.getDependentes(this.getCpf())) {
    		s +=d.getLimite();
    	}
    	return s;
    }

    public double getDebito() {
        return ClientesDAO.getDebito(this);
    }

    public void setDebito(double debito) {
        ClientesDAO.alterarDebito(this, debito);
    }
    public double getSaldo1() {
    	return this.saldo;
    }
    public double getLimite1() {
    	return this.limite;
    }
    public double getDebito1() {
    	return this.debito;
    }
    public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setNome(String nome) {
		this.nome=nome;
	}
	public void setCpf(int cpf) {
		this.cpf=cpf;
	}
}
