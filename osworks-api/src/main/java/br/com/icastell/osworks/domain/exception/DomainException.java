package br.com.icastell.osworks.domain.exception;

public class DomainException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	//representa um erro de negócio (domain error)
	public DomainException(String message) {
		super(message);
	}

}