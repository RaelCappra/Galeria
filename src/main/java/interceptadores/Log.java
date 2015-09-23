package interceptadores;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AfterCall;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import controller.IndexController;
import controller.UsuarioController;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import sessao.UsuarioSessao;

@Intercepts // para testar o login
public class Log {
    @Inject
    private HttpServletRequest request;
      
    @Inject
    private UsuarioSessao usuarioSessao; 
    
    @Inject
    private Result result; 
    
    @Accepts
    public boolean accept(ControllerMethod m) {
        return ! m.getController().getType().equals(IndexController.class);
    }

    @BeforeCall
    public void interceptaAntes() throws SQLException {
        if (!usuarioSessao.isLogged()){
          this.result.include("mensagem", "Você precisa se logar para acessar esse serviço  ");
          this.result.redirectTo(IndexController.class).index();
        }
        System.out.println("----------------------------------------");
        System.out.println("Aqui intercepta todos os metodos de qualquer controller antes de qualquer coisa...");
        System.out.println("URL:"+request.getRequestURI());
        System.out.println("----------------------------------------");
    
    }
    
    @AfterCall
    public void interceptaDepois(){
        System.out.println("Depois....");
    }
    
    @AroundCall
    public void intercept(SimpleInterceptorStack stack) {
        // código a ser executado antes da lógica
        System.out.println("=============================================");
        System.out.println("Interceptando " + request.getRequestURI());
//        System.out.println("Adicionado?"+logDAO.insert(request.getRequestURI()));    
        System.out.println("===================");
        stack.next(); // continua a execução
        System.out.println("Depois......");
    }   
  
}