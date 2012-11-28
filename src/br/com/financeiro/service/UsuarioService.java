package br.com.financeiro.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.financeiro.dao.PermissaoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.dominio.Permissao;
import br.com.financeiro.dominio.Usuario;

@Component
public class UsuarioService {
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private PermissaoDao permissaoDao;

	public void saveOrUpdate(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioDao.beginTransaction();		
		usuarioDao.saveOrUpdate(usuario);
		permissaoDao.joinSession(usuarioDao.getSession());
		for (Permissao p : usuario.getPermissao()) {			
			permissaoDao.saveOrUpdate(p);
		}
		usuarioDao.commitAndCloseTransaction();
	}

	public void update(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioDao.beginTransaction();
		usuarioDao.update(usuario);
		usuarioDao.commitAndCloseTransaction();
	}

	public void delete(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioDao.beginTransaction();
		usuarioDao.delete(usuario);
		permissaoDao.joinSession(usuarioDao.getSession());
		for (Permissao p : usuario.getPermissao()) {			
			permissaoDao.delete(p);
		}		
		usuarioDao.commitAndCloseTransaction();
	}

	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		List<Usuario> lista;
		usuarioDao.beginTransaction();
		lista = usuarioDao.findAll();
		for (Usuario usuario : lista) {
			usuario.setPermissao(permissaoDao.findUsuario(usuario, usuarioDao.getSession()));
		}
		usuarioDao.commitAndCloseTransaction();
		return lista;
	}

	public void removePermissao(Usuario usuario, Permissao permissao) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		usuarioDao.beginTransaction();	
		usuario.getPermissao().remove(permissao);
		usuarioDao.saveOrUpdate(usuario);
		permissaoDao.joinSession(usuarioDao.getSession());
		for (Permissao p : usuario.getPermissao()) {			
			permissaoDao.saveOrUpdate(p);
			System.out.println("Usada - "+p);
		}
		permissaoDao.delete(permissao);
		usuarioDao.commitAndCloseTransaction();		
	}
}
