package br.com.exemplo.services;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.exemplo.dto.EquipeDTO;
import br.com.exemplo.dto.EquipeResponseDTO;
import br.com.exemplo.entity.Equipe;
import br.com.exemplo.exceptions.BadRequestException;
import br.com.exemplo.exceptions.NotFoundException;
import br.com.exemplo.repository.EquipeRepository;


@Service
public class EquipeServices {
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// BUSCAR POR ID
	public Equipe buscarEquipeId(Long id) {
		return equipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nunhuma Equipe encontrada com o id informado: " + id));
	}
	
	// BUSCAR POR NOME
	public Equipe buscarEquipePorNome(String nomeEquipe) {
		return equipeRepository.findByNomeEquipe(nomeEquipe)
				.orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o nome informadado: " + nomeEquipe));
	}
	
	// LISTAR
	public EquipeResponseDTO listarEquipes() {
		EquipeResponseDTO equipes = new EquipeResponseDTO();
		equipes.setEquipe(equipeRepository.findAll());
		return equipes;
	}
	
	// INSERIR
	public Equipe inserirEquipe(EquipeDTO dto) {
		boolean exists = equipeRepository.existsByNomeEquipe(dto.getNomeEquipe());
		
		if(exists) {
			throw new BadRequestException("Já existe uma equipe cadastrada com o nome informado");
		}
		
		Equipe equipe = modelMapper.map(dto, Equipe.class);

		return equipeRepository.save(equipe);
	}
	
	// ALTERAR
	public void alterarEquipe(Long id, @Valid EquipeDTO dto) {
		boolean exists = equipeRepository.existsById(id);
		
		if(!exists) {
			throw new BadRequestException("Não foi possível alterar a equipe: ID inexistente!");
		}
		
		Equipe equipe = modelMapper.map(dto, Equipe.class);
		equipe.setId(id);
		equipeRepository.save(equipe);
		
	}

}
