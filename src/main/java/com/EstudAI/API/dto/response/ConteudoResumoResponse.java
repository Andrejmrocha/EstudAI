package com.EstudAI.API.dto.response;

import com.EstudAI.API.domain.Conteudo;

import java.time.LocalDateTime;

public record ConteudoResumoResponse(
        Long id,
        String titulo,
        LocalDateTime criadoEm
) {
    public ConteudoResumoResponse(Conteudo conteudo) {
        this(conteudo.getId(), conteudo.getTitulo(), conteudo.getCriadoEm());
    }
}
