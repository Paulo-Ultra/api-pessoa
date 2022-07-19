package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class PessoaCreateDTO {
    @Schema(description = "Nome da PessoaEntity")
    @NotBlank(message = "Insira um nome!" )
    //Para evitar que o atributo tenha o getter por exemplo
//    @Getter(AccessLevel.NONE)
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
}
