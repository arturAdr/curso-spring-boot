package com.arturribeiro.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arturribeiro.cursomc.domain.Category;
import com.arturribeiro.cursomc.repositories.CategoryRepository;
import com.arturribeiro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public Category find(Integer id) {
	 Optional<Category> obj = repo.findById(id);
	 return obj.orElseThrow(() -> new ObjectNotFoundException(
			 "Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}
	
}
