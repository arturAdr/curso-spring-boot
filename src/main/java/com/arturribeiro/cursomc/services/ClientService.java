package com.arturribeiro.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arturribeiro.cursomc.domain.Address;
import com.arturribeiro.cursomc.domain.City;
import com.arturribeiro.cursomc.domain.Client;
import com.arturribeiro.cursomc.domain.enums.CustomerType;
import com.arturribeiro.cursomc.dto.ClientDTO;
import com.arturribeiro.cursomc.dto.ClientNewDTO;
import com.arturribeiro.cursomc.repositories.AddressRepository;
import com.arturribeiro.cursomc.repositories.ClientRepository;
import com.arturribeiro.cursomc.services.exceptions.DataIntegrityException;
import com.arturribeiro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private AddressRepository addressRepository;
	
	public Client find(Integer id) {
	 Optional<Client> obj = repo.findById(id);
	 return obj.orElseThrow(() -> new ObjectNotFoundException(
			 "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getAdresses());
		return obj;
	}
	
	public Client update(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um client com pedidos");
		}
	}
	
	public List<Client> findAll() {
		List<Client> categories = repo.findAll();		
		return categories;
	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Client fromDTO(ClientDTO obj) {
		return new Client(obj.getId(), obj.getName(), obj.getEmail(), null, null);
	}
	
	public Client fromDTO(ClientNewDTO obj) {
		
		Client cli =  new Client(null, obj.getName(), obj.getEmail(), obj.getCpfOrCnpj(), CustomerType.toEnum(obj.getCustomerType()));
		
		City ci = new City(obj.getCityId(), null, null);
		
		Address address = new Address(null , obj.getPublicPlace(),  obj.getNumber(), obj.getComplement(), obj.getDistrict(), obj.getCep(),
				cli,  ci);
		
		cli.getAdresses().add(address);
		
		cli.getPhones().add(obj.getPhone1());
		
		if (obj.getPhone2()!=null) {
			cli.getPhones().add(obj.getPhone2());
		}
		
		if (obj.getPhone3()!=null) {
			cli.getPhones().add(obj.getPhone3());
		}
		
		return cli;
	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

}
