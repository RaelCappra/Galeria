package sessao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import model.Galeria;
import model.Usuario;
import persistencia.GaleriaDao;

@SessionScoped
public class UsuarioSessao implements Serializable {

    private Usuario user;

    public UsuarioSessao() {

    }

    public Usuario getUsuario() {
        return user;
    }

    public void setUsuario(Usuario user) {
        this.user = user;
    }

    public boolean isLogged() {
        return user != null;
    }

    public void logout() {
        user = null;
    }
    
    public List<Long> getIdsPermitidosDeGalerias(){
        List<Long> result = new ArrayList<>();
        
        if(user == null){
            return result;
        }
        GaleriaDao galeriaDao = new GaleriaDao();
        List<Galeria> galerias = galeriaDao.listByUsuario(user);
        for (Galeria g : galerias){
            result.add(g.getId());
        }
        
        return result;
    }
}
