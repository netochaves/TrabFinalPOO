package controle;

import excecoes.EstabelecimentoJaCadastrado;
import excecoes.EstabelecimentoNaoCadastrado;
import model.Estabelecimento;

import java.util.ArrayList;

public class ArrayListDeEstabelecimentos {
    ArrayList<Estabelecimento> estabelecimentos = new ArrayList<>();

    public void add(Estabelecimento estabelecimento) throws EstabelecimentoJaCadastrado {
        for (Estabelecimento e:estabelecimentos)
            if(e.getCnpj() == estabelecimento.getCnpj()) throw new EstabelecimentoJaCadastrado();
        estabelecimentos.add(estabelecimento);
    }

    public Estabelecimento procurar(int cnpj) throws EstabelecimentoNaoCadastrado {
        for (Estabelecimento c:estabelecimentos)
            if(c.getCnpj() == cnpj)
                return c;
        throw new EstabelecimentoNaoCadastrado();
    }
}
