package com.desafio.compasso.msclientecidade.repository;

import com.desafio.compasso.msclientecidade.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {
    Optional<ClienteEntity> findByNomeCompleto(String nome);
}
