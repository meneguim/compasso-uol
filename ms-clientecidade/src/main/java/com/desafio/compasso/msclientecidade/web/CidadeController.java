package com.desafio.compasso.msclientecidade.web;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import com.desafio.compasso.msclientecidade.mapper.CidadeMapper;
import com.desafio.compasso.msclientecidade.service.CidadeService;
import com.desafio.compasso.msclientecidade.web.request.CriarCidadeRequest;
import com.desafio.compasso.msclientecidade.web.request.ListarCidadesRequest;
import com.desafio.compasso.msclientecidade.web.response.CidadeResponse;
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
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeMapper cidadeMapper;

    @GetMapping
    public Page<CidadeResponse> listarCidades (ListarCidadesRequest request,@PageableDefault Pageable pageable){
        log.info("c=CidadeController m=listarCidades, request={}, pageable={}",request,pageable);

        Page<CidadeDTO> listaCidades = this.cidadeService.listarCidades(this.cidadeMapper.toCidadeDTO(request), pageable);

        return listaCidades.map(it -> this.cidadeMapper.toCidadeResponse(it));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponse criarCidade(@Valid  @RequestBody CriarCidadeRequest request){
        log.info("c=CidadeController m=criarCidade, request={}",request);
        return this.cidadeMapper.toCidadeResponse(this.cidadeService.criarCidade(this.cidadeMapper.toCidadeDTO(request)));
    }
}
