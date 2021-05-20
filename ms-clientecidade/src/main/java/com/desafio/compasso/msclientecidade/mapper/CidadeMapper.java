package com.desafio.compasso.msclientecidade.mapper;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.entity.CidadeEntity;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import com.desafio.compasso.msclientecidade.web.request.CidadeRequest;
import com.desafio.compasso.msclientecidade.web.request.CriarCidadeRequest;
import com.desafio.compasso.msclientecidade.web.request.ListarCidadesRequest;
import com.desafio.compasso.msclientecidade.web.response.CidadeResponse;
import org.springframework.stereotype.Component;

@Component
public class CidadeMapper {

    public CidadeEntity toCidadeEntity (CidadeDTO cidadeDTO){
        return CidadeEntity
                .builder()
                .id(cidadeDTO.getId())
                .nome(cidadeDTO.getNome())
                .estado(cidadeDTO.getEstado())
                .build();
    }

    public CidadeResponse toCidadeResponse(CidadeDTO cidadeDTO){
        return CidadeResponse
                .builder()
                .id(cidadeDTO.getId())
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

    public CidadeDTO toCidadeDTO (ListarCidadesRequest listarCidadesRequest){
            return CidadeDTO
                    .builder()
                    .nome(listarCidadesRequest.getNome())
                    .estado(preparaEnum(listarCidadesRequest.getEstado()))
                    .build();
    }

    public CidadeDTO toCidadeDTO(CriarCidadeRequest criarCidadeRequest){
        return CidadeDTO
                .builder()
                .nome(criarCidadeRequest.getNome())
                .estado(UfEnum.valueOf(criarCidadeRequest.getEstado().toUpperCase()))
                .build();
    }

    public CidadeDTO toCidadeDTO(CidadeRequest cidadeRequest){
        return CidadeDTO
                .builder()
                .id(cidadeRequest.getId())
                .nome(cidadeRequest.getNome())
                .estado(cidadeRequest.getEstado())
                .build();
    }

    private UfEnum preparaEnum(String enums) {
        if(enums != null) {
            return UfEnum.valueOf(enums.toUpperCase());
        }
        return null;
    }

}
