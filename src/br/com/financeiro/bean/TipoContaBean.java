package br.com.financeiro.bean;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.TipoConta;
import br.com.financeiro.service.TipoContaService;

@Component
@Scope("session")
public class TipoContaBean extends AbstractBean {
	@Inject
	private TipoContaService tipoContaService;
	private List<TipoConta> tiposConta;
	private TipoConta tipoConta;

	public String cadastrar(){
		resetar();	
		return "cadtipoconta";
	}
	
	public String cancelar(){
		resetar();
		return "tipoconta";
	}
	
	public String salvar(){
		try {			
			tipoContaService.saveOrUpdate(tipoConta);			
			displayInfoMessageToUser("Sucesso ao criar tipo de conta.");
			carregar();
			resetar();
			return "tipoconta";
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao salvar tipo de conta. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}	
	
	public String deletar(){
		try {
			tipoContaService.delete(tipoConta);			
			closeDialog();
			displayInfoMessageToUser("Sucesso ao deletar tipo de conta.");
			carregar();
			resetar();
			return "tipoconta";
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao deletar tipo de conta. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String editar(){
		return "cadtipoconta";
	}
	
	public void resetar() {
		tipoConta = new TipoConta();
	}

	public void carregar() {
		tiposConta = tipoContaService.findAll();
	}

	public List<TipoConta> getTiposConta() {
		if (tiposConta == null) {
			carregar();
		}
		return tiposConta;
	}

	public void setTiposConta(List<TipoConta> tiposConta) {
		this.tiposConta = tiposConta;
	}

	public TipoConta getTipoConta() {
		if (tipoConta == null) {
			tipoConta = new TipoConta();
		}
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

}
