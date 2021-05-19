package com.desafio.compasso.msclientecidade.web.request;

import com.desafio.compasso.msclientecidade.enums.UfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListarCidadesRequest {
    private String nome;
    private String estado;
}
