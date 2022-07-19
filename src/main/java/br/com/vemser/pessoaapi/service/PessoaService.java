package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.enums.TipoEmail;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

//    public PessoaService(){
//        pessoaRepository = new PessoaRepository();
//    }

    public PessoaDTO create(PessoaCreateDTO pessoa) throws RegraDeNegocioException, TemplateException, IOException {

        log.info("Criando a pessoa...");
        PessoaEntity pessoaEntity = convertPessoaEntity(pessoa);
        return convertPessoaDTO(pessoaRepository.save(pessoaEntity));
//        pessoaRepository.save(pessoaEntity);
//        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
//        log.info("PessoaEntity " + pessoaDTO.getNome() + " criada!");
//        String emailTipo = TipoEmail.CREATE.getTipo();
//        emailService.sendEmailPessoa(pessoaDTO, emailTipo);
//        return pessoaDTO(pessoaRepository.save(pessoaEntity));
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.findAll().stream()
                .map(this::convertPessoaDTO)
                .collect(Collectors.toList());
    }

    public PessoaDTO update(Integer id, PessoaCreateDTO pessoaAtualizar) throws RegraDeNegocioException, TemplateException, IOException {

        log.info("Alterando a pessoa...");
        PessoaEntity pessoaAtualizada = findByIdPessoa(id);
        pessoaAtualizada.setCpf(pessoaAtualizar.getCpf());
        pessoaAtualizada.setNome(pessoaAtualizar.getNome());
        pessoaAtualizada.setDataNascimento(pessoaAtualizar.getDataNascimento());
        pessoaAtualizada.setEmail(pessoaAtualizar.getEmail());
        log.info("PessoaEntity " + pessoaAtualizada.getNome() + " alterada!");
        return convertPessoaDTO(pessoaRepository.save(pessoaAtualizada));
//        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaRecuperada, PessoaDTO.class);
//        String emailTipo = TipoEmail.PUT.getTipo();
//        emailService.sendEmailPessoa(pessoaDTO, emailTipo);
//        return pessoaDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException, TemplateException, IOException {
        PessoaEntity pessoaRecuperada = findByIdPessoa(id);
        pessoaRepository.delete(pessoaRecuperada);
//        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaRecuperada, PessoaDTO.class);
//        String emailTipo = TipoEmail.DELETE.getTipo();
//        emailService.sendEmailPessoa(pessoaDTO, emailTipo);
        log.warn("Deletando a pessoa...");
        log.info("PessoaEntity id " + id + " deletada!");
    }

    public List<PessoaDTO> listByName(String nome) throws RegraDeNegocioException {
        log.info("Listando pessoa pelo nome...");
        if(findByName(nome).isEmpty()){
            log.info("Nome não encontrado");
            throw new RegraDeNegocioException("Nome não encontrado");
        } else {
            log.info("Nome encontrado...");
            return findByName(nome).stream()
                    .map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class))
                    .collect(Collectors.toList());
        }
    }

    public PessoaEntity findByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
       return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new RegraDeNegocioException("PessoaEntity não encontrada"));
    }

    //Teste para verificar se consigo recuperar pessoa pelo nome
    public List<PessoaEntity> findByName(String nome) throws RegraDeNegocioException {
        return pessoaRepository.findAll().stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());
    }

    public PessoaEntity convertPessoaEntity(PessoaCreateDTO pessoaCreateDTO) {
        return objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
    }

    public PessoaDTO convertPessoaDTO(PessoaEntity pessoaEntity) {
        return objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
    }
}
