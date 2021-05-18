package com.desafio.compasso.msclientecidade.repository;

import com.desafio.compasso.msclientecidade.entity.CidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeEntity,Long> {}
