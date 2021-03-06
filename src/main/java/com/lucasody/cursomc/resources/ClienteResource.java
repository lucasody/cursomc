package com.lucasody.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasody.cursomc.domain.Cliente;
import com.lucasody.cursomc.dto.ClienteDTO;
import com.lucasody.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")

public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);				
	}
		
	@RequestMapping(value="/{id}", method=RequestMethod.PUT) // Atualiza a categoria
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj =service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) //Deleta Cliente/id
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		
		service.delete(id);
		return ResponseEntity.noContent().build();			
	} 
	
	@RequestMapping(method=RequestMethod.GET) //Solicita categoria
	public ResponseEntity<List<ClienteDTO>> findAll(){
		
		List<Cliente> list = service.findAll(); //busca lista de categorias
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);				
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET) //Solicita pagina categoria com parametros
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction); //busca lista de categorias
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);				
	}
}