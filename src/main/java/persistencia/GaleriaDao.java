/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Galeria;
import model.Imagem;
import model.Usuario;

/**
 *
 * @author Rael
 */
public class GaleriaDao implements Dao<Galeria, Long> {

    private static ConexaoPostgreSQL conexao;

    private static final String TABELA = "galeria";

    @Override
    public void save(Galeria entity) {
	String query = "insert into " + TABELA + " (nome, usuario) values (?, ?)";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setString(1, entity.getNome());
		ps.setLong(2, entity.getUsuario().getId());
		ps.execute();
	    } catch (SQLException e) {
		//TODO: ERRO: nao foi adicionada o usuario
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void softDelete(Long id) {
	String query = "update " + TABELA + " set deleted = true where id = ?";

	ImagemDao imagemDao = new ImagemDao();
	List<Imagem> imagens = imagemDao.listByGaleria(new Galeria(id, null, null));

	for (Imagem imagem : imagens) {
	    imagemDao.softDelete(imagem.getId());
	}

	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, id);
		ps.execute();
	    } catch (SQLException e) {
		//TODO: ERRO: nao foi deletada a Galeria
		System.out.println("");
		throw new Exception(e);
	    }
	} catch (Exception ex) {
	    for (Imagem imagem : imagens) {
		imagemDao.restore(imagem.getId());
	    }
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
		//TODO: ERRO: nao foi deletada a Galeria
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    void restore(long id) {
	String query = "update " + TABELA + " set deleted = false where id = ?";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, id);
		ps.execute();
	    } catch (SQLException e) {
		//TODO: ERRO: nao foi deletada a Imagem
		System.out.println("");
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    @Override
    public List<Galeria> listAll() {
	return listAll(false);
    }

    public List<Galeria> listAll(boolean getDeleted) {
	String query;
	if (!getDeleted) {
	    query = "select * from " + TABELA + "where deleted=false";
	} else {
	    query = "select * from " + TABELA;
	}
	List<Galeria> result = new ArrayList<>();
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
		    Galeria galeria = new Galeria();
		    long id = rs.getLong("id");
		    String nome = rs.getString("nome");
		    long idUsuario = rs.getLong("usuario");
		    Usuario usuario = new UsuarioDao().getById(idUsuario);
		    galeria.setId(id);
		    galeria.setNome(nome);
		    galeria.setUsuario(usuario);

		    result.add(galeria);
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
    public Galeria getById(Long pk) {
	return getById(pk, false);
    }

    public Galeria getById(Long pk, boolean getDeleted) {
	Galeria result = null;

	String query;
	if (getDeleted) {
	    query = "select * from " + TABELA + " where id = ?";
	} else {
	    query = "select * from " + TABELA + " where id = ? and deleted = false";
	}

	//String query = "select * from " + TABELA + " where id = ?";
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
		    String nome = rs.getString("nome");
		    long idUsuario = rs.getLong("usuario");
		    Usuario usuario = new UsuarioDao().getById(idUsuario);
		    result = new Galeria();
		    result.setId(id);
		    result.setNome(nome);
		    result.setUsuario(usuario);

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

    public void edit(long id, String nome) {
	edit(id, nome, false);
    }

    public void edit(long id, String nome, boolean affectDeleted) {
	String query;
	if (affectDeleted) {
	    query = "update " + TABELA + " set nome = ? where id = ?";
	} else {
	    query = "update " + TABELA + " set nome = ? where id = ? and deleted = false";
	}
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(2, id);
		ps.setString(1, nome);
		ps.execute();
	    } catch (SQLException e) {
		System.out.println("errou");
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public List<Galeria> listByUsuario(Usuario usuario) {
	return listByUsuario(usuario, false);
    }

    public List<Galeria> listByUsuario(Usuario usuario, boolean getDeleted) {
	String query;
	if (getDeleted) {
	    query = "select * from " + TABELA + " where usuario = ?";
	} else {
	    query = "select * from " + TABELA + " where usuario = ? and deleted=false";
	}

	List<Galeria> result = new ArrayList<>();
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, usuario.getId());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
		    Galeria galeria = new Galeria();
		    long id = rs.getLong("id");
		    String nome = rs.getString("nome");
		    galeria.setId(id);
		    galeria.setNome(nome);
		    galeria.setUsuario(usuario);

		    result.add(galeria);
		}
	    } catch (SQLException e) {
		//TODO: ERRO: nao ocorreu a listagem
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}

	return result;
    }
}
