package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.entity.Contato;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.ContatoRepository;
import br.com.vemser.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

//    public ContatoService(){
//        contatoRepository = new ContatoRepository();
//    }

    public ContatoDTO create(Integer idPessoa, ContatoCreateDTO contato) throws RegraDeNegocioException {
        pessoaService.findByIdPessoa(idPessoa);
        Contato contatoEntity = objectMapper.convertValue(contato, Contato.class);
        contatoEntity = contatoRepository.create(idPessoa, contatoEntity);
        contatoEntity.setIdPessoa(idPessoa);
        log.info("Criando o contato...");
        log.info("Contato da pessoa " + contatoEntity.getIdPessoa() + " criado!");
        return objectMapper.convertValue(contatoEntity, ContatoDTO.class);
    }

    public List<ContatoDTO> list (){
        return contatoRepository.list().stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contatoAtualizar) throws RegraDeNegocioException{

        pessoaService.findByIdPessoa(contatoAtualizar.getIdPessoa());
        Contato contatoAtualizado = findByIdContato(id);
        contatoAtualizado.setIdPessoa(contatoAtualizar.getIdPessoa());
        contatoAtualizado.setTipoContato(contatoAtualizar.getTipoContato());
        contatoAtualizado.setNumero(contatoAtualizar.getNumero());
        contatoAtualizado.setDescricao(contatoAtualizar.getDescricao());

        log.info("Alterando contato...");
        log.info("Contato " + contatoAtualizado.getIdContato() + " alterado!");
        return objectMapper.convertValue(contatoAtualizado, ContatoDTO.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        Contato contatoRecuperado = findByIdContato(id);
        contatoRepository.list().remove(contatoRecuperado);
        log.warn("Deletando o contato...");
        log.info("Contato " + id + " deletado!");
    }

    public List<ContatoDTO> listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        pessoaService.findByIdPessoa(idPessoa);
        return contatoRepository.list().stream()
                .filter(contato -> contato.getIdPessoa().equals(idPessoa))
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public Contato findByIdContato(Integer idContato) throws RegraDeNegocioException {
        Contato contatoById = contatoRepository.list().stream()
                .filter(endereco -> endereco.getIdContato().equals(idContato))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado"));
        return contatoById;
    }
}

