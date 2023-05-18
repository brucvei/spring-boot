package com.bruna.demo.services;

import com.bruna.demo.domain.Categoria;
import com.bruna.demo.domain.Produto;
import com.bruna.demo.repositories.CategoriaRepository;
import com.bruna.demo.repositories.ProdutoRepository;
import com.bruna.demo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepo;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(
				(() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()
				)
				)
		);
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return repo.findDistinctByNomeContainingAndAndCategoriasIn(nome, categorias, pageRequest);
//		return repo.search(nome, categorias, pageRequest);
	}
}
