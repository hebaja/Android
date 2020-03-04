package br.com.hebaja.ceep.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable, Comparable<Nota> {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String titulo;
    private String descricao;
    private int color;
    private int posicao;

    @Ignore
    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Nota() {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    @Override
    public int compareTo(Nota outraNota) {
        if (this.posicao < outraNota.posicao) {
            return -1;
        }
        if (this.posicao > outraNota.posicao) {
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + this.getTitulo() + " Posicao: " + this.getPosicao() + " - " + " id: " + this.getId() + "]";
    }
}