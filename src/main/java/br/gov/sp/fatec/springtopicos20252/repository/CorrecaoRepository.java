package br.gov.sp.fatec.springtopicos20252.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.sp.fatec.springtopicos20252.entity.Correcao;

public interface CorrecaoRepository extends JpaRepository<Correcao, Long> {

    // Consulta simples (nota > X e anotacao específica)
    List<Correcao> findByNotaGreaterThanAndAnotacao_Id(Integer nota, Long anotacaoId);

    // Consulta avançada (nota > X e anotacao registrada após data/hora)
    List<Correcao> findByNotaGreaterThanAndAnotacao_DataHoraAfter(Integer nota, LocalDateTime dataHora);
}
