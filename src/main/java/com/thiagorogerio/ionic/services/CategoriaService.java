package com.thiagorogerio.ionic.services;

/**
 * @author trcustodio
 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.thiagorogerio.ionic.domain.Categoria;
import com.thiagorogerio.ionic.repositories.CategoriaRepository;
import com.thiagorogerio.ionic.services.exceptions.DataIntegrityException;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> objCategoria = categoriaRepository.findById(id);
		return objCategoria.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
				+", Tipo: "+Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria objCategoria) {
		objCategoria.setId(null);
		return categoriaRepository.save(objCategoria);
	}
	
	public Categoria update(Categoria objCategoria) {
		find(objCategoria.getId());
		return categoriaRepository.save(objCategoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
		categoriaRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possua produtos!");
		}
	}
}
