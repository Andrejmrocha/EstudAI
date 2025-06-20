package com.EstudAI.API.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class OpenAiService {

    private final ChatClient chatClient;


    public OpenAiService(ChatClient.Builder builder){
        chatClient = builder.build();

    }

    public String gerarResumo(String texto){
        return enviarPrompt("Resuma o seguinte texto em até 5 parágrafos:\n" + texto);
    }

    public String gerarPerguntas(String texto){
        return enviarPrompt("""
                Você é um assistente especialista em criar material de estudo. Sua tarefa é gerar exatamente 3 perguntas de múltipla escolha com base no texto fornecido abaixo.
                
                       Para cada pergunta, você deve fornecer:
                       1.  O enunciado da pergunta.
                       2.  A alternativa correta.
                       3.  Pelo menos 3 (três) alternativas incorretas.
                
                       A sua resposta DEVE ser exclusivamente um JSON válido, sem nenhum texto, comentário ou explicação adicional antes ou depois dele. O JSON deve ser uma lista (array) de objetos, onde cada objeto representa uma pergunta e deve conter as seguintes chaves (keys):
                       - "enunciado": uma string com a pergunta.
                       - "alternativaCorreta": uma string com a resposta certa.
                       - "alternativasIncorretas": uma lista (array) de strings contendo as respostas erradas.
                
                       Exemplo do formato de saída esperado:
                       [
                         {
                           "enunciado": "Enunciado da pergunta 1...",
                           "alternativaCorreta": "Texto da alternativa correta.",
                           "alternativasIncorretas": [
                             "Texto da primeira alternativa incorreta.",
                             "Texto da segunda alternativa incorreta.",
                             "Texto da terceira alternativa incorreta."
                           ]
                         },
                         {
                           "enunciado": "Enunciado da pergunta 2...",
                           "alternativaCorreta": "Texto da alternativa correta.",
                           "alternativasIncorretas": [
                             "Incorreta A.",
                             "Incorreta B.",
                             "Incorreta C.",
                             "Incorreta D."
                           ]
                         },
                         {
                           "enunciado": "Enunciado da pergunta 3...",
                           "alternativaCorreta": "Texto da alternativa correta.",
                           "alternativasIncorretas": [
                             "Incorreta X.",
                             "Incorreta Y.",
                             "Incorreta Z."
                           ]
                         }
                       ]
                """ + texto);
    }

    private String enviarPrompt(String prompt){
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
