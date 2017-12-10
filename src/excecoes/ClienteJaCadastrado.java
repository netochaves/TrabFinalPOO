package excecoes;

public class ClienteJaCadastrado extends Exception {
    public ClienteJaCadastrado(){
        super("cliente já está cadastrado");
    }
}
