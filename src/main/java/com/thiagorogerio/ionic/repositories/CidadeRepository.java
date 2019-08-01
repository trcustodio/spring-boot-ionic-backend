package com.thiagorogerio.ionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagorogerio.ionic.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
