/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletContext;
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
    
    private static final String UPLOAD_DIR = "uploads";
    
 
    public void addImagem( Imagem imagem, long galeriaId, UploadedFile file) throws FileNotFoundException, IOException {
	if(!sessao.getIdsPermitidosDeGalerias().contains(galeriaId)){
            result.redirectTo(UsuarioController.class).listaGalerias();
            return;
        }else{
        
            if (null != file) {
                //String extensao = file.getFileName().substring(file.getFileName().indexOf("."), file.getFileName().length());
                String tipo = file.getContentType();

                if(!ACCEPTED_TYPES.contains(tipo)){
                    result.include("mensagem", "Erro: tipo ilegal de imagem");
                    result.redirectTo(UsuarioController.class).listaGalerias();
                }
                String extensao = tipo.split("/")[1];
                imagem.setExtensao(extensao);
                imagem.setGaleria(galeriaDao.getById(galeriaId));
                Long id = imagemDao.saveReturningId(imagem);
                //TODO realpath usando servletContext
                String realPath = "/home/aluno/Galeria/src/main/webapp";

                String fullName = realPath + "/" + UPLOAD_DIR + "/" + id + "." + extensao;

                File f = new File(fullName);

                IOUtils.copyLarge(file.getFile(), new FileOutputStream(f));

                // TODO: mensagem
                result.include("mensagem", "O arquivo foi adicionado");

                this.result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);

            } else {
                result.include("mensagem", "Nenhum arquivo foi selecionado...");
                this.result.redirectTo(UsuarioController.class).viewGaleria(galeriaId);
            }
        }
    }
}
