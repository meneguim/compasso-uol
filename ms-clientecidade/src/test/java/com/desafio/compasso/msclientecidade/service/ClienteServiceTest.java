package com.desafio.compasso.msclientecidade.service;

import com.desafio.compasso.msclientecidade.AbstractTest;
import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.DTO.ClienteDTO;
import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteServiceTest extends AbstractTest {

    public static final String RANDOM_CLIENT_PREFIX = "NOME NUMERO ";

    @Autowired
    ClienteService clienteService;

    @Autowired
    CidadeService cidadeService;

    @Test
    @Transactional
    public void listarClienteVazio(){
        ClienteDTO clienteDTO = ClienteDTO.builder().build();
        Pageable pageable = Page.empty().getPageable();
        Page<ClienteDTO> dto = this.clienteService.listarClientes(clienteDTO,pageable);
        assertEquals(dto.getTotalElements(), 0);
    }

    @Test
    @Transactional
    public void listarUmCliente() throws ParseException {
        List<ClienteDTO> lista = criarVariosClientesRandom(1);
        ClienteDTO clienteDTO = ClienteDTO.builder().build();
        Pageable pageable = Page.empty().getPageable();
        Page<ClienteDTO> dto = this.clienteService.listarClientes(clienteDTO,pageable);
        assertEquals(dto.getTotalElements(), 1);
    }

    @Test
    @Transactional
    public void listarDezCliente() throws ParseException {
        List<ClienteDTO> lista = criarVariosClientesRandom(10);
        ClienteDTO clienteDTO = ClienteDTO.builder().build();
        Pageable pageable = Page.empty().getPageable();
        Page<ClienteDTO> dto = this.clienteService.listarClientes(clienteDTO,pageable);
        assertEquals(dto.getTotalElements(), 10);
    }

    @Test
    @Transactional
    public void listarClientePorNomeCompleto() throws ParseException {
        List<ClienteDTO> lista = criarVariosClientesRandom(10);
        ClienteDTO clienteDTO = ClienteDTO.builder().nomeCompleto("NOME NUMERO 1").build();
        Pageable pageable = Page.empty().getPageable();
        Page<ClienteDTO> dto = this.clienteService.listarClientes(clienteDTO,pageable);
        assertEquals(dto.getTotalElements(), 2);
    }

    @Test
    @Transactional
    public void listarClientePorId() throws ParseException {
        CidadeDTO cidadeDTO = this.cidadeService.criarCidade(CidadeDTO.builder()
                .nome("CIDADE NUMERO 1")
                .estado(UfEnum.SP)
                .build());
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .nomeCompleto("CLIENTE NUMERO 1")
                .dataNascimento(criarData("01/01/2000"))
                .sexo(SexoEnum.MASCULINO)
                .cidade(cidadeDTO)
                .build();
        ClienteDTO retorno = this.clienteService.criarCliente(clienteDTO);

        ClienteDTO clienteDTOBusca = ClienteDTO.builder().id(retorno.getId()).build();
        Pageable pageable = Page.empty().getPageable();
        Page<ClienteDTO> dto = this.clienteService.listarClientes(clienteDTOBusca,pageable);
        assertEquals(dto.getTotalElements(), 1);
    }

    @Test
    @Transactional
    public void criarCliente() throws ParseException {
        CidadeDTO cidadeDTO = this.cidadeService.criarCidade(CidadeDTO.builder()
            .nome("CIDADE NUMERO 1")
            .estado(UfEnum.SP)
            .build());
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .nomeCompleto("CLIENTE NUMERO 1")
                .dataNascimento(criarData("01/01/2000"))
                .sexo(SexoEnum.MASCULINO)
                .cidade(cidadeDTO)
                .build();
        ClienteDTO retorno = this.clienteService.criarCliente(clienteDTO);
        assertEquals(retorno.getId(),1l);
        assertEquals(retorno.getNomeCompleto(),"CLIENTE NUMERO 1");
    }

    @Test
    @Transactional
    public void atualizarNomeCliente() throws ParseException {
        CidadeDTO cidadeDTO = this.cidadeService.criarCidade(CidadeDTO.builder()
                .nome("CIDADE NUMERO 1")
                .estado(UfEnum.SP)
                .build());
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .nomeCompleto("CLIENTE NUMERO 1")
                .dataNascimento(criarData("01/01/2000"))
                .sexo(SexoEnum.MASCULINO)
                .cidade(cidadeDTO)
                .build();
        ClienteDTO clienteDTOCriado = this.clienteService.criarCliente(clienteDTO);
        ClienteDTO clienteDTOAtualizado = this.clienteService.atualizarNomeCliente(clienteDTOCriado.getId(),"CLIENTE TESTE 1");
        assertEquals(clienteDTOAtualizado.getNomeCompleto(),"CLIENTE TESTE 1");
    }

    @Test
    @Transactional
    public void deletarCliente() throws ParseException {
        CidadeDTO cidadeDTO = this.cidadeService.criarCidade(CidadeDTO.builder()
                .nome("CIDADE NUMERO 1")
                .estado(UfEnum.SP)
                .build());
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .nomeCompleto("CLIENTE NUMERO 1")
                .dataNascimento(criarData("01/01/2000"))
                .sexo(SexoEnum.MASCULINO)
                .cidade(cidadeDTO)
                .build();
        ClienteDTO clienteDTOCriado = this.clienteService.criarCliente(clienteDTO);

        this.clienteService.deletarCliente(clienteDTOCriado.getId());

        Page<ClienteDTO> dto = this.clienteService.listarClientes(ClienteDTO.builder().build(),Page.empty().getPageable());
        assertEquals(dto.getTotalElements(), 0);
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
