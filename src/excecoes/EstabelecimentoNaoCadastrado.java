package excecoes;

public class EstabelecimentoNaoCadastrado extends Exception {
    public EstabelecimentoNaoCadastrado(){
        super("Estabelcimeto não está cadastrado");
    }
}
