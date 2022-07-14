package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.*;
import br.com.vemser.pessoaapi.exception.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PessoaDadosPessoaisService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private DadosPessoaisService dadosPessoaisService;

    @Autowired
    private ObjectMapper objectMapper;

    public PessoaDadosPessoaisDTO create(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisDTOcreate) throws TemplateException, RegraDeNegocioException, IOException {
        PessoaDTO pessoaDTO = pessoaService.create(convertPessoaCreateDTOEmPessoaDados(pessoaDadosPessoaisDTOcreate));
        dadosPessoaisService.post(convertDadosEmPessoaDados(pessoaDadosPessoaisDTOcreate));
        PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO = createDTOPessoaDadosPessoaisDTO(pessoaDadosPessoaisDTOcreate);
        pessoaDadosPessoaisDTO.setIdPessoaDadosPessoais(pessoaDTO.getIdPessoa());
        return pessoaDadosPessoaisDTO;
    }

    public PessoaDadosPessoaisDTO update(Integer id, String cpf, PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisDTOUpdate) throws TemplateException, RegraDeNegocioException, IOException {
        pessoaService.update(id, convertPessoaCreateDTOEmPessoaDados(pessoaDadosPessoaisDTOUpdate));
        dadosPessoaisService.put(cpf, convertDadosEmPessoaDados(pessoaDadosPessoaisDTOUpdate));
        PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO = createDTOPessoaDadosPessoaisDTO(pessoaDadosPessoaisDTOUpdate);

        return pessoaDadosPessoaisDTO;
    }

    public void delete(Integer id, String cpf) throws TemplateException, RegraDeNegocioException, IOException {
        pessoaService.delete(id);
        dadosPessoaisService.delete(cpf);
    }

    public List<PessoaDadosPessoaisDTO> list() {
//        List<PessoaDadosPessoaisCreateDTO> pessoaDadosPessoaisCreateDTOList = Stream.of(pessoaRepository.list(), dadosPessoaisService.getAll()
//                        .stream().flatMap(Collection::stream)
//                        .collect(Collectors.toList()));

        List<PessoaDadosPessoaisDTO> pessoaDadosPessoaisDTO = new ArrayList<>();
        List<PessoaDTO> pessoasDTO = pessoaService.list();

        for (int i = 0; i < pessoasDTO.size(); i++) {
            pessoaDadosPessoaisDTO.add(new PessoaDadosPessoaisDTO());
            pessoaDadosPessoaisDTO.get(i).setIdPessoaDadosPessoais(pessoasDTO.get(i).getIdPessoa());
            pessoaDadosPessoaisDTO.get(i).setNome(pessoasDTO.get(i).getNome());
            pessoaDadosPessoaisDTO.get(i).setCpf(pessoasDTO.get(i).getCpf());
            pessoaDadosPessoaisDTO.get(i).setDataNascimento(pessoasDTO.get(i).getDataNascimento());
            pessoaDadosPessoaisDTO.get(i).setEmail(pessoasDTO.get(i).getEmail());
        }

        for (PessoaDadosPessoaisDTO pessoaDPDTO : pessoaDadosPessoaisDTO) {
            Optional<DadosPessoaisDTO> dadosPessoaisDTO = dadosPessoaisService.getAll()
                    .stream()
                    .filter(dadosPessoais -> dadosPessoais.getCpf().equals(pessoaDPDTO.getCpf()))
                    .findAny();
            if (dadosPessoaisDTO.isPresent()) {
                pessoaDPDTO.setCnh(dadosPessoaisDTO.get().getCnh());
                pessoaDPDTO.setNomeMae(dadosPessoaisDTO.get().getNomeMae());
                pessoaDPDTO.setNomePai(dadosPessoaisDTO.get().getNomePai());
                pessoaDPDTO.setRg(dadosPessoaisDTO.get().getRg());
                pessoaDPDTO.setSexo(dadosPessoaisDTO.get().getSexo());
                pessoaDPDTO.setTituloEleitor(dadosPessoaisDTO.get().getTituloEleitor());
            }
        }
        return pessoaDadosPessoaisDTO;
    }


    public DadosPessoaisDTO convertDadosEmPessoaDados(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisDTO) {
        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO();
        dadosPessoaisDTO.setCnh(pessoaDadosPessoaisDTO.getCnh());
        dadosPessoaisDTO.setCpf(pessoaDadosPessoaisDTO.getCpf());
        dadosPessoaisDTO.setNome(pessoaDadosPessoaisDTO.getNome());
        dadosPessoaisDTO.setNomeMae(pessoaDadosPessoaisDTO.getNomeMae());
        dadosPessoaisDTO.setNomePai(pessoaDadosPessoaisDTO.getNomePai());
        dadosPessoaisDTO.setRg(pessoaDadosPessoaisDTO.getRg());
        dadosPessoaisDTO.setSexo(pessoaDadosPessoaisDTO.getSexo());
        dadosPessoaisDTO.setTituloEleitor(pessoaDadosPessoaisDTO.getTituloEleitor());
        return dadosPessoaisDTO;
    }

    public PessoaCreateDTO convertPessoaCreateDTOEmPessoaDados(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisDTO) {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();
        pessoaCreateDTO.setNome(pessoaDadosPessoaisDTO.getCnh());
        pessoaCreateDTO.setDataNascimento(pessoaDadosPessoaisDTO.getDataNascimento());
        pessoaCreateDTO.setCpf(pessoaDadosPessoaisDTO.getCpf());
        pessoaCreateDTO.setEmail(pessoaDadosPessoaisDTO.getEmail());
        return pessoaCreateDTO;
    }

    public PessoaDadosPessoaisDTO createDTOPessoaDadosPessoaisDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) {
        return objectMapper.convertValue(pessoaDadosPessoaisCreateDTO, PessoaDadosPessoaisDTO.class);
    }

}
