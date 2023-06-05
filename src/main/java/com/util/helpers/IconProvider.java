package com.util.helpers;

import java.util.List;

import javafx.scene.image.Image;

public class IconProvider {

    public static List<Image> getWindowIcons() {

        return List.of(
                new Image(IconProvider.class.getResourceAsStream("/images/icons8-packages-16.png")),
                new Image(IconProvider.class.getResourceAsStream("/images/icons8-packages-32.png")),
                new Image(IconProvider.class.getResourceAsStream("/images/icons8-packages-48.png")),
                new Image(IconProvider.class.getResourceAsStream("/images/icons8-packages-100.png")));
    }

}
