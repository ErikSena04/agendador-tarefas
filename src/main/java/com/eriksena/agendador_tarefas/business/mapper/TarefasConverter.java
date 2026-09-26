package com.eriksena.agendador_tarefas.business.mapper;

import com.eriksena.agendador_tarefas.business.dtos.TarefasDTORecord;
import com.eriksena.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface TarefasConverter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")
    TarefasEntity paraTarefaEntity(TarefasDTORecord tarefaDTO);

    TarefasDTORecord paraTarefaDTORecord(TarefasEntity tarefaEntity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTORecord> tarefasDTO);

    List<TarefasDTORecord> paraListaTarefasDTORecord(List<TarefasEntity> tarefasEntities);
}
