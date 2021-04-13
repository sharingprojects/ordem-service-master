package br.com.icastell.osworks.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

//includes only non-null fields when displaying the message in Json
@JsonInclude(Include.NON_NULL)
@Data
public class Problem {

	@Data
	public static class Field {

		private String nome;
		private String msg;

		public Field(String nome, String msg) {
			super();
			this.nome = nome;
			this.msg = msg;
		}		

	}

	private Integer status;	
	private LocalDateTime dataHora;
	private String titulo;

	private List<Field> fields;


}