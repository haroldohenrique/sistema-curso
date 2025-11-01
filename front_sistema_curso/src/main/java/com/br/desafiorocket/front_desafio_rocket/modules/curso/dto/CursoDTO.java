package com.br.desafiorocket.front_desafio_rocket.modules.curso.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO {
    private UUID id;

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "A categoria é obrigatória.")
    private String category;

    @NotBlank(message = "O nome do professor(a) é obrigatório.")
    private String professor;

    @NotNull(message = "O campo ativo é obrigatório.")
    private boolean active;
    private LocalDateTime createdAt;
}
