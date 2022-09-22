package br.com.wacase.infrastructure.repository.user;

import br.com.wacase.business.user.repository.UserRepository;
import br.com.wacase.dto.SaveUserCommandDTO;
import br.com.wacase.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final UserReactiveRepository userReactiveRepository;
    @Override
    public Mono<UserDTO> save(final SaveUserCommandDTO saveUserCommandDTO) {
        return Mono
                .just(saveUserCommandDTO)
                .publishOn(Schedulers.boundedElastic())
                .checkpoint("UserRepositoryImpl::save::" + saveUserCommandDTO.nome(), true)
                .map(UserEntity::toEntity)
                .flatMap(userReactiveRepository::save)
                .map(UserEntity::toUserDTO)
                .doOnError(throwable
                        -> log.error("Falhou ao tentar salvar o usuário {} no banco.", saveUserCommandDTO, throwable))
                ;

    }

    @Override
    public Mono<UserDTO> findById(Long id) {
        return Mono.just(id)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(userReactiveRepository::findById)
                .map(UserEntity::toUserDTO);
    }

    @Override
    public Mono<UserDTO> delete(final UserDTO userDTO) {
        return Mono
                .just(userDTO)
                .map(UserDTO::id)
                .flatMap(userReactiveRepository::deleteById)
                .map(__ -> userDTO)
                ;
    }

    @Override
    public Flux<UserDTO> findAll() {
        return userReactiveRepository
                .findAll()
                .map(UserEntity::toUserDTO);
    }
}
