package com.desafio.compasso.msclientecidade.exception;

public class ClienteNaoEncontradoException extends RuntimeException{
    private static final long serialVersionUID = 1824703709266285665L;

    public ClienteNaoEncontradoException(String ex) {
        super(ex);
    }
}
