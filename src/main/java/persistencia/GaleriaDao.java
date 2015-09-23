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
import model.Usuario;

/**
 *
 * @author Rael
 */
public class GaleriaDao implements Dao<Galeria, Long> {

    private static ConexaoPostgreSQL conexao;

    private static final String TABELA = "galeria";

    private static final String DATABASE = "galeria";

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

    @Override
    public List<Galeria> listAll() {
	String query = "select * from " + TABELA;
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
	Galeria result = null;
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
	String query = "update " + TABELA + " set  nome = ? where id = ?;";
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
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public List<Galeria> listByUsuario(Usuario usuario) {
	String query = "select * from " + TABELA + " where usuario = ?";
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