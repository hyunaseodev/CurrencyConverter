package com.wirebarley.currencyconverter.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ExchangeRateNotFoundException.class})
    public ResponseEntity<ApiException> handleApiRequestException(ExchangeRateNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ApiException apiException = ApiException.builder()
            .message(e.getMessage())
            .throwable(e)
            .httpStatus(notFound)
            .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
            .build();

        return new ResponseEntity<>(apiException, notFound);
    }
}
