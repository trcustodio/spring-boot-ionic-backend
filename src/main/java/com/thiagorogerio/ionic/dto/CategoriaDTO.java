package com.thiagorogerio.ionic.dto;

import java.io.Serializable;

import com.thiagorogerio.ionic.domain.Categoria;

/**
 * 
 * @author trogerio
 *
 */

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 5362513919499569269L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
	}
	
	public CategoriaDTO(Categoria objCategoria) {
		id = objCategoria.getId();
		nome = objCategoria.getNome();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
