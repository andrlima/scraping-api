package br.com.exemplo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.exemplo.dto.PartidaGoogleDTO;
import br.com.exemplo.entity.Partida;
import br.com.exemplo.util.ScrapingUtil;

@Service
public class ScrapingServices {
	
	// Injeções de dependencias
	@Autowired
	private ScrapingUtil scrapingUtil;
	
	@Autowired
	private PartidaServices partidaService;
	
	public void verificaPartidaPeriodo() {
		Integer quantidadePartida = partidaService.buscarQuantidadePartidasPeriodo();
		
		if(quantidadePartida > 0) {
			List<Partida> partidas = partidaService.ListarPartidaPeriodo();
			
			// MONTANDO A URL 
			partidas.forEach(partida -> {
				String urlPartida = scrapingUtil.montandoUrlGoogle(
						partida.getEquipeCasa().getNomeEquipe(), 
						partida.getEquipeVisitante().getNomeEquipe());
				
				
				PartidaGoogleDTO partidaGoogle = scrapingUtil.obtendoInformacoesGoogle(urlPartida); 
				
				partidaService.atualizarPartida(partida, partidaGoogle);
				
			});
			
		}
		
	}
	
}
















