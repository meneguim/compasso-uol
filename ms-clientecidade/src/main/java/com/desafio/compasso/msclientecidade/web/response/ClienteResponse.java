package com.desafio.compasso.msclientecidade.web.response;

import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nomeCompleto;
    private SexoEnum sexo;
    private Date dataNascimento;
    private Integer idade;
    private CidadeResponse cidade;
}
