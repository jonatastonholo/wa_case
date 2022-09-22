package br.com.wacase.business.user.repository;

import br.com.wacase.dto.SaveUserCommandDTO;
import br.com.wacase.dto.UserDTO;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<UserDTO> save(SaveUserCommandDTO saveUserCommandDTO);
    Mono<UserDTO> findById(Long id);

    Mono<UserDTO> delete(UserDTO userDTO);
}
