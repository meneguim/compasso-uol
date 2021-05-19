package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.exception.ClienteEncontradoException;
import com.desafio.compasso.msclientecidade.exception.ClienteNaoEncontradoException;
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
            .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nome " + nome.toUpperCase() + " não localizado")));
    }

    public ClienteDTO findById(Long id){
        log.info("c=ClienteService m=findById long={}",id);
        return this.clienteMapper.toClienteDTO(this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente id " + id + " não localizado")));
    }

    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        log.info("c=ClienteService m=criarCliente dto={}",clienteDTO);

        if(this.clienteRepository.findByNomeCompleto(clienteDTO.getNomeCompleto().toUpperCase()).isPresent()){
            throw new ClienteEncontradoException("Cliente nome " + clienteDTO.getNomeCompleto().toUpperCase() + " já está cadastrado");
        };

        return clienteMapper.toClienteDTO(clienteRepository.saveAndFlush(this.clienteMapper.toClienteEntity(clienteDTO)));
    }

}
