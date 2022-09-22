package br.com.wacase.business.user;

import br.com.wacase.dto.SaveUserCommandDTO;
import br.com.wacase.dto.UserDTO;
import br.com.wacase.shared.util.UseCase;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase extends UseCase<SaveUserCommandDTO, Mono<UserDTO>> {

}
