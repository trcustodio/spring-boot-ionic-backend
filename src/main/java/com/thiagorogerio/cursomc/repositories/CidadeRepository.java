package com.thiagorogerio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagorogerio.cursomc.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
