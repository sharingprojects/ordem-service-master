package br.com.icastell.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.icastell.osworks.domain.exception.DomainException;
import br.com.icastell.osworks.domain.model.Cliente;
import br.com.icastell.osworks.domain.repository.ClienteRepository;

//adicionando essa anotation do spring: @Service, se torna um componente do spring
//e indica que essa classe é um serviço (um domain service). O spring vai instanciar um objeto
//dessa classe, e essa objeto vai se tornar disponível para ser injetado em qualquer outro componente do spring:
//como por exemplo no ClienteController
@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienterepository;

	public Cliente salvar(Cliente cliente) {

		Cliente clienteExistente = clienterepository.findByEmail(cliente.getEmail());

		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new DomainException("E-mail já cadastrado!");
		}

		return clienterepository.save(cliente);

	}

	public void excluir(Long clienteId) {
		clienterepository.deleteById(clienteId);
	}

}
