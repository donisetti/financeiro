package br.com.financeiro.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.financeiro.dominio.Papel;


@FacesConverter(value="papelConverter", forClass=Papel.class)
public class PapelConverter implements Converter{
	
	private static Map<String, Papel> papeis = new HashMap<String, Papel>();
	
	static {
		papeis.put("ADMINISTRADOR", Papel.ADMINISTRADOR);
		papeis.put("USUARIO", Papel.USUARIO);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return papeis.get(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return arg2.toString();
	}

}
