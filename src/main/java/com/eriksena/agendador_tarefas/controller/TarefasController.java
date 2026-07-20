package com.eriksena.agendador_tarefas.controller;

import com.eriksena.agendador_tarefas.business.TarefasService;
import com.eriksena.agendador_tarefas.business.dtos.TarefasDTO;
import com.eriksena.agendador_tarefas.infrastructure.repository.TarefasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor

public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO tarefasDTO,
                                                    @RequestHeader("Authorization") String token) {
        return  ResponseEntity.ok(tarefasService.gravarTarefa(token, tarefasDTO));
    }
}
