package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int standCounter= 0;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;

        direction = "down";
    }

    public void getPlayerImage(){

        up1 = setUp("boy_up_1");
        up2 = setUp("boy_up_2");
        down1 = setUp("boy_down_1");
        down2 = setUp("boy_down_2");
        right1 = setUp("boy_right_1");
        right2 = setUp("boy_right_2");
        left1 = setUp("boy_left_1");
        left2 = setUp("boy_left_2");
    }

    public BufferedImage setUp(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image =  ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png")));
            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }

        return image;
    }

    public void update(){
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed){
                direction = "up";
            }else if(keyH.downPressed){
                direction = "down";
            }else if(keyH.leftPressed){
                direction = "left";
            }else if(keyH.rightPressed){
                direction = "right";
            }

            //Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //Check object collision
            int obj_index = gp.cChecker.checkObject(this, true);
            pickUpObject(obj_index);

            //If collision is false, player can move
            if(!collisionOn){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;

                }
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }else {
            standCounter++;

            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }

    }

    public void pickUpObject(int index){
        if(index != 999){
            String objectName = gp.obj[index].name;

            switch(objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    gp.playSE(3);
                    if(hasKey > 0) {
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened a door!");
                    }else{
                        gp.ui.showMessage("You need a key");
                    }

                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("Speed up!");
                    break;

                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;

            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;

            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;

            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
        };

        g2.drawImage(image, screenX,screenY, null);

    }

}
