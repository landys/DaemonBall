package landys.daemonBall;
/**
 * 随机颜色球产生算法,是抽象类,产生的球色,保存静态变量
 * 另外这里实现白球产生的机率约为其他球产生概率的1/8
 */

import java.util.Random;

/**
 * @author landys
 * 抽象类,产生各色球
 * 这里实现白球产生的机率约为其他球产生概率的1/8
 */
public abstract class RandBalls
{
	/**
	 * @return 任一随机球色
	 */
	public static int getOneBall()
	{
		int x = mRand.nextInt(8) + 1;
		if (x == BallColor.WHITE)	// 保证白球产生的机率约为其他球产生概率的1/8
		{
			x = mRand.nextInt(8) + 1;
		}
		return x;
	}
	
	/**
	 * @return 三个随机球色
	 */
	public static int[] getBalls()
	{
		for (int i=0; i<3; i++)
		{
			mRandBalls[i] = getOneBall();
		}
		return mRandBalls;
	}
	
	private static Random mRand = new Random();	// 随机数产生类的静态对象
	public static int mRandBalls[] = new int[3];	// 产生的三个随机球保存的静态变量
}