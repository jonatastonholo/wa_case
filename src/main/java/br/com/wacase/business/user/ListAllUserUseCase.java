package br.com.wacase.business.user;

import br.com.wacase.dto.UserDTO;
import br.com.wacase.shared.util.UseCaseWithoutCommand;
import reactor.core.publisher.Flux;

public interface ListAllUserUseCase extends UseCaseWithoutCommand<Flux<UserDTO>> {

}
