package controle;

import DAO.ClientesDAO;
import DAO.CompraDAO;
import DAO.EstabelecimentoDAO;
import DAO.FaturaDAO;
import DAO.PagamentoDAO;
import excecoes.*;
import model.*;

import java.util.ArrayList;
import java.util.Date;
@SuppressWarnings("deprecation")
public class AdministradoraCartaoDeCredito implements InterfaceCartaoDeCredito {
    ArrayList<Pagamento> pagamentos = new ArrayList<>();
    ArrayList<Compra> compras = new ArrayList<>();
    ArrayListDeClientes clientes = new ArrayListDeClientes();
    ArrayListDeEstabelecimentos estabelecimentos = new ArrayListDeEstabelecimentos();
    ArrayListDeFaturas faturas = new ArrayListDeFaturas();
    private double fatura;

    public double getFatura() {
        return fatura;
    }

    public void setFatura(double fatura) {
        this.fatura = fatura;
    }

    public int cadastrarCliente(int cpf, String nome, double limite,String endereco,String cidade,String estado) throws ClienteJaCadastrado, CpfInvalido, SalarioInvalido {
        checkCpf(cpf);
        checkSalario(limite);
        Cliente c = new Cliente(cpf,nome,limite, cpf);
        c.setEndereco(endereco);
        c.setCidade(cidade);
        c.setEstado(estado);
        ClientesDAO.insert(c);
        return cpf;
    }

    @Override
    public int cadastrarDependente(int cpf, String nome, int cpfTitular, double limite,String endereco,String cidade,String estado) throws ClienteNaoEncontrado, LimiteInvalido,
            CpfInvalido, LimiteExcedido, ClienteJaCadastrado {
        checkCpf(cpf);
        checkLimite(limite);
        Cliente t = ClientesDAO.pesquisar(cpfTitular);
        if(t==null) throw new ClienteNaoEncontrado();
        checkLimiteExcedido(t,limite);

        
        if(limite >= t.getLimite())throw new LimiteExcedido();
        Dependente d = new Dependente(cpf,nome,limite,t.getCpf(),cpf);
        d.setEndereco(endereco);
        d.setCidade(cidade);
        d.setEstado(estado);
        
        ClientesDAO.insert(d);
        t.addDependente(d);
        t.setLimite(t.getLimite()-limite);
        
        return cpf;
    }

    @Override
    public void cadastrarEstabelecimento(int cnpj, String nome, int parcelas,String endereco, String cidade, String estado, String email,String telefone) throws EstabelecimentoJaCadastrado, CnpjInvalido,
            ParcelamentoInvalido {
        checkCnpj(cnpj);
        checkParcelamento(parcelas);
        System.out.println(estado);
        Estabelecimento e = new Estabelecimento(cnpj,nome,parcelas,endereco,cidade,estado,email,telefone);
        EstabelecimentoDAO.inserir(e);
    }

    @Override
    public void registrarCompra(Date data, int cartao, int estabelecimento, double valor, int parcelas)
            throws CartaoInexistente,
            LimiteExcedido, EstabelecimentoNaoCadastrado, ParcelamentoInvalido,
            DataInvalida, CartaoInvalido, EstabelecimentoInvalido,
            ValorInvalido, ClienteNaoEncontrado, FaturaJaExistente {

        checkParcelamento(parcelas);
        checkData(data.getMonth()+1,data.getYear()+1900);
        checkCartao(cartao);
        checkEstabelecimento(estabelecimento);
        checkValor(valor);
        checkFaturaJaExistente(data.getMonth(),data.getYear());

        Estabelecimento e = EstabelecimentoDAO.pesquisar(estabelecimento);
        Compra compra;

        //boolean achou = false;

        if(e.getParcelas() < parcelas) throw new ParcelamentoInvalido();

        Cliente c = ClientesDAO.pesquisarCartao(cartao);

        if(c.getLimite() < valor) throw new LimiteExcedido();
        if((c.getLimite()-c.limiteDosDependentes()) < valor) throw new LimiteExcedido();

        double valorParcelado = valor/parcelas;

        c.setTotal(c.getTotal()+valorParcelado);

        c.setUltimaFatura(data);

        if(c instanceof Cliente)
            c.setSaldo(c.getLimite()-(c.getTotal()+c.CompraDependentes()));

        if(c instanceof Dependente)
            c.setSaldo(c.getLimite()-c.getTotal());

        if(parcelas == 1)
             compra = new Compra(cartao,data,estabelecimento,valor,parcelas);
        else
            compra = new Compra(cartao,data,estabelecimento,valorParcelado,parcelas);

        CompraDAO.inserir(compra);
        // int count=1;
        int aux = parcelas-1;
        int month = data.getMonth();
        while (aux > 0){
            Date date = new Date(data.getYear(),++month,data.getDate());
            Compra c1 = new Compra(cartao,date,estabelecimento,valorParcelado,parcelas);
            CompraDAO.inserir(c1);
            aux--;
        }
    }

    @Override
    public double valorDeCompras(int mes, int ano, int cartao) throws CartaoInexistente, DataInvalida, CartaoInvalido {
        checkCartao(cartao);
        checkData(mes,ano);
        Cliente c = ClientesDAO.pesquisarCartao(cartao);
        
        mes-=1;
        ano-=1900;
        double s = 0;
        for (Compra compra : CompraDAO.getAll()) {
            if (compra.getCartao() == c.getNumCartao()){
                if (compra.getData().getMonth() == mes && compra.getData().getYear() == ano) {
                    s += compra.getValor();
                }
            }
        }
        return s;
    }
    @Override
    public double faturar(int cartao, int mes, int ano) throws CartaoInexistente, FaturaInexistente, DataInvalida, CartaoInvalido {
        checkCartao(cartao);
        checkData(mes,ano);

        Date date = new Date(ano,mes,1);

        Fatura f = new Fatura(mes,ano,cartao);

        Cliente c = ClientesDAO.pesquisarCartao(cartao);

        if(c.getDebito()==0){
            date.setMonth(date.getMonth()-1);
            f.setValor(valorDeCompras(mes,ano,cartao));
            FaturaDAO.inserir(f);
            return valorDeCompras(mes,ano,cartao);
        }
        Date date1 = new Date(c.getUltimaFatura().getYear(), c.getUltimaFatura().getMonth(),1);
        Date date2 = new Date(ano,mes,1);

        int aux = date1.getMonth();

        f.setValor(this.valorDeCompras(mes,ano,cartao)+c.getDebito()+(c.getDebito()*0.11));
        date1.setMonth(++aux);
        
        FaturaDAO.inserir(f);

        return f.getValor();
    }

        @Override
        public void registrarPagamento(int cartao, int mes, int ano, double valor) throws CartaoInexistente, PagamentoInvalido, CartaoInvalido, ValorInvalido, DataInvalida, FaturaInexistente{
            checkCartao(cartao);
            checkValor(valor);
            checkData(mes,ano);

            Date date = new Date(ano,mes,1);
            
            checkFaturaInexistente(mes, ano, cartao);
            
            Fatura f = FaturaDAO.pesquisar(mes, ano, cartao);
            
            Cliente c = ClientesDAO.pesquisarCartao(cartao);

            if (valor<(f.getValor()*0.1)||valor>f.getValor()) throw new PagamentoInvalido();

            if (date.before(c.getUltimaFatura())) throw new PagamentoInvalido();

            if (getFatura()==valor){
                Date date1 = new Date(ano,mes,1);
                c.setDebito(f.getValor()-valor);
                f.setValor(f.getValor()-valor);
                c.setLimite(c.getLimite()+valor);
                c.setUltimaFatura(date1);
            }else{
                c.setLimite(c.getLimite()+valor);
                c.setDebito(f.getValor()-valor);
                f.setValor(f.getValor()-valor);
            }
            Pagamento p = new Pagamento(cartao,ano,mes,valor);
            p.setPago(true);
            PagamentoDAO.inserir(p);
        }

    @Override
    public double valorDePagamentos(int mes, int ano, int cartao) throws CartaoInexistente, FaturaInexistente, DataInvalida, CartaoInvalido {
        checkData(mes,ano);
        checkCartao(cartao);

        if(ano > 1000) ano -= 1900;
        mes -= 1;

        Cliente c = ClientesDAO.pesquisarCartao(cartao);
        double s = 0;
        for (Pagamento p:PagamentoDAO.getAll()) {
            if(p.getCartao() == c.getNumCartao())
                if(p.getMes() == mes && p.getAno()==ano)
                    s+=p.getValor();
        }
        return s;
    }

    public void checkLimiteExcedido(Cliente c,double valor) throws LimiteExcedido {
        ArrayList<Dependente> d = c.getDependentes();
        double total = valor;
        for (Dependente dep: d)
            total += dep.getLimite();

        if(total > c.getLimite()) throw new LimiteExcedido();
    }

    public void checkCpf(int cpf) throws  CpfInvalido{
        if(cpf <= 0) throw new CpfInvalido();
    }
    public void checkCnpj(int cnpj) throws CnpjInvalido{
        if(cnpj <= 0) throw new CnpjInvalido();
    }
    public void checkSalario(double salario) throws SalarioInvalido{
        if(salario <= 0) throw new SalarioInvalido();
    }
    public void checkLimite(double limite) throws LimiteInvalido{
        if(limite <= 0) throw new LimiteInvalido();
    }
    public void checkData(int mes, int ano) throws DataInvalida{
        if(mes < 0 || mes > 12 || ano <= 0) throw new DataInvalida();
    }
    public void checkParcelamento(int parcelas) throws ParcelamentoInvalido{
        if(parcelas <= 0) throw new ParcelamentoInvalido();
    }
    public void checkCartao(int cartao) throws CartaoInvalido{
        if(cartao <= 0) throw new CartaoInvalido();
    }
    public void checkEstabelecimento(int estabelicimento) throws EstabelecimentoInvalido{
        if (estabelicimento <= 0) throw new EstabelecimentoInvalido();
    }
    public void checkValor(double valor) throws ValorInvalido{
        if(valor <= 0) throw new ValorInvalido();
    }
    public void checkFaturaInexistente(int mes,int ano,int cartao) throws FaturaInexistente{
        Fatura f = FaturaDAO.pesquisar(mes, ano, cartao);
        if(f == null) throw new FaturaInexistente();
    }
    public void checkFaturaJaExistente(int mes,int ano) throws FaturaJaExistente {
        Fatura f = faturas.procurar(mes,ano);
        if(f != null) throw new FaturaJaExistente();
    }


}
