package com.EstudAI.API.dto.response;

import com.EstudAI.API.domain.Pergunta;

import java.util.List;

public record PerguntaResponse(
        String enunciado,
        String alternativaCorreta,
        List<String> alternativasIncorretas
) {
    public PerguntaResponse(Pergunta pergunta) {
        this(pergunta.getEnunciado(), pergunta.getAlternativaCorreta(), pergunta.getAlternativasIncorretas());
    }
}
