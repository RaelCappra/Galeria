package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import model.Usuario;

@RequestScoped
public class UsuarioDao implements Dao<Usuario, Long> {

    private static ConexaoPostgreSQL conexao;

    private static final String TABELA = "usuario";
    
    @Override
    public void save(Usuario entity) {
	String query = "insert into " + TABELA + " (email, nome, senha) values (?, ?, ?)";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setString(1, entity.getEmail());
		ps.setString(2, entity.getNome());
		ps.setString(3, entity.getSenha());
		ps.execute();
	    } catch (SQLException e) {
		//TODO: ERRO: nao foi adicionada o usuario
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @Override
    public void delete(Long id) {
	String query = "delete from " + TABELA + " where id = ?";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, id);
		ps.execute();
	    } catch (SQLException e) {
		//TODO: ERRO: nao foi deletado usuario
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public List<Usuario> listAll() {
	String query = "select * from " + TABELA;
	List<Usuario> result = new ArrayList<>();
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
		    Usuario usuario = new Usuario();
		    long id = rs.getLong("id");
		    String nome = rs.getString("nome");
		    String senha = rs.getString("senha");
		    String email = rs.getString("email");
		    usuario.setId(id);
		    usuario.setNome(nome);
		    usuario.setEmail(email);
		    usuario.setSenha(senha);

		    result.add(usuario);
		}
	    } catch (SQLException e) {
		//TODO: ERRO: nao ocorreu a listagem
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}

	return result;
    }

    @Override
    public Usuario getById(Long pk) {
	Usuario result = null;
	String query = "select * from " + TABELA + " where id = ?";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, pk);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
		    long id = rs.getLong("id");
		    String email = rs.getString("email");
		    String nome = rs.getString("nome");
		    String senha = rs.getString("senha");
		    result = new Usuario();
		    result.setId(id);
		    result.setNome(nome);
		    result.setSenha(senha);
		    result.setEmail(email);

		} else {
		    //TODO: ERRO: ---
		}
	    } catch (SQLException e) {
		int i = 0;
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    public void edit(long id, String email, String nome, String senha) {
	String query = "update " + TABELA + " set email = ?, nome = ?, senha = ? where id = ?;";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(4, id);
		ps.setString(1, email);
		ps.setString(2, nome);
		ps.setString(3, senha);
		ps.execute();
	    } catch (SQLException e) {
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public boolean autentica(String email, String senha) {
	boolean result = false;
	String query = "select * from " + TABELA + " where email = ?";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
		    
		    String senhaRecebida = rs.getString("senha");
		    System.out.println("senhaRecebida: " + senhaRecebida);
		    result = senha.equals(senhaRecebida);

		}
	    } catch (SQLException e) {
		System.out.println("");
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    public Usuario getByEmail(String email) {
	Usuario result = null;
	String query = "select * from " + TABELA + " where email = ?";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
		    long id = rs.getLong("id");
		    String nome = rs.getString("nome");
		    String senha = rs.getString("senha");
		    result = new Usuario();
		    result.setId(id);
		    result.setNome(nome);
		    result.setSenha(senha);
		    result.setEmail(email);

		} else {
		    //TODO: ERRO: ---
		}
	    } catch (SQLException e) {
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    

}
