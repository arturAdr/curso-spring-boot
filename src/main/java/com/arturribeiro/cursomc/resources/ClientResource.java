package com.arturribeiro.cursomc.resources;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arturribeiro.cursomc.domain.Client;
import com.arturribeiro.cursomc.dto.ClientDTO;
import com.arturribeiro.cursomc.dto.ClientNewDTO;
import com.arturribeiro.cursomc.services.ClientService;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {

	@Autowired
	private ClientService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Client> find(@PathVariable Integer id) {

		Client obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO obj) {
		Client objClient = service.fromDTO(obj);
			   objClient = service.insert(objClient);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objClient.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO obj, @PathVariable Integer id) {
		Client objClient = service.fromDTO(obj);
		objClient.setId(id);
		objClient = service.update(objClient);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<Client> categories = service.findAll();
		List <ClientDTO> categoriesDTO = categories.stream()
				.map(obj -> new ClientDTO(obj)).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(categoriesDTO);
	}
	
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClientDTO>> finPage(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		Page<Client> categories = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClientDTO> categoriesDTO = categories.map(obj -> new ClientDTO(obj));
		return ResponseEntity.ok().body(categoriesDTO);
	}
}
