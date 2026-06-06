package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Screen Settings
    final int originalTileSize = 16; //16 x 16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize* maxScreenRow; //576 pixels

    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    //Set player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
//    public void run() {
//        double drawInterval = 1000000000/FPS;  //0.0166666 times a second
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while(gameThread != null){
//            //System.out.println("This game loop is running");
//            long currentTime = System.nanoTime();
//           System.out.println("current Time: " + currentTime);
//            //1 UPDATE: Update the character position
//            update();
//            //2 DRAW: draw  the screen with updated info
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//
//                if(remainingTime < 0){
//                    remainingTime = 0;
//                }
//                Thread.sleep((long)remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void run() {
        double drawInterval = 1000000000/FPS;  //0.0166666 times a second
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){
                update();
                //2 DRAW: draw  the screen with updated info
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        if(keyH.upPressed){
            playerY -= playerSpeed;
        }else if(keyH.downPressed){
            playerY += playerSpeed;
        }else if(keyH.leftPressed){
            playerX -= playerSpeed;
        }else if(keyH.rightPressed){
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY,tileSize, tileSize );
        g2.dispose();
    }
}
