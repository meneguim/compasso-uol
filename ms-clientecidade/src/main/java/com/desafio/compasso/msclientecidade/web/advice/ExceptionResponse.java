package com.desafio.compasso.msclientecidade.web.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 1516925017703049603L;
    private Date timestamp;
    private String message;
    private String details;
    private List<DetalheErro> errors;
}
