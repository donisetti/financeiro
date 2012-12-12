package br.com.financeiro.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Conta;
import br.com.financeiro.dominio.Parcela;

@Component
public class ParcelaDao extends AbstractDaoImp<Parcela> {
	public ParcelaDao() {
		// TODO Auto-generated constructor stub
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Parcela> findByConta(Conta conta) {
		// TODO Auto-generated method stub		
		return s.createQuery("from Parcela p where p.conta.id = '" + conta.getId() + "' order by parcela").list();
	}
}
