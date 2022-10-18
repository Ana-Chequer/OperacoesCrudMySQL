package br.com.ana.operacoescrudmysql.exception;

public class ContatoNaoEncontradoException extends RuntimeException {
	
	public static final long serialVersionUID = 1L;
	
	public ContatoNaoEncontradoException (String mensagem) {
		super(mensagem);
	}
	
	

}
