package ThePackage;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel implements MouseMotionListener,MouseListener{
    //继承JPanel类
    public static final int width = 400;
    public static final int height = 1000;

    //设置对象图片名称（与源文件名称一致）
    public static Image background;
    public static Image airplane;
    public static Image bee;
    public static Image bullet;
    public static Image gameover;
    public static Image hero0;
    public static Image hero1;
    public static Image pause;
    public static Image start;

    //定义游戏的四种状态
    public static final int START = 0;									//开始状态
    public static final int RUNNING = 1;								//运行状态
    public static final int PAUSE = 3;									//暂停状态
    public static final int GAMEOVER =4;								//结束状态
    int state =START;
    //创建对象-----------------------------------------------------------------------------------------------

    Hero hero = new Hero();												//创建英雄机对象
    Bullet[] bullets = {};												//子弹（众多），定义子弹数组
    FlyingObject[] flyings = {};										//小敌机（众多），定义小敌机数组

    //ShootGame无参构造方法（给创建对象初始化）
	/*public ShootGame() {
		flyings = new FlyingObject[2];
		//多态 夫妇
		flyings[0] = new Airplane();//小敌机
		flyings[1] = new Bee();//小蜜蜂

		bullets = new Bullet[2];
		bullets[0] = new Bullet(130,250);
		bullets[1] = new Bullet(100,120);

	}*/

    //静态代码块内容加载
    static {
        try {

            //导入图片文件-----------------------------------------------------------------------------------

            //将图片文件赋给图片对象

            background = ImageIO.read(new File("image\\background.png"));
            airplane = ImageIO.read(new File("image\\airplane.png"));
            bee = ImageIO.read(new File("image\\bee.png"));
            bullet = ImageIO.read(new File("image\\bullet.png"));
            gameover = ImageIO.read(new File("image\\gameover.png"));
            hero0 = ImageIO.read(new File("image\\hero0.png"));
            hero1 = ImageIO.read(new File("image\\hero1.png"));
            pause = ImageIO.read(new File("image\\pause.png"));
            start = ImageIO.read(new File("image\\start.png"));



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //产生敌人方法-------------------------------------------------------------------------------------------
    public FlyingObject nextOne() {
        FlyingObject f;										//小敌机类型
        Random r = new Random();							//随机产生数
        int random = r.nextInt(20);							//产生[0,20)随机数
        if(random > 3) {
            f = new  Airplane();							//随机数为[4,20)  产生小敌机
        }else {
            f = new Bee();									//随机数为[0,3]  产生小蜜蜂
        }
        return f;											//返回小敌机
    }



    int flyingEnterIndex = 0;								//敌人对象初始化为0

    //把产生的敌人添加到敌人数组中方法------------------------------------------------------------------------
    public void enterAction() {
        flyingEnterIndex++;									//敌人对象自加
        if(flyingEnterIndex % 50 == 0) {					//50 100...每隔50毫秒执行一次，用于控制敌人出现速度

            //1、创建敌人对象
            FlyingObject one = nextOne();						//创建敌人对象，赋值给 One

            //2.将敌人对象添加到flyings敌人数组中
            flyings = Arrays.copyOf(flyings, flyings.length+1);	//扩容+1(增加小敌机)
            flyings[flyings.length-1] = one;					//把产的敌人one赋值（添加）给最后一个数组元素

        }
    }

    //游戏中各个对象的移动方式--------------------------------------------------------------------------------
    public void stepAction() {

        //敌人的移动（敌人处于敌人数组中，调用数组）
        for(int i=0; i<flyings.length; i++) {				//通过循环实现每个敌人移动
            flyings[i].step();								//调用step方法，实现敌人的移动
        }

        //子弹移动（子弹处于子弹数组中，调用子弹数组）
        for(int i=0; i<bullets.length; i++) {				//通过循环实现每个子弹移动
            bullets[i].step();								//调用step方法，实现子弹的移动
        }

        //英雄机的移动
        hero.step();										//调用step方法，实现英雄机的移动
    }


    int shootIndex =0;
    //英雄机发射子弹方法-------------------------------------------------------------------------------------
    public void shootAction() {
        shootIndex++;
        if(shootIndex % 30 == 0) {											//10*30=300毫秒
            Bullet[] bs = hero.shoot();											//用数组存放子弹

            //扩容（将bs存放至bullets子弹数组中）
            bullets = Arrays.copyOf(bullets, bullets.length+bs.length);			//子弹数组+新生成对象

            //将子弹对象添加至子弹数组中
            System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
        }
    }

    int score = 0;
    //子弹与敌人相撞-----------------------------------------------------------------------------------------
    public void hitAction() {
        //遍历所有子弹
        for(int i= 0; i<bullets.length; i++) {

            Bullet b = bullets[i];										//记录当前子弹

            //遍历所有敌人
            for(int j = 0; j<flyings.length; j++) {

                FlyingObject f = flyings[j];							//记录当前敌人
                //判断是否相撞
                if(f.hitBy(b)) {

                    //敌人消失

                    //1，当前敌人与最后一个数组元素交换
                    FlyingObject temp = flyings[j];
                    flyings[j] = flyings[flyings.length-1];
                    flyings[flyings.length-1] = temp;

                    //2.扩容-1（消灭敌人）
                    flyings=Arrays.copyOf(flyings, flyings.length-1);

                    //子弹消失

                    //1.当前子弹与最后一个数组元素交换
                    Bullet t =bullets[i];
                    bullets[i] = bullets[bullets.length-1];
                    bullets[bullets.length-1] = t;

                    //2.扩容-1（子弹消失）
                    bullets=Arrays.copyOf(bullets, bullets.length-1);

                    //得到加分或奖励,需要区分是小敌机还是小蜜蜂
                    if(f instanceof Airplane) {
                        //是小敌机
                        score += hero.getScore();						//加奖励分
                    }
                    if(f instanceof Bee) {
                        //是小蜜蜂，根据奖励类型增加火力还是生命
                        Bee bee = (Bee)f;								//向下转型Bee
                        int award = bee.getAwardType();
                        switch(award) {
                            case 0:
                                hero.addDoubleFire();						//增加火力
                                break;
                            case 1:
                                hero.addLife();								//增加生命值
                                break;
                        }
                    }

                }
            }
        }
    }

    //英雄机与敌人相撞方法------------------------------------------------------------------------------------
    public void duangAction() {
        //遍历所有敌人
        for(int i = 0; i<flyings.length; i++) {
            if(hero.duang(flyings[i])) {

                //敌人消失
                FlyingObject temp = flyings[i];
                flyings[i] = flyings[flyings.length-1];
                flyings[flyings.length-1] = temp;
                flyings=Arrays.copyOf(flyings, flyings.length-1);		//扩容，减掉敌人

                //英雄机减掉生命值，火力清零
                hero.life--;
                hero.doubleFire = 0;
            }
        }
    }

    //检测英雄机生命值方法------------------------------------------------------------------------------------
    public void checkGame() {
        if(hero.life <= 0) {
            state = GAMEOVER;
        }
    }


    //游戏中的各种行为方法-----------------------------------------------------------------------------------
    public void action() {

        //添加定时器（每隔多久出现一次）
        Timer timer = new Timer();//utill包下

        //安排指定任务从指定的延迟后开始进行重复的固定延迟执行
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(state == RUNNING) {								//处于运行状态执行操作
                    enterAction();										//敌人入场

                    stepAction();										//设置对象的移动方式

                    shootAction();										//英雄机发射子弹

                    hitAction();										//子弹与敌人相撞

                    duangAction();										//英雄机与敌人撞

                    checkGame();										//检测英雄机生命值
                }

                repaint();											//重新绘制（产生新的小敌机）

            }}, 10,10);

    }

    //绘制分数方法-------------------------------------------------------------------------------------------
    public  void paintScore(Graphics g) {
        g.setFont(new Font("黑体",Font.BOLD,20));					//颜色，字体，字号
        g.drawString("SCORE:"+score, 30, 30);						//绘制SCORE：分数（坐标位置）
        g.drawString("LIFE:"+hero.life,30,60);						//绘制LIFE： 生命值（坐标位置）
        g.drawString("FIRE:"+hero.doubleFire, 30, 90);				//FIRE：火力值（坐标位置）
    }


    //绘制状态图方法-----------------------------------------------------------------------------------------
    public void paintState(Graphics g) {
        switch(state) {
            case START:
                g.drawImage(start, 0, 0, null);							//绘制start图片，坐标（0,0）
                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);							//绘制pause图片，坐标（0,0）
                break;
            case GAMEOVER:
                g.drawImage(gameover, 0, 0, null);						//绘制gameover图片，坐标（0,0）
                break;
        }
    }

    //绘图方法-----------------------------------------------------------------------------------------------
    public void paint(Graphics g) {									//Jpanel类下paint方法

        super.paint(g);
        g.drawImage(background,0,0,null);						//通过背景图片进行绘画，从坐标（0,0）开始绘画
        //g.drawImage(airplane, 100, 100, null);绘制小敌机固定位置图像

        //通过方法调用实现动态实时绘图

        //绘制敌人
        paintFlying(g);

        //绘制子弹
        paintBullet(g);

        //绘制英雄机
        paintHero(g);

        //绘制分值
        paintScore(g);

        //绘制状态图
        paintState(g);

    }

    //绘制英雄机方法-----------------------------------------------------------------------------------------
    public void paintHero(Graphics g) {
        // TODO Auto-generated method stub
        g.drawImage(hero.image, hero.x, hero.y, null);

    }

    //绘制子弹方法-------------------------------------------------------------------------------------------
    public void paintBullet(Graphics g) {
        // TODO Auto-generated method stub
        for(int i = 0; i<bullets.length; i++) {
            g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
        }
    }

    //绘制敌人方法-------------------------------------------------------------------------------------------
    public  void paintFlying(Graphics g) {
        // TODO Auto-generated method stub
        for(int i = 0; i<flyings.length; i++) {
            g.drawImage(flyings[i].image, flyings[i].x, flyings[i].y, null);
        }
    }







    //主方法
    public static void main(String[] args) {

        //飞机大战窗体制作

        JFrame jf = new JFrame("飞机大战");
        jf.setSize(width, height);									//设置窗体宽高
        jf.setLocationRelativeTo(null);								//窗体居中
        jf.setVisible(true);										//窗体可见
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //导入背景图片
        ShootGame game = new ShootGame();							//创建ShootGame对象
        jf.add(game);												//在窗体中添加背景图片



        game.action();												//游戏中的各种操作
        jf.addMouseMotionListener(game);							//添加监听器（对对象实时监控）
        jf.addMouseListener(game);									//添加监听器（对鼠标实时监控）
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {							//鼠标移动
        // TODO Auto-generated method stub
        if(state == RUNNING) {										//在运行状态下执行操作
            hero.x =e.getX()-hero.width/2;								//鼠标的具体坐标（x,y）
            hero.y =e.getY()-hero.height/2;								//根据鼠标的位置确定英雄机的位置
        }

    }

    //鼠标单击启动
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(state == START) {
            state = RUNNING;										//开始运行
        }
        if(state == GAMEOVER) {
            state = START;											//返回开始状态
            hero = new Hero();
            flyings = new FlyingObject[] {};
            bullets = new Bullet[] {};
            score = 0;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    //鼠标进入
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        if(state == PAUSE) {
            state = RUNNING;										//修改为运行

        }
    }

    //鼠标退出
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        if(state == RUNNING) {
            state = PAUSE;											//修改为暂停

        }
    }

}

