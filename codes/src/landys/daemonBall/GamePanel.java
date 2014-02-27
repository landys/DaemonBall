package landys.daemonBall;
/**
 * 游戏主要区域类,包含了两个主要区域类的对象,对游戏中操作的响应也在这里
 * 但游戏主要算法不在这里
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author landys
 * 游戏区类,又分为左右两个区域(panel)
 */
public class GamePanel extends JPanel
{
	/**
	 * 构造函数,初始化界面,按钮和图片对象等
	 */
	public GamePanel()
	{
		super();
		
		mBkImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_LEFT));
		this.setLayout(null);
		mBp.setLocation(FaceStyle.BALL_PANEL_LEFT, FaceStyle.BALL_PANEL_TOP);
		mTp.setLocation(FaceStyle.LEFT_WIDTH, 0);
		this.add(mBp);
		this.add(mTp);
		mBp.addMouseListener(new BpMouseListener());
		mTp.mBtnHelp.addActionListener(new TpActionListener());
		mTp.mBtnNew.addActionListener(new TpActionListener());
		mTp.mBtnHelp.addMouseListener(new BpMouseListener());
		mTp.mBtnNew.addMouseListener(new BpMouseListener());
		mImgBtnNewAct = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_NEW_ACT));
		mImgBtnHelpAct = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_HELP_ACT));
		mIcnBtnNewAct = new ImageIcon(mImgBtnNewAct);
		mIcnBtnHelpAct = new ImageIcon(mImgBtnHelpAct);
		mImgBtnNew = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_NEW));
		mImgBtnHelp = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_HELP));
		mIcnBtnNew = new ImageIcon(mImgBtnNew);
		mIcnBtnHelp = new ImageIcon(mImgBtnHelp);
	}

	/**
	 * 游戏开始时的初始化工作都在这里
	 */
	public void initialize()
	{
		mBp.initialize();
		RandBalls.getBalls();	// 首次得到三个随机球
		mTp.initialize();
		
		Graphics gBp = mBp.getGraphics();
		Graphics gTp = mTp.getGraphics();
		for (int i=0; i<5; i++)	// 首先随机放置五个随机球
		{
			int mark1 = BallPanel.GetMarks();
			mBp.randPlaceBall(gBp, RandBalls.getOneBall());
			int mark2 = BallPanel.GetMarks();
			if (mark1 != mark2)
			{
				mTp.drawMarks(gTp, mark2);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * 重载,实现自定义背景绘制
	 */
	public void paint(Graphics g)
	{
		g.drawImage(mBkImage, 0, 0, this);
		super.paintChildren(g);
	}
	
	/**
	 * @author landys
	 * 监听鼠标对BallPanel的点击,及在移到和离开按钮的响应
	 */
	class BpMouseListener extends MouseAdapter
	{
		/**
		 * 构造函数,这里啥也不做
		 */
		public BpMouseListener()
		{
			super();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 * 鼠标点击游戏区的响应
		 */
		public void mouseClicked(MouseEvent e)
		{
			Point p = e.getPoint();
			int x = p.x / FaceStyle.BALL_WIDTH;
			int y = p.y / FaceStyle.BALL_HEIGHT;
			Graphics g = mBp.getGraphics();
			if (mBp.mWhatBall[x][y] == BallColor.EMPTY)
			{
				// 下面看是否该移动球
				if (mBp.mCurBall != BallColor.EMPTY)
				{
					// 移动球
					mBp.mToPlace.setSize(x, y);
					if (!mBp.moveBall())	// 移动球, 可能不能移动, 返回false表示游戏结束
					{
						GameOverDlg god = new GameOverDlg();	// 显示游戏结束提示对话框
						god.setLocationRelativeTo(getParent());
						god.setVisible(true);
						initialize();	// 游戏重新开始
						return;
					}
					Graphics gTp = mTp.getGraphics();
					mTp.drawMarks(gTp, BallPanel.GetMarks());	// 画分数
					mTp.drawBalls(gTp);	// 画右边区域的三个球
				}
			}
			else
			{
				// 选中一个球
				if (x == mBp.mFromPlace.width && y == mBp.mFromPlace.height)
				{
					// 若仍点击选中的球,什么也不做
					return;
				}
				if (mBp.mCurBall != BallColor.EMPTY)
				{
					// 原先已选中过一个球
					mBp.drawOneBall(g, mBp.mCurBall, 0, mBp.mFromPlace.width, mBp.mFromPlace.height);
				}
				mBp.mCurBall = mBp.mWhatBall[x][y];
				mBp.mFromPlace.setSize(x, y);
				mBp.drawOneBall(g, mBp.mCurBall, 2, x, y);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 * 鼠标进入按钮时的响应
		 */
		public void mouseEntered(MouseEvent e)
		{
			if (e.getSource() == mTp.mBtnNew)	// 重新开始按钮
			{
				mTp.mBtnNew.setIcon(mIcnBtnNewAct);
			}
			else if (e.getSource() == mTp.mBtnHelp)	// 帮助按钮
			{
				mTp.mBtnHelp.setIcon(mIcnBtnHelpAct);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 *　鼠标离开按钮时的响应
		 */
		public void mouseExited(MouseEvent e)
		{
			if (e.getSource() == mTp.mBtnNew)	// 重新开始按钮
			{
				mTp.mBtnNew.setIcon(mIcnBtnNew);
			}
			else if (e.getSource() == mTp.mBtnHelp)	// 帮助按钮
			{
				mTp.mBtnHelp.setIcon(mIcnBtnHelp);
			}
		}
	}
	
	/**
	 * @author landys
	 * 监听鼠标对Button的点击
	 */
	class TpActionListener implements ActionListener
	{
		/**
		 * 构造函数,什么也不做
		 */
		public TpActionListener()
		{
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * 按钮按下时的活动
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == mTp.mBtnNew)	// 重新开始按钮
			{
				initialize();
			}
			else if (e.getSource() == mTp.mBtnHelp)	// 帮助按钮
			{
				HelpDlg hd = new HelpDlg();	// 显示帮助对话框
				hd.setLocationRelativeTo(getParent());
				hd.setVisible(true);
			}
		}
	}
	
	private Image mBkImage;	// 背景图片
	private BallPanel mBp = new BallPanel();	// 球区
	private ToolPanel mTp = new ToolPanel();	// 右边工具区
	private Image mImgBtnNewAct;	// 重新开始按钮活动图片
	private Image mImgBtnHelpAct;	// 帮助按钮活动图片
	private ImageIcon mIcnBtnNewAct;	// 重新开始按钮活动图标
	private ImageIcon mIcnBtnHelpAct;	// 帮助按钮活动图标
	private Image mImgBtnNew;	// 重新开始按钮非活动图片
	private Image mImgBtnHelp;	// 帮助按钮活动非图片
	private ImageIcon mIcnBtnNew;	// 重新开始按钮非活动图标
	private ImageIcon mIcnBtnHelp;	// 帮助按钮非活动图标
}
