package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.config.PropertieReader;
import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.EmailService;
import br.com.vemser.pessoaapi.service.PessoaService;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Validated
public class PessoaController {

    @Autowired
    private PropertieReader propertieReader;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PessoaService pessoaService;

    //Modelo mais novo
//    private final PessoaService pessoaService;

    //Modelo mais novo
//    public PessoaController(PessoaService pessoaService){
//        this.pessoaService = pessoaService;
//    }

    @Value("${spring.application.name}")
    private String app;

//    @GetMapping("/ambiente")
//    public String getAmbiente(){
//        return propertieReader.getAmbiente();
//    }
//
//    @GetMapping("/hello") //localhost:8080/pessoa/hello
//    public String hello(){
//        return "Hello " + app + "!";
//    }

//    @SneakyThrows
//    @GetMapping("/email")
//    public String email() {
//        emailService.sendSimpleMessage();
//        emailService.sendWithAttachment();
//        return "Enviando E-mail.. " + app + "!";
//    }

    @Operation(summary = "Criar pessoas", description = "Cria as pessoas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria uma pessoa"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PessoaDTO> create(@RequestBody @Valid PessoaCreateDTO pessoa) throws RegraDeNegocioException, TemplateException, IOException {
//        return ResponseEntity.ok(pessoaService.create(pessoa));
        //Um ou outro

        return new ResponseEntity<>(pessoaService.create(pessoa), HttpStatus.CREATED);
    }
    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pessoas"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<PessoaDTO> list (){
        return pessoaService.list();
    }

    @Operation(summary = "listar pessoas", description = "Lista as pessoas do banco pelo nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a pessoa pelo nome"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/byname") // localhost:8080/pessoa/byname?nome=Paulo
    public ResponseEntity<List<PessoaDTO>> listByName(@RequestParam("nome") String nome) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaService.listByName(nome), HttpStatus.OK);
    }

    @Operation(summary = "Altera pessoas", description = "Altera as pessoas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Altera uma pessoa"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> update(@PathVariable("idPessoa") Integer id, @RequestBody @Valid PessoaCreateDTO pessoaAtualizar) throws RegraDeNegocioException, TemplateException, IOException {
        return new ResponseEntity<>(pessoaService.update(id, pessoaAtualizar), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete de pessoas", description = "Deleta as pessoas do banco pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleta a pessoa"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") Integer id) throws RegraDeNegocioException, TemplateException, IOException {
        pessoaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
