package com.EstudAI.API.controller;

import com.EstudAI.API.domain.Conteudo;
import com.EstudAI.API.dto.request.ConteudoRequest;
import com.EstudAI.API.dto.response.ConteudoDetalhadoResponse;
import com.EstudAI.API.dto.response.ConteudoResumoResponse;
import com.EstudAI.API.service.ConteudoService;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/conteudos")
public class ConteudoController {

    @Autowired
    private ConteudoService conteudoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConteudoDetalhadoResponse> receberTexto(@RequestBody ConteudoRequest request) {
        Conteudo salvo = conteudoService.processarTexto(request.titulo(), request.texto());
        return ResponseEntity.ok(new ConteudoDetalhadoResponse(salvo));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConteudoDetalhadoResponse> receberArquivo(@RequestParam("titulo") String titulo,
                                                 @RequestParam("arquivo") MultipartFile arquivo) throws IOException, TikaException {
        String textoExtraido = extrairTextoDoArquivo(arquivo);
        Conteudo salvo = conteudoService.processarTexto(titulo, textoExtraido);
        return ResponseEntity.ok(new ConteudoDetalhadoResponse(salvo));
    }

    private String extrairTextoDoArquivo(MultipartFile arquivo) throws IOException, TikaException {
        Tika tika = new Tika();
        return tika.parseToString(arquivo.getInputStream());
    }

    @GetMapping
    public ResponseEntity<List<ConteudoResumoResponse>> listarConteudos(){
        return ResponseEntity.ok(conteudoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoDetalhadoResponse> buscarPorid(@PathVariable Long id){
        return ResponseEntity.ok(conteudoService.buscarPorId(id));
    }

}
