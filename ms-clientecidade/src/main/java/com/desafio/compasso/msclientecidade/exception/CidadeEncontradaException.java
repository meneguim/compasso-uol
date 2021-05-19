package com.desafio.compasso.msclientecidade.exception;

public class CidadeEncontradaException extends RuntimeException{
    private static final long serialVersionUID = 5702255826077178566L;

    public CidadeEncontradaException(String ex) {
        super(ex);
    }
}
