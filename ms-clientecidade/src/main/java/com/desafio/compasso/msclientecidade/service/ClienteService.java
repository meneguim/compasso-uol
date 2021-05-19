package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.exception.ClienteNotFoundException;
import com.desafio.compasso.msclientecidade.mapper.ClienteMapper;
import com.desafio.compasso.msclientecidade.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteMapper clienteMapper;

    public ClienteDTO findByNomeCompleto(String nome){
        log.info("c=ClienteService m=findByNomeCompleto string={}",nome);
        return this.clienteMapper.toClienteDTO(this.clienteRepository.findByNomeCompleto(nome.toUpperCase())
            .orElseThrow(() -> new ClienteNotFoundException("Cliente nome " + nome.toUpperCase() + " não localizado")));
    }

    public ClienteDTO findById(Long id){
        log.info("c=ClienteService m=findById long={}",id);
        return this.clienteMapper.toClienteDTO(this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente id " + id + " não localizado")));
    }

}
