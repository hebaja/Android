package br.com.hebaja.ceep.ui.activity;

import java.util.HashMap;
import java.util.Map;

public enum LayoutState {
    LINEAR(0),
    STAGGERED_GRID(1);

    private int value;
    private static Map map = new HashMap<>();

    LayoutState(int value) {
        this.value = value;
    }

    static {
        for (LayoutState layoutState : LayoutState.values()) {
            map.put(layoutState.value, layoutState);
        }
    }

    public static LayoutState valueOf(int layoutState) {
        return (LayoutState) map.get(layoutState);
    }

    public int getValue() {
        return value;
    }
}
