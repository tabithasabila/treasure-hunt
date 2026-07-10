package main.obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends SuperObject{
    GamePanel gp;

    public Chest(GamePanel gp) {
        name = "Chest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
