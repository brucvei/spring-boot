package com.bruna.demo.services.validation;

import com.bruna.demo.domain.enums.TipoCliente;
import com.bruna.demo.dto.ClienteNewDTO;
import com.bruna.demo.repositories.ClienteRepository;
import com.bruna.demo.services.exceptions.FieldMessage;
import com.bruna.demo.services.validation.utils.BR;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (repo.findByEmail(objDTO.getEmail()) != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		if (objDTO.getTipo() == null) {
			list.add(new FieldMessage("tipo", "Tipo não pode ser nulo"));
		}

		if (objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		} else if (objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}

		return list.isEmpty();
	}
}
