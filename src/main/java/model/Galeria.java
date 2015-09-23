/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Rael
 */
public class Galeria {
    private long id;
    private Usuario usuario;
    private String nome;

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
    
    
}
