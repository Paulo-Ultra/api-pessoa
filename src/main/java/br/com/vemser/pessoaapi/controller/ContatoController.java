package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.entity.Contato;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/contato")
public class ContatoController {

    //Modelo mais novo
//    private final ContatoService contatoService;

    //Modelo mais novo
//        public ContatoController(){
//        contatoService = new ContatoService();
//    }

    @Autowired
    private ContatoService contatoService;

//    public ContatoController(ContatoService contatoService){
//        this.contatoService = contatoService;
//    }

    @Operation(summary = "Criar contato", description = "Cria os contatos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria um contato"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping ("/{idPessoa}")//localhost:8080/contato
    public ResponseEntity<ContatoDTO> create(@PathVariable ("idPessoa") Integer idPessoa, @RequestBody @Valid ContatoCreateDTO contato) throws RegraDeNegocioException {
        return new ResponseEntity<>(contatoService.create(idPessoa, contato), HttpStatus.CREATED);
    }

    @Operation(summary = "Lista Contatos", description = "Lista todos os contatos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os contatos"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping //localhost:8080/contato
    public List<ContatoDTO> list (){
        return contatoService.list();
    }

    @Operation(summary = "Lista Contatos", description = "Lista os contatos do banco pelo parâmetro do id da pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato do ID da pessoa selecionada"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/byIdPessoa") //localhost:8080/contato/byIdPessoa?idPessoa=1
    public ResponseEntity<List<ContatoDTO>> listByIdPessoaParams (@RequestParam("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(contatoService.listByIdPessoa(idPessoa), HttpStatus.OK);
    }

    @Operation(summary = "Lista Contatos", description = "Lista os contatos do banco pelo id da pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato do ID da pessoa selecionada"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPessoa}") //localhost:8080/contato/2
    public ResponseEntity<List<ContatoDTO>> listByIdPessoa(@PathVariable ("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(contatoService.listByIdPessoa(idPessoa), HttpStatus.OK);
    }

    @Operation(summary = "Altera Contatos", description = "Altera os contatos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato do ID selecionado"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idContato}") //localhost:8080/contato/{idContato}
    public ResponseEntity<ContatoDTO> update(@PathVariable("idContato") Integer id, @RequestBody @Valid ContatoCreateDTO contatoAtualizar) throws RegraDeNegocioException{
        return new ResponseEntity<>(contatoService.update(id, contatoAtualizar), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deleta Contato", description = "Deleta o contato do banco pelo ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleta o contato"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idContato}") //localhost:8080/contato/{idContato}
    public ResponseEntity<Void> delete(@PathVariable("idContato") Integer id) throws RegraDeNegocioException {
        contatoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
