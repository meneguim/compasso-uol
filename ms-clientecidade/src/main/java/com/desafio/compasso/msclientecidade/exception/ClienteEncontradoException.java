package com.desafio.compasso.msclientecidade.exception;

public class ClienteEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 918060664319112656L;

    public ClienteEncontradoException(String ex) {
        super(ex);
    }
}
