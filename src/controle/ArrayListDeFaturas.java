package controle;

import model.Fatura;

import java.util.ArrayList;

public class ArrayListDeFaturas {

    private ArrayList<Fatura> faturas = new ArrayList<>();

    public void incluir(Fatura f){
        faturas.add(f);
    }
    public Fatura procurar(int mes,int ano){
        for (Fatura f:faturas) {
            if(mes == f.getMes() && ano == f.getAno()){
                return f;
            }
        }
        return null;
    }

}
