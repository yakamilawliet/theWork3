package ThePackage;

//小敌机
public class Airplane extends FlyingObject {
    int ySpeed;												//向下速度

    public Airplane() {
        image = ShootGame.airplane;							//小敌机图片
        width = image.getWidth(null);						//小敌机宽度
        height = image.getHeight(null);						//小敌机高度
        x = (int) (Math.random()*ShootGame.width);			//小敌机出现x坐标，背景屏幕宽度
        y = -height;										//小敌机的y坐标
        ySpeed = 1;											//向下移动速度


    }

    //实现抽象父类中对象移动的方法-----------------------------------------------------------------------------
    @Override
    public void step() {
        // TODO Auto-generated method stub
        y += ySpeed;										//一次递增一个速度


    }
}

