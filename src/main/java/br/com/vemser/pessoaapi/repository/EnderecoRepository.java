package br.com.vemser.pessoaapi.repository;

import br.com.vemser.pessoaapi.entity.Endereco;
import br.com.vemser.pessoaapi.enums.TipoEndereco;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EnderecoRepository {
    private static List<Endereco> listaEnderecos = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public EnderecoRepository(){
        listaEnderecos.add(new Endereco(COUNTER.incrementAndGet(), 1, TipoEndereco.ofTipo(1), "Rua Presidente Dutra", 9
                ,"Bairro Maria Cândida", "44915-000", "São Gabriel", "Bahia", "Brasil"));
        listaEnderecos.add(new Endereco(COUNTER.incrementAndGet(), 2, TipoEndereco.ofTipo(2), "Rua das Margaridas", 250
                ,"Centro", "15001-000", "São Paulo", "São Paulo", "Brasil"));
        listaEnderecos.add(new Endereco(COUNTER.incrementAndGet(), 3, TipoEndereco.ofTipo(1), "Avenida dos Navegantes", 135
                ,"Apartamento", "51125-000", "Passos", "Minas Gerais", "Brasil"));
        listaEnderecos.add(new Endereco(COUNTER.incrementAndGet(), 4, TipoEndereco.ofTipo(2), "Avenida Paulista", 1010
                ,"Hotel Bandeirantes", "11355-000", "São Paulo", "São Paulo", "Brasil"));
        listaEnderecos.add(new Endereco(COUNTER.incrementAndGet(), 5, TipoEndereco.ofTipo(1), "Rua Getulio Vargas", 35
                ,"Próximo ao Centro", "72220-000", "Brasília", "São Paulo", "Brasil"));
    }

    public Endereco create(Endereco endereco) {
        endereco.setIdEndereco(COUNTER.incrementAndGet());
        listaEnderecos.add(endereco);
        return endereco;
    }

    public List<Endereco> list() {
        return listaEnderecos;
    }
}
