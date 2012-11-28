package br.com.financeiro.service;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.dominio.Usuario;

@Component
@SessionScoped
public class UsuarioLogadoService {
	@Inject
	private UsuarioDao usuarioDao;	
	
	public UsuarioLogadoService() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario getUsuario(){
		Usuario usuario = new Usuario();
		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				if (authentication instanceof Authentication) {					
					if (authentication.getPrincipal() instanceof String){
						System.out.println("Usuário Anônimo <==============");						
					} else {								
						usuario.setEmail(((User) authentication.getPrincipal()).getUsername());													
						usuario = usuarioDao.findByEmail(usuario.getEmail());																										
					}
				}
			}
		}		
		return usuario;
	}
}
