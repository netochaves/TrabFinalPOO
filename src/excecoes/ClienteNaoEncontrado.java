package excecoes;

public class ClienteNaoEncontrado extends Exception {
    public ClienteNaoEncontrado(){
        super("Cliente não está cadastrado");
    }
}
