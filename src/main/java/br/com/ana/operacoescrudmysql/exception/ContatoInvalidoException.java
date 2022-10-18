package br.com.ana.operacoescrudmysql.exception;

public class ContatoInvalidoException extends RuntimeException {
	
	public static final long serialVersionUID = 1L;
	
	public ContatoInvalidoException (String mensagem) {
		super(mensagem);
	}
	
	

}
