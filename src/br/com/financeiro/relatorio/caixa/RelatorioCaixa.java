package br.com.financeiro.relatorio.caixa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import br.com.financeiro.service.UsuarioLogadoService;

public class RelatorioCaixa extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoService usuarioLogado;
	public static Connection con;
	public static String database = "financeiro";
	public static String user = "postgres";
	public static String password = "postgres";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		conexao(); // Faz a conex�o
		// Na variavel pathJasper ficara o caminho do diret�rio para
		// os relat�rios compilados (.jasper)
		String pathJasper = getServletContext().getRealPath("/WEB-INF/classes/br/com/financeiro/relatorio/caixa/")+"/";
		// A variavel path armazena o caminho real para o contexto
		// isso � util pois o seu web container pode estar instalado em lugares
		// diferentes
		String path = getServletContext().getRealPath("/sistema/relatorio");
		// Parametros do relatorio
		Map parametros = new HashMap();
		// O �nico parametro que devemos passar � o PathRelAutores
		// � o caminho pro RelAutores.jasper (que foi subtituido pelo
		// valor fixo �D:/iReport-0.4.0/RelAutores.jasper�)		
		parametros.put("PathRelatorioCaixa", pathJasper + "caixa.jasper");
		parametros.put("IdUsuario", usuarioLogado.getUsuario().getId());
		try {
			// Aqui ele cria o relat�rio
			JasperPrint impressao = JasperFillManager.fillReport(pathJasper + "caixa.jasper", parametros, con);
			// Grava o relat�rio em disco em pdf
			JasperExportManager.exportReportToPdfFile(impressao, path + "/caixa.pdf");
			// Redireciona para o pdf gerado
			resp.sendRedirect("caixa.pdf");
		} catch (Exception e) {
			resp.getWriter().println("Erro ao gerar o relat�rio: " + e);
		}
	}

	public void conexao() {
		try {
			if (con == null || con.isClosed()) {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, user, password);
			}
		} catch (Exception e) {
			System.out.println("n�o foi poss�vel conectar ao banco ->");
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}
