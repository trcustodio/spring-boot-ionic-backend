package com.thiagorogerio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagorogerio.cursomc.domain.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
