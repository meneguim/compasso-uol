package com.desafio.compasso.msclientecidade.controller;

import com.desafio.compasso.msclientecidade.AbstractTest;
import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import com.desafio.compasso.msclientecidade.service.CidadeService;
import com.desafio.compasso.msclientecidade.service.ClienteService;
import com.desafio.compasso.msclientecidade.web.request.AtualizarClienteRequest;
import com.desafio.compasso.msclientecidade.web.request.CriarClienteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteControllerTest extends AbstractTest {

    public static final String CLIENTE_PATH = "/clientes";
    public static final String RANDOM_CLIENT_PREFIX = "NOME NUMERO ";

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CidadeService cidadeService;

    @Test
    @Transactional
    public void listarClientesVazio() throws Exception{
        super.mockMvc.perform(MockMvcRequestBuilders.get(CLIENTE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(0));
    }

    @Test
    @Transactional
    public void listarUmCliente() throws Exception{
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        this.criarCliente(ClienteDTO.builder()
                .nomeCompleto("Cliente 1")
                .sexo(SexoEnum.MASCULINO)
                .dataNascimento(criarData("01/01/2000"))
                .cidade(cidadeDTO)
                .build());

        super.mockMvc.perform(MockMvcRequestBuilders.get(CLIENTE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nomeCompleto").value("CLIENTE 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].sexo").value("MASCULINO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].dataNascimento").value("01-01-2000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].idade").value("21"));
    }

    @Test
    @Transactional
    public void listarDezClientes() throws Exception{
        List<ClienteDTO> listaClientes = this.criarVariosClientesRandom(10);

        super.mockMvc.perform(MockMvcRequestBuilders.get(CLIENTE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(10));
    }

    @Test
    @Transactional
    public void listarClientesPorNome() throws Exception{
        List<ClienteDTO> listaClientes = this.criarVariosClientesRandom(4);

        String nomeClienteBusca = RANDOM_CLIENT_PREFIX+ "4";

        super.mockMvc.perform(MockMvcRequestBuilders.get(CLIENTE_PATH+"?nome="+nomeClienteBusca))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nomeCompleto").value(nomeClienteBusca));
    }

    @Test
    @Transactional
    public void criarClienteComSucesso() throws Exception{
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        Integer anoAtual = Integer.parseInt(formato.format(System.currentTimeMillis()));

        CriarClienteRequest request = CriarClienteRequest.builder()
                .nomeCompleto("Cliente nome 1")
                .sexo(SexoEnum.MASCULINO)
                .dataNascimento(criarData("01/01/" + anoAtual))
                .idCidade(cidadeDTO.getId())
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CLIENTE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    public void criarClienteComErro() throws Exception{
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        Integer anoAtual = Integer.parseInt(formato.format(System.currentTimeMillis()));

        CriarClienteRequest request = CriarClienteRequest.builder()
                .nomeCompleto("")
                .sexo(null)
                .dataNascimento(criarData("01/01/" + anoAtual))
                .idCidade(cidadeDTO.getId())
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CLIENTE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    public void criarClienteDuplicado() throws Exception{
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        Integer anoAtual = Integer.parseInt(formato.format(System.currentTimeMillis()));

        CriarClienteRequest request = CriarClienteRequest.builder()
                .nomeCompleto("Cliente nome 1")
                .sexo(SexoEnum.MASCULINO)
                .dataNascimento(criarData("01/01/" + anoAtual))
                .idCidade(cidadeDTO.getId())
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CLIENTE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        super.mockMvc.perform(MockMvcRequestBuilders.post(CLIENTE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    public void criarClienteIdadeNegativa() throws Exception{
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        Integer anoAtual = Integer.parseInt(formato.format(System.currentTimeMillis()));

        CriarClienteRequest request = CriarClienteRequest.builder()
                .nomeCompleto("Cliente nome 1")
                .sexo(SexoEnum.MASCULINO)
                .dataNascimento(criarData("01/01/" + anoAtual + 1))
                .idCidade(cidadeDTO.getId())
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.post(CLIENTE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    public void atualizarNomeCliente() throws Exception{
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        ClienteDTO clienteDTO = clienteService.criarCliente(ClienteDTO.builder()
                .nomeCompleto("Cliente nome 1")
                .sexo(SexoEnum.MASCULINO)
                .dataNascimento(criarData("01/01/2000"))
                .idade(null)
                .cidade(cidadeDTO)
                .build());

        AtualizarClienteRequest request = AtualizarClienteRequest.builder()
                .nome("Cliente nome 300")
                .build();

        super.mockMvc.perform(MockMvcRequestBuilders.patch(CLIENTE_PATH+ "/" + clienteDTO.getId() + "/nome")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void excluirCliente() throws Exception{
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        ClienteDTO clienteDTO = clienteService.criarCliente(ClienteDTO.builder()
                .nomeCompleto("Cliente nome 1")
                .sexo(SexoEnum.MASCULINO)
                .dataNascimento(criarData("01/01/2000"))
                .idade(null)
                .cidade(cidadeDTO)
                .build());

        super.mockMvc.perform(MockMvcRequestBuilders.delete(CLIENTE_PATH+ "/" + clienteDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private List<ClienteDTO> criarVariosClientesRandom(Integer numeroClientes) throws ParseException {
        CidadeDTO cidadeDTO = cidadeService.criarCidade(CidadeDTO
                .builder()
                .nome("SÃO PAULO")
                .estado(UfEnum.SP)
                .build());

        List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
        for(int x=1;x<=numeroClientes; x++){
            lista.add(this.criarCliente(ClienteDTO
                    .builder()
                    .nomeCompleto(RANDOM_CLIENT_PREFIX + x)
                    .sexo(SexoEnum.MASCULINO)
                    .dataNascimento(criarData("01/01/2000"))
                    .idade(21)
                    .cidade(cidadeDTO)
                    .build()));
        }
        return lista;
    }

    private ClienteDTO criarCliente(ClienteDTO dto) {return this.clienteService.criarCliente(dto); }

    private Date criarData (String data) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.parse(data);
    }


}
