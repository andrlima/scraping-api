package br.com.exemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.exemplo.entity.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long>{
	

}
