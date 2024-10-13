package com.example.min_proyecto_2.model.font;

import javafx.scene.text.Font;

public class Fonts {
    private Font font;

    public Fonts(int size, String width) {
        if(width == "semibold"){
            font = Font.loadFont(getClass().getResourceAsStream("/com/example/min_proyecto_2/Fonts/LeagueSpartan-SemiBold.ttf"), size);
        }else if(width == "bold"){
            font = Font.loadFont(getClass().getResourceAsStream("/com/example/min_proyecto_2/Fonts/LeagueSpartan-Bold.ttf"), size);
        }

    }

    public Font getFont() {
        return font;
    }
}
