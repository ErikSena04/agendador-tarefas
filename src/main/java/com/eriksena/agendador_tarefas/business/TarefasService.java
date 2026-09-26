package com.eriksena.agendador_tarefas.business;

import com.eriksena.agendador_tarefas.business.dtos.TarefasDTORecord;
import com.eriksena.agendador_tarefas.business.mapper.TarefasConverter;
import com.eriksena.agendador_tarefas.business.mapper.TarefasUpdateConverter;
import com.eriksena.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.eriksena.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.eriksena.agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.eriksena.agendador_tarefas.infrastructure.repository.TarefasRepository;
import com.eriksena.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TarefasService {


    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTORecord gravarTarefa(String token, TarefasDTORecord tarefasDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        TarefasDTORecord dtoRecord = new TarefasDTORecord(null, tarefasDTO.nomeTarefa(),
                tarefasDTO.descricao(), LocalDateTime.now(), tarefasDTO.dataEvento(), email, null,
                StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefasEntity = tarefasConverter.paraTarefaEntity(dtoRecord);
        return tarefasConverter.paraTarefaDTORecord(tarefasRepository.save(tarefasEntity));
    }

    public List<TarefasDTORecord> buscaTarefasAgendandorPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListaTarefasDTORecord(tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(
                dataInicial, dataFinal,StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTORecord> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);
        return tarefasConverter.paraListaTarefasDTORecord(listaTarefas);
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceAccessException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente " + id, e.getCause());
        }
    }

    public TarefasDTORecord alteraStatus(StatusNotificacaoEnum statusNotificacao, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(statusNotificacao);
            return tarefasConverter.paraTarefaDTORecord(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getMessage(), e);
        }
    }

    public TarefasDTORecord updateTarefas(TarefasDTORecord tarefasDTO, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            tarefasUpdateConverter.updateTarefas(tarefasDTO, entity);
            return tarefasConverter.paraTarefaDTORecord(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getMessage(), e);
        }
    }
}
