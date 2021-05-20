package com.desafio.compasso.msclientecidade.repository;

import com.desafio.compasso.msclientecidade.entity.CidadeEntity;
import com.desafio.compasso.msclientecidade.enums.UfEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeEntity,Long> {

    @Query("select count(obj.id) from CidadeEntity obj where obj.nome = ?1 and obj.estado = ?2")
    Integer validaExisteCidade(String nome, UfEnum estado);

}
