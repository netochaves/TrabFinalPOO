package controle;
import java.util.Date;
import excecoes.*;

public interface InterfaceCartaoDeCredito {
    /**
     * Cadastra um cliente e gera seu número de cartão.
     * @param cpf
     * @param nome
     * @param salario
     * @return
     * @throws ClienteJaCadastrado Exceção gerada caso haja a tentativa de
     * cadastrar um cpf já cadastrado como cliente, sendo titular ou dependente
     * @throws CpfInvalido Exceção gerada a partir do uso de um cpf <= 0
     * @throws SalarioInvalido Exceção gerada a partir do uso de um salario <= 0
     */
    public int cadastrarCliente(int cpf, String nome, double salario,String endereco,String cidade,String estado)
            throws ClienteJaCadastrado, CpfInvalido, SalarioInvalido;
    /**
     * Cadastra um dependente vinculado a um cartão
     * @param cpf
     * @param nome
     * @param cpfTitular
     * @param limite
     * @return
     * @throws ClienteNaoEncontrado Gerada quando se tenta cadastrar um dependente
     * ligado a um cliente não cadastrado
     * @throws LimiteInvalido Tentativa de cadastrar um dependente com limite <= 0
     * @throws CpfInvalido Tentativa de cadastrar um dependente com cpf <= 0
     * @throws LimiteExcedido Tentativa de cadastrar um dependente com limite
     * maior que o máximo permitido para o titular, somando os limites de todos os
     * dependentes
     * @throws ClienteJaCadastrado Tentativa de cadastrar um dependente já cadastrado
     * anteriormente, seja como titular ou como dependente
     */
    public int cadastrarDependente(int cpf, String nome, int cpfTitular,
                                   double limite,String endereco,String cidade,String estado) throws ClienteNaoEncontrado, LimiteInvalido,
            CpfInvalido, LimiteExcedido, ClienteJaCadastrado;
    /**
     * Cadastra um estabelecimento para realização de compras.
     * @param cnpj
     * @param nome
     * @param parcelas
     * @throws EstabelecimentoJaCadastrado Gerada quando se tentar cadastrar um
     * estabelecimento com cnpj já cadastrado.
     * @throws CnpjInvalido Gerada quando se usa um cnpj <= 0
     * @throws ParcelamentoInvalido Gerada quando se usa um parcelamento <= 0
     */
    public void cadastrarEstabelecimento(int cnpj, String nome, int parcelas,String endereco, String cidade, String estado, String email,String telefone)
            throws EstabelecimentoJaCadastrado, CnpjInvalido,
            ParcelamentoInvalido;
    /**
     * Registra uma compra para um cartão em uma data específica.
     * @param data
     * @param cartao
     * @param estabelecimento
     * @param valor
     * @param parcelas
     * @throws CartaoInexistente Gerada com o uso de um cartão não cadastrado.
     * @throws LimiteExcedido Gerada quando se tenta comprar algo acima do limite
     * permitido para o cartão. Se for titular, tem que avaliar a compra dos dependentes.
     * @throws EstabelecimentoNaoCadastrado Tentativa de comprar em um estabelecimento não cadastrado.
     * @throws ParcelamentoInvalido Tentativa de comprar com um parcelamento <= 0 ou maior
     * que o parcelamento aceito para o estabelecimento.
     * @throws DataInvalida Tentativa de usar uma data nula
     * @throws CartaoInvalido Tentativa de usar um cartão <= 0
     * @throws EstabelecimentoInvalido Tentativa de usar um cnpj <= 0
     * @throws ValorInvalido Tentativa de usar um valor <= 0
     * @throws ClienteNaoEncontrado Tentativa de comprar com um cartão não vinculado a uma pessoa
     * @throws FaturaJaExistente Tentativa de comprar em um mês com fatura já gerada.
     */
    public void registrarCompra(Date data, int cartao, int estabelecimento,
                                double valor, int parcelas) throws CartaoInexistente,
            LimiteExcedido, EstabelecimentoNaoCadastrado, ParcelamentoInvalido,
            DataInvalida, CartaoInvalido, EstabelecimentoInvalido,
            ValorInvalido, ClienteNaoEncontrado, FaturaJaExistente;
    /**
     * Obtém o valor das compras em um dado mês.
     * @param mes
     * @param ano
     * @param cartao
     * @return
     * @throws CartaoInexistente Gerada quando se usa um cartão não cadastrado.
     * @throws DataInvalida Gerado quando se usa um mês (<1 e >12) ou ano (<0) inválido
     * @throws CartaoInvalido Gerada quando se usa um cartão <= 0
     */
    public double valorDeCompras(int mes, int ano, int cartao) throws CartaoInexistente, DataInvalida, CartaoInvalido;

    /**
     * Fecha a fatura de um mês impedindo de ter novas compras e calcula
     * os juros em cima da parcela não paga da fatura anterior.
     * @param cartao
     * @param mes
     * @param ano
     * @return
     * @throws CartaoInexistente Gerada quando se usa um cartão não cadastrado.
     * @throws FaturaInexistente Gerada quando se tentar gerar uma fatura e não existe a fatura anterior.
     * @throws DataInvalida Gerado quando se usa um mês (<1 ou >12) ou ano (<0) inválido
     */
    public double faturar(int cartao, int mes, int ano)
            throws CartaoInexistente, FaturaInexistente, DataInvalida, CartaoInvalido, FaturaJaExistente;
    /**
     * Registra o pagamento de uma parcela da fatura.
     * @param cartao
     * @param mes
     * @param ano
     * @param valor
     * @throws CartaoInexistente Tentativa de usar um cartão não cadastrado.
     * @throws PagamentoInvalido Tentativa de pagar abaixo do mínimo ou acima do máximo
     * permitido para a fatura.
     * @throws CartaoInvalido Tentativa de usar um cartao <= 0
     * @throws ValorInvalido Tentativa de usar um valor <= 0
     * @throws DataInvalida Gerado quando se usa um mês (<1 ou >12) ou ano (<0) inválido
     * @throws FaturaInexistente Tentativa de pagar uma fatura inexistente.
     */
    public void registrarPagamento(int cartao, int mes, int ano, double valor)
            throws CartaoInexistente, PagamentoInvalido, CartaoInvalido, ValorInvalido, DataInvalida, FaturaInexistente;
    /**
     * Obtém o valor de pagamentos para uma fatura.
     * @param mes
     * @param ano
     * @param cartao
     * @return
     * @throws CartaoInexistente Tentativa de usar um cartão não cadastrado.
     * @throws FaturaInexistente Tentativa de usar uma fatura ainda não gerada.
     * @throws DataInvalida Tentativa de usar um mês (<1 ou >12) ou ano (<0) invalido
     * @throws CartaoInvalido Tentativa de usar um cartão invalido (<=0)
     */
    public double valorDePagamentos(int mes, int ano, int cartao) throws CartaoInexistente, FaturaInexistente, DataInvalida, CartaoInvalido;
}