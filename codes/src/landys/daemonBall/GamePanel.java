package landys.daemonBall;
/**
 * ��Ϸ��Ҫ������,������������Ҫ������Ķ���,����Ϸ�в�������ӦҲ������
 * ����Ϸ��Ҫ�㷨��������
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author landys
 * ��Ϸ����,�ַ�Ϊ������������(panel)
 */
public class GamePanel extends JPanel
{
	/**
	 * ���캯��,��ʼ������,��ť��ͼƬ�����
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
	 * ��Ϸ��ʼʱ�ĳ�ʼ��������������
	 */
	public void initialize()
	{
		mBp.initialize();
		RandBalls.getBalls();	// �״εõ����������
		mTp.initialize();
		
		Graphics gBp = mBp.getGraphics();
		Graphics gTp = mTp.getGraphics();
		for (int i=0; i<5; i++)	// �������������������
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
	 * ����,ʵ���Զ��屳������
	 */
	public void paint(Graphics g)
	{
		g.drawImage(mBkImage, 0, 0, this);
		super.paintChildren(g);
	}
	
	/**
	 * @author landys
	 * ��������BallPanel�ĵ��,�����Ƶ����뿪��ť����Ӧ
	 */
	class BpMouseListener extends MouseAdapter
	{
		/**
		 * ���캯��,����ɶҲ����
		 */
		public BpMouseListener()
		{
			super();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 * �������Ϸ������Ӧ
		 */
		public void mouseClicked(MouseEvent e)
		{
			Point p = e.getPoint();
			int x = p.x / FaceStyle.BALL_WIDTH;
			int y = p.y / FaceStyle.BALL_HEIGHT;
			Graphics g = mBp.getGraphics();
			if (mBp.mWhatBall[x][y] == BallColor.EMPTY)
			{
				// ���濴�Ƿ���ƶ���
				if (mBp.mCurBall != BallColor.EMPTY)
				{
					// �ƶ���
					mBp.mToPlace.setSize(x, y);
					if (!mBp.moveBall())	// �ƶ���, ���ܲ����ƶ�, ����false��ʾ��Ϸ����
					{
						GameOverDlg god = new GameOverDlg();	// ��ʾ��Ϸ������ʾ�Ի���
						god.setLocationRelativeTo(getParent());
						god.setVisible(true);
						initialize();	// ��Ϸ���¿�ʼ
						return;
					}
					Graphics gTp = mTp.getGraphics();
					mTp.drawMarks(gTp, BallPanel.GetMarks());	// ������
					mTp.drawBalls(gTp);	// ���ұ������������
				}
			}
			else
			{
				// ѡ��һ����
				if (x == mBp.mFromPlace.width && y == mBp.mFromPlace.height)
				{
					// ���Ե��ѡ�е���,ʲôҲ����
					return;
				}
				if (mBp.mCurBall != BallColor.EMPTY)
				{
					// ԭ����ѡ�й�һ����
					mBp.drawOneBall(g, mBp.mCurBall, 0, mBp.mFromPlace.width, mBp.mFromPlace.height);
				}
				mBp.mCurBall = mBp.mWhatBall[x][y];
				mBp.mFromPlace.setSize(x, y);
				mBp.drawOneBall(g, mBp.mCurBall, 2, x, y);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 * �����밴ťʱ����Ӧ
		 */
		public void mouseEntered(MouseEvent e)
		{
			if (e.getSource() == mTp.mBtnNew)	// ���¿�ʼ��ť
			{
				mTp.mBtnNew.setIcon(mIcnBtnNewAct);
			}
			else if (e.getSource() == mTp.mBtnHelp)	// ������ť
			{
				mTp.mBtnHelp.setIcon(mIcnBtnHelpAct);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 *������뿪��ťʱ����Ӧ
		 */
		public void mouseExited(MouseEvent e)
		{
			if (e.getSource() == mTp.mBtnNew)	// ���¿�ʼ��ť
			{
				mTp.mBtnNew.setIcon(mIcnBtnNew);
			}
			else if (e.getSource() == mTp.mBtnHelp)	// ������ť
			{
				mTp.mBtnHelp.setIcon(mIcnBtnHelp);
			}
		}
	}
	
	/**
	 * @author landys
	 * ��������Button�ĵ��
	 */
	class TpActionListener implements ActionListener
	{
		/**
		 * ���캯��,ʲôҲ����
		 */
		public TpActionListener()
		{
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * ��ť����ʱ�Ļ
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == mTp.mBtnNew)	// ���¿�ʼ��ť
			{
				initialize();
			}
			else if (e.getSource() == mTp.mBtnHelp)	// ������ť
			{
				HelpDlg hd = new HelpDlg();	// ��ʾ�����Ի���
				hd.setLocationRelativeTo(getParent());
				hd.setVisible(true);
			}
		}
	}
	
	private Image mBkImage;	// ����ͼƬ
	private BallPanel mBp = new BallPanel();	// ����
	private ToolPanel mTp = new ToolPanel();	// �ұ߹�����
	private Image mImgBtnNewAct;	// ���¿�ʼ��ť�ͼƬ
	private Image mImgBtnHelpAct;	// ������ť�ͼƬ
	private ImageIcon mIcnBtnNewAct;	// ���¿�ʼ��ť�ͼ��
	private ImageIcon mIcnBtnHelpAct;	// ������ť�ͼ��
	private Image mImgBtnNew;	// ���¿�ʼ��ť�ǻͼƬ
	private Image mImgBtnHelp;	// ������ť���ͼƬ
	private ImageIcon mIcnBtnNew;	// ���¿�ʼ��ť�ǻͼ��
	private ImageIcon mIcnBtnHelp;	// ������ť�ǻͼ��
}
