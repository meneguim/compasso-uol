package com.desafio.compasso.msclientecidade.web;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.mapper.ClienteMapper;
import com.desafio.compasso.msclientecidade.service.ClienteService;
import com.desafio.compasso.msclientecidade.web.request.AtualizarClienteRequest;
import com.desafio.compasso.msclientecidade.web.request.CriarCidadeRequest;
import com.desafio.compasso.msclientecidade.web.request.CriarClienteRequest;
import com.desafio.compasso.msclientecidade.web.request.ListarClienteRequest;
import com.desafio.compasso.msclientecidade.web.response.ClienteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteMapper clienteMapper;

    @GetMapping
    public Page<ClienteResponse> listarCliente(ListarClienteRequest request,@PageableDefault Pageable pageable){
        log.info("c=ClienteController m=listarCliente, request={}, pageable={}",request,pageable);

        Page<ClienteDTO> listaClientes = this.clienteService.listarClientes(this.clienteMapper.toClienteDTO(request),pageable);

        return listaClientes.map(it -> this.clienteMapper.toClienteResponse(it));
    }

    @PostMapping
    public ClienteResponse criarCliente(@Valid @RequestBody CriarClienteRequest request){
        log.info("c=ClienteController m=criarCliente, request={}",request);
        return clienteMapper.toClienteResponse(clienteService.criarCliente(this.clienteMapper.toClienteDTO(request)));
    }

    @PatchMapping("/{id}/nome")
    public ClienteResponse atualizarNomeCliente(@Valid @PathVariable(value = "id", required = true) Long id,
                                                @Valid @RequestBody AtualizarClienteRequest request){
        log.info("c=ClienteController m=atualizarNomeCliente, Long={} request",id, request);
        return clienteMapper.toClienteResponse(clienteService.atualizarNomeCliente(id,request.getNome()));
    }
}
