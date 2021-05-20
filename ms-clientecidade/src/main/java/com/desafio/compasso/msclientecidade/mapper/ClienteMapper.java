package com.desafio.compasso.msclientecidade.mapper;

import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.entity.ClienteEntity;
import com.desafio.compasso.msclientecidade.web.request.CriarClienteRequest;
import com.desafio.compasso.msclientecidade.web.request.ListarClienteRequest;
import com.desafio.compasso.msclientecidade.web.response.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    @Autowired
    CidadeMapper cidadeMapper;

    public ClienteDTO toClienteDTO (ClienteEntity clienteEntity){
        return ClienteDTO
                .builder()
                .id(clienteEntity.getId())
                .nomeCompleto(clienteEntity.getNomeCompleto())
                .sexo(clienteEntity.getSexo())
                .dataNascimento(clienteEntity.getDataNascimento())
                .idade(clienteEntity.getIdade())
                .cidade(this.cidadeMapper.toCidadeDTO(clienteEntity.getCidade()))
                .build();
    }

    public ClienteEntity toClienteEntity (ClienteDTO clienteDTO){
        return ClienteEntity
                .builder()
                .id(clienteDTO.getId())
                .nomeCompleto(clienteDTO.getNomeCompleto())
                .sexo(clienteDTO.getSexo())
                .dataNascimento(clienteDTO.getDataNascimento())
                .idade(clienteDTO.getIdade())
                .cidade(this.cidadeMapper.toCidadeEntity(clienteDTO.getCidade()))
                .build();
    }

    public ClienteDTO toClienteDTO (CriarClienteRequest clienteRequest){
        return ClienteDTO
                .builder()
                .nomeCompleto(clienteRequest.getNomeCompleto())
                .sexo(clienteRequest.getSexo())
                .dataNascimento(clienteRequest.getDataNascimento())
                .idade(clienteRequest.getIdade())
                .cidade(this.cidadeMapper.toCidadeDTO(clienteRequest.getCidade()))
                .build();
    }

    public ClienteDTO toClienteDTO (ListarClienteRequest clienteRequest){
        return ClienteDTO
                .builder()
                .id(clienteRequest.getId())
                .nomeCompleto(clienteRequest.getNome())
                .build();
    }

    public ClienteResponse toClienteResponse (ClienteDTO clienteDTO){
        return ClienteResponse
                .builder()
                .id(clienteDTO.getId())
                .nomeCompleto(clienteDTO.getNomeCompleto())
                .sexo(clienteDTO.getSexo())
                .dataNascimento(clienteDTO.getDataNascimento())
                .idade(clienteDTO.getIdade())
                .cidade(this.cidadeMapper.toCidadeResponse(clienteDTO.getCidade()))
                .build();
    }



}
