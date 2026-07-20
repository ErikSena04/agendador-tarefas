package com.eriksena.agendador_tarefas.business;

import com.eriksena.agendador_tarefas.business.dtos.TarefasDTO;
import com.eriksena.agendador_tarefas.business.mapper.TarefasConverter;
import com.eriksena.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.eriksena.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.eriksena.agendador_tarefas.infrastructure.repository.TarefasRepository;
import com.eriksena.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class TarefasService {


    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO tarefasDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        tarefasDTO.setEmailUsuario(email);
        TarefasEntity tarefasEntity = tarefasConverter.paraTarefaEntity(tarefasDTO);
        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }
}
