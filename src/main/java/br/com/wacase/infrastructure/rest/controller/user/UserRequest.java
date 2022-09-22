
package br.com.wacase.infrastructure.rest.controller.user;

import br.com.wacase.dto.SaveUserCommandDTO;

public record UserRequest(
        String nome,
        String documento
) {

    public SaveUserCommandDTO toDTO(final Long id) {
        return new SaveUserCommandDTO(
                id,
                nome,
                documento,
                null
        );
    }
    public SaveUserCommandDTO toDTO() {
        return toDTO(null);
    }
}
