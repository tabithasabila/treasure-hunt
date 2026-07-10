package main.obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Key extends SuperObject{
    GamePanel gp;

    public Key(GamePanel gp) {
        name = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
