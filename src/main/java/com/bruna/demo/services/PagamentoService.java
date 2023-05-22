package com.bruna.demo.services;

import com.bruna.demo.domain.Pagamento;
import com.bruna.demo.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository repo;

	public Pagamento insert(Pagamento obj) {
		obj.setId(null);
		return repo.save(obj);
	}
}
