package com.thiagorogerio.ionic.services;

/**
 * @author trcustodio
 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagorogerio.ionic.domain.Cliente;
import com.thiagorogerio.ionic.repositories.ClienteRepository;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> objeto = clienteRepository.findById(id);
		return objeto.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
				+", Tipo: "+Cliente.class.getName()));
	}
}
