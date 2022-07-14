package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.client.DadosPessoaisClient;
import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisDTO;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.PessoaDadosPessoaisService;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("pessoa-dados-pessoais")
public class PessoaDadosPessoaisController {

    @Autowired
    private PessoaDadosPessoaisService pessoaDadosPessoaisService;

    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pessoas"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<PessoaDadosPessoaisDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity(pessoaDadosPessoaisService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Criar pessoas", description = "Cria as pessoas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria uma pessoa"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PessoaDadosPessoaisDTO> create(@Valid @RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException, TemplateException, IOException {
        return new ResponseEntity(pessoaDadosPessoaisService.create(pessoaDadosPessoaisCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Altera pessoas", description = "Altera as pessoas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Altera uma pessoa"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPessoa}/{cpf}")
    public ResponseEntity<PessoaDadosPessoaisDTO> update(@PathVariable("idPessoa") Integer idPessoa, String cpf, @Valid @RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException, TemplateException, IOException {
        return new ResponseEntity(pessoaDadosPessoaisService.update(idPessoa, cpf, pessoaDadosPessoaisCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete de pessoas", description = "Deleta as pessoas do banco pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleta a pessoa"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPessoa}/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable ("idPessoa") Integer idPessoa, String cpf) throws RegraDeNegocioException, TemplateException, IOException {
        pessoaDadosPessoaisService.delete(idPessoa, cpf);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }

}
