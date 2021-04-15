package com.ifms.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ifms.dto.EscolaDTO;
import com.ifms.services.EscolaService;

@RestController
@RequestMapping(value = "/escolas")
public class EscolaResource {
		
	@Autowired 
	private EscolaService service;
	
	//todos
	@GetMapping
	public ResponseEntity<List<EscolaDTO>> findAll(){
		List<EscolaDTO> lista = service.findAll();		
		return ResponseEntity.ok().body(lista);			
	}
	
	//por id
	@GetMapping(value = "/{id}")
	public ResponseEntity<EscolaDTO> findById(@PathVariable Long id){
		EscolaDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);		
	}
	
	//inserir
	@PostMapping
	public ResponseEntity<EscolaDTO> insert(@RequestBody EscolaDTO dto){
		dto = service.insert(dto);		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);	
	}
	
	//atualizar
	@PutMapping(value = "/{id}")
	public ResponseEntity<EscolaDTO> update(@PathVariable Long id, @RequestBody EscolaDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);	
	}
	
	//deletar
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<EscolaDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
