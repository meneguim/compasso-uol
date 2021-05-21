package com.desafio.compasso.msclientecidade.web.response;

import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nomeCompleto;
    private SexoEnum sexo;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dataNascimento;
    private Integer idade;
    private CidadeResponse cidade;
}
