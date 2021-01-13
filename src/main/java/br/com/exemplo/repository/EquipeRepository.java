package br.com.exemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.exemplo.entity.Equipe;


//Essa função faz o crud, porém ainda não entendi como realmente funcionar.
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}
