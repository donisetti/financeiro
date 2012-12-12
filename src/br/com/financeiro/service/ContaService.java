package br.com.financeiro.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.financeiro.dao.ContaDao;
import br.com.financeiro.dao.EstabelecimentoDao;
import br.com.financeiro.dao.ParcelaDao;
import br.com.financeiro.dao.TipoContaDao;
import br.com.financeiro.dominio.Conta;
import br.com.financeiro.dominio.Operacao;
import br.com.financeiro.dominio.Parcela;

@Component
public class ContaService {
	@Inject
	private ContaDao contaDao;
	@Inject	
	private ParcelaDao parcelaDao; 
	@Inject
	private UsuarioLogadoService usuarioLogadoService;
	@Inject 
	private TipoContaDao tipoContaDao;
	@Inject
	private EstabelecimentoDao estabelecimentoDao;
	

	public void saveOrUpdate(Conta conta) {
		// TODO Auto-generated method stub
		conta.setUsuario(usuarioLogadoService.getUsuario());		
		contaDao.beginTransaction();				
		estabelecimentoDao.joinSession(contaDao.getSession());
		tipoContaDao.joinSession(contaDao.getSession());
		parcelaDao.joinSession(contaDao.getSession());
		conta.setEstabelecimento(estabelecimentoDao.find(conta.getEstabelecimento().getId()));
		conta.setTipoConta(tipoContaDao.find(conta.getTipoConta().getId()));
		for (Parcela parcela : conta.getParcelas()) {		
			parcelaDao.saveOrUpdate(parcela);
		}
		contaDao.saveOrUpdate(conta);
		contaDao.commitAndCloseTransaction();
	}


	public List<Conta> findContas(Operacao operacao) {
		// TODO Auto-generated method stub
		List<Conta> retorno;
		contaDao.beginTransaction();
		parcelaDao.joinSession(contaDao.getSession());
		retorno = contaDao.findContasByOperacao(operacao, usuarioLogadoService.getUsuario());
		for (Conta conta : retorno) {
			conta.setParcelas(parcelaDao.findByConta(conta));
		}			
		contaDao.commitAndCloseTransaction();		
		return retorno;
	}


	public void deleteParcela(Conta conta, Parcela parcela) {
		// TODO Auto-generated method stub
		contaDao.beginTransaction();
		conta.getParcelas().remove(parcela);
		parcelaDao.joinSession(contaDao.getSession());		
		for (Parcela p : conta.getParcelas()) {		
			parcelaDao.saveOrUpdate(p);
		}
		parcelaDao.delete(parcela);
		contaDao.saveOrUpdate(conta);
		contaDao.commitAndCloseTransaction();		
	}


//	public List<Parcela> findParcelas(Conta conta) {
//		// TODO Auto-generated method stub
//		List<Parcela> lista;
//		parcelaDao.beginTransaction();
//		lista = parcelaDao.findByConta(conta);
//		parcelaDao.commitAndCloseTransaction();
//		return lista;
//	}
	
}
