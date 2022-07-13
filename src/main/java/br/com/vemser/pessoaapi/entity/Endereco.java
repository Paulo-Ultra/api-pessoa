package br.com.vemser.pessoaapi.entity;

import br.com.vemser.pessoaapi.enums.TipoEndereco;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    private Integer idEndereco;
    private Integer idPessoa;
    private TipoEndereco tipo;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
}