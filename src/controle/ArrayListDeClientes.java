package controle;

import excecoes.CartaoInexistente;
import excecoes.ClienteJaCadastrado;
import excecoes.ClienteNaoEncontrado;
import model.Cliente;

import java.util.ArrayList;

public class ArrayListDeClientes {
    ArrayList<Cliente> clientes = new ArrayList<>();

    public void add(Cliente cliente) throws ClienteJaCadastrado {
        for (Cliente c:clientes)
            if(c.getCpf() == cliente.getCpf())
                throw new ClienteJaCadastrado();
        clientes.add(cliente);
    }

    public Cliente procurar(int cpf) throws ClienteNaoEncontrado {
        for (Cliente c:clientes)
            if(c.getCpf() == cpf)
                return c;
        throw new ClienteNaoEncontrado();
    }
    public Cliente procurarCartao(int cartao) throws CartaoInexistente {
        for (Cliente c:clientes)
            if(c.getNumCartao() == cartao)
                return c;
        throw new CartaoInexistente();
    }
}
