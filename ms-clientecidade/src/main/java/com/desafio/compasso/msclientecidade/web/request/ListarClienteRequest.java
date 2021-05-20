package com.desafio.compasso.msclientecidade.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListarClienteRequest {
    private Long id;
    private String nome;
}
