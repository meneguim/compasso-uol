package com.desafio.compasso.msclientecidade.web.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalheErro {
    private String campo;
    private String mensagem;
}
