package br.com.hebaja.ceep.ui.recyclerview.adapter;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public enum CoresEnum {

    AZUL(Color.parseColor("#FFFFFF")),
    BRANCO(Color.WHITE),
    VERDE(Color.GREEN),
    AMARELO(Color.YELLOW),
    LILAS(Color.rgb(241, 203, 255)),
    CINZA(Color.GRAY),
    MARROM(Color.rgb(164, 124, 72)),
    ROXO(Color.rgb(190, 41, 236));

    private int cor;
    private static Map map = new HashMap<>();

    CoresEnum(int cor) {
        this.cor = cor;
    }

    static {
        for(CoresEnum cores : CoresEnum.values()) {
            map.put(cores.cor, cores);
        }
    }

    public static CoresEnum valueOf(int cores) {
        return (CoresEnum) map.get(cores);
    }

    public int getValue() {
        return cor;
    }
}
