package com.desafio.compasso.msclientecidade.web;

import com.desafio.compasso.msclientecidade.mapper.CidadeMapper;
import com.desafio.compasso.msclientecidade.service.CidadeService;
import com.desafio.compasso.msclientecidade.web.request.ListarCidadePorNomeRequest;
import com.desafio.compasso.msclientecidade.web.response.ListarCidadePorNomeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeMapper cidadeMapper;

    @GetMapping()
    public ListarCidadePorNomeResponse findByName (@RequestBody ListarCidadePorNomeRequest listarCidadePorNome){
        log.info("c=CidadeController m=ListarCidadePorNomeResponse, request={}",listarCidadePorNome);
        return this.cidadeMapper.toListarCidadePorNomeResponse(cidadeService.buscaPorNome(listarCidadePorNome.getNome()));
    }
}
