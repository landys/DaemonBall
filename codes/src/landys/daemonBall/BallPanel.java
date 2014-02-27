package landys.daemonBall;
/**
 * ������,��Ϸ�ĺ����㷨��������,��������ƶ���,�Ǳ���������Ҫ����
 * ����������GamePanel��
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * @author landys
 * �������,��Ҫ����Ϸ��Ч�����㷨,��������Ӧ�ŵ���ӵ�и���������GamePanel����
 */
public class BallPanel extends JPanel
{
	/**
	 * ���캯��,��ʼ�����б���,����ͼ���ļ�
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
	 * ��ʼ����Ϸ�տ�ʼʱ�Ļ���
	 */
	public void initialize()
	{
		mMarks = 0;
		mEmptyPlace = FaceStyle.BALL_HNUM * FaceStyle.BALL_VNUM;
		mCurBall = BallColor.EMPTY;	// ��ǰ�����ɫ
		mFromPlace = new Dimension(NO_SELECTED);	// ��ʼ���λ��
		mToPlace = new Dimension(NO_SELECTED);	// ��Ҫ�ƶ�����λ��
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				mWhatBall[i][j] = BallColor.EMPTY;
			}
		}
		drawAllBalls(this.getGraphics());	// �������е���
	}
	
	/**
	 * ����Ϸ��������
	 * @param g ͼ��������
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
	 * ���س��ຯ��,��֤��Ϸ�����һ����
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		drawAllBalls(g);
	}
	
	/**
	 * @param aBallColor �����ɫ
	 * @param aType �������, ����Ц��֮���, Ϊ0, 1, 2
	 * @param ai ��ĺ���λ��, ��0��ʼ
	 * @param aj �������λ��, ��0��ʼ
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
	 * @param aSize ������ȥ������
	 * ����÷��㷨
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
	 * @return ��ǰ��ҵ÷�
	 */
	public static int GetMarks()
	{
		return mMarks;
	}

	/**
	 * @param g ͼ��������
	 * @param aBallColor �����ɫ
	 * @param aType �������,�����Ƿ�Ц��֮���
	 * @param ai ���������,�������Ϊ��λ
	 * @param aj ����������,�������Ϊ��λ
	 * @return �Ƿ�����ȥ
	 * ��һ����,������Ҫ��ȥʱ��ȥ,��ȥ����ж���Ч��
	 */
	private boolean drawBallCheck(Graphics g, int aBallColor, int aType, int ai, int aj)
	{
		drawOneBall(g, aBallColor, aType, ai, aj);	// ������
		
		Dimension di = new Dimension();
		if (isDying(new Dimension(ai, aj), aBallColor))	// �ж��Ƿ���Ҫ��ȥ
		{
			// ��ȥ
			int size = mBallDying.size();
			addMarks(size);	// �ӷ�
			mEmptyPlace += size;	// �����λ
			try
			{
				for (int j=0; j<2; j++)	// ������ȥ����Ч��
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
			
			for (int i = 0; i < size; i++)	// ����Ĩ�������и���ȥ����
			{
				di = (Dimension) mBallDying.removeLast();
				mWhatBall[di.width][di.height] = BallColor.EMPTY;
				g.drawImage(mEmpty, di.width * FaceStyle.BALL_WIDTH,
						di.height * FaceStyle.BALL_HEIGHT, this);
			}
			
			return true;	// ����ȥ
		}
		
		return false;	// ����ȥ
	}
	
	/**
	 * @param g ͼ��������
	 * @param aBallColor ����ɫ
	 * @return �ܷ������,һ�����ܷ�����Ϸ����
	 * �������
	 */
	public boolean randPlaceBall(Graphics g, int aBallColor)
	{
		if (mEmptyPlace <= 0)	// �޿�λ
		{
			return false;
		}
		
		int index = mRand.nextInt(mEmptyPlace);	// �õ����λ,����������ǵڼ�����λ,�����ɱ�֤�ȶ�
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)	// �ҵ�λ
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				if (mWhatBall[i][j] == BallColor.EMPTY)
				{
					if (index == 0)
					{
						mWhatBall[i][j] = aBallColor;
						mEmptyPlace--;	// ��λ��һ
						drawBallCheck(g, aBallColor, 0, i, j);	// ����
						return true;	// ����ɹ�
					}
					index--;
				}
			}
		}
		
		return false;	// �޿�λ
	}
	
	/**
	 * @return ����·������
	 * ���򵽴�ĳһ��·��·��
	 * ��������������㷨
	 */
	private boolean isAnyWay()
	{
		if (mToPlace.equals(NO_SELECTED) || mFromPlace.equals(NO_SELECTED))
		{
			return false;
		}
		
		mBallList.clear();	// �б����,�Է����·��
		Dimension mCurPlace = new Dimension(mFromPlace);	// �õ�����ʼλ��
		int whereGo[][] = new int[FaceStyle.BALL_HNUM][FaceStyle.BALL_VNUM]; // �����ƶ��㷨��
				
		for (int i=0; i<FaceStyle.BALL_HNUM; i++)
		{
			for (int j=0; j<FaceStyle.BALL_VNUM; j++)
			{
				if (mWhatBall[i][j] == BallColor.EMPTY)
				{
					whereGo[i][j] = 0;	// ��λ��0
				}
				else
				{
					whereGo[i][j] = 1; // �ǿ�λ��1
				}
			}
		}
		
		mBallList.addLast(new Dimension(mCurPlace));	// �ѵ�ǰ���������
		whereGo[mCurPlace.width][mCurPlace.height] = -1;	// ����ʱ�������ظ�·��
		
		int hdis = 0;
		int vdis = 0;
		
		while (true)
		{
			hdis = mToPlace.width - mCurPlace.width;
			vdis = mToPlace.height - mCurPlace.height;
			if ((hdis == 0 && Math.abs(vdis) == 1) || (vdis == 0 && Math.abs(hdis) == 1))
			{
				mBallList.addLast(new Dimension(mToPlace));	// �ҵ�·��
				return true;
			}
			
			if (mCurPlace.width + 1 < FaceStyle.BALL_HNUM &&
				whereGo[mCurPlace.width+1][mCurPlace.height] == 0)	// ����
			{
				mBallList.addLast(new Dimension(mCurPlace.width+1, mCurPlace.height));
				mCurPlace.setSize(mCurPlace.width+1, mCurPlace.height);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else if (mCurPlace.height + 1 < FaceStyle.BALL_VNUM &&
					whereGo[mCurPlace.width][mCurPlace.height+1] == 0) // ����
			{
				mBallList.addLast(new Dimension(mCurPlace.width, mCurPlace.height+1));
				mCurPlace.setSize(mCurPlace.width, mCurPlace.height+1);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else if (mCurPlace.height - 1 >= 0 &&
					whereGo[mCurPlace.width][mCurPlace.height-1] == 0) // ����
			{
				mBallList.addLast(new Dimension(mCurPlace.width, mCurPlace.height-1));
				mCurPlace.setSize(mCurPlace.width, mCurPlace.height-1);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else if (mCurPlace.width - 1 >= 0 &&
				whereGo[mCurPlace.width-1][mCurPlace.height] == 0)	// ����
			{
				mBallList.addLast(new Dimension(mCurPlace.width-1, mCurPlace.height));
				mCurPlace.setSize(mCurPlace.width-1, mCurPlace.height);
				whereGo[mCurPlace.width][mCurPlace.height] = -1;
				
			}
			else	// ��·,�����,��ȥ��
			{
				if (mBallList.size() == 1)
				{
					// died
					return false;
				}
				else
				{
					// ����
					mBallList.removeLast();
					mCurPlace.setSize((Dimension) mBallList.getLast());
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#repaint()
	 * ����,��ֹ�ػ�
	 */
	public void repaint()
	{
	}
	
	/**
	 * @return ��false,��Ϸ����,��true,����.ע������true��false���Ǳ�ʾ�ƶ��Ƿ�ɹ�
	 * ��ֻ�ǰ�randPlaceBall()�ķ���ֵ���س���,���ϲ�ʹ��
	 * �ƶ�����㷨,ʵ������ô�ƶ�������mBallList��,���湤����isAnyWay�����
	 */
	public boolean moveBall()
	{
		Graphics g = this.getGraphics();
		if (isAnyWay())
		{
			Dimension di = (Dimension) mBallList.removeFirst();
			g.drawImage(mEmpty, mFromPlace.width * FaceStyle.BALL_WIDTH, mFromPlace.height * FaceStyle.BALL_HEIGHT, this);
			while (true)	// �ƶ�����
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
					for (int i=0; i<3; i++)	// ��������
					{
						if(!randPlaceBall(g, RandBalls.mRandBalls[i]))
						{
							return false;	// ��������,��Ϸ����
						}
						
						Thread.sleep(120);
					}
					if (mEmptyPlace == 0)	// ������,��Ϸ����
					{
						return false;
					}
				}
				catch (InterruptedException e)
				{
					
				}
				
				RandBalls.getBalls();	// ������������� 
				if (mEmptyPlace == FaceStyle.BALL_HNUM * FaceStyle.BALL_VNUM)
				{
					// ȫ���������,����Ҫ�²���������,�����Ա�֤��Ϸ�ļ���
					try
					{
						Thread.sleep(200);
						for (int i=0; i<3; i++)	// ��������
						{
							if(!randPlaceBall(g, RandBalls.mRandBalls[i]))
							{
								return false;	// ��������,��Ϸ����
							}
							
							Thread.sleep(120);
						}
					}
					catch (InterruptedException e)
					{
						
					}
					
					RandBalls.getBalls();	// ������������� 
				}
			}
				
			mCurBall = BallColor.EMPTY;	// �õ�ǰ��Ϊ��
			mFromPlace.setSize(NO_SELECTED);	// ����ǰѡ��λ��Ϊ��
		}
		else
		{
			// ������,գ���۾�
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
		mToPlace.setSize(NO_SELECTED);	// �õ�ǰ��Ҫ�Ƶ���λ��Ϊ��
		
		return true;	// ������Ϸδ����
	}
	
	/**
	 * @param aDi ��ǰ��������λ��
	 * @param ai ��ǰ�Ƿ��ں���ɨ��,ȡֵΪ-1,0,1
	 * @param aj ��ǰ�Ƿ�������ɨ��,ȡֵΪ-1,0,1
	 * �жϰ���ĵ�������ȥ�ܱ߶�����,����������ȥ,������ֻ��һ������,���Һ�,����б��
	 * �����������Ϊ��isDying()�жϰ������ʱ�Ĵ���
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
	 * @param aDi ��ǰ���λ��
	 * @param aColor ��ǰ�����ɫ
	 * @return �Ƿ�Ҫ��ȥ
	 * �ж�һ����ĵ����Ƿ�������ȥ���㷨
	 * ��������,����������5��һ��,��,б����ȥ,����ʹ�ܱ�ͬһ��ɫ����һ�����,��,б����ȥ
	 */
	private boolean isDying(Dimension aDi, int aColor)
	{
		if (!mBallDying.isEmpty())
		{
			// ���ڴ�Ҫ��ȥ��Ķ��в�Ϊ��ʱ�����
			mBallDying.clear();
		}
		
		if (aColor == BallColor.WHITE)
		{
			// ����ʱ,������ɨ��
			DyingWhiteDir(aDi, 1, 0);	// ��
			DyingWhiteDir(aDi, -1, 0);	// ��
			DyingWhiteDir(aDi, 0, 1);	// ��
			DyingWhiteDir(aDi, 0, -1);	// ��
			DyingWhiteDir(aDi, 1, 1);	// ����
			DyingWhiteDir(aDi, -1, -1);	// ����
			DyingWhiteDir(aDi, 1, -1);	// ����
			DyingWhiteDir(aDi, -1, 1);	// ����
		}
		else
		{
			// ���ǰ���,��ȥ�㷨,��������������
			
			// ����
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
				// С��5��һ����ȥ,ȥ���ս����е�Ԫ��,��ͬ
				mBallDying.clear();	
			}
			
			// ����
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
			
			// б
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
			
			// ��б
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
			// �ǿ������ȥ
			mBallDying.addLast(new Dimension(aDi));
			return true;
		}
		
		return false;	// û����ȥ
	}
	
	public int mCurBall = BallColor.EMPTY;	// ��ǰ�����ɫ
	public Dimension mFromPlace = new Dimension(NO_SELECTED);	// ��ʼ���λ��
	public Dimension mToPlace = new Dimension(NO_SELECTED);	// ��Ҫ�ƶ�����λ��
	public int mWhatBall[][] = new int[FaceStyle.BALL_HNUM][FaceStyle.BALL_VNUM];	// ��ĵ�ǰ״̬
	private static int mMarks = 0;	// ��̬,��ҵ�ǰ�÷�
	private Image mEmpty;	// ��λ��ͼƬ
	private Image mBalls;	// ��ͼƬ
	private static final Dimension NO_SELECTED = new Dimension(-1, -1);	// ��λ����
	private Random mRand = new Random();	// ����������Ķ���
	private int mEmptyPlace = FaceStyle.BALL_HNUM * FaceStyle.BALL_VNUM;	// ��λ��
	private LinkedList mBallList = new LinkedList();	// ��������ƶ�, ����¼·��
	private LinkedList mBallDying = new LinkedList();	// ���ڼ�¼��Ҫ��ȥ����
}
