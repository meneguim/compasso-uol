package com.desafio.compasso.msclientecidade.web.response;

import com.desafio.compasso.msclientecidade.enums.UfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadeResponse {
    private Long id;
    private String nome;
    private UfEnum estado;
}
