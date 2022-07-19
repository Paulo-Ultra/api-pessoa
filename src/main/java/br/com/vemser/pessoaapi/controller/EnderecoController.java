package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.EnderecoService;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Cria Endereço", description = "Cria os endereços do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria uma endereço"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idPessoa}")
    public ResponseEntity<EnderecoDTO> create(@PathVariable("idPessoa") Integer idPessoa, @RequestBody @Valid EnderecoCreateDTO endereco) throws RegraDeNegocioException, TemplateException, IOException {
        return new ResponseEntity<>(enderecoService.create(idPessoa, endereco), HttpStatus.CREATED);
    }

    @Operation(summary = "Lista Endereços", description = "Lista todas os endereços do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<EnderecoDTO> list (){
        return enderecoService.list();
    }

    @Operation(summary = "Lista Endereços", description = "Mostra o endereço pelo id dos endereços do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> listByIdEndereco (@PathVariable ("idEndereco") Integer idEndereco) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.listByIdEndereco(idEndereco), HttpStatus.OK);
    }

    @Operation(summary = "Lista Endereços", description = "Mostra o endereço do banco pelo Id da pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPessoa}/pessoa")
    public ResponseEntity<List<EnderecoDTO>> listByIdPessoa (@PathVariable ("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.listByIdPessoa(idPessoa), HttpStatus.OK);
    }

    @Operation(summary = "Altera Endereços", description = "Altera os endereços do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço do ID selecionado"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id, @RequestBody @Valid EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException, TemplateException, IOException {
        return new ResponseEntity<>(enderecoService.update(id, enderecoAtualizar), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deleta Endereço", description = "Deleta o endereço do banco pelo ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleta o endereço"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity <Void> delete(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException, TemplateException, IOException {
        enderecoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
