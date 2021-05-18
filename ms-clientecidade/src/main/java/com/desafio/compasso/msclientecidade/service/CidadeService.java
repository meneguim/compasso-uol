package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.mapper.CidadeMapper;
import com.desafio.compasso.msclientecidade.repository.CidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    public CidadeDTO buscaPorNome(String nome){
        log.info("c=CidadeService m=findByName, String={}",nome);
        return this.cidadeMapper.toCidadeDTO(cidadeRepository.findByNome(nome.toUpperCase()));
    }


}
