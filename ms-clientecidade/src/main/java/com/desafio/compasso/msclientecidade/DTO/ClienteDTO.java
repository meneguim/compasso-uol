package com.desafio.compasso.msclientecidade.DTO;

import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nomeCompleto;
    private SexoEnum sexo;
    private Date dataNascimento;
    private Integer idade;
    private CidadeDTO cidade;
}
