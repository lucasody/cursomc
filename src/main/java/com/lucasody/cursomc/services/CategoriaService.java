package com.lucasody.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasody.cursomc.domain.Categoria;
import com.lucasody.cursomc.repositories.CategoriaRepository;
import com.lucasody.cursomc.services.exepctions.DataIntegrityException;
import com.lucasody.cursomc.services.exepctions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired //puxa a dependencia automaticamente para categoriarepository
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); //verifica se o Id existe
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id); //verifica se o Id existe
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e){ //tentar deletar categoria com produtos
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){ //retorna lista com todas categorias
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);		
	}
}

