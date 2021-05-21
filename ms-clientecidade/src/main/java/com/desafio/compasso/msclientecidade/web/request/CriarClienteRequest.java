package com.desafio.compasso.msclientecidade.web.request;

import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriarClienteRequest {

    @NotBlank(message = "Por favor informe o nome completo a ser inserido")
    private String nomeCompleto;

    @NotNull(message = "Por favor informe o sexo a ser inserido")
    private SexoEnum sexo;

    @NotNull(message = "Por favor informe a data de nascimento a ser inserida")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataNascimento;

    @NotNull(message = "Por favor informe o id da cidade a ser associada ao cliente")
    private Long idCidade;
}
