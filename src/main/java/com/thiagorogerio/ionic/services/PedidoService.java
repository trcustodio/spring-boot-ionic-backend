package com.thiagorogerio.ionic.services;

/**
 * @author trogerio
 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagorogerio.ionic.domain.Pedido;
import com.thiagorogerio.ionic.repositories.PedidoRepository;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> objeto = pedidoRepository.findById(id);
		return objeto.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
				+", Tipo: "+ Pedido.class.getName()));
	}
}
