package com.thiagorogerio.cursomc.services;

/**
 * @author trcustodio
 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagorogerio.cursomc.domain.Categoria;
import com.thiagorogerio.cursomc.repositories.CategoriaRepository;
import com.thiagorogerio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> objeto = categoriaRepository.findById(id);
		return objeto.orElseThrow(()-> new ObjectNotFoundException("Obejto não encontrado! Id: "+id
				+", Tipo: "+Categoria.class.getName()));
	}
}
