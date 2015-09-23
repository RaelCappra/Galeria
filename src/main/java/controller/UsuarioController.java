package controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import java.util.List;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import model.Galeria;
import model.Imagem;
import model.Usuario;
import persistencia.GaleriaDao;
import persistencia.ImagemDao;
import sessao.UsuarioSessao;

@Controller
@Any
@Default
public class UsuarioController {

    @Inject
    private Result result;

    @Inject
    private UsuarioSessao sessao;
    
    @Inject
    private GaleriaDao galeriaDao;
    @Inject
    private ImagemDao imagemDao;
    
    
    public List<Galeria> listaGalerias() {
	Usuario usuario = sessao.getUsuario();
	List<Galeria> galerias = galeriaDao.listByUsuario(usuario);
	result.include("nome", usuario.getNome());
	return galerias;
    }
    
    @Path("usuario/viewGaleria/{id}")
    public List<Imagem> viewGaleria(Long id){
        if(!sessao.getIdsPermitidosDeGalerias().contains(id)){
            result.redirectTo(UsuarioController.class).listaGalerias();
            result.include("mensagem", "Acesso Negado");
            return null;
        }else{
            Galeria galeria = galeriaDao.getById(id);
            List<Imagem> imagens = imagemDao.listByGaleria(galeria);
            result.include("galeria", galeria);
            return imagens;
        }
    }
    
    public void addGaleria(Galeria galeria){
        
	galeria.setUsuario(sessao.getUsuario());
	galeriaDao.save(galeria);
	result.redirectTo(this).listaGalerias();
    }

    @Path({"/usuario/logout", "/usuario/logout/"})
    public void logout(){
	this.sessao.logout();
	this.result.redirectTo(IndexController.class).index();

    }
}
