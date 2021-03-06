package br.com.exemplo.dto;

import java.io.Serializable;

import br.com.exemplo.util.StatusPartida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO = Data Transfer Object

//O lombok esta criando os gatters, setters e o método construtor vazío e o completo, por isso não foi criado a baixo das variáveis

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaGoogleDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private StatusPartida statusPartida;
	private String tempoPartida;
	
	// Informações da equipe da casa 
	private String nomeEquipeCasa;
	private String urlLogoEquipeCasa;
	private Integer placarEquipeCasa;
	private String golsEquipeCasa;
	private Integer placarEstendidoEquipeCasa;
	
	// Informações da equipe visitante
	private String nomeEquipeVisitante;
	private String urlLogoEquipeVisitante;
	private Integer placarEquipeVisitante;
	private String golsEquipeVisitante;
	private Integer placarEstendidoEquipeVisitante;
	
	
	
	
	

}
