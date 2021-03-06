package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.entity.ClienteEntity;
import com.desafio.compasso.msclientecidade.exception.*;
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

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

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

        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        Integer anoNascimento = Integer.parseInt(formato.format(clienteDTO.getDataNascimento()));
        Integer anoAtual = Integer.parseInt(formato.format(System.currentTimeMillis()));

        if(this.clienteRepository.findByNomeCompleto(clienteDTO.getNomeCompleto().toUpperCase()).isPresent()){
            throw new ClienteEncontradoException("Cliente nome " + clienteDTO.getNomeCompleto().toUpperCase() + " j?? est?? cadastrado");
        };

        if((anoAtual - anoNascimento) < 0){
            throw new ClienteIdadeNegativaException("A data de nascimento deve ser menor ou igual ao ano de " + anoAtual);
        } else {
            clienteDTO.setIdade(anoAtual - anoNascimento);
        }

        CidadeDTO cidadeDTO = this.cidadeMapper.toCidadeDTO(cidadeRepository.findById(clienteDTO.getCidade().getId())
                .orElseThrow(() -> new CidadeEncontradaException("Cidade id " + clienteDTO.getCidade().getId() + " n??o localizada")));

        clienteDTO.setCidade(cidadeDTO);

        return this.clienteMapper.toClienteDTO(clienteRepository.saveAndFlush(this.clienteMapper.toClienteEntity(normalizaDados(clienteDTO))));
    }

    public ClienteDTO atualizarNomeCliente(Long id, String nomeCompleto){
        log.info("c=ClienteService m=atualizarNomeCliente long={} string={} ",id,nomeCompleto);

        final ClienteDTO clienteDTO = this.clienteMapper.toClienteDTO(this.clienteRepository.findById(id)
                .orElseThrow((() -> new ClienteNaoEncontradoException("Cliente id " + id + " n??o localizado"))));

        clienteDTO.setNomeCompleto(nomeCompleto.toUpperCase());

        return this.clienteMapper.toClienteDTO(this.clienteRepository.saveAndFlush(this.clienteMapper.toClienteEntity(clienteDTO)));
    }

    public void deletarCliente(Long id){
        log.info("c=ClienteService m=deletarCliente long={}",id);

        final ClienteDTO clienteDTO = this.clienteMapper.toClienteDTO(this.clienteRepository.findById(id)
                .orElseThrow((() -> new ClienteNaoEncontradoException("Cliente id " + id + " n??o localizado"))));

        clienteRepository.deleteById(id);
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
