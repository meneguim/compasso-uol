package com.desafio.compasso.msclientecidade.web;

import com.desafio.compasso.msclientecidade.mapper.ClienteMapper;
import com.desafio.compasso.msclientecidade.service.ClienteService;
import com.desafio.compasso.msclientecidade.web.response.ClienteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteMapper clienteMapper;

    @GetMapping
    public ClienteResponse findByIdNome(@RequestParam(required = false, name="nome") String nome,
                                        @RequestParam(required = false, name="id") Long id){
        log.info("c=ClienteController m=findByNomeCompleto String={} Long={}",nome,id);
        if(nome != null){
            return clienteMapper.toClienteResponse(clienteService.findByNomeCompleto(nome));
        } else if (id != null) {
            return clienteMapper.toClienteResponse(clienteService.findById(id));
        }
        return null;
    }

}
