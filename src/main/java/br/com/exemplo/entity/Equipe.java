package br.com.exemplo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 


// Importado do Lombok
// Importados do  persistence
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // Entity informa que essa é a entidade do banco de dados.
@Table(name = "equipe") // Table cria uma tabela 
public class Equipe implements Serializable {
	
	// Default do serial version ID
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "equipe_id")
	private Long id;
	
	@Column(name = "nome_equipe")
	private String nomeEquipe;
	
	@Column(name = "url_logo_equipe")
	private String urlLogoEquipe;

}
