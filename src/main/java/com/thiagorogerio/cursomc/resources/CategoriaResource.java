package com.thiagorogerio.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thiagorogerio.cursomc.domain.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		
		Categoria categoria1 = new Categoria(1, "Informática");
		Categoria categorai2 = new Categoria(2, "Escritório");
		
		List<Categoria> listaCategoria = new ArrayList<>();
		listaCategoria.add(categoria1);
		listaCategoria.add(categorai2);
		
		return listaCategoria;
	}

}
