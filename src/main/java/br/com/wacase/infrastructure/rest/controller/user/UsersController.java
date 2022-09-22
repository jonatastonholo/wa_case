package br.com.wacase.infrastructure.rest.controller.user;

import br.com.wacase.business.user.CreateUserUseCase;
import br.com.wacase.business.user.DeleteUserUseCase;
import br.com.wacase.business.user.ListAllUserUseCase;
import br.com.wacase.business.user.RetriveUserUseCase;
import br.com.wacase.business.user.UpdateUserUseCase;
import br.com.wacase.shared.exceptions.errors.NoContentException;
import br.com.wacase.shared.exceptions.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UsersController {
    private final CreateUserUseCase createUserUseCase;
    private final RetriveUserUseCase retriveUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ListAllUserUseCase listAllUserUseCase;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> detail(@PathVariable(value = "id") final Long userId) {
        return Mono.just(userId)
                .flatMap(retriveUserUseCase::run)
                .switchIfEmpty(Mono.error(new NotFoundException("Usuário " + userId + " não encontrado")))
                .map(UserResponse::from);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserResponse> listAll() {
        return listAllUserUseCase.run()
                .switchIfEmpty(Mono.error(new NoContentException()))
                .map(UserResponse::from);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> create(@RequestBody final UserRequest userRequest) {
        return Mono
                .just(userRequest)
                .map(UserRequest::toDTO)
                .flatMap(createUserUseCase::run)
                .map(UserResponse::from);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> update(
            @PathVariable(value = "id") final Long userId,
            @RequestBody final UserRequest userRequest) {
        return Mono
                .just(userId)
                .map(userRequest::toDTO)
                .flatMap(updateUserUseCase::run)
                .map(UserResponse::from);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> update(
            @PathVariable(value = "id") final Long userId) {
        return Mono
                .just(userId)
                .flatMap(deleteUserUseCase::run)
                .map(UserResponse::from);
    }

}
