package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.entity.ClienteEntity;
import com.desafio.compasso.msclientecidade.exception.CidadeEncontradaException;
import com.desafio.compasso.msclientecidade.exception.ClienteEncontradoException;
import com.desafio.compasso.msclientecidade.exception.ClienteNaoEncontradoException;
import com.desafio.compasso.msclientecidade.mapper.CidadeMapper;
import com.desafio.compasso.msclientecidade.mapper.ClienteMapper;
import com.desafio.compasso.msclientecidade.repository.CidadeRepository;
import com.desafio.compasso.msclientecidade.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    ClienteMapper clienteMapper;

    @Autowired
    CidadeMapper cidadeMapper;

/*    public ClienteDTO findByNomeCompleto(String nome){
        log.info("c=ClienteService m=findByNomeCompleto string={}",nome);
        return this.clienteMapper.toClienteDTO(this.clienteRepository.findByNomeCompleto(nome.toUpperCase())
            .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nome " + nome.toUpperCase() + " não localizado")));
    }

    public ClienteDTO findById(Long id){
        log.info("c=ClienteService m=findById long={}",id);
        return this.clienteMapper.toClienteDTO(this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente id " + id + " não localizado")));
    }*/

    public Page<ClienteDTO> listarClientes (ClienteDTO clienteDTO, Pageable pageable){
        log.info("c=ClienteService m=listarClientes, dto={}, pageable={}",clienteDTO,pageable);

        final ClienteEntity clienteEntity = ClienteEntity
                .builder()
                .id(clienteDTO.getId())
                .nomeCompleto((clienteDTO.getNomeCompleto() != null) ? clienteDTO.getNomeCompleto().toUpperCase() : null)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase("nomeCompleto")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        final Example<ClienteEntity> example = Example.of(clienteEntity, matcher);
        Page<ClienteEntity> clientes = this.clienteRepository.findAll(example,pageable);

        return clientes.map(it -> this.clienteMapper.toClienteDTO(it));
    }

    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        log.info("c=ClienteService m=criarCliente dto={}",clienteDTO);

        if(this.clienteRepository.findByNomeCompleto(clienteDTO.getNomeCompleto().toUpperCase()).isPresent()){
            throw new ClienteEncontradoException("Cliente nome " + clienteDTO.getNomeCompleto().toUpperCase() + " já está cadastrado");
        };

        CidadeDTO cidadeDTO = this.cidadeMapper.toCidadeDTO(cidadeRepository.findById(clienteDTO.getCidade().getId())
                .orElseThrow(() -> new CidadeEncontradaException("Cidade id " + clienteDTO.getCidade().getId() + " não localizada")));

        clienteDTO.setCidade(cidadeDTO);

        return clienteMapper.toClienteDTO(clienteRepository.saveAndFlush(this.clienteMapper.toClienteEntity(normalizaDados(clienteDTO))));
    }

    private ClienteDTO normalizaDados(ClienteDTO clienteDTO){
        return ClienteDTO
                .builder()
                .id(clienteDTO.getId())
                .nomeCompleto(clienteDTO.getNomeCompleto().toUpperCase())
                .sexo(clienteDTO.getSexo())
                .dataNascimento(clienteDTO.getDataNascimento())
                .idade(clienteDTO.getIdade())
                .cidade(clienteDTO.getCidade())
                .build();
    }

}
