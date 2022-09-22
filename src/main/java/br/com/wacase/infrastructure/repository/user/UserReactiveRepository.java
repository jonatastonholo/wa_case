package br.com.wacase.infrastructure.repository.user;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserReactiveRepository extends R2dbcRepository<UserEntity, Long> {

}
