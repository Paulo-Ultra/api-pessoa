package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EnderecoCreateDTO {
    private Integer idPessoa;
    @NotNull
    @Schema(description = "Tipo do Endereço")
    private TipoEndereco tipo;
    @Length(min = 1, max = 250)
    @Schema(description = "Logradouro")
    private String logradouro;
    @NotNull
    @Schema(description = "Número da Residência")
    private Integer numero;
    @Schema(description = "Complemento")
    private String complemento;
    @NotBlank
    @Size(min = 8, max = 8, message = "CEP deve possuir {max} números")
    @Schema(description = "CEP")
    private String cep;
    @NotBlank
    @Length(max = 250, message = "Campo cidade pode ter no máximo {max} caracteres")
    @Schema(description = "Cidade")
    private String cidade;
    @NotNull
    @Schema(description = "Estado")
    private String estado;
    @NotNull
    @Schema(description = "Pais")
    private String pais;
}
