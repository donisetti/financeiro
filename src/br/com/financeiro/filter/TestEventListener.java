package br.com.financeiro.filter;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class TestEventListener implements ApplicationListener {	
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		if (event instanceof AuthenticationSuccessEvent) {
			System.out.println("Usu�rio autenticado com sucesso");			
		}
		if (event instanceof AbstractAuthenticationFailureEvent) {
			System.out.println("Usu�rio n�o autenticado Geral");
		}
		if (event instanceof AuthenticationFailureBadCredentialsEvent) {
			System.out.println("Usu�rio n�o autenticado Usu�rio e Senha");
		}
		if (event instanceof AuthenticationFailureCredentialsExpiredEvent) {
			System.out.println("Usu�rio n�o autenticado Crediciais Expiradas");
		}
	}
}
