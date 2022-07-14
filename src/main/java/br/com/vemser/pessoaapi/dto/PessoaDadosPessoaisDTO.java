package br.com.vemser.pessoaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDadosPessoaisDTO extends PessoaDadosPessoaisCreateDTO {
    private Integer idPessoaDadosPessoais;
}
