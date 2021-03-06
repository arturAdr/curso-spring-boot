package com.arturribeiro.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.arturribeiro.cursomc.domain.Client;

public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="preenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120")
	private String name;
	
	@NotEmpty(message="preenchimento obrigatório")
	@Email(message = "email invalido")
	private String email;
	
	public ClientDTO() { 
		
	}
	
	public ClientDTO(Client obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
