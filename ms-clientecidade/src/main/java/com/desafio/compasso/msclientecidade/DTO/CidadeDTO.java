package com.desafio.compasso.msclientecidade.DTO;

import com.desafio.compasso.msclientecidade.enums.UfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CidadeDTO {
    private Long id;
    private String nome;
    private UfEnum estado;
}
