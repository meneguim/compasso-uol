package com.desafio.compasso.msclientecidade.web.advice;

import com.desafio.compasso.msclientecidade.exception.CidadeEncontradaException;
import com.desafio.compasso.msclientecidade.exception.ClienteEncontradoException;
import com.desafio.compasso.msclientecidade.exception.ClienteNaoEncontradoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.ControllerAdvice
public class Advice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<DetalheErro> errors = ex.getBindingResult().getFieldErrors()
                .stream().map( it -> new DetalheErro(it.getField(), it.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(createExceptionResponse(ex, request, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CidadeEncontradaException.class)
    public final ResponseEntity<ExceptionResponse> handleCidadeEncontradaException(CidadeEncontradaException ex, WebRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public final ResponseEntity<ExceptionResponse> handleClienteNotFoundException(ClienteNaoEncontradoException ex, WebRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteEncontradoException.class)
    public final ResponseEntity<ExceptionResponse> handleClienteEncontradoException(ClienteEncontradoException ex, WebRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionResponse createExceptionResponse(Exception ex, WebRequest request){
        return createExceptionResponse(ex, request, null);
    }

    private ExceptionResponse createExceptionResponse(Exception ex, WebRequest request, List<DetalheErro> errors){
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .errors(errors)
                .build();
    }
}
