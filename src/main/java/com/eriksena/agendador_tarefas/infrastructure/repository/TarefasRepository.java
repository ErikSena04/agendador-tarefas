package com.eriksena.agendador_tarefas.infrastructure.repository;

import com.eriksena.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.eriksena.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {

    List<TarefasEntity> findByDataEventoBetweenAndStatusNotificacaoEnum(LocalDateTime dataIncial,
                                                                        LocalDateTime dataFinal,
                                                                        StatusNotificacaoEnum statusNotificacaoEnum);

    List<TarefasEntity> findByEmailUsuario(String email);
}
