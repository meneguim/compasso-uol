package com.desafio.compasso.msclientecidade.controller;

import com.desafio.compasso.msclientecidade.AbstractTest;
import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import com.desafio.compasso.msclientecidade.service.CidadeService;
import com.desafio.compasso.msclientecidade.web.request.CriarCidadeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class CidadeControllerTest extends AbstractTest {

    public static final String CIDADES_PATH = "/cidades";
    public static final String RANDOM_CIDADE_PREFIX = "CIDADE NUMERO ";

    @Autowired
    private CidadeService service;

    @Test
    @Transactional
    public void listarCidadesVazio() throws Exception{
        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(0));
    }

    @Test
    @Transactional
    public void listarUmaCidade() throws Exception{
        this.criarCidade(CidadeDTO.builder()
                .nome("são paulo")
                .estado(UfEnum.SP)
                .build());

        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nome").value("SÃO PAULO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].estado").value("SP"));

    }

    @Test
    @Transactional
    public void listarDezCidades() throws Exception{
        List<CidadeDTO> listaCidades = this.criarVariasCidadesRandom(10);

        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(10));

    }

    @Test
    @Transactional
    public void listarCidadePorNome() throws Exception{
        List<CidadeDTO> listaCidades = this.criarVariasCidadesRandom(4);

        String nomeCidadeBusca = RANDOM_CIDADE_PREFIX+ "4";

        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH+"?nome="+nomeCidadeBusca))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nome").value(nomeCidadeBusca))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].estado").value("SP"));

    }

    @Test
    @Transactional
    public void listarCidadePorEstado() throws Exception{
        //-- CIDADES DE SP
        this.criarCidade(CidadeDTO.builder().nome("são paulo").estado(UfEnum.SP).build());
        this.criarCidade(CidadeDTO.builder().nome("ubatuba").estado(UfEnum.SP).build());
        this.criarCidade(CidadeDTO.builder().nome("osasco").estado(UfEnum.SP).build());

        //-- CIDADES DO RJ
        this.criarCidade(CidadeDTO.builder().nome("cabo frio").estado(UfEnum.RJ).build());
        this.criarCidade(CidadeDTO.builder().nome("Rio de Janeiro").estado(UfEnum.RJ).build());

        //-- CIDADES DE BH
        this.criarCidade(CidadeDTO.builder().nome("Belo Horizonte").estado(UfEnum.MG).build());


        //-- BUSCANDO SÃO PAULO
        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH+"?estado=SP"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].estado").value("SP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].estado").value("SP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].estado").value("SP"));

        //-- BUSCANDO RIO DE JANEIRO
        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH+"?estado=RJ"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].estado").value("RJ"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].estado").value("RJ"));

        //-- BUSCANDO MINAS GERAIS
        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH+"?estado=MG"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].estado").value("MG"));

    }

    @Test
    @Transactional
    public void criarCidadeComSucesso() throws Exception{
        CriarCidadeRequest request = CriarCidadeRequest.builder()
                .nome("ARACAJU")
                .estado("SE")
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CIDADES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        super.mockMvc.perform(MockMvcRequestBuilders.get(CIDADES_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nome").value("ARACAJU"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].estado").value("SE"));
    }

    @Test
    @Transactional
    public void CriarCidadeComErro() throws Exception{
        CriarCidadeRequest request = CriarCidadeRequest.builder()
                .nome(null)
                .estado("")
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CIDADES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    public void criarCidadeDuplicada() throws Exception{
        this.criarCidade(CidadeDTO.builder().nome("são paulo").estado(UfEnum.SP).build());

        CriarCidadeRequest request = CriarCidadeRequest.builder()
                .nome("são paulo")
                .estado("sp")
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CIDADES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    public void criarCidadeEstadoInvalido() throws Exception{
        CriarCidadeRequest request = CriarCidadeRequest.builder()
                .nome("são paulo")
                .estado("QG")
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CIDADES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
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
