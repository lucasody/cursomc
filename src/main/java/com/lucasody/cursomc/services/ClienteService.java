package com.lucasody.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasody.cursomc.domain.Cliente;
import com.lucasody.cursomc.repositories.ClienteRepository;
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
}
