package br.com.financeiro.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Usuario;

@Component
public class UsuarioDao extends AbstractDaoImp<Usuario>{
	public UsuarioDao() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@SuppressWarnings("unchecked")
	public Usuario findByEmail(String email) {
		s = getSessionFactory().openSession();
		List<Usuario> lista = s.createQuery("from Usuario where email = '" + email + "'").list();
		s.close();
		Usuario u = new Usuario();
		if (lista.size() > 0){
			u = lista.get(0);
		}
		return u;
	}	
}
