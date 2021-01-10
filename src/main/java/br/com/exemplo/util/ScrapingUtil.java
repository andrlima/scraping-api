package br.com.exemplo.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.exemplo.dto.PartidaGoogleDTO;

public class ScrapingUtil {
	
	
	// Importado do org.slf4j
	private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
	
	// ?q = query
	private static final String BASE_URL_GOOGLE = "https://google.com.br/search?q=";
	
	// Query que queremos buscar, ou seja, o parâmetro.
	// Varáveis para teste
	private static final String QUERY = "palmeiras+x+corinthias+08/08/2020";
	private static final String QUERY2 = "fortaleza+e+grêmio"; // partida 09/01/2021
	private static final String QUERY3 = "tigres+UANL+x+León&rlz"; // partida 09/01/2021
	

	// informa para o google qual a linguagem que estamos buscando, também é um parâmtro.
	private static final String COMPLEMENTO_URL_GOOGLE = "&hl=pt-BR"; 
	
	public static void main(String[] args) {
		
		String url = BASE_URL_GOOGLE + QUERY3 + COMPLEMENTO_URL_GOOGLE;
		ScrapingUtil scraping = new ScrapingUtil();
		
		scraping.obtendoInformacoesPartida(url);

	}
	
	public PartidaGoogleDTO obtendoInformacoesPartida(String utl) {
		
		PartidaGoogleDTO partida = new PartidaGoogleDTO();
		
		// Importado do org.jsoup.nodes
		Document document = null;
		
		try {
			
			document = Jsoup.connect(utl).get();
			
			String title = document.title();
			LOGGER.info("Titulo da página: {}", title); // Pegando o titulo da página.
			
			
			StatusPartida statusPartida = obtendoStatusPartida(document);
			LOGGER.info("Estatus da partida: {} ", statusPartida);
			
			String tempoDaPartida = obtendoTempoDaPartida(document);
			LOGGER.info("Tempo da partida: {}", tempoDaPartida);
			
			
		}
		catch (IOException e) {
			
			LOGGER.error("Erro ao tentar conectar no google com JSoup -> {}", e.getMessage());
			e.printStackTrace();
			
		}
		
		
		return partida;
	}
	
	
	// Ira buscar os estados das partidas
	public StatusPartida obtendoStatusPartida(Document document) {
		
		// Situações
		// 1 - Partida não iniciada
		// 2 - Partida inicia / jogo rolando / intervalo
		// 3 - Partida encerrada
		// 4 - Penalidades
		
		StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
		
		
		boolean isTempoPartida =  document.select("div[class=imso_mh__lv-m-stts-cont]").isEmpty();
		if(!isTempoPartida) {
			String tempoPartida = document.select("div[class=imso_mh__lv-m-stts-cont]").first().text();
			statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;
			
			//Se por acaso estiver nos pênaltis
			if(tempoPartida.contains("Pênaltis")) {
				statusPartida = StatusPartida.PARTIDA_PENALTIS;
				
			}
			//LOGGER.info(tempoPartida);
			
		}
		
		isTempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").isEmpty();
		if(!isTempoPartida) {
			statusPartida = StatusPartida.PARTIDA_ENCERRADA;
			
		}
		//LOGGER.info(statusPartida.toString());
		
		return statusPartida;
	
	}
	
	public String obtendoTempoDaPartida(Document document) {
		
		String tempoDaPartida = null;
		
		// Jogo rolando, intervalo ou penalidades
		boolean isTempoDaPartida = document.select("div[class = imso_mh__lv-m-stts-cont]").isEmpty();
		if(!isTempoDaPartida) {
			tempoDaPartida = document.select("div[class = imso_mh__lv-m-stts-cont]").first().text();
		}
		
		isTempoDaPartida = document.select("span[class = imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").isEmpty();
		if(!isTempoDaPartida) {
			tempoDaPartida = document.select("span[class = imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").first().text();
		}
		
		//LOGGER.info(corrigeTempoDaPartida(tempoDaPartida)); <- esta printando.
		
		return corrigeTempoDaPartida(tempoDaPartida);
		
	}
	
	// Método para corrigir o tempo ex: 50' -> 50 min
	public String corrigeTempoDaPartida(String tempo) {
		
		if(tempo.contains("'")) {
			return tempo.replace("'", " min");
			
			// Retorna sem o espaçamento no tempo.
			//return tempo.replace(" ", "").replace("'", " min");
			
		}
		else {
			return tempo;
			
		}
		
	}

}



















