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
import org.springframework.transaction.annotation.Transactional;

import com.thiagorogerio.ionic.domain.Cidade;
import com.thiagorogerio.ionic.domain.Cliente;
import com.thiagorogerio.ionic.domain.Endereco;
import com.thiagorogerio.ionic.domain.enums.TipoCliente;
import com.thiagorogerio.ionic.dto.ClienteDTO;
import com.thiagorogerio.ionic.dto.ClienteNewDTO;
import com.thiagorogerio.ionic.repositories.ClienteRepository;
import com.thiagorogerio.ionic.repositories.EnderecoRepository;
import com.thiagorogerio.ionic.services.exceptions.DataIntegrityException;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> objeto = clienteRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente objCliente) {
		objCliente.setId(null);
		clienteRepository.save(objCliente);
		enderecoRepository.saveAll(objCliente.getEnderecos());
		return objCliente;
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

	public Cliente fromDTO(ClienteNewDTO objClienteNewDto) {
		Cliente cliente = new Cliente(null, objClienteNewDto.getNome(), objClienteNewDto.getEmail(), 
								objClienteNewDto.getCpfOuCnpj(), TipoCliente.toEnum(objClienteNewDto.getTipo()));
		
		Cidade cidade = new Cidade (objClienteNewDto.getCidadeId(), null, null);
		
		Endereco endereco = new Endereco(null,objClienteNewDto.getLogradouro(), objClienteNewDto.getNumero(),
									objClienteNewDto.getComplemento(), objClienteNewDto.getBairro(), objClienteNewDto.getCep(), cidade, cliente);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objClienteNewDto.getTelefone1());
		if (objClienteNewDto.getTelefone2() != null)
			cliente.getTelefones().add(objClienteNewDto.getTelefone2());
		
		if(objClienteNewDto.getTelefone3() != null)
			cliente.getTelefones().add(objClienteNewDto.getTelefone3());
		
		return cliente;
	}
	
	private void updateData(Cliente newObjCliente, Cliente objCliente) {
		newObjCliente.setNome(objCliente.getNome());
		newObjCliente.setEmail(objCliente.getEmail());

	}
}
