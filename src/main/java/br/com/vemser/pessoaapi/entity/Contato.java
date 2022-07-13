package br.com.vemser.pessoaapi.entity;

import br.com.vemser.pessoaapi.enums.TipoContato;
import lombok.*;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contato {

    private Integer idContato;
    private Integer idPessoa;
    private TipoContato tipoContato;
    private String numero;
    private String descricao;
}
