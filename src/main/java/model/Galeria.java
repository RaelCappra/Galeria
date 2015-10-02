/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import persistencia.ImagemDao;

/**
 *
 * @author Rael
 */
public class Galeria {
    private @NotNull long id;
    private @NotNull Usuario usuario;
    private @NotNull @Size(min=3, message="O nome da galeria deve ser maior que {min}") String nome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Galeria() {
    }

    public Galeria(long id, Usuario usuario, String nome) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
    }
    
    public String getThumbnail(){
	List<Imagem> imagens = new ImagemDao().listByGaleria(this);
	if (null == imagens || imagens.isEmpty()){
	    return "assets/vazio.gif";
	} else{
	    Imagem imagem = imagens.get(0);
	    return "uploads/" + imagem.getId() + "." + imagem.getExtensao();
	}
    }
}
