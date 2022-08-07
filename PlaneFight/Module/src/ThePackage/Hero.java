package ThePackage;

import java.awt.Image;

//英雄机
public class Hero extends FlyingObject{
    int life;													//英雄机生命
    int doubleFire;												//英雄机火力值0/1
    Image[] images = {};										//图片数组（两张图片切换动画）

    public Hero() {
        image = ShootGame.hero0;								//使用第一张英雄机图片
        width = image.getWidth(null);							//获得英雄机宽度
        height = image.getHeight(null);							//获得英雄机宽度
        x = 150;												//英雄机初始高度宽度
        y = 450;
        life = 3;												//初始生命
        doubleFire = 0;											//开始英雄机单倍火力
        images =new Image[] {ShootGame.hero0,ShootGame.hero1};	//两张图片

    }

    int index = 0;

    //实现抽象父类中对象移动的方法
    @Override
    public void step() {
        // TODO Auto-generated method stub
        image = images[index++/10%images.length];//图片数组切换，形成英雄机动画，最后赋值给image
    }


    //发射子弹的方法------------------------------------------------------------------------------------------
    public Bullet[]  shoot() {
        Bullet[] bs = {};										//子弹数组，初值为空
        if(doubleFire ==0) {									//单倍火力
            bs = new Bullet[1];
            bs[0] = new Bullet( x+width/2-4,y-14);				//一颗子弹的坐标计算
        }else {													//双倍火力
            bs =new Bullet[2];
            bs[0] = new Bullet(x+width/4-4,y-14);				//英雄机的宽度1/4-子弹宽度的一半
            bs[1] = new Bullet(x+width*3/4-4,y-14);
            doubleFire -= 2;
        }
        return bs;
    }

    //获取得分------------------------------------------------------------------------------------------------
    public int getScore() {
        return 5;
    }

    //增加火力------------------------------------------------------------------------------------------------
    public void addDoubleFire() {
        doubleFire +=40;

    }

    //增加生命------------------------------------------------------------------------------------------------
    public void addLife() {
        life += 1;
    }

    //判断英雄机是否与敌人相撞方法-----------------------------------------------------------------------------
    public boolean duang(FlyingObject f) {
        int x = this.x;
        int y = this.y;											//获得英雄机当前位置
        int x1 = f.x - this.width;								//敌人x坐标-英雄机宽度
        int x2 = f.x + f.width;									//敌人x坐标+敌人宽度
        int y1 = f.y - this.height;								//敌人y坐标-英雄机高度
        int y2 = f.y + f.height;								//敌人y坐标+敌人高度

        return x>x1 && x<x2
                &&y>y1 && y<y2;								//临介条件
    }

}

