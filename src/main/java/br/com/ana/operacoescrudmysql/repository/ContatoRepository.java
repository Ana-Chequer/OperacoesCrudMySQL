package br.com.ana.operacoescrudmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ana.operacoescrudmysql.domain.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
	
}
	
	
	
