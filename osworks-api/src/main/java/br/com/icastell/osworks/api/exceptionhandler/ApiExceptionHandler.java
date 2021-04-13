package br.com.icastell.osworks.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.icastell.osworks.domain.exception.DomainException;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	//MessageSource = Interface do spring usada para resolver mensagens do message
	@Autowired //atribuir uma Instancia do MessageSource na variável: messageSource
	private MessageSource messageSource;

	/**
	 * when the exception is thrown by a controller, it will come to this method 
	 * due to this annotation(@ExceptionHandler(DomainException.class)).
	 * DomainException.class => name class exception 
	 * 
	 * return ResponseEntity => Because we are going to customize the status code, the body code.
	 */
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handlerDomain(DomainException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST; //400 Bad Request.
		var problem = new Problem();
		problem.setStatus(status.value());
	    problem.setTitulo(ex.getMessage());
	    problem.setDataHora(LocalDateTime.now());	

		return super.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		var fields = new ArrayList<Problem.Field>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			//LocaleContextHolder = pegar qual o idioma da região onde estamos
			String msg = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			fields.add(new Problem.Field(nome, msg));
		}

		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitulo("Um ou mais campos estão inválidos. Preencha corretamente!");
		problem.setDataHora(LocalDateTime.now());
		problem.setFields(fields);

		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}

}
