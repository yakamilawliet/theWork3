package ThePackage;

import java.awt.Image;

//父类（英雄机，小敌机，小蜜蜂，子弹）

public abstract class FlyingObject {						//抽象方法必须为抽象类
    int x,y;												//定位
    int width,height;										//对象宽度和高度
    Image image;											//定义图像


    //让每一个子类移动的方法----------------------------------------------------------------------------------
    public abstract void step();							//由于每个子类的移动方法不一样，定为抽象方法


    //判断子弹是否与敌人相撞方法-------------------------------------------------------------------------------
    public boolean hitBy(Bullet bullet) {
        int x = bullet.x;									//子弹x 坐标
        int y = bullet.y;

        return x>this.x && x<this.x+this.width
                &&
                y>this.y && y<this.y+height;				//子弹与敌人相撞的临界状态

    }
}

