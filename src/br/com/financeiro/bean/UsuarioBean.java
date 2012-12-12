package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Papel;
import br.com.financeiro.dominio.Permissao;
import br.com.financeiro.dominio.Usuario;
import br.com.financeiro.service.UsuarioService;
import br.com.financeiro.util.Criptografia;

@Component
@Scope("session")
public class UsuarioBean extends AbstractBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@Inject
	private UsuarioService usuarioService;
	private Usuario usuario;
		
	private List<Usuario> usuarios;	
	private DualListModel<Papel> papeis;
	private List<Papel> livres = new ArrayList<Papel>();
	private Papel papel;
	private Permissao permissao;
	
	public void resetar(){
		usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setPermissao(new ArrayList<Permissao>());
	}
	
	public String cadastrar(){
		resetar();
		return "cadusuario";
	}
	
	public String editar(){
		return "cadusuario";
	}
	
	public String cancelar(){
		resetar();
		return "usuario";
	}
	
	public String permissao(){
		livres = new ArrayList<Papel>();		
		Papel[] listaPapel = Papel.values();		
		for (Papel papel : listaPapel) {
			boolean usado = false;
			for (Permissao permissao : usuario.getPermissao()) {
				if (permissao.getPapel().equals(papel)){
					usado = true;
				}
			}
			if (!usado){
				livres.add(papel);
			}
		}		
		return "perusuario";
	}
	
	public String remPermissao(){		
		try {			
			usuarioService.removePermissao(usuario, permissao);			
			displayInfoMessageToUser("Sucesso ao criar o usuário.");
			carregar();
			resetar();
			return "usuario";
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao salvar usuário. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}				
	}
	
	public String addPapel(){		
		try {
			Permissao p = new Permissao();
			p.setPapel(papel);
			p.setUsuario(usuario);
			usuario.getPermissao().add(p);
			usuarioService.saveOrUpdate(usuario);			
			displayInfoMessageToUser("Sucesso ao criar o usuário.");
			carregar();
			resetar();
			return "usuario";
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao salvar usuário. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}				
	}	
	
	public String inserir(){
		try {
			usuario.setSenha(Criptografia.criptografar(usuario.getSenha()));
			Permissao p = new Permissao();
			p.setUsuario(usuario);
			p.setPapel(Papel.USUARIO);			
			usuario.getPermissao().add(p);
			usuarioService.saveOrUpdate(usuario);			
			displayInfoMessageToUser("Sucesso ao criar o usuário.");
			carregar();
			resetar();
			return "usuario";
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao salvar usuário. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String atualizar(){
		try {
			usuarioService.update(usuario);
			closeDialog();
			displayInfoMessageToUser("Sucesso ao atualizar usuário.");
			carregar();
			resetar();
			return "usuario";
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao atualizar usuário. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}		
	}
	
	public String atualizarSenha(){
		try {
			usuario.setSenha(Criptografia.criptografar(usuario.getSenha()));
			usuarioService.update(usuario);
			closeDialog();
			displayInfoMessageToUser("Sucesso ao atualizar senha.");
			carregar();
			resetar();
			return "usuario";
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao salvar senha. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}		
	}	
	
	public String deletar(){
		try {
			usuarioService.delete(usuario);			
			closeDialog();
			displayInfoMessageToUser("Sucesso ao deletar usuário.");
			carregar();
			resetar();
			return "usuario";
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao deletar usuário. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}		
	}
	
	public String resetarSenha(){
		try {
			usuario.setSenha(Criptografia.criptografar("1"));
			usuarioService.update(usuario);			
			closeDialog();
			displayInfoMessageToUser("Sucesso ao deletar usuário!");
			carregar();
			resetar();
			return "usuario";
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao resetar senha usuário. Tente novamente.\nDetalhes:"+e.getMessage());
			e.printStackTrace();
			return null;
		}		
	}	
	
	public void carregar(){
		usuarios = usuarioService.findAll();
	}	
	
	public Usuario getUsuario() {
		if (usuario == null){
			usuario = new Usuario();			
		}
		return usuario;
	}
	
	public List<Usuario> getUsuarios() {
		if (usuarios == null){
			carregar();
		}
		return usuarios;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public DualListModel<Papel> getPapeis() {
		return papeis;
	}
	
	public void setPapeis(DualListModel<Papel> papeis) {
		this.papeis = papeis;
	}
	
	public List<Papel> getLivres() {
		return livres;
	}
	
	public void setLivres(List<Papel> livres) {
		this.livres = livres;
	}
	
	public Papel getPapel() {
		return papel;
	}
	
	public void setPapel(Papel papel) {
		this.papel = papel;
	}
	
	public Permissao getPermissao() {
		return permissao;
	}
	
	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}
}
