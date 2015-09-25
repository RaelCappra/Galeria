/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import br.com.caelum.vraptor.Controller;
//import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.ByteArrayDownload;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import model.Galeria;
import model.Imagem;
import org.apache.commons.io.IOUtils;
import persistencia.GaleriaDao;
import persistencia.ImagemDao;
import sessao.UsuarioSessao;

/**
 *
 * @author Rael
 */
@Controller
public class GaleriaController {

    //para o realPath
    //private ServletContext servletContext;
/*
     public GaleriaController(ServletContext servletContext) {
     this.servletContext = servletContext;
     }
     */

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

    private static final List<String> ACCEPTED_TYPES = Arrays.asList(
	"image/jpeg",
	"image/png",
	"image/bmp",
	"image/gif"
    );

    static final String UPLOAD_DIR = "uploads";

    public void addImagem(Imagem imagem, long galeriaId, UploadedFile file) throws FileNotFoundException, IOException {
	if (!sessao.getIdsPermitidosDeGalerias().contains(galeriaId)) {
	    result.redirectTo(UsuarioController.class).listaGalerias();
	} else {

	    if (null != file) {
		//String extensao = file.getFileName().substring(file.getFileName().indexOf("."), file.getFileName().length());
		String tipo = file.getContentType();

		if (!ACCEPTED_TYPES.contains(tipo)) {
		    result.include("mensagem", "Erro: tipo ilegal de imagem");
		    result.forwardTo(UsuarioController.class).viewGaleria(galeriaId);
		} else {
		    String extensao = tipo.split("/")[1];
		    imagem.setExtensao(extensao);
		    imagem.setGaleria(galeriaDao.getById(galeriaId));
		    Long id = imagemDao.saveReturningId(imagem);

		    String realPath = servletContext.getRealPath("/");//"/home/aluno/Galeria/src/main/webapp";

		    String fullName = realPath + "/" + UPLOAD_DIR + "/" + id + "." + extensao;

		    File f = new File(fullName);

		    IOUtils.copyLarge(file.getFile(), new FileOutputStream(f));

		    //result.include("mensagem", "O arquivo foi adicionado");
		    this.result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);
		}

	    } else {
		result.include("mensagem", "Nenhum arquivo foi selecionado...");
		this.result.forwardTo(UsuarioController.class).viewGaleria(galeriaId);
	    }
	}
    }

    public void editImagem(Imagem imagem) {
	long galeriaId = imagemDao.getById(imagem.getId()).getGaleria().getId();
	
	if (!sessao.getIdsPermitidosDeGalerias().contains(galeriaId)) {
	    result.include("mensagem", "Acesso Negado");
	    result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);
	    return;
	}
	
	imagemDao.edit(imagem.getId(), imagem.getNome());
	result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);
    }
    
    @br.com.caelum.vraptor.Path("galeria/deleteImagem/{id}/")
    public void deleteImagem(Long id) {
	Imagem imagem = imagemDao.getById(id);
	long galeriaId = imagem.getGaleria().getId();
	
	if (!sessao.getIdsPermitidosDeGalerias().contains(galeriaId)) {
	    result.include("mensagem", "Acesso Negado");
	    result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);
	    return;
	}
	
	imagemDao.softDelete(id);
	result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);
    }

    @br.com.caelum.vraptor.Path("galeria/zipGaleria/{galeriaId}")
    public Download zipGaleria(long galeriaId) {
	if (!sessao.getIdsPermitidosDeGalerias().contains(galeriaId)) {
	    result.include("mensagem", "Acesso Negado");
	    result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);
	    return null;
	}
	Galeria galeria = new Galeria();
	galeria.setId(galeriaId);
	List<Imagem> imagens = imagemDao.listByGaleria(galeria);
	if (imagens == null || imagens.isEmpty()) {
	    result.include("mensagem", "Galeria vazia");
	    result.forwardTo(UsuarioController.class).viewGaleria(galeriaId);
	    return null;
	}

	List<Path> paths = new ArrayList<>();
	for (Imagem imagem : imagens) {
	    String realPath = servletContext.getRealPath("/");
	    java.nio.file.Path imagemPath = new File(realPath + "/" + UPLOAD_DIR + "/" + imagem.getFileName()).toPath();
	    paths.add(imagemPath);
	}

	byte buffer[] = new byte[2048];
	try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ZipOutputStream zos = new ZipOutputStream(baos)) {
	    zos.setMethod(ZipOutputStream.DEFLATED);
	    zos.setLevel(5);
	    for (Path path : paths) {

		try (FileInputStream fis = new FileInputStream(path.toFile());
		    BufferedInputStream bis = new BufferedInputStream(fis)) {
		    String pathFileName = path.getFileName().toString();
		    zos.putNextEntry(new ZipEntry(pathFileName));

		    int bytesRead;
		    while ((bytesRead = bis.read(buffer)) != -1) {
			zos.write(buffer, 0, bytesRead);
		    }

		    zos.closeEntry();
		    zos.flush();
		} catch (IOException e) {
		    result.include("mensagem", "Erro no download do zip");
		    result.forwardTo(UsuarioController.class).viewGaleria(galeriaId);
		    return null;
		}
	    }
	    zos.finish();
	    byte[] zip = baos.toByteArray();

	    Download download = new ByteArrayDownload(zip, "application/zip", sessao.getUsuario().getNome() + ".zip");
	    return download;

	    //zipDownload = new ZipDownload(sessao.getUsuario().getNome() + ".zip", paths);
	    //return zipDownloadBuilder.build();
	} catch (IOException e) {
	    result.include("mensagem", "Erro no download do zip");
	    result.forwardTo(UsuarioController.class).viewGaleria(galeriaId);
	    return null;
	}
    }
}
