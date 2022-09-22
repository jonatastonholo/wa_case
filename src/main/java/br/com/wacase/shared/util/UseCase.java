package br.com.wacase.shared.util;

public interface UseCase<COMMAND_DTO, EVENT_DTO>{
    EVENT_DTO run(COMMAND_DTO command_dto);
}
