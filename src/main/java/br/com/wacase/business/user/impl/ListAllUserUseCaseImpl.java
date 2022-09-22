package br.com.wacase.business.user.impl;

import br.com.wacase.business.user.ListAllUserUseCase;
import br.com.wacase.business.user.repository.UserRepository;
import br.com.wacase.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListAllUserUseCaseImpl implements ListAllUserUseCase {
    private final UserRepository userRepository;

    @Override
    public Flux<UserDTO> run() {
        return userRepository
                .findAll()
                .doOnError(throwable
                        -> log.error("Falhou ao buscar todos os usuários", throwable))
                ;
    }
}
