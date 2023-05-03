package com.bruna.demo.services;

import java.util.List;
import java.util.Optional;

import com.bruna.demo.domain.Cliente;
import com.bruna.demo.dto.CategoriaDTO;
import com.bruna.demo.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bruna.demo.domain.Categoria;
import com.bruna.demo.repositories.CategoriaRepository;
import com.bruna.demo.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	public List<Categoria> findAll() {
		return repo.findAll();
	}

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(
				(() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()
				)
			)
		);
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"Não é possível excluir uma categoria que possui produtos"
			);
		}
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		return repo.findAll(PageRequest.of(
				page, linesPerPage, Sort.Direction.valueOf(direction), orderBy
		));
	}

	public Categoria fromDTO(CategoriaDTO obj) {
		return new Categoria(obj.getId(), obj.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}