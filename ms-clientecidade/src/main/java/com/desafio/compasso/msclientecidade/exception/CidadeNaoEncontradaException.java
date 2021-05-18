package com.desafio.compasso.msclientecidade.exception;

public class CidadeNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = -4212084500010191741L;

    public CidadeNaoEncontradaException(String ex) {
        super(ex);
    }
}
