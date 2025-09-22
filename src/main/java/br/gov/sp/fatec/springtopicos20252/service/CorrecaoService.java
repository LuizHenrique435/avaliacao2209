package br.gov.sp.fatec.springtopicos20252.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.fatec.springtopicos20252.entity.Anotacao;
import br.gov.sp.fatec.springtopicos20252.entity.Correcao;
import br.gov.sp.fatec.springtopicos20252.repository.AnotacaoRepository;
import br.gov.sp.fatec.springtopicos20252.repository.CorrecaoRepository;

@Service
public class CorrecaoService {

    @Autowired
    private CorrecaoRepository correcaoRepository;

    @Autowired
    private AnotacaoRepository anotacaoRepository;

    // Cadastro com validações
    public Correcao salvar(Correcao correcao) {
        if (correcao.getNota() == null || correcao.getNota() < 0 || correcao.getNota() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota inválida");
        }

        if (correcao.getFeedback() == null || correcao.getFeedback().length() <= 5
                || !Character.isUpperCase(correcao.getFeedback().charAt(0))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feedback inválido");
        }

        Anotacao anot = anotacaoRepository.findById(correcao.getAnotacao().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anotação inexistente"));

        if (correcao.getData() == null) {
            correcao.setData(LocalDate.now());
        }

        correcao.setAnotacao(anot);
        return correcaoRepository.save(correcao);
    }

    // Listar todas
    public List<Correcao> listarTodas() {
        return correcaoRepository.findAll();
    }

    // Consulta simples (nota > X e anotacao específica)
    public List<Correcao> buscarPorNotaEAnotacao(Integer nota, Long anotacaoId) {
        return correcaoRepository.findByNotaGreaterThanAndAnotacao_Id(nota, anotacaoId);
    }

    // Consulta avançada (nota > X e anotacao após data/hora)
    public List<Correcao> buscarPorNotaEDataAnotacao(Integer nota, LocalDateTime dataHora) {
        return correcaoRepository.findByNotaGreaterThanAndAnotacao_DataHoraAfter(nota, dataHora);
    }
}
