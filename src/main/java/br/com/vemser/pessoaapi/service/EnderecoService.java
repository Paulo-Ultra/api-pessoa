package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.Endereco;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.enums.TipoEmail;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnderecoService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

    public EnderecoDTO create(Integer idPessoa, EnderecoCreateDTO endereco) throws RegraDeNegocioException, TemplateException, IOException {

        log.info("Criando o endereço...");
        Pessoa pessoa = pessoaService.findByIdPessoa(idPessoa);
        Endereco enderecoEntity = objectMapper.convertValue(endereco, Endereco.class);
        enderecoEntity = enderecoRepository.create(enderecoEntity);
        enderecoEntity.setIdPessoa(idPessoa);
        log.info("Endereço da pessoa " + idPessoa + " criado!");
        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
        String emailTipo = TipoEmail.CREATE.getTipo();
        emailService.sendEmailEndereco(pessoa, enderecoDTO, emailTipo);
        return enderecoDTO;
    }

    public List<EnderecoDTO> list() {
             return enderecoRepository.list().stream()
                     .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                     .collect(Collectors.toList());
    }

    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException, TemplateException, IOException {
        Pessoa pessoa = pessoaService.findByIdPessoa(enderecoAtualizar.getIdPessoa());

        Endereco enderecoAtualizado = finByIdEndereco(id);
        enderecoAtualizado.setIdPessoa(enderecoAtualizar.getIdPessoa());
        enderecoAtualizado.setTipo(enderecoAtualizar.getTipo());
        enderecoAtualizado.setLogradouro(enderecoAtualizar.getLogradouro());
        enderecoAtualizado.setNumero(enderecoAtualizar.getNumero());
        enderecoAtualizado.setComplemento(enderecoAtualizar.getComplemento());
        enderecoAtualizado.setCep(enderecoAtualizar.getCep());
        enderecoAtualizado.setCidade(enderecoAtualizar.getCidade());
        enderecoAtualizado.setEstado(enderecoAtualizar.getEstado());
        enderecoAtualizado.setPais(enderecoAtualizar.getPais());

        log.info("Alterando endereço...");
        log.info("Endereço " + enderecoAtualizado.getIdEndereco() + " alterado!");
        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoAtualizado, EnderecoDTO.class);
        String emailTipo = TipoEmail.PUT.getTipo();
        emailService.sendEmailEndereco(pessoa, enderecoDTO, emailTipo);
        return enderecoDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException, TemplateException, IOException {
        Endereco enderecoRecuperado = finByIdEndereco(id);
        Pessoa pessoaRecuperada = pessoaService.findByIdPessoa(enderecoRecuperado.getIdPessoa());
       enderecoRepository.list().remove(enderecoRecuperado);
        log.warn("Deletando o endereço...");
        log.info("Endereço " + id + " deletado!");
        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoRecuperado, EnderecoDTO.class);
        String emailTipo = TipoEmail.DELETE.getTipo();
        emailService.sendEmailEndereco(pessoaRecuperada, enderecoDTO, emailTipo);
    }

    public EnderecoDTO listByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        return objectMapper.convertValue(finByIdEndereco(idEndereco), EnderecoDTO.class);
        }

    public List<EnderecoDTO> listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        pessoaService.findByIdPessoa(idPessoa);
        return enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdPessoa().equals(idPessoa))
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public Endereco finByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        Endereco enderecoById = enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));
        return enderecoById;
    }
}

