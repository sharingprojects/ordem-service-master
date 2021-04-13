package br.com.icastell.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.icastell.osworks.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	List<Cliente> findByNome(String nome);	
	//Containing = find all who has the word that was passed by parameter
	List<Cliente> findByNomeContaining(String nome);	
	Cliente findByEmail(String email);
}