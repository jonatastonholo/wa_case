package br.com.wacase.business.user.impl;

import br.com.wacase.business.user.DeleteUserUseCase;
import br.com.wacase.business.user.repository.UserRepository;
import br.com.wacase.dto.UserDTO;
import br.com.wacase.shared.exceptions.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDTO> run(final Long id) {
        return Mono
                .just(id)
                .checkpoint("DeleteUserUseCase::" + id, true)
                .doOnNext(dto
                        -> log.info("Deletando o usuário -> {}", dto))
                .flatMap(userRepository::findById)
                .switchIfEmpty(Mono.error(new NotFoundException("Usuário " + id + " não encontrado")))
                .flatMap(userRepository::delete)
                .doOnNext(dto
                        -> log.info("Usuário {} removido com sucesso", id))
                .doOnError(throwable
                        -> log.error("Erro ao tentar remover o usuário -> {}", id))
                ;
    }
}
