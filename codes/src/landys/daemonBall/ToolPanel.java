package landys.daemonBall;
/**
 * 游戏工具类,包括按钮和分数等,但对操作的响应在GamePanel中,这样做的目的是为了与
 * BallPanel通信
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;

/**
 * @author landys
 * 右边区(工具区)类
 */
public class ToolPanel extends JPanel
{
	/**
	 * 构造函数
	 */
	public ToolPanel()
	{
		super();
		
		this.setSize(FaceStyle.RIGHT_WIDTH, FaceStyle.RIGHT_HEIGHT);
		mBkImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_RIGHT));	
		mImgNum = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_NUM));
		mImgBalls = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_BALLS));
		mImgBtnNew = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_NEW));
		mImgBtnHelp = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_HELP));
		mIcnBtnNew = new ImageIcon(mImgBtnNew);
		mIcnBtnHelp = new ImageIcon(mImgBtnHelp);
		mBtnNew.setIcon(mIcnBtnNew);
		mBtnHelp.setIcon(mIcnBtnHelp);
		mBtnNew.setBounds(FaceStyle.NEW_LEFT, FaceStyle.NEW_TOP, FaceStyle.BTN_WIDTH, FaceStyle.BTN_HEIGHT);
		mBtnHelp.setBounds(FaceStyle.HELP_LEFT, FaceStyle.HELP_TOP, FaceStyle.BTN_WIDTH, FaceStyle.BTN_HEIGHT);
		mBtnNew.setBorder(null);
		mBtnHelp.setBorder(null);
		this.setLayout(null);
		this.add(mBtnNew);
		this.add(mBtnHelp);
	}
	
	/**
	 * 初始化
	 */
	public void initialize()
	{
		Graphics g = this.getGraphics();
		for (int i=0; i<FaceStyle.NUM_NUM; i++)	// 画分数为0
		{
			g.drawImage(mImgNum, FaceStyle.NUM_LEFT+i*FaceStyle.NUM_WIDTH, 
					FaceStyle.NUM_TOP, 
					FaceStyle.NUM_LEFT+(i+1)*FaceStyle.NUM_WIDTH, 
					FaceStyle.NUM_TOP+FaceStyle.NUM_HEIGHT, 
					0, 0, FaceStyle.NUM_WIDTH, FaceStyle.NUM_HEIGHT, this);
		}
		drawBalls(g);	// 画所有球区的球
	}
	
	/**
	 * @param g 图形上下文
	 * 画所有球区的球
	 */
	public void drawBalls(Graphics g)
	{
		int[] balls = RandBalls.mRandBalls;
		for (int i=0; i<3; i++)
		{
			int sx = (balls[i]-1) * FaceStyle.BALL_WIDTH;
			int sy = FaceStyle.BALL_HEIGHT; 
			g.drawImage(mImgBalls, FaceStyle.RAND_BALL_LEFT+i*FaceStyle.BALL_WIDTH, 
					FaceStyle.RAND_BALL_TOP, 
					FaceStyle.RAND_BALL_LEFT+(i+1)*FaceStyle.BALL_WIDTH, 
					FaceStyle.RAND_BALL_TOP+FaceStyle.BALL_HEIGHT, 
					sx, sy, sx+FaceStyle.BALL_WIDTH, sy+FaceStyle.BALL_HEIGHT, this);
		}
	}
	
	/**
	 * @param g 图形上下文
	 * @param aMarks 画分数
	 */
	public void drawMarks(Graphics g, int aMarks)
	{
		int x = FaceStyle.NUM_LEFT + FaceStyle.NUM_WIDTH * (FaceStyle.NUM_NUM - 1);
		int y = FaceStyle.NUM_TOP;
	
		int temp = aMarks;	// 先得到分数
		int dig = 0;
		for (int i=0; i<FaceStyle.NUM_NUM; i++)
		{
			if (temp == 0)
			{
				break;
			}
			dig = temp % 10;
			g.drawImage(mImgNum, x, y, x+FaceStyle.NUM_WIDTH, y+FaceStyle.NUM_HEIGHT, 
					dig*FaceStyle.NUM_WIDTH, 0, (dig+1)*FaceStyle.NUM_WIDTH, FaceStyle.NUM_HEIGHT, this);
			x -= FaceStyle.NUM_WIDTH;
			temp /= 10;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * 重载,实现自定义背景,及游戏图的一些绘制
	 */
	public void paint(Graphics g)
	{
		g.drawImage(mBkImage, 0, 0, this);
		drawBalls(g);
		drawMarks(g, BallPanel.GetMarks());
		super.paintChildren(g);
	}
	
	public JButton mBtnNew = new JButton();	// 重新开始按钮
	public JButton mBtnHelp = new JButton();	// 帮助按钮
	private Image mBkImage;	// 背景图片
	private Image mImgNum;	// 数字图片
	private Image mImgBalls;	// 球图片
	private Image mImgBtnNew;	// 重新开始按钮图片
	private Image mImgBtnHelp;	// 帮助按钮图片
	private ImageIcon mIcnBtnNew;	// 重新开始按钮图标
	private ImageIcon mIcnBtnHelp;	// 帮助按钮图标
}

