package com.desafio.compasso.msclientecidade.mapper;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.entity.CidadeEntity;
import com.desafio.compasso.msclientecidade.web.request.ListarCidadePorNomeRequest;
import com.desafio.compasso.msclientecidade.web.response.ListarCidadePorNomeResponse;
import org.springframework.stereotype.Component;

@Component
public class CidadeMapper {

    public CidadeEntity toCidadeEntity (CidadeDTO cidadeDTO){
        return CidadeEntity
                .builder()
                .nome(cidadeDTO.getNome())
                .estado(cidadeDTO.getEstado())
                .build();
    }

    public CidadeDTO toCidadeDTO (CidadeEntity cidadeEntity){
        return CidadeDTO
                .builder()
                .id(cidadeEntity.getId())
                .nome(cidadeEntity.getNome())
                .estado(cidadeEntity.getEstado())
                .build();
    }

    public ListarCidadePorNomeResponse toListarCidadePorNomeResponse(CidadeDTO cidadeDTO){
        return ListarCidadePorNomeResponse
                .builder()
                .id(cidadeDTO.getId())
                .nome(cidadeDTO.getNome())
                .estado(cidadeDTO.getEstado())
                .build();
    }
}
