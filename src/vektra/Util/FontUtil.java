package vektra.Util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.InputStream;

public class FontUtil {

    private static Font customFont = null;

    // Carga la fuente una sola vez para ahorrar memoria
    public static Font getCustomFont() {
        if (customFont == null) {
            try {
                InputStream is = FontUtil.class.getResourceAsStream("/vektra/View/Fuentes/TheStudentsTeacher-Regular.ttf");
                if (is != null) {
                    customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                } else {
                    System.err.println("No se pudo encontrar el archivo de fuente.");
                    customFont = new Font("SansSerif", Font.PLAIN, 14); // Fallback
                }
            } catch (Exception e) {
                e.printStackTrace();
                customFont = new Font("SansSerif", Font.PLAIN, 14); // Fallback
            }
        }
        return customFont;
    }

    // Aplica la fuente a un componente y a todos sus hijos (útil para pasarle un JPanel entero)
    public static void applyCustomFont(Component component) {
        Font custom = getCustomFont();
        if (custom != null) {
            // Obtener el tamaño y estilo (negrita, cursiva) que el diseñador de NetBeans le puso
            Font currentFont = component.getFont();
            if (currentFont != null) {
                component.setFont(custom.deriveFont(currentFont.getStyle(), currentFont.getSize()));
            } else {
                component.setFont(custom.deriveFont(Font.PLAIN, 14f));
            }
        }

        // Si es un contenedor (como un JPanel), recorrer todos sus hijos recursivamente
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyCustomFont(child);
            }
        }
    }
}
