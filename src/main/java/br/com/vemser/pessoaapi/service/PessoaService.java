package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.Endereco;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.enums.TipoEmail;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;
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
//        boolean pessoaExiste = ObjectUtils.isEmpty(pessoa.getDataNascimento());
//        boolean nomeEmBranco = StringUtils.isBlank(pessoa.getNome());
////        boolean cpfEmBranco = StringUtils.isBlank(pessoa.getCpf());
//
//        if (!nomeEmBranco && !pessoaExiste && !cpfEmBranco && pessoa.getCpf().length() == 14) {
        log.info("Criando a pessoa...");
        Pessoa pessoaEntity = objectMapper.convertValue(pessoa, Pessoa.class);
        pessoaRepository.create(pessoaEntity);

        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
        log.info("Pessoa " + pessoaDTO.getNome() + " criada!");
        String emailTipo = TipoEmail.CREATE.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, emailTipo);
        return pessoaDTO;
//        } else {
//            throw new RegraDeNegocioException("Pessoa não foi criada");
//        }
    }

    public List<PessoaDTO> list() {
//        List<PessoaDTO> pessoasDTO = new ArrayList<>();
//        List<Pessoa> pessoasEntity = pessoaRepository.list();
//        for (Pessoa pessoa : pessoasEntity) {
//            pessoasDTO.add(objectMapper.convertValue(pessoa, PessoaDTO.class));
//        }
//        return pessoasDTO;
        return pessoaRepository.list().stream()
                .map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

    public PessoaDTO update(Integer id, PessoaCreateDTO pessoaAtualizar) throws RegraDeNegocioException, TemplateException, IOException {
        //Somente um teste
//        findByName(String.valueOf(pessoaAtualizar));
        log.info("Alterando a pessoa...");
        objectMapper.convertValue(pessoaAtualizar, Pessoa.class);
        Pessoa pessoaRecuperada = findByIdPessoa(id);
        pessoaRecuperada.setCpf(pessoaAtualizar.getCpf());
        pessoaRecuperada.setNome(pessoaAtualizar.getNome());
        pessoaRecuperada.setDataNascimento(pessoaAtualizar.getDataNascimento());
        pessoaRecuperada.setEmail(pessoaAtualizar.getEmail());
        log.info("Pessoa " + pessoaRecuperada.getNome() + " alterada!");
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaRecuperada, PessoaDTO.class);
        String emailTipo = TipoEmail.PUT.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, emailTipo);
        return pessoaDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException, TemplateException, IOException {
        Pessoa pessoaRecuperada = findByIdPessoa(id);
        pessoaRepository.list().remove(pessoaRecuperada);
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaRecuperada, PessoaDTO.class);
        String emailTipo = TipoEmail.DELETE.getTipo();
        emailService.sendEmailPessoa(pessoaDTO, emailTipo);
        log.warn("Deletando a pessoa...");
        log.info("Pessoa id " + id + " deletada!");
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

    public Pessoa findByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
       return pessoaRepository.list().stream()
                .filter(pessoa -> pessoa.getIdPessoa().equals(idPessoa))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Pessoa não encontrada"));
    }

    //Teste para verificar se consigo recuperar pessoa pelo nome
    public List<Pessoa> findByName(String nome) throws RegraDeNegocioException {
        return pessoaRepository.list().stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());
    }
}
