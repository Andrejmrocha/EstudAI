package com.EstudAI.API.service;

import com.EstudAI.API.domain.Conteudo;
import com.EstudAI.API.domain.Pergunta;
import com.EstudAI.API.dto.response.ConteudoDetalhadoResponse;
import com.EstudAI.API.dto.response.ConteudoResumoResponse;
import com.EstudAI.API.dto.response.PerguntaResponse;
import com.EstudAI.API.repository.ConteudoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ConteudoService {

    private final ConteudoRepository conteudoRepository;
    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper;

    // Construtor para injeção
    @Autowired // O @Autowired é opcional em construtores a partir do Spring 4.3
    public ConteudoService(ConteudoRepository conteudoRepository,
                           OpenAiService openAiService,
                           ObjectMapper objectMapper) {
        this.conteudoRepository = conteudoRepository;
        this.openAiService = openAiService;
        this.objectMapper = objectMapper;
    }

    public Conteudo processarTexto(String titulo, String texto){
        String resumoGerado = openAiService.gerarResumo(texto);
        String respostaPerguntas = openAiService.gerarPerguntas(texto);

        List<PerguntaResponse> perguntasDTO = parseiaPerguntas(respostaPerguntas);

        Conteudo conteudo = new Conteudo(titulo, texto, resumoGerado);

        List<Pergunta> perguntas = perguntasDTO.stream()
                        .map(dto -> new Pergunta(dto.enunciado(), dto.alternativaCorreta(), dto.alternativasIncorretas()))
                        .peek(pergunta -> pergunta.setConteudo(conteudo))
                        .collect(Collectors.toList());
        conteudo.setPerguntas(perguntas);

        return conteudoRepository.save(conteudo);
    }

    private List<PerguntaResponse> parseiaPerguntas(String respostaJson){
        try {
            return objectMapper.readValue(respostaJson, new TypeReference<List<PerguntaResponse>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer parser das perguntas", e);
        }
    }

    private String gerarResumoComIA(String texto){
        return "Resumo simulado";
    }

    private List<Pergunta> gerarPerguntasComIA(String texto){
        Pergunta p1 = new Pergunta("Quem é o autor?", "Einstein", List.of("Napoleão", "Voltaire", "Luís XVI"));
        return List.of(p1);
    }

    public List<ConteudoResumoResponse> listarTodos() {
            return conteudoRepository.findAll()
                    .stream()
                    .map(ConteudoResumoResponse::new)
                    .toList();
    }

    public ConteudoDetalhadoResponse buscarPorId(Long id){
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));
        return new ConteudoDetalhadoResponse(conteudo);
    }
}
