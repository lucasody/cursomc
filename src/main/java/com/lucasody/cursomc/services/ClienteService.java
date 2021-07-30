package com.lucasody.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasody.cursomc.domain.Cliente;
import com.lucasody.cursomc.dto.ClienteDTO;
import com.lucasody.cursomc.repositories.ClienteRepository;
import com.lucasody.cursomc.services.exepctions.DataIntegrityException;
import com.lucasody.cursomc.services.exepctions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //puxa a dependencia automaticamente para categoriarepository
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 

	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId()); //verifica se o Id existe
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	
	public void delete(Integer id) {
		find(id); //verifica se o Id existe
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e){ //tentar deletar categoria com produtos
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){ //retorna lista com todas categorias
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);		
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
