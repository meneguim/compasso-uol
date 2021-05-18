package com.desafio.compasso.msclientecidade.web.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ListarCidadePorNomeRequest {
    @NotNull(message = "Por favor informe o nome da cidade as ser consultada")
    private String nome;
}
