package br.com.hebaja.ceep.dao;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.ceep.model.Circulo;

public class CirculoDAO {

    private final static ArrayList<Circulo> circulos = new ArrayList<>();

    private final static List<Circulo> montaListaCirculos() {

        circulos.add(new Circulo(Color.parseColor("#FFFFFF"), "Branco"));
        circulos.add(new Circulo(Color.parseColor("#408EC9"), "Azul"));
        circulos.add(new Circulo(Color.parseColor("#9ACD32"), "Verde"));
        circulos.add(new Circulo(Color.parseColor("#F9F256"), "Amarelo"));
        circulos.add(new Circulo(Color.parseColor("#F1CBFF"), "Lilás"));
        circulos.add(new Circulo(Color.parseColor("#D2D4DC"), "Cinza"));
        circulos.add(new Circulo(Color.parseColor("#A47C48"), "Marrom"));
        circulos.add(new Circulo(Color.parseColor("#BE29EC"), "Roxo"));
        circulos.add(new Circulo(Color.parseColor("#00ffcc"), "Ciano"));

        return circulos;
    }

    public List<Circulo> todos() {
        montaListaCirculos();
        return (List<Circulo>) circulos.clone();
    }

    public void removeTodosCirculos() {
        circulos.clear();
    }

}
