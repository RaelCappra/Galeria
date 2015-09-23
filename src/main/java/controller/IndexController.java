package controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import java.io.IOException;
import javax.inject.Inject;
import sessao.UsuarioSessao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;
import org.apache.commons.io.IOUtils;
import persistencia.UsuarioDao;

@Controller
public class IndexController {

    @Inject
    private Result result;

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    private UsuarioSessao sessao;

    private static final String DIR_DOWNLOAD = "/media/iapereira/Dados/Dropbox/ifrs/Link para NetBeansProjects/vraptor4CRUD/src/main/webapp/WEB-INF/download/";

    @Path("/")
    public void index() {
	if (sessao.isLogged()) {
	    result.redirectTo(UsuarioController.class).listaGalerias();
	} else {
	    
	}
    }

    public void login(String email, String senha) throws SQLException {
	System.out.println("email: " + email);
	System.out.println("senha: " + senha);
	boolean resultado = this.usuarioDao.autentica(email, senha);
	if (resultado) {
            Usuario usuario = usuarioDao.getByEmail(email);
	    this.sessao.setUsuario(usuario);
	    this.result.redirectTo(UsuarioController.class).listaGalerias();
	} else {
	    this.result.include("mensagem", "Acesso negado");
	    this.result.redirectTo(this).index();
	}
    }

    
}
