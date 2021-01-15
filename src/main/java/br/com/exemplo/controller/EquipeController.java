package br.com.exemplo.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.exemplo.dto.EquipeDTO;
import br.com.exemplo.dto.EquipeResponseDTO;
import br.com.exemplo.entity.Equipe;
import br.com.exemplo.exceptions.StandardError;
import br.com.exemplo.services.EquipeServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api("API de equipes")
@RestController
@RequestMapping("/api/v1/equipes")
public class EquipeController {
	
	@Autowired
	private EquipeServices equipeService;
	
	//Retornara uma lisata de objetos 
	@ApiOperation(value = "Buscar equipe por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Equipe.class),
			@ApiResponse(code = 400, message = "Bad resquest", response = StandardError.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
			@ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
			@ApiResponse(code = 404, message = "Not found", response = StandardError.class),
			@ApiResponse(code = 500, message = "Internal server error", response = StandardError.class),
		
	})
	@GetMapping("/{id}")
	public ResponseEntity<Equipe> buscarEquipeId(@PathVariable("id") Long id){
		return ResponseEntity.ok().body(equipeService.buscarEquipeId(id));
		
		
	}
	
	//Retornara uma lisata de objetos 
	@ApiOperation(value = "Listar equipes")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = EquipeResponseDTO.class),
			@ApiResponse(code = 400, message = "Bad resquest", response = StandardError.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
			@ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
			@ApiResponse(code = 404, message = "Not found", response = StandardError.class),
			@ApiResponse(code = 500, message = "Internal server error", response = StandardError.class),
		
	})
	@GetMapping
	public ResponseEntity<EquipeResponseDTO> listarEquipes(){
		return ResponseEntity.ok().body(equipeService.listarEquipes());
	}
	
	//Retornara uma lisata de objetos 
	@ApiOperation(value = "Inserindo equipes")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = Equipe.class),
			@ApiResponse(code = 400, message = "Bad resquest", response = StandardError.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
			@ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
			@ApiResponse(code = 404, message = "Not found", response = StandardError.class),
			@ApiResponse(code = 500, message = "Internal server error", response = StandardError.class),
		
	})
	@PostMapping
	public ResponseEntity<Equipe> inserirEquipe(@Valid @RequestBody EquipeDTO dto){
		Equipe equipe = equipeService.inserirEquipe(dto);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/(id)")
				.buildAndExpand(equipe.getId()).toUri();
		
		return ResponseEntity.created(location).body(equipe);
		
	}
	
	//Retornara uma lisata de objetos 
	@ApiOperation(value = "Alterar equipes")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "no content", response = Void.class),
			@ApiResponse(code = 400, message = "Bad resquest", response = StandardError.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
			@ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
			@ApiResponse(code = 404, message = "Not found", response = StandardError.class),
			@ApiResponse(code = 500, message = "Internal server error", response = StandardError.class),
	})
	@PutMapping("/(id)")
	public ResponseEntity<Void> alterarEquipe(@PathVariable("id") Long id, 
			@Valid @RequestBody EquipeDTO dto){
		
		equipeService.alterarEquipe(id, dto);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
