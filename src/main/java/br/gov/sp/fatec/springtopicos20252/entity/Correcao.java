package br.gov.sp.fatec.springtopicos20252.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "cor_correcao")
public class Correcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cor_id")
    private Long id;

    @Column(name = "cor_feedback", nullable = false, length = 100)
    private String feedback;

    @Column(name = "cor_nota", nullable = false)
    private Float nota;

    @Column(name = "cor_data", nullable = false)
    private LocalDate data;

    @Column(name = "cor_sequencia")
    private Integer sequencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cor_anotacao", nullable = false)
    private Anotacao anotacao;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public Float getNota() { return nota; }
    public void setNota(Float nota) { this.nota = nota; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Integer getSequencia() { return sequencia; }
    public void setSequencia(Integer sequencia) { this.sequencia = sequencia; }
    public Anotacao getAnotacao() { return anotacao; }
    public void setAnotacao(Anotacao anotacao) { this.anotacao = anotacao; }
}
