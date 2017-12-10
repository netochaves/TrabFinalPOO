package excecoes;

public class EstabelecimentoJaCadastrado extends Exception {
    public EstabelecimentoJaCadastrado(){
        super("estabelecimeto já está cadastrado");
    }
}
