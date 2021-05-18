package com.desafio.compasso.msclientecidade.entity;

import com.desafio.compasso.msclientecidade.enums.UfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cidade")
public class CidadeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="uf")
    @Enumerated(EnumType.STRING)
    private UfEnum estado;

}
