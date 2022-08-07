package ThePackage;

//子弹
public class Bullet extends FlyingObject{
    int ySpeed;												//子弹向下速度

    public Bullet(int x,int y) {
        image = ShootGame.bullet;							//调用子弹图片
        width = image.getWidth(null);						//获得子弹宽度
        height = image.getHeight(null);						//获得子弹高度
        this.x = x;											//子弹实时坐标（随英雄机变换）
        this.y = y;
        ySpeed = 3;

    }


    //实现抽象父类中对象移动的方法
    @Override
    public void step() {
        // TODO Auto-generated method stub
        y -= ySpeed;

    }



}


