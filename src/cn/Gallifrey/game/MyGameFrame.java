package cn.Gallifrey.game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;


public class MyGameFrame extends JFrame {
    Image planeImg= GameUtil.getImage("images/plane.png");
    Image bg= GameUtil.getImage("images/bg2.jpg");
    Plane p1= new Plane(planeImg,365,400);

    int time=0;
    int shellnum=0;
    static int kill=0;
    int y=-1500;
    static boolean start=false;
    static boolean pause=false;
    boolean restart=false;
    JButton button2;
    JButton button1;
    static LinkedList<Enemy> enemyArrayList=new LinkedList<>();
    static LinkedList<Shell2>shell2ArrayList=new LinkedList<>();
    static  LinkedList<Explode>explodeArrayList=new LinkedList<>();



    //绘画线程
    class PaintThread implements Runnable{
        @Override
        public void run() {
            while (true){
                time+=20;
                repaint(); // 重写
                try{Thread.sleep(10); //1000ms/20ms=50
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //单独开一个计算线程
    class CountThread implements Runnable{

        @Override
        public void run() {
            while (true){
             if(pause==false&&start==true){
                    //计算敌人
                    if (time % 400 == 0) {
                        enemyArrayList.add(new Enemy());
                    }
                    //初始化炮弹2
                    if (p1.attack && time % 200 == 0 && shellnum != 0) {
                        shell2ArrayList.add(new Shell2(p1.x + 25, p1.y));
                        shellnum = shellnum - 1;
                    }
                    //生成子弹

                    if (time % 400 == 0) {
                        shellnum++;
                    }

                    //判断碰撞
                    for (int i2 = 0; i2 < shell2ArrayList.size(); i2++) {
                        for (int b = 0; b < enemyArrayList.size(); b++) {
                            shell2ArrayList.get(i2).hit(enemyArrayList.get(b));
                        }

                    }
                    if(restart==true){
                        shell2ArrayList.clear();
                        enemyArrayList.clear();
                        explodeArrayList.clear();
                    }

             }

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }

        }
    }

    //编写绘画类
    class paintFirst extends JPanel{
        public void paintComponent(Graphics graphics){
            Graphics2D g2d=(Graphics2D) graphics;
            //背景的移动
            if(y <0) {
                g2d.drawImage(bg, 0, y, null);
                y=y+2;
            }else {
                y = -1500;
            }

            //画飞机
            p1.drawSelf(g2d);

//初始化敌人
                if (start == true) {
                    for (int i = 0; i < enemyArrayList.size(); i++) {
                        if (enemyArrayList.get(i).live) {
                            enemyArrayList.get(i).draw(g2d);
                        } else {
                            enemyArrayList.remove(i);
                        }
                    }
                }


//画炮弹

                for (int i = 0; i < shell2ArrayList.size(); i++) {
                    if (shell2ArrayList.get(i).live) {
                        shell2ArrayList.get(i).draw(g2d);
                    } else {
                        shell2ArrayList.remove(i);
                    }
                }


//显示子弹数目



//爆炸实现
            for(int i=0;i<explodeArrayList.size();i++) {
                if(explodeArrayList.get(i).y>50&&explodeArrayList.get(i).y<Constant.GAME_HEIGHT) {
                    explodeArrayList.get(i).draw(g2d);

                }
            }

            for(int i=0;i<explodeArrayList.size();i++){
                if(explodeArrayList.size()>10){
                    explodeArrayList.remove(i);
                }
            }


            if(restart==true){
                shellnum=0;
                kill=0;
            }

            Color c=g2d.getColor();
            g2d.setColor(Color.WHITE);
            g2d.drawString("子弹数量:"+shellnum,500,500);
            g2d.drawString("消灭敌机数量:"+kill,500,525);
            g2d.setColor(c);



        }

        public void Menu(){
            JPanel menu=new JPanel();
            menu.setBackground(Color.black);
             button1=new JButton("开始");
             button2=new JButton("暂停");
            JButton button3=new JButton("成就");
            JButton button4=new JButton("说明");
            button1.addActionListener(new button12Listener());
            button2.addActionListener(new button2Listener());
            button3.addActionListener(new button1Listener());
            button4.addActionListener(new button1Listener());
            menu.add(button1);
            menu.add(button2);
            menu.add(button3);
            menu.add(button4);
            this.add(menu);
        }

        public paintFirst(){
            Menu();
        }


    }


    //定义键盘监听的内部类
    class KeyMonitor extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {

            p1.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            p1.minusDirection(e);
        }
    }

    //重写构造器
    public MyGameFrame(){
        paintFirst pf1=new paintFirst();
        this.add(pf1);
    }
    //初始化窗口
    public void lauchFrame() {
        this.setTitle("xxxxx");
        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        this.setBackground(Color.blue);
        this.setVisible(true);
        this.setLocation(300, 300);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        new Thread(new PaintThread(),"绘画1").start();//启动重画窗口的线程
        new Thread(new CountThread(),"计算1").start();//启动计算的线程
        addKeyListener(new KeyMonitor());//给窗口增加键盘监听

    }







    public static void main(String[] args) {
    MyGameFrame p1=new MyGameFrame();
    p1.lauchFrame();


    }
    //双缓冲
    private Image offScreenImage =null;
    public void update(Graphics g){
        if(offScreenImage==null)
            offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
            Graphics gOff= offScreenImage.getGraphics();
            paint(gOff);
            g.drawImage(offScreenImage,0,0,null);

    }


    class button1Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MyGameFrame.this.requestFocusInWindow();

        }
    }

    class button12Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(button1.getText()=="开始"){
            MyGameFrame.this.requestFocusInWindow();
            start=true;
            button1.setText("重玩");
            restart=false;
            }else if(button1.getText()=="重玩"){
                button1.setText("开始");
                restart=true;
            }

        }
    }
    class button2Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(button2.getText()=="暂停") {
                button2.requestFocus();
                pause = true;
                button2.setText("继续");
            }else if(button2.getText()=="继续"){
                MyGameFrame.this.requestFocus();
                pause = false;
                button2.setText("暂停");
            }
        }
    }

}
