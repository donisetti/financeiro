package br.com.financeiro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Permissao;
import br.com.financeiro.dominio.Usuario;

@Component
public class PermissaoDao extends AbstractDaoImp<Permissao> {
	public PermissaoDao() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@SuppressWarnings("unchecked")
	public List<Permissao> findUsuario(Usuario usuario, Session session) {
		List<Permissao> retorno = new ArrayList<Permissao>();
		joinSession(session);
		retorno = s.createQuery("from Permissao p where p.usuario.id = '" + usuario.getId() + "'").list();		
		return retorno;
	}	
}
