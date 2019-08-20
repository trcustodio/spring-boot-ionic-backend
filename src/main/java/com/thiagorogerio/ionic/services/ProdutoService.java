package com.thiagorogerio.ionic.services;

import java.util.List;

/**
 * @author trogerio
 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.thiagorogerio.ionic.domain.Categoria;
import com.thiagorogerio.ionic.domain.Produto;
import com.thiagorogerio.ionic.repositories.CategoriaRepository;
import com.thiagorogerio.ionic.repositories.ProdutoRepository;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> objeto = produtoRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		List<Categoria> categorias = categoriaRepository.findAllById(ids);

		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
