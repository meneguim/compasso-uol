package com.desafio.compasso.msclientecidade.web.request;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
public class CriarClienteRequest {
    private String nomeCompleto;

    private SexoEnum sexo;

    private Date dataNascimento;

    private Integer idade;

    private CidadeRequest cidade;
}
