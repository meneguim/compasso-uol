package com.desafio.compasso.msclientecidade.web.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CriarCidadeRequest {
    @NotEmpty(message = "Por favor informe o nome da cidade a ser inserida")
    private String nome;

    @NotEmpty(message = "Por favor informe o estado ao qual a cidade pertence")
    private String estado;
}
