package landys.daemonBall;
/**
 * �����ɫ������㷨,�ǳ�����,��������ɫ,���澲̬����
 * ��������ʵ�ְ�������Ļ���ԼΪ������������ʵ�1/8
 */

import java.util.Random;

/**
 * @author landys
 * ������,������ɫ��
 * ����ʵ�ְ�������Ļ���ԼΪ������������ʵ�1/8
 */
public abstract class RandBalls
{
	/**
	 * @return ��һ�����ɫ
	 */
	public static int getOneBall()
	{
		int x = mRand.nextInt(8) + 1;
		if (x == BallColor.WHITE)	// ��֤��������Ļ���ԼΪ������������ʵ�1/8
		{
			x = mRand.nextInt(8) + 1;
		}
		return x;
	}
	
	/**
	 * @return ���������ɫ
	 */
	public static int[] getBalls()
	{
		for (int i=0; i<3; i++)
		{
			mRandBalls[i] = getOneBall();
		}
		return mRandBalls;
	}
	
	private static Random mRand = new Random();	// �����������ľ�̬����
	public static int mRandBalls[] = new int[3];	// ��������������򱣴�ľ�̬����
}