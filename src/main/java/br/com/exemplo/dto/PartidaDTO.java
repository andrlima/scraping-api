package br.com.exemplo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String nomeEquipe;
	
	@NotBlank
	private String nomeEquipeVisiatante;
	
	@NotBlank
	private String localPartida;
	
	@NotNull
	@ApiModelProperty(example = "dd/mm/yyyy hh:mm")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy hh:mm", timezone = "America/Sao_Paulo" )
	private String dataHoraPartida;
	
	
}
