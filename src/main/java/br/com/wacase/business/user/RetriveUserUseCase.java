package br.com.wacase.business.user;

import br.com.wacase.dto.UserDTO;
import br.com.wacase.shared.util.UseCaseWithCommand;
import reactor.core.publisher.Mono;

public interface RetriveUserUseCase extends UseCaseWithCommand<Long, Mono<UserDTO>> {

}
