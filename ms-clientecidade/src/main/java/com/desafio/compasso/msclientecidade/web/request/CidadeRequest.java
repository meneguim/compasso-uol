package com.desafio.compasso.msclientecidade.web.request;

import com.desafio.compasso.msclientecidade.enums.UfEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CidadeRequest {
    private Long id;
    private String nome;
    private UfEnum estado;
}
