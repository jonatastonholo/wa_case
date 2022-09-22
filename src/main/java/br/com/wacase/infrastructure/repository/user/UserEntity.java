package br.com.wacase.infrastructure.repository.user;

import static java.time.LocalDateTime.now;

import br.com.wacase.dto.SaveUserCommandDTO;
import br.com.wacase.dto.UserDTO;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tb_user")
@Builder
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private Long id;
    private String nome;
    private String documento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public UserDTO toUserDTO() {
        return new UserDTO(
                id,
                nome,
                documento,
                dataCriacao,
                dataAtualizacao
        );
    }

    public static UserEntity toEntity(final SaveUserCommandDTO saveUserCommandDTO) {
        final var dataCriacao = saveUserCommandDTO.dataCriacao() == null
                ? now()
                : saveUserCommandDTO.dataCriacao();

        return UserEntity.builder()
                .id(saveUserCommandDTO.id())
                .nome(saveUserCommandDTO.nome())
                .documento(saveUserCommandDTO.documento())
                .dataCriacao(dataCriacao)
                .dataAtualizacao(now())
                .build();
    }
}
