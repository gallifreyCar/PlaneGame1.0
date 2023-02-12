package cn.Gallifrey.game;

import java.awt.*;

import static cn.Gallifrey.game.MyGameFrame.*;

public class Shell2 extends GameObject{
    boolean live=true;


    public Shell2(double x,double y){

       this.x=x;
       this.y=y;
        speed=3;
        width=10;
        height=10;



    }
    //绘制炮弹
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);

        if(y<0&&y>Constant.GAME_HEIGHT){
            live=false;
        }
        if(live) {
            g.fillOval((int) x, (int) y, width, height);
            if(pause==false){
            y = y - speed;
            }
        }

    }

//子弹碰撞到敌人的判断方法
    public void hit(Enemy e1){
        Rectangle s2= this.getRect();
        Rectangle e2=e1.getRect();

        if( s2.intersects(e2)){
            this.live=false;
            e1.live=false;
            explodeArrayList.add(new Explode(e1.x,e1.y));
            kill++;


        }
    }


}
