package com.thiagorogerio.ionic.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.thiagorogerio.ionic.domain.PagamentoComBoleto;

/**
 * 
 * @author trogerio
 *
 */

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagamentoBoleto,
			Date instanteDoPedido) {
		
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(instanteDoPedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pagamentoBoleto.setDataVencimento(calendario.getTime());
		
	}
	
}
