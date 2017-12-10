package model;

public class Estabelecimento {
    private int cnpj;
    private String nome;
    private int parcelas;
    private String endereco;
    
    public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	private String cidade;
    private String estado;
    private String email;
    private String telefone;
    
    
    public Estabelecimento(int cnpj, String nome, int parcelas) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.parcelas = parcelas;
    }

    public Estabelecimento(int cnpj, String nome, int parcelas,String endereco, String cidade, String estado, String email,String telefone) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.parcelas = parcelas;
        this.telefone = telefone;
        this.estado=estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
    }

    public int getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public int getParcelas() {
        return parcelas;
    }

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}
    
}
