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

import com.thiagorogerio.ionic.domain.Cliente;
import com.thiagorogerio.ionic.dto.ClienteDTO;
import com.thiagorogerio.ionic.repositories.ClienteRepository;
import com.thiagorogerio.ionic.services.exceptions.DataIntegrityException;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> objeto = clienteRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente objCliente) {
		objCliente.setId(null);
		return clienteRepository.save(objCliente);
	}

	public Cliente update(Cliente objCliente) {
		Cliente newObjCliente = find(objCliente.getId());
		updateData(newObjCliente, objCliente);
		return clienteRepository.save(newObjCliente);
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possua pedidos!");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objClienteDto) {
		return new Cliente(objClienteDto.getId(), objClienteDto.getNome(), objClienteDto.getEmail(), null, null);
	}

	private void updateData(Cliente newObjCliente, Cliente objCliente) {
		newObjCliente.setNome(objCliente.getNome());
		newObjCliente.setEmail(objCliente.getEmail());

	}
}
