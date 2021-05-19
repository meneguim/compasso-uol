package com.desafio.compasso.msclientecidade.web;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import com.desafio.compasso.msclientecidade.mapper.CidadeMapper;
import com.desafio.compasso.msclientecidade.service.CidadeService;
import com.desafio.compasso.msclientecidade.web.request.ListarCidadesRequest;
import com.desafio.compasso.msclientecidade.web.response.ListarCidadesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeMapper cidadeMapper;

    @GetMapping
    public Page<ListarCidadesResponse> listarCidades (
                @RequestParam(required = false, name="nome") String nome,
                @RequestParam(required = false, name="estado") UfEnum estado,
                @PageableDefault Pageable pageable){
        log.info("c=CidadeController m=listarCidades, nome={}, estado={}, pageable={}",nome,estado,pageable);
        ListarCidadesRequest request = ListarCidadesRequest.builder().nome(nome).estado(estado).build();
        Page<CidadeDTO> listaCidades = this.cidadeService.listarCidades(this.cidadeMapper.toCidadeDTO(request), pageable);
        return listaCidades.map(it -> this.cidadeMapper.toListarCidadeResponse(it));
    }
}
