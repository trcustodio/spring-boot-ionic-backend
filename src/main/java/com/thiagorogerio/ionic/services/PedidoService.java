package com.thiagorogerio.ionic.services;

import java.util.Date;

/**
 * @author trogerio
 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagorogerio.ionic.domain.ItemPedido;
import com.thiagorogerio.ionic.domain.PagamentoComBoleto;
import com.thiagorogerio.ionic.domain.Pedido;
import com.thiagorogerio.ionic.domain.enums.EstadoPagamento;
import com.thiagorogerio.ionic.repositories.ItemPedidoRepository;
import com.thiagorogerio.ionic.repositories.PagamentoRepository;
import com.thiagorogerio.ionic.repositories.PedidoRepository;
import com.thiagorogerio.ionic.repositories.ProdutoRepository;
import com.thiagorogerio.ionic.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> objeto = pedidoRepository.findById(id);
		return objeto.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id
				+", Tipo: "+ Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido objPedido) {
		objPedido.setId(null);
		objPedido.setInstante(new Date());
		objPedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		objPedido.getPagamento().setPedido(objPedido);
		if(objPedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoBoleto = (PagamentoComBoleto) objPedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamentoBoleto, objPedido.getInstante());
		}
		
		objPedido = pedidoRepository.save(objPedido);
		pagamentoRepository.save(objPedido.getPagamento());
		
		for(ItemPedido itemPedido : objPedido.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setPreco(produtoRepository.findOne(itemPedido.getProduto().getId()).getPreco());
			itemPedido.setPedido(objPedido);
		}
		
		itemPedidoRepository.saveAll(objPedido.getItens());
		return objPedido;
	
	}
}
