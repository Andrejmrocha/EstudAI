package com.EstudAI.API.dto.response;

import com.EstudAI.API.domain.Conteudo;

import java.time.LocalDateTime;
import java.util.List;

public record ConteudoDetalhadoResponse(
        String titulo,
        String textoOriginal,
        String resumo,
        LocalDateTime criadoEm,
        List<PerguntaResponse> perguntas
) {
    public ConteudoDetalhadoResponse(Conteudo conteudo){
        this(
                conteudo.getTitulo(), conteudo.getTextoOriginal(), conteudo.getResumo(),
                conteudo.getCriadoEm(), conteudo.getPerguntas().stream().map(PerguntaResponse::new).toList());
    }
}
