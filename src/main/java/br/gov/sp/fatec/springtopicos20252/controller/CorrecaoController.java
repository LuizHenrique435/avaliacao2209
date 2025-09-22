package br.gov.sp.fatec.springtopicos20252.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.gov.sp.fatec.springtopicos20252.entity.Correcao;
import br.gov.sp.fatec.springtopicos20252.service.CorrecaoService;

@RestController
@RequestMapping("/correcao")
public class CorrecaoController {

    @Autowired
    private CorrecaoService correcaoService;

    // Cadastro
    @PostMapping
    public ResponseEntity<Correcao> cadastrar(@RequestBody Correcao correcao) {
        return ResponseEntity.ok(correcaoService.salvar(correcao));
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Correcao>> listarTodos() {
        return ResponseEntity.ok(correcaoService.listarTodas());
    }

    // Consulta simples (nota > X e anotacao específica)
    @GetMapping("/consulta")
    public ResponseEntity<List<Correcao>> consultar(
            @RequestParam Integer nota,
            @RequestParam Long anotacaoId) {
        return ResponseEntity.ok(correcaoService.buscarPorNotaEAnotacao(nota, anotacaoId));
    }

    // Consulta avançada (nota > X e anotacao após data/hora)
    @GetMapping("/consulta-avancada")
    public ResponseEntity<List<Correcao>> consultarAvancado(
            @RequestParam Integer nota,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHora) {
        return ResponseEntity.ok(correcaoService.buscarPorNotaEDataAnotacao(nota, dataHora));
    }
}
