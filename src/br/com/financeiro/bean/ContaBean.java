package br.com.financeiro.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Conta;
import br.com.financeiro.dominio.Estabelecimento;
import br.com.financeiro.dominio.Operacao;
import br.com.financeiro.dominio.Parcela;
import br.com.financeiro.dominio.TipoConta;
import br.com.financeiro.dominio.Usuario;
import br.com.financeiro.service.ContaService;

@Component
@Scope("session")
public class ContaBean extends AbstractBean{
	@Inject
	private ContaService contaService;
	private Conta conta;
	private Operacao[] operacoes;
	private List<Conta> contas;
	private Parcela parcela;
	
	public void resetar(){
		conta = new Conta();
		conta.setEstabelecimento(new Estabelecimento());
		conta.setTipoConta(new TipoConta());
		conta.setUsuario(new Usuario());
		conta.setData(new Date());
		conta.setParcelas(new ArrayList<Parcela>());
	}
	
	public void resetarParcela(){
		parcela = new Parcela();
		parcela.setData(new Date());
		parcela.setValor(0);
		parcela.setConta(new Conta());
		parcela.setParcela(0);
	}
	
	public String novaDebito(){
		resetar();
		conta.setOperacao(Operacao.DEBITO);
		return "/sistema/controle/cadconta";		
	}
	
	public String novaCredito(){
		resetar();
		conta.setOperacao(Operacao.CREDITO);
		return "/sistema/controle/cadconta";		
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
			return "index";
		}
	}
	
	public String listaconta(){
		carregarContas();
		return "/sistema/controle/listaconta";
	}
	
	public String parcelar(){
		return "parconta";
	}
	
	public void carregarContas(){
		contas = contaService.findContas(Operacao.DEBITO);
	}
	
	public void addParcela(ActionEvent event){
		try {
			parcela.setConta(conta);
			parcela.setParcela(conta.getParcelas().size()+1);
			conta.getParcelas().add(parcela);		
			contaService.saveOrUpdate(conta);
			resetarParcela();			
			displayInfoMessageToUser("Parcela adicionada com Sucesso!");			
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao salvar parcela. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String deleteParcela(){
		try {					
			contaService.deleteParcela(conta, parcela);
			resetarParcela();			
			displayInfoMessageToUser("Parcela removida com Sucesso!");
			return "";
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao remover parcela. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return "";
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
	
	public List<Conta> getContas() {
		if (contas == null){
			contas = contaService.findContas(Operacao.DEBITO);
		}
		return contas;
	}
	
	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
	
	public Parcela getParcela() {
		if (parcela == null){
			resetarParcela();
		}
		return parcela;
	}
	
	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}
}
