package cn.Gallifrey.game;

import java.awt.Graphics;
import java.awt.Image;

/*
 * 爆炸类
 */
public class Explode {
    double x, y;

    static Image[] imgs = new Image[26];
    static {
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = GameUtil.getImage("images/exploder/exploder" + (i + 1) + ".gif");
            //imgs[i].getWidth(null);
        }
    }

    int count;

    public void draw(Graphics g) {
        if (count <= 25) {
            g.drawImage(imgs[count], (int) x, (int) y, null);
            count++;
        }else {
            return;
        }
    }

    public Explode(double x, double y) {
        this.x = x;
        this.y = y;
    }
}