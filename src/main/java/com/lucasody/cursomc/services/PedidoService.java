package com.lucasody.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasody.cursomc.domain.Pedido;
import com.lucasody.cursomc.repositories.PedidoRepository;
import com.lucasody.cursomc.services.exepctions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired //puxa a dependencia automaticamente para categoriarepository
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 

	}
}
