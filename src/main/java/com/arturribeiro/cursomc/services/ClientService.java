package com.arturribeiro.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arturribeiro.cursomc.domain.Client;
import com.arturribeiro.cursomc.repositories.ClientRepository;
import com.arturribeiro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	
	public Client find(Integer id) {
	 Optional<Client> obj = repo.findById(id);
	 return obj.orElseThrow(() -> new ObjectNotFoundException(
			 "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
}
