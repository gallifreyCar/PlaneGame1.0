package cn.Gallifrey.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Date;

public class Plane extends GameObject {
    boolean left,right,up,down;
    boolean attack;

    int speed=8;

    boolean live=true;
    @Override
    public void drawSelf(Graphics g) {

        if (live) {
            g.drawImage(img, (int) x, (int) y, null);
            if (left) {
                x -= speed;
            }
            if (right) {
                x += speed;
            }
            if (up) {
                y -= speed;
            }
            if (down) {
                y += speed;
            }

            if(x>800){
                x=0;
            }
            if(x<0){
                x=800;
            }
        }
    }
    public Plane(Image img,double x,double y){
        this.img=img;
        this.x=x;
        this.y=y;
        this.width=img.getWidth(null);
        this.height=img.getHeight(null);


    }

    public void addDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_SPACE:
                attack=true;
                break;

        }
    }
    public void minusDirection(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_A:
                    left=false;
                    break;
                case KeyEvent.VK_D:
                    right=false;
                    break;
                case KeyEvent.VK_W:
                    up=false;
                case KeyEvent.VK_S:
                    down =false;
                case KeyEvent.VK_SPACE:

                    attack=false;


            }
        }


}





