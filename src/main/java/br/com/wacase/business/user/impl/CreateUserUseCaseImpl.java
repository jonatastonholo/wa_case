package br.com.wacase.business.user.impl;

import br.com.wacase.business.user.CreateUserUseCase;
import br.com.wacase.business.user.repository.UserRepository;
import br.com.wacase.dto.SaveUserCommandDTO;
import br.com.wacase.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDTO> run(final SaveUserCommandDTO saveUserCommandDTO) {
        return Mono
                .just(saveUserCommandDTO)
                .checkpoint("SaveUserUseCase::" + saveUserCommandDTO.nome(), true)
                .doOnNext(dto
                        -> log.info("Salvando o usuário -> {}", dto))
                .flatMap(userRepository::save)
                .doOnNext(dto
                        -> log.info("Usuário {} salvo com sucesso", dto.id()))
                .doOnError(throwable
                        -> log.error("Erro ao tentar salvar o usuário -> {}", saveUserCommandDTO))
                ;
    }

}
