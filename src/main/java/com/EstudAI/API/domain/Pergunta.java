package com.EstudAI.API.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String enunciado;
    private String alternativaCorreta;
    @ElementCollection
    private List<String> alternativasIncorretas;
    @ManyToOne
    @JoinColumn(name = "conteudo_id")
    private Conteudo conteudo;

    public Pergunta(String enunciado, String resposta, List<String> alternativasIncorretas) {
        this.enunciado = enunciado;
        this.alternativaCorreta = resposta;
        this.alternativasIncorretas = alternativasIncorretas;
    }
}
