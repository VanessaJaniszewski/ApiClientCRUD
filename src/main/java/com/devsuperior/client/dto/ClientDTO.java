package com.devsuperior.client.dto;

import java.io.Serializable;
import java.time.Instant;

import com.devsuperior.client.entities.ClientEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Long id;
	private String name;
	private String cpf;
	private Double income;
	private Instant birthDate;
	private Integer children;
	
	
	public ClientDTO(ClientEntity client) {
		this.id =client.getId();
		this.name = client.getName();
		this.cpf = client.getCpf();
		this.income = client.getIncome();
		this.birthDate=client.getBirthDate();
		this.children = client.getChildren();
	}
	
}
