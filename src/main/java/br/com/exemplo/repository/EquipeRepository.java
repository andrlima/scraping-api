package br.com.exemplo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.exemplo.entity.Equipe;


//Essa função faz o crud, porém ainda não entendi como realmente funcionar.
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
	
	//Recupera a equipe por nome e valida de a equipe existe pelo nome
	public Optional<Equipe> findByNomeEquipe(String nomeEquipe);
	
	public boolean existsByNomeEquipe(String nomeEquipe);
}
