package com.bruna.demo.services;

import java.util.List;
import java.util.Optional;

import com.bruna.demo.domain.*;
import com.bruna.demo.domain.enums.TipoCliente;
import com.bruna.demo.dto.ClienteDTO;
import com.bruna.demo.dto.ClienteNewDTO;
import com.bruna.demo.repositories.CidadeRepository;
import com.bruna.demo.repositories.EnderecoRepository;
import com.bruna.demo.services.exceptions.DataIntegrityException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bruna.demo.domain.Cliente;
import com.bruna.demo.repositories.ClienteRepository;
import com.bruna.demo.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRepo;

	@Autowired
	private EnderecoRepository enderecoRepo;

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(
				(() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
				)
			)
		);
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepo.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"Não é possível excluir pois este cliente possui pedidos associados"
			);
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		return repo.findAll(PageRequest.of(
				page, linesPerPage, Sort.Direction.valueOf(direction), orderBy
		));
	}

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(
				null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo())
		);
		Cidade cid = cidadeRepo.findById(objDTO.getCidadeId()).get();
		Endereco end = new Endereco(
				null, objDTO.getLogadouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid
		);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) cli.getTelefones().add(objDTO.getTelefone2());
		if (objDTO.getTelefone3() != null) cli.getTelefones().add(objDTO.getTelefone3());

		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}