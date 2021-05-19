package com.desafio.compasso.msclientecidade.exception;

public class CidadeNaoInformadaException extends IllegalArgumentException {
    private static final long serialVersionUID = -4212084500010191741L;

    public CidadeNaoInformadaException(String ex) {
        super(ex);
    }
}
