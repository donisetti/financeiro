package br.com.financeiro.ws;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

@WebService(endpointInterface="br.com.financeiro.ws.MensagemWs")
@Component
public class MensagemWsImpl implements MensagemWs{

	@Override
	public String mensagem(String nome) {
		// TODO Auto-generated method stub
		System.out.println("MensagemWs: "+nome);
		return "Hello "+nome;
	}
	
}
