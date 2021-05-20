package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.entity.CidadeEntity;
import com.desafio.compasso.msclientecidade.exception.CidadeEncontradaException;
import com.desafio.compasso.msclientecidade.mapper.CidadeMapper;
import com.desafio.compasso.msclientecidade.repository.CidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    public Page<CidadeDTO> listarCidades(CidadeDTO cidadeDTO, Pageable pageable){
        log.info("c=CidadeService m=listarCidades, dto={}, pageable={}",cidadeDTO,pageable);

        final CidadeEntity cidadeEntity = CidadeEntity
                .builder()
                .id(cidadeDTO.getId())
                .nome((cidadeDTO.getNome() == null) ? cidadeDTO.getNome() : cidadeDTO.getNome().toUpperCase())
                .estado(cidadeDTO.getEstado())
                .build();

        final Example<CidadeEntity> example = Example.of(cidadeEntity);
        Page<CidadeEntity> cidades = this.cidadeRepository.findAll(example,pageable);

        return cidades.map(it -> this.cidadeMapper.toCidadeDTO(it));
    }

    public CidadeDTO criarCidade(CidadeDTO cidadeDTO){
        log.info("c=CidadeService m=CriarCidadeRequest, dto={}",cidadeDTO);

        if(cidadeRepository.validaExisteCidade(cidadeDTO.getNome().toUpperCase(),cidadeDTO.getEstado().toString().toUpperCase()) > 0) {
            throw new CidadeEncontradaException("A cidade " + cidadeDTO.getNome().toUpperCase() + " já existe no cadastro");
        }

        return this.cidadeMapper.toCidadeDTO(this.cidadeRepository.saveAndFlush(this.cidadeMapper.toCidadeEntity(normalizaDados(cidadeDTO))));
    }

    private CidadeDTO normalizaDados(CidadeDTO cidadeDTO){
        return CidadeDTO
                .builder()
                .id(cidadeDTO.getId())
                .nome(cidadeDTO.getNome().toUpperCase())
                .estado(cidadeDTO.getEstado())
                .build();
    }


}
