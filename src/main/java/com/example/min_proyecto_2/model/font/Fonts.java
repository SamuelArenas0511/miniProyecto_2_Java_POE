package com.example.min_proyecto_2.model.font;

import javafx.scene.text.Font;

/**
 * Class to manage custom fonts used in the application.
 * Loads fonts based on the specified size and weight.
 *
 * @author Samuel Arenas
 */
public class Fonts {

    /** The Font object representing the custom font. */
    private Font font;

    /**
     * Constructor for the Fonts class.
     * Loads a font based on the specified size and weight.
     *
     * @param size The size of the font.
     * @param width The weight of the font, either "semibold" or "bold".
     */
    public Fonts(int size, String width) {
        if (width.equals("semibold")) {
            font = Font.loadFont(getClass().getResourceAsStream("/com/example/min_proyecto_2/Fonts/LeagueSpartan-SemiBold.ttf"), size);
        } else if (width.equals("bold")) {
            font = Font.loadFont(getClass().getResourceAsStream("/com/example/min_proyecto_2/Fonts/LeagueSpartan-Bold.ttf"), size);
        }
    }

    /**
     * Gets the loaded Font object.
     *
     * @return The Font object.
     */
    public Font getFont() {
        return font;
    }
}