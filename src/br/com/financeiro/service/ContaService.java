package br.com.financeiro.service;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.financeiro.dao.ContaDao;
import br.com.financeiro.dao.EstabelecimentoDao;
import br.com.financeiro.dao.TipoContaDao;
import br.com.financeiro.dominio.Conta;

@Component
public class ContaService {
	@Inject
	private ContaDao contaDao;
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
		conta.setEstabelecimento(estabelecimentoDao.find(conta.getEstabelecimento().getId()));
		conta.setTipoConta(tipoContaDao.find(conta.getTipoConta().getId()));		
		contaDao.saveOrUpdate(conta);
		contaDao.commitAndCloseTransaction();
	}
}
