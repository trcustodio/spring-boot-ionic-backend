package com.thiagorogerio.ionic.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thiagorogerio.ionic.domain.Categoria;
import com.thiagorogerio.ionic.dto.CategoriaDTO;
import com.thiagorogerio.ionic.services.CategoriaService;

/**
 * 
 * @author trogerio
 * 
 */

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria objCategoria = categoriaService.find(id);
		return ResponseEntity.ok().body(objCategoria);

	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objCategoriaDto){
		Categoria objCategoria = categoriaService.fromDTO(objCategoriaDto);
		objCategoria = categoriaService.insert(objCategoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(objCategoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody CategoriaDTO objCategoriaDto, @PathVariable Integer id){
		Categoria objCategoria = categoriaService.fromDTO(objCategoriaDto);
		objCategoria.setId(id);
		objCategoria = categoriaService.update(objCategoria);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> lsObjCategoria = categoriaService.findAll();
		List<CategoriaDTO> lsObjCategoriaDTO = lsObjCategoria.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(lsObjCategoriaDTO);

	}

	@RequestMapping(value="page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Categoria> lsObjCategoria = categoriaService.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> lsObjCategoriaDTO = lsObjCategoria.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(lsObjCategoriaDTO);

	}

}
