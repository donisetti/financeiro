package br.com.financeiro.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.financeiro.dao.TipoContaDao;
import br.com.financeiro.dominio.TipoConta;

@Component
public class TipoContaService {
	@Inject
	private TipoContaDao tipoContaDao;

	public void saveOrUpdate(TipoConta tipoConta) {
		// TODO Auto-generated method stub
		tipoContaDao.beginTransaction();
		tipoContaDao.saveOrUpdate(tipoConta);
		tipoContaDao.commitAndCloseTransaction();
	}

	public void delete(TipoConta tipoConta) {
		// TODO Auto-generated method stub
		tipoContaDao.beginTransaction();
		tipoContaDao.delete(tipoConta);
		tipoContaDao.commitAndCloseTransaction();		
	}

	public List<TipoConta> findAll() {
		// TODO Auto-generated method stub
		List<TipoConta> lista;
		tipoContaDao.beginTransaction();
		lista = tipoContaDao.findAll();
		tipoContaDao.commitAndCloseTransaction();
		return lista;
	}
}
