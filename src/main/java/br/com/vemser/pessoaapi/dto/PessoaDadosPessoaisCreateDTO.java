package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDadosPessoaisCreateDTO{
    @Schema(description = "Nome da PessoaEntity")
    @NotBlank(message = "Insira um nome!" )
    private String nome;
    @NotNull
    @Past
    @Schema(description = "Data de Nascimento")
    private LocalDate dataNascimento;
    @NotEmpty
    @Size(min = 11, max = 11, message = "CPF deve conter {max} números")
    @Schema(description = "CPF")
    private String cpf;
    @Schema(description = "E-mail", example="prfultra@yahoo.com.br")
    @NotBlank
    private String email;
    @Schema (description = "Habilitação de Trânsito")
    private String cnh;
    private	String nomeMae;
    private String nomePai;
    @Schema (description = "Registro Geral")
    private String rg;
    private Sexo sexo;
    @Schema (description = "Número do Título de Eleitor")
    private String tituloEleitor;
}
