package com.thiagorogerio.ionic.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.thiagorogerio.ionic.domain.enums.TipoCliente;
import com.thiagorogerio.ionic.dto.ClienteNewDTO;
import com.thiagorogerio.ionic.resources.exceptions.FieldMessage;
import com.thiagorogerio.ionic.services.validation.utils.BR;
/**
 * 
 * @author trogerio
 *
 */

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{
	
	@Override
	public void initialize(ClienteInsert clienteInsert) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO clienteNewDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(clienteNewDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo())
				&& !BR.isValidCpf(clienteNewDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
		}
	
		if(clienteNewDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo())
				&& !BR.isValidCnpj(clienteNewDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}

}
