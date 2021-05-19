package com.desafio.compasso.msclientecidade.repository;

import com.desafio.compasso.msclientecidade.DTO.CidadeDTO;
import com.desafio.compasso.msclientecidade.entity.CidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeEntity,Long> {

    @Query("select count(obj.id) from CidadeEntity obj where obj.nome = ?1")
    Integer validaExisteCidade(String nome);

}
