package com.desafio.compasso.msclientecidade.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarClienteRequest {
    @NotBlank(message = "Por favor informe o nome do cliente a ser atualizado")
    private String nome;
}
