package br.com.wacase.dto;

import br.com.wacase.shared.exceptions.errors.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaveUserCommandDTOTest {
    @Test
    @DisplayName("Dado um dto válido, não deve lançar exception")
    void validate01() {
        Assertions.assertDoesNotThrow(()
                -> new SaveUserCommandDTO("Jonatas", "12354"));
    }

    @Test
    @DisplayName("Dado um dto sem nome, deve lançar exception")
    void validate02() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, ()
                -> new SaveUserCommandDTO(null, "12354"));

        Assertions.assertEquals("Nome é obrigatório", exception.message());
    }

    @Test
    @DisplayName("Dado um dto sem nome, deve lançar exception")
    void validate03() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, ()
                -> new SaveUserCommandDTO("Jonatas", null));

        Assertions.assertEquals("Documento é obrigatório", exception.message());
    }
}