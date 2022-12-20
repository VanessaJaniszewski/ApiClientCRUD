package com.devsuperior.client.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.ClientEntity;
import com.devsuperior.client.repository.ClientRepository;
import com.devsuperior.client.services.exceptions.DataBaseException;
import com.devsuperior.client.services.exceptions.ResourceNotFoundException;




@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		List<ClientDTO> listDto = new ArrayList<>();
		List<ClientEntity> list =	repository.findAll();
		for (ClientEntity client : list) {
			listDto.add(new ClientDTO((com.devsuperior.client.entities.ClientEntity) client));
		}
		return listDto;
	}
	
	@Transactional(readOnly =true)
	public ClientDTO findById(Long id) {
		try {
		Optional<ClientEntity> obj = repository.findById(id);	
		ClientEntity client = obj.orElseThrow(()-> new EntityNotFoundException ("Entity not found.")); 
		return new ClientDTO(client);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("There is no client with id: "+id+" in the Database.");
		}
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		try{
			ClientEntity entity = new ClientEntity();
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
			}catch (EntityNotFoundException e) {
				throw new ResourceNotFoundException("Entity was not found.");
			}}
	

	private void copyDtoToEntity(ClientDTO dto, ClientEntity entity) {
		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
			try {
			ClientEntity entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
			}catch(EntityNotFoundException e) {
				throw new ResourceNotFoundException("Entity was not found.");
			}
		}
	

	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
			}catch(EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException("Id not found "+id);
			}catch(DataIntegrityViolationException e) {
				throw new DataBaseException("Integrity violation.");
			}
		}
	
}
