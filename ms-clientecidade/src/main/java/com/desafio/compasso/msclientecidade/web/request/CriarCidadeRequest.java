package com.desafio.compasso.msclientecidade.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriarCidadeRequest {
    @NotBlank(message = "Por favor informe o nome da cidade a ser inserida")
    private String nome;

    @NotBlank(message = "Por favor informe o estado ao qual a cidade pertence")
    private String estado;
}