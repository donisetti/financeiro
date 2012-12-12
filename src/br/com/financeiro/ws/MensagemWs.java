package br.com.financeiro.ws;

import javax.jws.WebService;

@WebService
public interface MensagemWs {
	String mensagem(String nome);
}
