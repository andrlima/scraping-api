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
	private static final String QUERY = "palmeiras+x+corinthias+08/08/2020";
	
	// informa para o google qual a linguagem que estamos buscando, também é um parâmtro.
	private static final String COMPLEMENTO_URL_GOOGLE = "&hl=pt-BR"; 
	
	public static void main(String[] args) {
		
		String url = BASE_URL_GOOGLE + QUERY + COMPLEMENTO_URL_GOOGLE;
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
			LOGGER.info("Titulo da página: {}", title);
			
		}
		catch (IOException e) {
			
			LOGGER.error("Erro ao tentar conectar no google com JSoup -> {}", e.getMessage());
			e.printStackTrace();
			
		}
		
		
		return partida;
	}

}
