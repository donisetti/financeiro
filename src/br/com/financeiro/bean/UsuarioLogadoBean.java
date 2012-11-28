package br.com.financeiro.bean;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.financeiro.dominio.Usuario;
import br.com.financeiro.service.UsuarioLogadoService;

@Component
@Scope("session")
public class UsuarioLogadoBean {
	@Inject
	private UsuarioLogadoService usuarioLogadoService;

	private Usuario usuario;		

	public Usuario getUsuario() {
		usuario = usuarioLogadoService.getUsuario();
		return usuario;
	}		

	public String getNomessesao() {
		return (((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getName());
	}		

}
