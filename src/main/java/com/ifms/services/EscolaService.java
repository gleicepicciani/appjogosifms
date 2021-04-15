package com.ifms.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifms.dto.EscolaDTO;
import com.ifms.entities.Escola;
import com.ifms.repositories.EscolaRepository;
import com.ifms.services.exceptions.DataBaseException;
import com.ifms.services.exceptions.ResourceNotFoundException;

@Service
public class EscolaService {	
	
	//ele depende do repository
	@Autowired
	private EscolaRepository repository;
	

	//todos
	@Transactional(readOnly = true)
	public List<EscolaDTO> findAll() {
		List<Escola> list = repository.findAll();
		return list.stream().map(escola -> new EscolaDTO(escola))
				.collect(Collectors.toList());
	}

	//busca de um numero especifico que nao possui no sistema
	@Transactional(readOnly = true)
	public EscolaDTO findById(Long id) {
		Optional<Escola> obj = repository.findById(id);
		Escola escola = obj.orElseThrow(() -> new ResourceNotFoundException(
				"A escola solicitada não foi localizada") );
		return new EscolaDTO(escola);
	}

	//inserir
	@Transactional
	public EscolaDTO insert(EscolaDTO dto) {
		Escola entity = new Escola();
		entity.setNome(dto.getNome());
		entity.setEndereco(dto.getEndereco());
		entity.setSite(dto.getSite());
		entity.setEmail(dto.getEmail());
		entity.setDiretor(dto.getDiretor());
		entity.setTelefone(dto.getTelefone());	
		entity = repository.save(entity);
		return new EscolaDTO(entity);
	}
	
	//atualizar
	@Transactional
	public EscolaDTO update(Long id, EscolaDTO dto) {
		try {
			Escola entity = repository.getOne(id);
			entity.setNome(dto.getNome());
			entity.setEndereco(dto.getEndereco());
			entity.setSite(dto.getSite());
			entity.setEmail(dto.getEmail());
			entity.setDiretor(dto.getDiretor());
			entity.setTelefone(dto.getTelefone());
			entity = repository.save(entity);
			return new EscolaDTO(entity);			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O id da escola não foi localizado");
		}
	}

	//excluir
	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possível deletar, o id da escola não foi localizado");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não foi possível deletar a escola, pois a mesma está em uso");
		}
	}
}

