package com.lucasody.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasody.cursomc.domain.Categoria;
import com.lucasody.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired //puxa a dependencia automaticamente para categoriarepository
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
