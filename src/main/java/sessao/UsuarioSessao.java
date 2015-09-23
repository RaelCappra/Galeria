package sessao;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import model.Usuario;

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
}
