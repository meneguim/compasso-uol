package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.AbstractTest;
import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import com.desafio.compasso.msclientecidade.exception.CidadeEstadoNaoEspecificadoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CidadeServiceTest extends AbstractTest {

    public static final String RANDOM_CIDADE_PREFIX = "CIDADE NUMERO ";

    @Autowired
    CidadeService service;

    @Test
    @Transactional
    public void listarCidadesVazio(){
        //List<CidadeDTO> lista = criarVariasCidadesRandom(1);
        CidadeDTO cidadeDTO = CidadeDTO.builder().build();
        Pageable pageable = Page.empty().getPageable();
        Page<CidadeDTO> dto = this.service.listarCidades(cidadeDTO,pageable);
        assertEquals(dto.getTotalElements(),0);
    }

    @Test
    @Transactional
    public void listarUmaCidade(){
        List<CidadeDTO> lista = criarVariasCidadesRandom(1);
        CidadeDTO cidadeDTO = CidadeDTO.builder().build();
        Pageable pageable = Page.empty().getPageable();
        Page<CidadeDTO> dto = this.service.listarCidades(cidadeDTO,pageable);
        assertEquals(dto.getTotalElements(),1);
    }

    @Test
    @Transactional
    public void listarDezCidades(){
        List<CidadeDTO> lista = criarVariasCidadesRandom(10);
        CidadeDTO cidadeDTO = CidadeDTO.builder().build();
        Pageable pageable = Page.empty().getPageable();
        Page<CidadeDTO> dto = this.service.listarCidades(cidadeDTO,pageable);
        assertEquals(dto.getTotalElements(),10);
    }

    @Test
    @Transactional
    public void listarCidadePorNome(){
        List<CidadeDTO> lista = criarVariasCidadesRandom(1);
        CidadeDTO cidadeDTO = CidadeDTO.builder().nome("CIDADE NUMERO 1").build();
        Pageable pageable = Page.empty().getPageable();
        Page<CidadeDTO> dto = this.service.listarCidades(cidadeDTO,pageable);
        assertEquals(dto.getTotalElements(),1);
    }

    @Test
    @Transactional
    public void listarCidadePorEstado(){
        List<CidadeDTO> lista = criarVariasCidadesRandom(1);
        CidadeDTO cidadeDTO = CidadeDTO.builder().estado(UfEnum.SP).build();
        Pageable pageable = Page.empty().getPageable();
        Page<CidadeDTO> dto = this.service.listarCidades(cidadeDTO,pageable);
        assertEquals(dto.getTotalElements(),1);
    }

    @Test
    @Transactional
    public void criarCidadeEEstadoVazio(){
        CidadeDTO dto = CidadeDTO.builder().build();
        Assertions.assertThrows(CidadeEstadoNaoEspecificadoException.class, () -> {
            CidadeDTO retorno = this.service.criarCidade(dto);
        });
    }

    @Test
    @Transactional
    public void criarCidadeComEstadoVazio(){
        CidadeDTO dto = CidadeDTO.builder().nome("CIDADE NUMERO 1").build();
        Assertions.assertThrows(CidadeEstadoNaoEspecificadoException.class, () -> {
            CidadeDTO retorno = this.service.criarCidade(dto);
        });
    }

    @Test
    @Transactional
    public void criarCidadeEstado(){
        CidadeDTO dto = CidadeDTO.builder().nome("CIDADE NUMERO 1").estado(UfEnum.SP).build();
        CidadeDTO retorno = this.service.criarCidade(dto);
        assertEquals(retorno.getNome(),"CIDADE NUMERO 1");
        assertEquals(retorno.getEstado().toString(),"SP");
    }

    private List<CidadeDTO> criarVariasCidadesRandom(Integer numeroCidades){
        List<CidadeDTO> lista = new ArrayList<CidadeDTO>();
        for(int x=1; x<=numeroCidades; x++){
            lista.add(this.criarCidade(CidadeDTO.builder().nome(RANDOM_CIDADE_PREFIX + x).estado(UfEnum.SP).build()));
        }
        return lista;
    }

    private CidadeDTO criarCidade(CidadeDTO dto){
        return this.service.criarCidade(dto);
    }


}
