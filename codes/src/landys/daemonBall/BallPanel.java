package landys.daemonBall;
/**
 * 球区类,游戏的核心算法都在这里,包括球的移动等,是本程序最重要的类
 * 其对象被组合在GamePanel中
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * @author landys
 * 左边区域,主要是游戏的效果和算法,对鼠标的响应放到了拥有该类对象的类GamePanel中了
 */
public class BallPanel extends JPanel
{
	/**
	 * 构造函数,初始化类中变量,读入图像文件
	 */
	public BallPanel()
	{
		super();
		
		this.setBackground(new Color(94, 94, 94));
		this.setSize(FaceStyle.BALL_PANEL_WIDTH, FaceStyle.BALL_PANEL_HEIGHT);
		mEmpty = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_EMPTY));
		mBalls = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_BALLS));
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				mWhatBall[i][j] = BallColor.EMPTY;
			}
		}
	}
	
	/**
	 * 初始化游戏刚开始时的画面
	 */
	public void initialize()
	{
		mMarks = 0;
		mEmptyPlace = FaceStyle.BALL_HNUM * FaceStyle.BALL_VNUM;
		mCurBall = BallColor.EMPTY;	// 当前球的颜色
		mFromPlace = new Dimension(NO_SELECTED);	// 开始球的位置
		mToPlace = new Dimension(NO_SELECTED);	// 想要移动到的位置
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				mWhatBall[i][j] = BallColor.EMPTY;
			}
		}
		drawAllBalls(this.getGraphics());	// 画出所有的球
	}
	
	/**
	 * 画游戏区所有球
	 * @param g 图形上下文
	 */
	private void drawAllBalls(Graphics g)
	{
		int dx = 0;
		int dy = 0;
		int sx = 0;
		int sy = 0;
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				dx = i * FaceStyle.BALL_WIDTH;
				dy = j * FaceStyle.BALL_HEIGHT;
				if (mWhatBall[i][j] == BallColor.EMPTY)
				{
					g.drawImage(mEmpty, dx, dy, this);
				}
				else
				{
					sx = (mWhatBall[i][j]-1) * FaceStyle.BALL_WIDTH;
					sy = 0;
					if (i == mFromPlace.width && j == mFromPlace.height)
					{
						sy = 2 * FaceStyle.BALL_HEIGHT;
					}
					g.drawImage(mBalls, dx, dy, dx+FaceStyle.BALL_WIDTH, dy+FaceStyle.BALL_HEIGHT, sx, sy, sx+FaceStyle.BALL_WIDTH, sy+FaceStyle.BALL_HEIGHT, this);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * 重载超类函数,保证游戏界面的一致性
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		drawAllBalls(g);
	}
	
	/**
	 * @param aBallColor 球的颜色
	 * @param aType 球的种类, 比如笑脸之类的, 为0, 1, 2
	 * @param ai 球的横向位置, 以0开始
	 * @param aj 球的纵向位置, 以0开始
	 */
	public void drawOneBall(Graphics g, int aBallColor, int aType, int ai, int aj)
	{
		int dx = ai * (FaceStyle.BALL_WIDTH);
		int dy = aj * (FaceStyle.BALL_HEIGHT);
		int sx = (aBallColor-1) * FaceStyle.BALL_WIDTH;
		int sy = aType * FaceStyle.BALL_HEIGHT; 
		g.drawImage(mBalls, dx, dy, dx+FaceStyle.BALL_WIDTH, dy+FaceStyle.BALL_HEIGHT, sx, sy, sx+FaceStyle.BALL_WIDTH, sy+FaceStyle.BALL_HEIGHT, this);
	}
	
	/**
	 * @param aSize 传进消去的球数
	 * 计算得分算法
	 */
	private void addMarks(int aSize)
	{
		if (aSize >= 5)
		{
			mMarks += ((aSize - 5) * 5 + 10);
		}
		else if (aSize >= 2)
		{
			mMarks += 5;
		}
	}
	
	/**
	 * @return 当前玩家得分
	 */
	public static int GetMarks()
	{
		return mMarks;
	}

	/**
	 * @param g 图形上下文
	 * @param aBallColor 球的颜色
	 * @param aType 球的类型,诸如是否笑脸之类的
	 * @param ai 该球横坐标,以球个数为单位
	 * @param aj 该球纵坐标,以球个数为单位
	 * @return 是否有消去
	 * 画一个球,并在需要消去时消去,消去球具有动画效果
	 */
	private boolean drawBallCheck(Graphics g, int aBallColor, int aType, int ai, int aj)
	{
		drawOneBall(g, aBallColor, aType, ai, aj);	// 画该球
		
		Dimension di = new Dimension();
		if (isDying(new Dimension(ai, aj), aBallColor))	// 判断是否需要消去
		{
			// 消去
			int size = mBallDying.size();
			addMarks(size);	// 加分
			mEmptyPlace += size;	// 计算空位
			try
			{
				for (int j=0; j<2; j++)	// 产生消去动画效果
				{
					for (int i = 0; i < size; i++)
					{
						di = (Dimension) mBallDying.get(i);
						g.drawImage(mEmpty, di.width * FaceStyle.BALL_WIDTH,
								di.height * FaceStyle.BALL_HEIGHT, this);
					}
					Thread.sleep(170);
					
					for (int i = 0; i < size; i++)
					{
						di = (Dimension) mBallDying.get(i);

						drawOneBall(g, mWhatBall[di.width][di.height], 2, di.width, di.height);
					}
					Thread.sleep(230);
				}
			}
			catch (InterruptedException e)
			{
				
			}
			
			for (int i = 0; i < size; i++)	// 真正抹掉界面中该消去的球
			{
				di = (Dimension) mBallDying.removeLast();
				mWhatBall[di.width][di.height] = BallColor.EMPTY;
				g.drawImage(mEmpty, di.width * FaceStyle.BALL_WIDTH,
						di.height * FaceStyle.BALL_HEIGHT, this);
			}
			
			return true;	// 有消去
		}
		
		return false;	// 无消去
	}
	
	/**
	 * @param g 图形上下文
	 * @param aBallColor 球颜色
	 * @return 能否放下球,一旦不能放下游戏结束
	 * 随机放球
	 */
	public boolean randPlaceBall(Graphics g, int aBallColor)
	{
		if (mEmptyPlace <= 0)	// 无空位
		{
			return false;
		}
		
		int index = mRand.nextInt(mEmptyPlace);	// 得到随机位,这里产生的是第几个空位,这样可保证稳定
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)	// 找到位
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				if (mWhatBall[i][j] == BallColor.EMPTY)
				{
					if (index == 0)
					{
						mWhatBall[i][j] = aBallColor;
						mEmptyPlace--;	// 空位减一
						drawBallCheck(g, aBallColor, 0, i, j);	// 画球
						return true;	// 放球成功
					}
					index--;
				}
			}
		}
		
		return false;	// 无空位
	}
	
	/**
	 * @return 有无路径到达
	 * 找球到达某一条路的路径
	 * 用深度优先搜索算法
	 */
	private boolean isAnyWay()
	{
		if (mToPlace.equals(NO_SELECTED) || mFromPlace.equals(NO_SELECTED))
		{
			return false;
		}
		
		mBallList.clear();	// 列表清空,以放球的路径
		Dimension mCurPlace = new Dimension(mFromPlace);	// 得到球起始位置
		int whereGo[][] = new int[FaceStyle.BALL_HNUM][FaceStyle.BALL_VNUM]; // 用于移动算法中
				
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				if (mWhatBall[i][j] == BallColor.EMPTY)
				{
					whereGo[i][j] = 0;	// 空位置0
				}
				else
				{
					whereGo[i][j] = 1; // 非空位置1
				}
			}
		}
		
		mBallList.addLast(new Dimension(mCurPlace));	// 把当前球加入链表
		whereGo[mCurPlace.width][mCurPlace.height] = -1;	// 回溯时避免走重复路径
		
		int hdis = 0;
		int vdis = 0;
		
		while (true)
		{
			hdis = mToPlace.width - mCurPlace.width;
			vdis = mToPlace.height - mCurPlace.height;
			if ((hdis == 0 && Math.abs(vdis) == 1) || (vdis == 0 && Math.abs(hdis) == 1))
			{
				mBallList.addLast(new Dimension(mToPlace));	// 找到路径
				return true;
			}
			
			if (mCurPlace.width + 1 < FaceStyle.BALL_HNUM &&
				whereGo[mCurPlace.width+1][mCurPlace.height] == 0)	// 右移
			{
				mBallList.addLast(new Dimension(mCurPlace.width+1, mCurPlace.height));
				mCurPlace.setSize(mCurPlace.width+1, mCurPlace.height);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else if (mCurPlace.height + 1 < FaceStyle.BALL_VNUM &&
					whereGo[mCurPlace.width][mCurPlace.height+1] == 0) // 下移
			{
				mBallList.addLast(new Dimension(mCurPlace.width, mCurPlace.height+1));
				mCurPlace.setSize(mCurPlace.width, mCurPlace.height+1);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else if (mCurPlace.height - 1 >= 0 &&
					whereGo[mCurPlace.width][mCurPlace.height-1] == 0) // 上移
			{
				mBallList.addLast(new Dimension(mCurPlace.width, mCurPlace.height-1));
				mCurPlace.setSize(mCurPlace.width, mCurPlace.height-1);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else if (mCurPlace.width - 1 >= 0 &&
				whereGo[mCurPlace.width-1][mCurPlace.height] == 0)	// 左移
			{
				mBallList.addLast(new Dimension(mCurPlace.width-1, mCurPlace.height));
				mCurPlace.setSize(mCurPlace.width-1, mCurPlace.height);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else	// 无路,或回溯,或去死
			{
				if (mBallList.size() == 1)
				{
					// died
					return false;
				}
				else
				{
					// 回溯
					mBallList.removeLast();
					mCurPlace.setSize((Dimension) mBallList.getLast());
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#repaint()
	 * 重载,防止重画
	 */
	public void repaint()
	{
	}
	
	/**
	 * @return 若false,游戏结束,若true,继续.注意这里true和false不是表示移动是否成功
	 * 而只是把randPlaceBall()的返回值返回出来,供上层使用
	 * 移动球的算法,实际上怎么移都保存在mBallList中,保存工作由isAnyWay完成了
	 */
	public boolean moveBall()
	{
		Graphics g = this.getGraphics();
		if (isAnyWay())
		{
			Dimension di = (Dimension) mBallList.removeFirst();
			g.drawImage(mEmpty, mFromPlace.width * FaceStyle.BALL_WIDTH, mFromPlace.height * FaceStyle.BALL_HEIGHT, this);
			while (true)	// 移动动画
			{
				di = (Dimension) mBallList.removeFirst();
				drawOneBall(g, mCurBall, 2, di.width, di.height);
				if (mBallList.size() == 0)
				{
					break;
				}
				try
				{
					Thread.sleep(20);
				} 
				catch (InterruptedException e)
				{
					
				}
				g.drawImage(mEmpty, di.width * FaceStyle.BALL_WIDTH, di.height * FaceStyle.BALL_HEIGHT, this);
			}
			
			mWhatBall[mFromPlace.width][mFromPlace.height] = BallColor.EMPTY;
			mWhatBall[mToPlace.width][mToPlace.height] = mCurBall;
			
			if (!drawBallCheck(g, mCurBall, 0, mToPlace.width, mToPlace.height))
			{
				try
				{
					Thread.sleep(200);
					for (int i=0; i<3; i++)	// 画三个球
					{
						if(!randPlaceBall(g, RandBalls.mRandBalls[i]))
						{
							return false;	// 球已填满,游戏结束
						}
						
						Thread.sleep(120);
					}
					if (mEmptyPlace == 0)	// 球已满,游戏结束
					{
						return false;
					}
				}
				catch (InterruptedException e)
				{
					
				}
				
				RandBalls.getBalls();	// 随机产生三个球 
				if (mEmptyPlace == FaceStyle.BALL_HNUM * FaceStyle.BALL_VNUM)
				{
					// 全部球清空了,必须要新产生三个球,这样以保证游戏的继续
					try
					{
						Thread.sleep(200);
						for (int i=0; i<3; i++)	// 画三个球
						{
							if(!randPlaceBall(g, RandBalls.mRandBalls[i]))
							{
								return false;	// 球已填满,游戏结束
							}
							
							Thread.sleep(120);
						}
					}
					catch (InterruptedException e)
					{
						
					}
					
					RandBalls.getBalls();	// 随机产生三个球 
				}
			}
				
			mCurBall = BallColor.EMPTY;	// 置当前球为空
			mFromPlace.setSize(NO_SELECTED);	// 至当前选中位置为空
		}
		else
		{
			// 不能移,眨下眼睛
			drawOneBall(g, mCurBall, 0, mFromPlace.width, mFromPlace.height);
			try
			{
				Thread.sleep(350);
			} 
			catch (InterruptedException e)
			{
				
			}
			drawOneBall(g, mCurBall, 2, mFromPlace.width, mFromPlace.height);
		}
		mToPlace.setSize(NO_SELECTED);	// 置当前球要移到的位置为空
		
		return true;	// 返回游戏未结束
	}
	
	/**
	 * @param aDi 当前白球所在位置
	 * @param ai 当前是否在横向扫描,取值为-1,0,1
	 * @param aj 当前是否在纵向扫描,取值为-1,0,1
	 * 判断白球的到来将消去周边多少球,或无球则不消去,而这里只在一个方向,如右横,左上斜等
	 * 这个函数纯粹为简化isDying()判断白球情况时的代码
	 */
	private void DyingWhiteDir(Dimension aDi, int ai, int aj)
	{
		int i = aDi.width + ai;
		int j = aDi.height + aj;
		int tmpBall = BallColor.EMPTY;
		while (i < FaceStyle.BALL_HNUM && j < FaceStyle.BALL_VNUM &&
			i >= 0 && j >= 0 && mWhatBall[i][j] != BallColor.EMPTY)
		{
			tmpBall = mWhatBall[i][j];
			mBallDying.addLast(new Dimension(i, j));
			i += ai;
			j += aj;
			if (tmpBall != BallColor.WHITE)
			{
				while (i < FaceStyle.BALL_HNUM && j < FaceStyle.BALL_VNUM &&
						i > 0 && j > 0 && (mWhatBall[i][j] == tmpBall || mWhatBall[i][j] == BallColor.WHITE))
				{
					mBallDying.addLast(new Dimension(i, j));
					i += ai;
					j += aj;
				}
				break;
			}
		}
	}
	
	/**
	 * @param aDi 当前球的位置
	 * @param aColor 当前球的颜色
	 * @return 是否要消去
	 * 判断一个球的到来是否引起消去的算法
	 * 除白球外,其他都至少5个一行,列,斜才消去,白球使周边同一颜色的连一起的行,列,斜都消去
	 */
	private boolean isDying(Dimension aDi, int aColor)
	{
		if (!mBallDying.isEmpty())
		{
			// 用于存要消去球的队列不为空时先清空
			mBallDying.clear();
		}
		
		if (aColor == BallColor.WHITE)
		{
			// 白球时,各方向扫描
			DyingWhiteDir(aDi, 1, 0);	// 右
			DyingWhiteDir(aDi, -1, 0);	// 左
			DyingWhiteDir(aDi, 0, 1);	// 下
			DyingWhiteDir(aDi, 0, -1);	// 上
			DyingWhiteDir(aDi, 1, 1);	// 右下
			DyingWhiteDir(aDi, -1, -1);	// 左上
			DyingWhiteDir(aDi, 1, -1);	// 右上
			DyingWhiteDir(aDi, -1, 1);	// 左下
		}
		else
		{
			// 不是白球,消去算法,白球可替代任意球
			
			// 横向
			int bLen = 1;
			int i = aDi.width + 1;
			int j = aDi.height;
			
			while (i < FaceStyle.BALL_HNUM && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i++, j));
				bLen++;
			}
			i = aDi.width - 1;
			while (i >= 0 && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i--, j));
				bLen++;
			}
			if (bLen < 5)
			{
				// 小于5个一列消去,去掉刚进队列的元素,下同
				mBallDying.clear();	
			}
			
			// 纵向
			bLen = 1;
			i = aDi.width;
			j = aDi.height + 1;
			
			while (j < FaceStyle.BALL_VNUM && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i, j++));
				bLen++;
			}
			j = aDi.height - 1;
			while (j >= 0 && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i, j--));
				bLen++;
			}
			if (bLen < 5)
			{
				for (; bLen>1; bLen--)
				{
					mBallDying.removeLast();
				}
			}
			
			// 斜
			bLen = 1;
			i = aDi.width + 1;
			j = aDi.height + 1;
			
			while (i < FaceStyle.BALL_HNUM && j < FaceStyle.BALL_VNUM && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i++, j++));
				bLen++;
			}
			i = aDi.width - 1;
			j = aDi.height - 1;
			while (i >= 0 && j >= 0 && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i--, j--));
				bLen++;
			}
			if (bLen < 5)
			{
				for (; bLen>1; bLen--)
				{
					mBallDying.removeLast();
				}
			}
			
			// 反斜
			bLen = 1;
			i = aDi.width + 1;
			j = aDi.height - 1;
			
			while (i < FaceStyle.BALL_HNUM && j >= 0 && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i++, j--));
				bLen++;
			}
			i = aDi.width - 1;
			j = aDi.height + 1;
			while (i >= 0 && j < FaceStyle.BALL_VNUM && (mWhatBall[i][j] == aColor || mWhatBall[i][j] == BallColor.WHITE))
			{
				mBallDying.addLast(new Dimension(i--, j++));
				bLen++;
			}
			if (bLen < 5)
			{
				for (; bLen>1; bLen--)
				{
					mBallDying.removeLast();
				}
			}
		}
		
		if (!mBallDying.isEmpty())
		{
			// 非空则该消去
			mBallDying.addLast(new Dimension(aDi));
			return true;
		}
		
		return false;	// 没得消去
	}
	
	public int mCurBall = BallColor.EMPTY;	// 当前球的颜色
	public Dimension mFromPlace = new Dimension(NO_SELECTED);	// 开始球的位置
	public Dimension mToPlace = new Dimension(NO_SELECTED);	// 想要移动到的位置
	public int mWhatBall[][] = new int[FaceStyle.BALL_HNUM][FaceStyle.BALL_VNUM];	// 球的当前状态
	private static int mMarks = 0;	// 静态,玩家当前得分
	private Image mEmpty;	// 空位块图片
	private Image mBalls;	// 球图片
	private static final Dimension NO_SELECTED = new Dimension(-1, -1);	// 空位常量
	private Random mRand = new Random();	// 产生随机数的对象
	private int mEmptyPlace = FaceStyle.BALL_HNUM * FaceStyle.BALL_VNUM;	// 空位数
	private LinkedList mBallList = new LinkedList();	// 用于球的移动, 并记录路径
	private LinkedList mBallDying = new LinkedList();	// 用于记录将要消去的球
}
