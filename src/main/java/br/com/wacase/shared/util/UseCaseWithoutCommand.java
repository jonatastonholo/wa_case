package br.com.wacase.shared.util;

public interface UseCaseWithoutCommand<EVENT_DTO>{
    EVENT_DTO run();
}
