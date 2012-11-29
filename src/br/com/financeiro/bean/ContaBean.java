package br.com.financeiro.bean;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Conta;
import br.com.financeiro.dominio.Estabelecimento;
import br.com.financeiro.dominio.Operacao;
import br.com.financeiro.dominio.TipoConta;
import br.com.financeiro.dominio.Usuario;
import br.com.financeiro.service.ContaService;

@Component
public class ContaBean extends AbstractBean{
	@Inject
	private ContaService contaService;
	private Conta conta;
	private Operacao[] operacoes;
	
	public void resetar(){
		conta = new Conta();
		conta.setEstabelecimento(new Estabelecimento());
		conta.setTipoConta(new TipoConta());
		conta.setUsuario(new Usuario());
		conta.setData(new Date());
	}
	
	public String salvar(){
		try {			
			contaService.saveOrUpdate(conta);			
			displayInfoMessageToUser("Sucesso ao criar conta.");
			resetar();
			return "index";
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao salvar conta. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String cancelar(){
		return "index";
	}
	
	public Conta getConta() {
		if (conta == null){
			resetar();
		}
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public Operacao[] getOperacoes() {
		operacoes = Operacao.values();
		return operacoes;
	}
	public void setOperacoes(Operacao[] operacoes) {
		this.operacoes = operacoes;
	}
	
	
}
