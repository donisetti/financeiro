package br.com.financeiro.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.financeiro.dao.EstabelecimentoDao;
import br.com.financeiro.dominio.Estabelecimento;

@Component
public class EstabelecimentoService {
	@Inject
	private EstabelecimentoDao estabelecimentoDao;

	public void saveOrUpdate(Estabelecimento estabelecimento) {
		// TODO Auto-generated method stub
		estabelecimentoDao.beginTransaction();
		estabelecimentoDao.saveOrUpdate(estabelecimento);
		estabelecimentoDao.commitAndCloseTransaction();
	}

	public void delete(Estabelecimento estabelecimento) {
		// TODO Auto-generated method stub
		estabelecimentoDao.beginTransaction();
		estabelecimentoDao.delete(estabelecimento);
		estabelecimentoDao.commitAndCloseTransaction();
	}

	public List<Estabelecimento> findAll() {
		// TODO Auto-generated method stub
		List<Estabelecimento> lista;
		estabelecimentoDao.beginTransaction();
		lista = estabelecimentoDao.findAll();
		estabelecimentoDao.commitAndCloseTransaction();
		return lista;
	}
	
}
