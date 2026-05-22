package com.eriksena.agendador_tarefas.business.dtos;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioDTO {

    private String email;
    private String senha;

}
