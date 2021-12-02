package com.arturribeiro.cursomc.domain.enums;

public enum CustomerType {

	PF(0, "Pessoa Fisica"),
	PJ(1, "Pessoa Juridica");
	
	private int cod;
	private String description;
	
	private CustomerType(int cod, String descricao) {
		 this.cod = cod;
		 this.description = descricao;
	 }

	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	public static CustomerType toEnum(Integer id) {

		if (id == null) {
			 return null;
		}
	
		for (CustomerType x : CustomerType.values()) {
			 if (id.equals(x.getCod())) {
				 return x;
			 }
		 }

		throw new IllegalArgumentException("Id inválido " + id);
	} 
	
}
