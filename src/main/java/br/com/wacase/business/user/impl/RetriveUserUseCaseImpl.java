package br.com.wacase.business.user.impl;

import br.com.wacase.business.user.RetriveUserUseCase;
import br.com.wacase.business.user.repository.UserRepository;
import br.com.wacase.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetriveUserUseCaseImpl implements RetriveUserUseCase {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDTO> run(final Long userId) {
        return Mono
                .just(userId)
                .checkpoint("DetailUserUseCase::" + userId, true)
                .doOnNext(id -> log.info("Buscando pelo usuário {}", id))
                .flatMap(userRepository::findById)
                .doOnError(throwable
                        -> log.error("Falhou ao buscar o usuário {}", userId, throwable))
                ;
    }
}
