package cn.Gallifrey.game;

import java.awt.*;

public class Enemy extends GameObject{

    boolean live=true;
    double degree;
    public Enemy(){
        x=(int)(Math.random()*Constant.GAME_WIDTH);
        y=30*Math.random();
        speed=2;
        width=Constant.EMEMY.getWidth(null);
        height=Constant.EMEMY.getHeight(null);
        degree=Math.random()*Math.PI*2;



    }



    public void draw(Graphics g) {

        if (live) {
            g.drawImage(Constant.EMEMY, (int) x, (int) y, null);
            if(MyGameFrame.pause==false){
            x += speed * Math.cos(degree);
            y+=speed;
            }
            if(y>Constant.GAME_HEIGHT-10){
                live=false;
            }
            if (x < 0 || x > Constant.GAME_WIDTH - width) {
                degree = Math.PI - degree;
            }

        }
    }
}






