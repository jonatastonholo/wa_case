package br.com.wacase.business.user.impl;

import br.com.wacase.business.user.UpdateUserUseCase;
import br.com.wacase.business.user.repository.UserRepository;
import br.com.wacase.dto.SaveUserCommandDTO;
import br.com.wacase.dto.UserDTO;
import br.com.wacase.shared.exceptions.errors.NotFoundException;
import br.com.wacase.shared.exceptions.errors.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDTO> run(final SaveUserCommandDTO saveUserCommandDTO) {
        return Mono
                .just(saveUserCommandDTO)
                .checkpoint("UpdateUserUseCase::" + saveUserCommandDTO.id(), true)
                .doOnNext(this::validate)
                .doOnNext(dto
                        -> log.info("Salvando o usuário -> {}", dto))
                .map(SaveUserCommandDTO::id)
                .flatMap(userRepository::findById)
                .switchIfEmpty(Mono.error(new NotFoundException("Usuário " + saveUserCommandDTO.id() + " não encontrado")))
                .map(userDTO -> saveUserCommandDTO.withDataCriacao(userDTO.dataCriacao()))
                .flatMap(userRepository::save)
                .doOnNext(dto
                        -> log.info("Usuário {} salvo com sucesso", dto.id()))
                .doOnError(throwable
                        -> log.error("Erro ao tentar salvar o usuário -> {}", saveUserCommandDTO))
                ;
    }

    private void validate(SaveUserCommandDTO saveUserCommandDTO) {
        try {
            Assert.notNull(saveUserCommandDTO.id(), "O id do usuário é obrigatório");
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }

    }

}
