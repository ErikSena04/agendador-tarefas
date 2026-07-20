package com.eriksena.agendador_tarefas.business.mapper;

import com.eriksena.agendador_tarefas.business.dtos.TarefasDTO;
import com.eriksena.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO tarefaDTO);

    TarefasDTO paraTarefaDTO(TarefasEntity tarefaEntity);
}
