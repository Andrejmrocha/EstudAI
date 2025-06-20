package com.EstudAI.API.repository;

import com.EstudAI.API.domain.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
}
