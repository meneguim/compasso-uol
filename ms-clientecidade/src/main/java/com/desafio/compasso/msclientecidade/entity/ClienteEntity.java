package com.desafio.compasso.msclientecidade.entity;

import com.desafio.compasso.msclientecidade.enums.SexoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="cliente")
public class ClienteEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome_completo")
    private String nomeCompleto;

    @Column(name="sexo")
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    @Column(name="data_nascimento")
    private Date dataNascimento;

    @Column(name="idade")
    private Integer idade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="uf")
    private CidadeEntity cidade;

}
