package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import br.com.vemser.pessoaapi.service.DadosPessoaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados-pessoais")
public class DadosPessoaisController {

    @Autowired
    private DadosPessoaisService dadosPessoaisService;

    @GetMapping
    public List<DadosPessoaisDTO> getAll() {
        return dadosPessoaisService.getAll();
    }
    @PostMapping
    public DadosPessoaisDTO post(@RequestBody DadosPessoaisDTO dadosPessoaisDTO) {
        return dadosPessoaisService.post(dadosPessoaisDTO);
    }

    @PutMapping("/{cpf}")
    public DadosPessoaisDTO put(@PathVariable("cpf") String cpf, @RequestBody DadosPessoaisDTO dadosPessoaisDTO) {
        return dadosPessoaisService.put(cpf, dadosPessoaisDTO);
    }

    @DeleteMapping("/{cpf}")
    public void delete(@PathVariable("cpf") String cpf) {
        dadosPessoaisService.delete(cpf);
    }

    @GetMapping("/{cpf}")
    public DadosPessoaisDTO get(@PathVariable ("cpf") String cpf) {
        return dadosPessoaisService.get(cpf);
    }
}
