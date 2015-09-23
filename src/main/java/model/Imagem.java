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
public class Imagem {

    private long id;
    private String nome, extensao;
    private Galeria galeria;

    public Imagem() {
    }

    public Imagem(long id, String nome, Galeria galeria, String extensao) {
	this.id = id;
	this.nome = nome;
	this.galeria = galeria;
	this.extensao = extensao;
    }

    public String getExtensao() {
	return extensao;
    }

    public void setExtensao(String extensao) {
	this.extensao = extensao;
    }

    public Galeria getGaleria() {
	return galeria;
    }

    public void setGaleria(Galeria galeria) {
	this.galeria = galeria;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }
    
    public String getFileName(){
        return id + "." + extensao;
    }

}
