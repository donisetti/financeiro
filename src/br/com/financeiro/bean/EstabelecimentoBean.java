package br.com.financeiro.bean;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Estabelecimento;
import br.com.financeiro.service.EstabelecimentoService;

@Component
@Scope("session")
public class EstabelecimentoBean extends AbstractBean {
	@Inject
	private EstabelecimentoService estabelecimentoService;

	private List<Estabelecimento> estabelecimentos;
	private Estabelecimento estabelecimento;

	public String cadastrar() {
		resetar();
		return "cadestabelecimento";
	}

	public String cancelar() {
		resetar();
		return "estabelecimento";
	}

	public String editar() {
		return "cadestabelecimento";
	}

	public String salvar() {
		try {
			estabelecimentoService.saveOrUpdate(estabelecimento);
			displayInfoMessageToUser("Sucesso ao criar estabelecimento.");
			carregar();
			resetar();
			return "estabelecimento";
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao salvar estabelecimento. Tente novamente.\nDetalhes:"
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public String deletar() {
		try {
			estabelecimentoService.delete(estabelecimento);
			closeDialog();
			displayInfoMessageToUser("Sucesso ao deletar estabelecimento.");
			carregar();
			resetar();
			return "estabelecimento";
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao deletar estabelecimento. Tente novamente.\nDetalhes:"
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public void resetar() {
		estabelecimento = new Estabelecimento();
	}

	public void carregar() {
		estabelecimentos = estabelecimentoService.findAll();
	}

	public List<Estabelecimento> getEstabelecimentos() {
		if (estabelecimentos == null) {
			carregar();
		}
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public Estabelecimento getEstabelecimento() {
		if (estabelecimento == null) {
			estabelecimento = new Estabelecimento();
		}
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

}
