package com.thiagorogerio.ionic.resources;

import java.util.List;

/**
 * @author trogerio
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thiagorogerio.ionic.domain.Produto;
import com.thiagorogerio.ionic.dto.ProdutoDTO;
import com.thiagorogerio.ionic.resources.utils.URL;
import com.thiagorogerio.ionic.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto objeto = produtoService.find(id);
		return ResponseEntity.ok().body(objeto);

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "0") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.decondeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> lsObjProduto = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> lsObjProdutoDTO = lsObjProduto.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(lsObjProdutoDTO);

	}

}
