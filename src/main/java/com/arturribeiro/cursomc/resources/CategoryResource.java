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

import com.arturribeiro.cursomc.domain.Category;
import com.arturribeiro.cursomc.dto.CategoryDTO;
import com.arturribeiro.cursomc.services.CategoryService;

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> find(@PathVariable Integer id) {

		Category obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO obj) {
		Category objCategory = service.fromDTO(obj);
				objCategory = service.insert(objCategory);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objCategory.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoryDTO obj, @PathVariable Integer id) {
		Category objCategory = service.fromDTO(obj);
		objCategory.setId(id);
		objCategory = service.update(objCategory);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> categories = service.findAll();
		List <CategoryDTO> categoriesDTO = categories.stream()
				.map(obj -> new CategoryDTO(obj)).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(categoriesDTO);
	}
	
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoryDTO>> finPage(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		Page<Category> categories = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoryDTO> categoriesDTO = categories.map(obj -> new CategoryDTO(obj));
		return ResponseEntity.ok().body(categoriesDTO);
	}
}
