package br.com.financeiro.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Conta;
import br.com.financeiro.dominio.Operacao;
import br.com.financeiro.dominio.Usuario;

@Component
public class ContaDao extends AbstractDaoImp<Conta>{
	public ContaDao() {
		// TODO Auto-generated constructor stub
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Conta> findContasByOperacao(Operacao operacao, Usuario usuario) {
		// TODO Auto-generated method stub										
		return s.createQuery("from Conta where operacao = '" + operacao + "' and usuario.id = "+usuario.getId()+" order by data desc").list();
	}
}
