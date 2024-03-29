package com.thiagorogerio.ionic.services;

import java.util.List;

/**
 * @author trcustodio
 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.thiagorogerio.ionic.domain.Categoria;
import com.thiagorogerio.ionic.dto.CategoriaDTO;
import com.thiagorogerio.ionic.repositories.CategoriaRepository;
import com.thiagorogerio.ionic.services.exceptions.DataIntegrityException;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria find(Integer id) {
		Optional<Categoria> objCategoria = categoriaRepository.findById(id);
		return objCategoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria objCategoria) {
		objCategoria.setId(null);
		return categoriaRepository.save(objCategoria);
	}

	public Categoria update(Categoria objCategoria) {
		Categoria newObjCategoria = find(objCategoria.getId());
		updateData(newObjCategoria, objCategoria);
		return categoriaRepository.save(newObjCategoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possua produtos!");
		}
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objCategoriaDto) {
		return new Categoria(objCategoriaDto.getId(), objCategoriaDto.getNome());
	}

	private void updateData(Categoria newObjCategoria, Categoria objCategoria) {
		newObjCategoria.setNome(objCategoria.getNome());
	}

}
