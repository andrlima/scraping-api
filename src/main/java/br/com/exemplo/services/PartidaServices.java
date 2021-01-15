package br.com.exemplo.services;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.exemplo.dto.PartidaDTO;
import br.com.exemplo.dto.PartidaResponseDTO;
import br.com.exemplo.entity.Partida;
import br.com.exemplo.exceptions.NotFoundException;
import br.com.exemplo.repository.PartidaRepository;

@Service
public class PartidaServices {

	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EquipeServices equipeService;
	
	// Buscar por id
	public Partida buscarPartidaPorId(Long id) {

		return partidaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nenhuma martida foi encontrada com o id informado: " + id));
	}
	
	// Listar todos
	public PartidaResponseDTO listarParidas() {
		PartidaResponseDTO partidas = new PartidaResponseDTO();
		partidas.setPartida(partidaRepository.findAll());
		
		return partidas;
	}
	
	// Inserir partidas
	public Partida inserirPartida(PartidaDTO dto) {
		Partida partida = modelMapper.map(dto, Partida.class);
		partida.setEquipeCasa(equipeService.buscarEquipePorNome(dto.getNomeEquipeCasa()));
		partida.setEquipeVisitante(equipeService.buscarEquipePorNome(dto.getNomeEquipeVisitante()));
		
		return salvarPartida(partida);
	}

	private Partida salvarPartida(Partida partida) {
		return partidaRepository.save(partida);
	}

	public void alterarPartida(Long id, PartidaDTO dto) {
		boolean exists = partidaRepository.existsById(id);
		
		if(!exists) {
			throw new NotFoundException("Não foi possível alterar a partida: ID inexistente");
		}
		
		Partida partida = buscarPartidaPorId(id);
		partida.setEquipeCasa(equipeService.buscarEquipePorNome(dto.getNomeEquipeCasa()));
		partida.setEquipeVisitante(equipeService.buscarEquipePorNome(dto.getNomeEquipeVisitante()));
		partida.setDataHoraPartida(dto.getDataHoraPartida());
		partida.setLocalPartida(dto.getLocalPartida());
		
		salvarPartida(partida);
		 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
