package controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import model.Galeria;
import model.Imagem;
import model.Usuario;
import org.apache.commons.io.IOUtils;
import persistencia.GaleriaDao;
import persistencia.ImagemDao;
import sessao.UsuarioSessao;

@Controller
@Any
@Default
public class UsuarioController {

    @Inject
    private ServletContext servletContext;

    @Inject
    private Result result;

    @Inject
    private UsuarioSessao sessao;

    @Inject
    private GaleriaDao galeriaDao;
    @Inject
    private ImagemDao imagemDao;

    private static final String EASTER_EGG = "PRAISELORDS";

    public List<Galeria> listaGalerias() {
	Usuario usuario = sessao.getUsuario();
	List<Galeria> galerias = galeriaDao.listByUsuario(usuario);
	result.include("nome", usuario.getNome());
	return galerias;

    }

    @Path("usuario/viewGaleria/{id}")
    public List<Imagem> viewGaleria(Long id) {
	if (!sessao.getIdsPermitidosDeGalerias().contains(id)) {
	    result.redirectTo(UsuarioController.class).listaGalerias();
	    result.include("mensagem", "Acesso Negado");
	    return null;
	} else {
	    Galeria galeria = galeriaDao.getById(id);
	    List<Imagem> imagens = imagemDao.listByGaleria(galeria);
	    result.include("galeria", galeria);
	    return imagens;
	}
    }

    public void addGaleria(Galeria galeria) {
	galeria.setUsuario(sessao.getUsuario());
	//if(false){
	
	if (EASTER_EGG.equals(galeria.getNome())) {
	    //TODO: Easter egg
	    long galeriaId = galeriaDao.saveReturningId(galeria);
	    
	    String pathEasterEgg = servletContext.getRealPath("/");
	    pathEasterEgg += "/assets/eegg";
	    String pathUpload = servletContext.getRealPath("/");
	    pathUpload += "/" + GaleriaController.UPLOAD_DIR;
	    File pastaEasterEgg = new File(pathEasterEgg);
	    for (File file : pastaEasterEgg.listFiles()) {
		String fileName = file.getName();
		
		Imagem imagem = new Imagem();
		String nome = fileName.split("\\.")[0].substring(1);
		String extensao = fileName.split("\\.")[1];
		imagem.setNome(nome);
		imagem.setExtensao(extensao);
		imagem.setGaleria(galeriaDao.getById(galeriaId));
		Long id = imagemDao.saveReturningId(imagem);
		
		File outputFile = new File(pathUpload, String.valueOf(id) + "." + extensao);
		try(FileInputStream fis = new FileInputStream(file);
		    FileOutputStream fos = new FileOutputStream(outputFile)){
		    IOUtils.copyLarge(fis, fos);
		}
		catch(IOException e){
		    
		}
	    }
	    
	    result.include("mensagem", "Em homenagem aos serviços que nos salvaram"
		+ " ao longo deste curso:");
	    result.redirectTo(this).viewGaleria(galeriaId);
	} else {
	    galeriaDao.save(galeria);
	    result.redirectTo(this).listaGalerias();
	}
    }

    public void editGaleria(Galeria galeria) {
	if (!sessao.getIdsPermitidosDeGalerias().contains(galeria.getId())) {
	    result.include("mensagem", "Acesso Negado");
	    result.redirectTo(UsuarioController.class).listaGalerias();

	    return;
	}
	galeriaDao.edit(galeria.getId(), galeria.getNome());
	result.redirectTo(this).listaGalerias();
    }

    @br.com.caelum.vraptor.Path("usuario/deleteGaleria/{id}/")
    public void deleteGaleria(Long id) {
	if (!sessao.getIdsPermitidosDeGalerias().contains(id)) {
	    result.include("mensagem", "Acesso Negado");
	    result.redirectTo(UsuarioController.class).listaGalerias();

	    return;
	}

	galeriaDao.softDelete(id);
	result.redirectTo(this).listaGalerias();
    }

    @Path({"/usuario/logout", "/usuario/logout/"})
    public void logout() {
	this.sessao.logout();
	this.result.redirectTo(IndexController.class).index();

    }
}
