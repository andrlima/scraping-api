package br.com.exemplo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.exemplo.dto.PartidaGoogleDTO;

//Importado do org.springframework.stereotype, para poder ser injetada no bot.
@Service
public class ScrapingUtil {
	
	// Importado do org.slf4j
	private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
	
	// ?q = query
	private static final String BASE_URL_GOOGLE = "https://google.com.br/search?q=";
	
	private static final String DIV_PARTIDA_ANDAMENTO = "div[class=imso_mh__lv-m-stts-cont]";
	private static final String DIV_PARTIDA_ENCERRADA = "span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]";
	
	private static final String DIV_PLACAR_EQUIPE_CASA = "div[ class = imso_mh__l-tm-sc imso_mh__scr-it imso-light-font ]";
	private static final String DIV_PLACAR_EQUIPE_VISITANTE = "div[ class = imso_mh__r-tm-sc imso_mh__scr-it imso-light-font ]";
	private static final String DIV_GOL_EQUIP_CASA = "div[ class = imso_gs__tgs imso_gs__left-team ]";
	private static final String DIV_GOL_EQUIP_VISITANTE = "div[ class = imso_gs__tgs imso_gs__right-team ]";
	private static final String ITEM_GOL = "div[ class = imso_gs__gs-r ]";
	private static final String DIV_PENALIDADES = "div[ class = imso_mh_s__psn-sc ]";
	
	private static final String DIV_DADOS_EQUIPE_CASA = "div[ class = imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol ]";
	private static final String DIV_DADOS_EQUIPE_VISITANTE = "div[ class = imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol ]";
	
	private static final String ITEM_LOGO = "img[ class = imso_btl__mh-logo ]";
	
	private static final String CASA = "casa";
	private static final String VISITANTE = "visitante";
	private static final String PENALTIS = "Pênaltis";
	
	private static final String HTTPS = "https:";
	private static final String SRC = "src";
	private static final String SPAN = "span";
		
	// informa para o google qual a linguagem que estamos buscando, também é um parâmtro.
	private static final String COMPLEMENTO_URL_GOOGLE = "&hl=pt-BR"; 
	
	public PartidaGoogleDTO obtendoInformacoesGoogle(String url) {
		// Importado do org.jsoup.nodes
		Document document = null;
		
		PartidaGoogleDTO partida = new PartidaGoogleDTO();
		
		// CHAMADA DE TODOS OS MÉTODOS
		try {
			
			document = Jsoup.connect(url).get();
			
			String title = document.title();
			LOGGER.info("Titulo da página: {}", title); // Pegando o titulo da página.
						
			StatusPartida statusPartida = obtendoStatusPartida(document);
			partida.setStatusPartida(statusPartida.toString());
			LOGGER.info("Estatus da partida: {} ", statusPartida);
			
			// Verificar se a partida ainda não iniciou, se iniciou, entra no if
			if(statusPartida != StatusPartida.PARTIDA_NAO_INICIADA) {
				String tempoDaPartida = obtendoTempoDaPartida(document);
				partida.setTempoPartida(tempoDaPartida);
				LOGGER.info("Tempo da partida: {}", tempoDaPartida);
				
				Integer placarDaEquipeCasa = recueprarPlacarDaEquipe(document, DIV_PLACAR_EQUIPE_CASA);
				partida.setPlacarEquipeCasa(placarDaEquipeCasa);
				LOGGER.info("Placar da equipe da casa: {}", placarDaEquipeCasa);
				
				Integer placarEquipeVisistante = recueprarPlacarDaEquipe(document, DIV_PLACAR_EQUIPE_VISITANTE);
				partida.setPlacarEquipeVisitante(placarEquipeVisistante);
				LOGGER.info("Placar da equipe visistante: {}", placarEquipeVisistante);
				
				String golsDaEquipeCasa = recuperaGolsEquipe(document, DIV_GOL_EQUIP_CASA);
				partida.setGolsEquipeCasa(golsDaEquipeCasa);
				LOGGER.info("Gols da casa : {}", golsDaEquipeCasa);
				
				String golsDaEquipeVisitante = recuperaGolsEquipe(document, DIV_GOL_EQUIP_VISITANTE);
				partida.setGolsEquipeVisitante(golsDaEquipeVisitante);
				LOGGER.info("Gols dos Visitantes: {}", golsDaEquipeVisitante);
				
				//Placar dos pênaltis 
				Integer placarEstendidoEquipeCasa = buscaPenalidades(document, CASA);
				partida.setPlacarEquipeCasa(placarEstendidoEquipeCasa);
				LOGGER.info("Placar estendido da equipe da casa: {}", placarEstendidoEquipeCasa);
				
				//Placar dos pênaltis 
				Integer placarEstendidoEquipeVisitante = buscaPenalidades(document, VISITANTE);
				partida.setPlacarEquipeVisitante(placarEstendidoEquipeVisitante);
				LOGGER.info("Placar estendido da equipe visitante: {}", placarEstendidoEquipeVisitante);
			}
			
			String nomeDaEquipeCasa = recuperaNomeDaEquipe(document, DIV_DADOS_EQUIPE_CASA);
			partida.setNomeEquipeCasa(nomeDaEquipeCasa);
			LOGGER.info("Nome da equipe da casa: {}", nomeDaEquipeCasa);

			String nomeDaEquipeVisitante = recuperaNomeDaEquipe(document, DIV_DADOS_EQUIPE_VISITANTE);
			partida.setNomeEquipeVisitante(nomeDaEquipeVisitante);
			LOGGER.info("Nome da equipe visitante: {}", nomeDaEquipeVisitante);
			
			String urlLogoDaEquipeCasa = recuperaLogoDaEquipe(document, DIV_DADOS_EQUIPE_CASA);
			partida.setUrlLogoEquipeCasa(nomeDaEquipeCasa);
			LOGGER.info("Url do logo da equipe da casa: {}", urlLogoDaEquipeCasa);
			
			String urlLogoDaEquipeVisitante = recuperaLogoDaEquipe(document, DIV_DADOS_EQUIPE_VISITANTE);
			partida.setUrlLogoEquipeVisitante(urlLogoDaEquipeVisitante);
			LOGGER.info("Url do logo da equipe da Visitante: {}", urlLogoDaEquipeVisitante);
			
			return partida;
			
			
		}
		catch (IOException e) {
			
			LOGGER.error("Erro ao tentar conectar no google com JSoup -> {}", e.getMessage());
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	
	// Ira buscar os estados das partidas
	public StatusPartida obtendoStatusPartida(Document document) {
		
		// Situações
		// 1 - Partida não iniciada
		// 2 - Partida inicia / jogo rolando / intervalo
		// 3 - Partida encerrada
		// 4 - Penalidades
		
		StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
		
		boolean isTempoPartida =  document.select(DIV_PARTIDA_ANDAMENTO).isEmpty();
		if(!isTempoPartida) {
			String tempoPartida = document.select(DIV_PARTIDA_ANDAMENTO).first().text();
			statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;
			
			//Se por acaso estiver nos pênaltis
			if(tempoPartida.contains(PENALTIS)) {
				statusPartida = StatusPartida.PARTIDA_PENALTIS;
				
			}
			//LOGGER.info(tempoPartida);
			
		}
		
		isTempoPartida = document.select(DIV_PARTIDA_ENCERRADA).isEmpty();
		if(!isTempoPartida) {
			statusPartida = StatusPartida.PARTIDA_ENCERRADA;
			
		}
		//LOGGER.info(statusPartida.toString());
		
		return statusPartida;
	
	}
	
	public String obtendoTempoDaPartida(Document document) {
		
		String tempoDaPartida = null;
		
		// Jogo rolando, intervalo ou penalidades
		boolean isTempoDaPartida = document.select(DIV_PARTIDA_ANDAMENTO).isEmpty();
		if(!isTempoDaPartida) {
			tempoDaPartida = document.select(DIV_PARTIDA_ANDAMENTO).first().text();
		}
		
		isTempoDaPartida = document.select(DIV_PARTIDA_ENCERRADA).isEmpty();
		if(!isTempoDaPartida) {
			tempoDaPartida = document.select(DIV_PARTIDA_ENCERRADA).first().text();
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
	
	public String recuperaNomeDaEquipe(Document document, String itemHtml) {
		Element elemento =  document.selectFirst(itemHtml);
		String nomeEquipe = elemento.select(SPAN).text();
		
		return nomeEquipe;
	}


	public String recuperaLogoDaEquipe(Document document, String itemHtml) {
		Element elemento = document.selectFirst(itemHtml);
		String urlLogoVisitante = HTTPS + elemento.select(ITEM_LOGO).attr(SRC);
		
		return urlLogoVisitante;
	
	}
	
	// Por mais que o método seja um int, sempre que pegamos algos em paginas html, etc. Eles vem como String.
	// Por isso é necessário converter de string para int.
	public Integer recueprarPlacarDaEquipe(Document document, String itemHtml) {
		String placarEquipe = document.selectFirst(itemHtml).text();
		
		//Já está retornando convertido, irá devolver um int.
		return formataPlacarStringInteger(placarEquipe);

	}
	
	// Recupera todos os itens de uma div
	public String recuperaGolsEquipe(Document document, String itemHtml) {
		List<String> golsEquipeCasa = new ArrayList<>();
		
		Elements elementos = document.select(itemHtml).select(ITEM_GOL);
		
		for(Element e : elementos) {
			String infoGol = e.select(ITEM_GOL).text();
			golsEquipeCasa.add(infoGol);// é uma lisata, porém não iremos devolver uma lista no return.
			
		}
		
		//golsEquipeCasa.toString();
		
		// Vai pegar cada elemento dessa lista, juntar tudo em uma string separando  por virgura e espaço.
		return String.join(", ", golsEquipeCasa);
	}
	
	
	public Integer buscaPenalidades(Document document, String tipoEquipe) {
		boolean isPenalidades = document.select(DIV_PENALIDADES).isEmpty();
		
		if(!isPenalidades) {
			String penalidades = document.select(DIV_PENALIDADES).text();
			String penalidadesCompleta = penalidades.substring(0, 5).replace(" ", "");
			String[] divisao = penalidadesCompleta.split("-");
			
			return tipoEquipe.equals(CASA) ? formataPlacarStringInteger(divisao[0]) : formataPlacarStringInteger(divisao[1]);
		}
		
		// Esse return é o resultado que irá mostrar nos placar dos pênaltis.
		return 0;
		
	}
	
	// Tratando possível erro de inializar com null
	public Integer formataPlacarStringInteger(String placar) {
		
		Integer valor;
		
		try {
			valor = Integer.parseInt(placar);
		
		}
		catch(Exception e){
			valor = 0;	
			
		}
		return valor;
		
	}
	
	// Montando a utl de forma dinâmica 
	public String montandoUrlGoogle(String nomeEquipeCasa, String nomeEquipeVisitante) {
		
		try {
			String equipeCasa = nomeEquipeCasa.replace(" ", "+").replace("-", "+");
			String equipeVisitante = nomeEquipeVisitante.replace(" ", "+").replace("-", "+");
			
			return BASE_URL_GOOGLE + equipeCasa + "+ X +" + equipeVisitante + COMPLEMENTO_URL_GOOGLE;
		}
		catch(Exception e) {
			LOGGER.error("ERROR: {}", e.getMessage());
			
		}
		
		return null;
		
	}
}



















