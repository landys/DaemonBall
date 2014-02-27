package landys.daemonBall;
/**
 * ��Ϸ������,������ť�ͷ�����,���Բ�������Ӧ��GamePanel��,��������Ŀ����Ϊ����
 * BallPanelͨ��
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;

/**
 * @author landys
 * �ұ���(������)��
 */
public class ToolPanel extends JPanel
{
	/**
	 * ���캯��
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
	 * ��ʼ��
	 */
	public void initialize()
	{
		Graphics g = this.getGraphics();
		for (int i=0; i<FaceStyle.NUM_NUM; i++)	// ������Ϊ0
		{
			g.drawImage(mImgNum, FaceStyle.NUM_LEFT+i*FaceStyle.NUM_WIDTH, 
					FaceStyle.NUM_TOP, 
					FaceStyle.NUM_LEFT+(i+1)*FaceStyle.NUM_WIDTH, 
					FaceStyle.NUM_TOP+FaceStyle.NUM_HEIGHT, 
					0, 0, FaceStyle.NUM_WIDTH, FaceStyle.NUM_HEIGHT, this);
		}
		drawBalls(g);	// ��������������
	}
	
	/**
	 * @param g ͼ��������
	 * ��������������
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
	 * @param g ͼ��������
	 * @param aMarks ������
	 */
	public void drawMarks(Graphics g, int aMarks)
	{
		int x = FaceStyle.NUM_LEFT + FaceStyle.NUM_WIDTH * (FaceStyle.NUM_NUM - 1);
		int y = FaceStyle.NUM_TOP;
	
		int temp = aMarks;	// �ȵõ�����
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
	 * ����,ʵ���Զ��屳��,����Ϸͼ��һЩ����
	 */
	public void paint(Graphics g)
	{
		g.drawImage(mBkImage, 0, 0, this);
		drawBalls(g);
		drawMarks(g, BallPanel.GetMarks());
		super.paintChildren(g);
	}
	
	public JButton mBtnNew = new JButton();	// ���¿�ʼ��ť
	public JButton mBtnHelp = new JButton();	// ������ť
	private Image mBkImage;	// ����ͼƬ
	private Image mImgNum;	// ����ͼƬ
	private Image mImgBalls;	// ��ͼƬ
	private Image mImgBtnNew;	// ���¿�ʼ��ťͼƬ
	private Image mImgBtnHelp;	// ������ťͼƬ
	private ImageIcon mIcnBtnNew;	// ���¿�ʼ��ťͼ��
	private ImageIcon mIcnBtnHelp;	// ������ťͼ��
}

