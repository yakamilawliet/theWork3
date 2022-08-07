package ThePackage;

//小蜜蜂
public class Bee extends FlyingObject{
    int ySpeed;//向下速度
    int xSpeed;//左右速度
    int awardType;

    public Bee() {
        image = ShootGame.bee;						//调用小蜜蜂图片
        width = image.getWidth(null);				//获取图片宽度
        height = image.getHeight(null);				//获取图片高度
        x = (int) (Math.random()*ShootGame.width);	//小蜜蜂x坐标（随机进入）
        y = -height;								//小蜜蜂高度，设为窗体下方，方便提前看到小蜜蜂
        xSpeed = 1;									//小蜜蜂x轴方向速度
        ySpeed = 1;									//小蜜蜂y轴方向速度
        awardType = (int) (Math.random()*2);		//得分(随机性)
    }

    //实现抽象父类中对象移动的方法-----------------------------------------------------------------------------
    @Override
    public void step() {
        // TODO Auto-generated method stub
        y += ySpeed;
        x += xSpeed;
        if(x > ShootGame.width - width) {			//大于屏幕宽度-x宽度小蜜蜂大于右边界
            xSpeed =-1;
        }
        if(x<=0) {
            xSpeed = 1;								//小蜜蜂移动至左边界，向右移动
        }

    }

    //获取得分方法--------------------------------------------------------------------------------------------
    public int getAwardType() {
        return awardType;
    }
}

