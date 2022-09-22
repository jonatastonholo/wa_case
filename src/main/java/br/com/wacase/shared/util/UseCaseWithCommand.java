package br.com.wacase.shared.util;

public interface UseCaseWithCommand<COMMAND_DTO, EVENT_DTO>{
    EVENT_DTO run(COMMAND_DTO command_dto);
}
