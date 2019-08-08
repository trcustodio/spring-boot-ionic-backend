package com.thiagorogerio.ionic.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

/**
 * @author trcustodio
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thiagorogerio.ionic.domain.Cliente;
import com.thiagorogerio.ionic.dto.ClienteDTO;
import com.thiagorogerio.ionic.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente objeto = clienteService.find(id);
		return ResponseEntity.ok().body(objeto);

	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO objClienteDto, @PathVariable Integer id){
		Cliente objCliente = clienteService.fromDTO(objClienteDto);
		objCliente.setId(id);
		objCliente = clienteService.update(objCliente);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> lsObjCliente = clienteService.findAll();
		List<ClienteDTO> lsObjClienteDTO = lsObjCliente.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(lsObjClienteDTO);

	}

	@RequestMapping(value="page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> lsObjCliente = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> lsObjClienteDTO = lsObjCliente.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(lsObjClienteDTO);

	}

}
