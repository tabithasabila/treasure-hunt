package main.obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Door extends SuperObject{
    GamePanel gp;

    public Door(GamePanel gp) {
        name = "Door";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);


        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}

