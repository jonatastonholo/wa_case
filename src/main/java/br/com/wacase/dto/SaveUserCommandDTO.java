package br.com.wacase.dto;

import br.com.wacase.shared.exceptions.errors.ValidationException;
import java.time.LocalDateTime;
import org.springframework.util.Assert;

public record SaveUserCommandDTO(
        Long id,
        String nome,
        String documento,
        LocalDateTime dataCriacao
) {

    public SaveUserCommandDTO {
        validate(nome, documento);
    }

    private void validate(final String nome, final String documento) {
        try {
            Assert.hasLength(nome, "Nome é obrigatório");
            Assert.hasLength(documento, "Documento é obrigatório");
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public SaveUserCommandDTO withId(final Long id) {
        return new SaveUserCommandDTO(id, nome, documento, dataCriacao);
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":"
                + id
                + ",\"nome\":\""
                + nome
                + "\""
                + "\"}";
    }

    public SaveUserCommandDTO withDataCriacao(LocalDateTime dataCriacao) {
        return new SaveUserCommandDTO(id, nome, documento, dataCriacao);
    }
}
