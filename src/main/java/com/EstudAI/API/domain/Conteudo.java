package com.EstudAI.API.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String textoOriginal;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String resumo;
    @CreationTimestamp
    private LocalDateTime criadoEm;
    @OneToMany(mappedBy = "conteudo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pergunta> perguntas = new ArrayList<>();

    public Conteudo(String titulo, String texto, String resumoGerado) {
        this.titulo = titulo;
        this.textoOriginal = texto;
        this.resumo = resumoGerado;
    }
}