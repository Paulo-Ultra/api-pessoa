package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.ContatoEntity;
import br.com.vemser.pessoaapi.entity.EnderecoEntity;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ContatoDTO create(Integer idPessoa, ContatoCreateDTO contato) throws RegraDeNegocioException {
        pessoaService.findByIdPessoa(idPessoa);

        ContatoEntity contatoEntity = convertContatoEntity(contato);
        contatoEntity.setIdPessoa(idPessoa);
        log.info("Criando o contato...");
        log.info("ContatoEntity da pessoa " + contatoEntity.getIdPessoa() + " criado!");
        return convertContatoDTO(contatoRepository.save(contatoEntity));

    }

    public List<ContatoDTO> list (){
        return contatoRepository.findAll().stream()
                .map(this::convertContatoDTO)
                .collect(Collectors.toList());
    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contatoAtualizar) throws RegraDeNegocioException{

        pessoaService.findByIdPessoa(contatoAtualizar.getIdPessoa());
        ContatoEntity contatoAtualizado = findByIdContato(id);
        contatoAtualizado.setIdPessoa(contatoAtualizar.getIdPessoa());
        contatoAtualizado.setTipoContato(contatoAtualizar.getTipoContato());
        contatoAtualizado.setNumero(contatoAtualizar.getNumero());
        contatoAtualizado.setDescricao(contatoAtualizar.getDescricao());

        log.info("Alterando contato...");
        log.info("ContatoEntity " + contatoAtualizado.getIdContato() + " alterado!");
        return convertContatoDTO(contatoAtualizado);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ContatoEntity contatoRecuperado = findByIdContato(id);
        contatoRepository.delete(contatoRecuperado);
        log.warn("Deletando o contato...");
        log.info("ContatoEntity " + id + " deletado!");
    }

    public List<ContatoDTO> listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        pessoaService.findByIdPessoa(idPessoa);
        return contatoRepository.findAll().stream()
                .filter(contato -> contato.getIdPessoa().equals(idPessoa))
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public ContatoEntity findByIdContato(Integer idContato) throws RegraDeNegocioException {
        ContatoEntity contatoById = contatoRepository.findAll().stream()
                .filter(endereco -> endereco.getIdContato().equals(idContato))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("ContatoEntity não encontrado"));
        return contatoById;
    }

    public ContatoEntity convertContatoEntity(ContatoCreateDTO contatoCreateDTO) {
        return objectMapper.convertValue(contatoCreateDTO, ContatoEntity.class);
    }

    public ContatoDTO convertContatoDTO(ContatoEntity contatoEntity) {
        return objectMapper.convertValue(contatoEntity, ContatoDTO.class);
    }
}

