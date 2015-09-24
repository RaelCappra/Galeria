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

/**
 *
 * @author Rael
 */
public class ImagemDao implements Dao<Imagem, Long> {

    private static ConexaoPostgreSQL conexao;

    private static final String TABELA = "imagem";
    

    @Override
    public void save(Imagem entity) {
	String query = "insert into " + TABELA + " (nome, galeria, extensao) values (?, ?, ?)";
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setString(2, entity.getNome());
		ps.setLong(3, entity.getGaleria().getId());
		ps.execute();
	    } catch (SQLException e) {
		//TODO: ERRO: nao foi adicionada o usuario
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public Long saveReturningId(Imagem entity) {
	String query = "insert into " + TABELA + " (nome, galeria, extensao) values (?, ?, ?) returning id";
	Long id = null;
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setString(1, entity.getNome());
		ps.setString(3, entity.getExtensao());
		ps.setLong(2, entity.getGaleria().getId());
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
		    id = rs.getLong("id");
		}

	    } catch (SQLException e) {
		//TODO: ERRO: nao foi adicionada o usuario
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
	return id;
    }

    public void softDelete(Long id) {
	String query = "update " + TABELA + " set deleted = true where id = ?";
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
    public List<Imagem> listAll() {
	return listAll(false);
    }

    public List<Imagem> listAll(boolean getDeleted) {
	String query;
	if (!getDeleted) {
	    query = "select * from " + TABELA + "where deleted=false";
	} else {
	    query = "select * from " + TABELA;
	}
	List<Imagem> result = new ArrayList<>();
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
		    Imagem imagem = new Imagem();
		    long id = rs.getLong("id");
		    String nome = rs.getString("nome");
		    long idGaleria = rs.getLong("galeria");
		    Galeria galeria = new GaleriaDao().getById(idGaleria);
		    imagem.setId(id);
		    imagem.setNome(nome);
		    imagem.setGaleria(galeria);

		    result.add(imagem);
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
    public Imagem getById(Long pk) {
	return getById(pk, false);
    }

    public Imagem getById(Long pk, boolean getDeleted) {
	Imagem result = null;
	String query;
	if (getDeleted) {
	    query = "select * from " + TABELA + " where id = ?";
	} else {
	    query = "select * from " + TABELA + " where id = ? and deleted = false";
	}

	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, pk);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
		    result = new Imagem();
		    long id = pk;
		    String nome = rs.getString("nome");
		    String extensao = rs.getString("extensao");
		    long idGaleria = rs.getLong("galeria");
		    Galeria galeria = new GaleriaDao().getById(idGaleria);
		    result.setId(id);
		    result.setNome(nome);
		    result.setExtensao(extensao);
		    result.setGaleria(galeria);

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
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public List<Imagem> listByGaleria(Galeria galeria) {
	return listByGaleria(galeria, false);
    }

    public List<Imagem> listByGaleria(Galeria galeria, boolean getDeleted) {
	String query;
	if (getDeleted) {
	    query = "select * from " + TABELA + " where galeria = ?";
	} else {
	    query = "select * from " + TABELA + " where galeria = ? and deleted=false";
	}
	List<Imagem> result = new ArrayList<>();
	try {
	    if (conexao == null || conexao.getConnection().isClosed()) {
		conexao = new ConexaoPostgreSQL("localhost", "postgres", "postgres", DATABASE);
	    }
	    try (Connection connection = conexao.getConnection();
		PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, galeria.getId());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
		    Imagem imagem = new Imagem();
		    long id = rs.getLong("id");
		    String nome = rs.getString("nome");
		    String extensao = rs.getString("extensao");
		    imagem.setId(id);
		    imagem.setNome(nome);
		    imagem.setExtensao(extensao);
		    imagem.setGaleria(galeria);

		    result.add(imagem);

		}
	    } catch (SQLException e) {
		//TODO: ERRO: nao ocorreu a listagem
	    }
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioDao.class
		.getName()).log(Level.SEVERE, null, ex);
	}

	return result;
    }

    public void restore(long id) {
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
}
