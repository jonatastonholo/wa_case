package br.com.wacase.infrastructure.rest.controller.user;

import br.com.wacase.dto.UserDTO;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String nome,
        String documento,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {

    public static UserResponse from(final UserDTO userDTO) {
        return new UserResponse(
                userDTO.id(),
                userDTO.nome(),
                userDTO.documento(),
                userDTO.dataCriacao(),
                userDTO.dataAtualizacao()
        );
    }
}
