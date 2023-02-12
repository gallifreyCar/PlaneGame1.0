package cn.Gallifrey.game;
import java.awt.*;

public class GameObject {
    Image img;
    double x,y;
    int height,width;

    int speed;
    public void drawSelf(Graphics g){
        g.drawImage(img,(int)x,(int)y,null);
    }

    public GameObject(Image img, double x, double y, int height, int width, int speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.speed = speed;
    }

    public GameObject(Image img, double x, double y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }
    public GameObject(){}

    public Rectangle getRect(){
        return new Rectangle((int)x,(int)y,width,height);
    }

}

