package br.com.wacase.dto;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String nome,
        String documento,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {}
