package com.arturribeiro.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arturribeiro.cursomc.domain.Order;
import com.arturribeiro.cursomc.repositories.OrderRepository;
import com.arturribeiro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;
	
	public Order find(Integer id) {
	 Optional<Order> obj = repo.findById(id);
	 return obj.orElseThrow(() -> new ObjectNotFoundException(
			 "Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}
	
}
