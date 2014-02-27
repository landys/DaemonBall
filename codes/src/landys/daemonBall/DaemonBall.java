package landys.daemonBall;
/**
 * ��ҪΪ��Ϸ�����,�̳���JFrame
 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * @author landys
 * ����ɫ����
 */
interface BallColor
{
	// EMPTY��ʾ�ÿ���û����,WHITE��ħ����,������ȥ��Χ������
	int EMPTY = 0, RED = 1, ORANGE = 2, YELLOW = 3, GREEN = 4, 
		BLUE = 5, INDIGO = 6, PURPLE = 7, WHITE = 8;
}

/**
 * @author landys
 * ��Ϸ���沼�ֳ���
 */
interface FaceStyle
{
	int FWIDTH = 546, FHEIGHT = 517;	// Frame���
	int LEFT_WIDTH = 390, LEFT_HEIGHT = 442;	// ���������
	int GAME_WIDTH = 540, GAME_HEIGHT = 442;	// ��Ҫ��Ϸ���Ŀ��,��������������
	int RIGHT_WIDTH = 150, RIGHT_HEIGHT = 442;	// �ұ�������
	int BOTTOM_WIDTH = 540, BOTTOM_HEIGHT = 43;	// �ײ�������
	int BALL_PANEL_LEFT = 21, BALL_PANEL_TOP = 62;	// ��Ϸ�������Ͻ�����������������
	int BALL_WIDTH = 40, BALL_HEIGHT = 40;	// С��ͼƬ�Ŀ��,����һ�������Ŀ�϶
	int BALL_HNUM = 9, BALL_VNUM = 9;	// �����������ĸ���
	int BALL_PANEL_WIDTH = BALL_HNUM * BALL_WIDTH;	// ��Ϸ������
	int BALL_PANEL_HEIGHT = BALL_VNUM * BALL_HEIGHT;	// ��Ϸ������
	int RAND_BALL_LEFT = 12, RAND_BALL_TOP = 94;	// �������������ʾ�����Ͻ�����������������
	int BTN_WIDTH = 85, BTN_HEIGHT = 25;	// ���¿�ʼ�Ͱ�����ť�Ŀ��
	int NEW_LEFT = 20, NEW_TOP = 313;	// ���¿�ʼ��ť���Ͻ�����������������
	int HELP_LEFT = 20, HELP_TOP = 360;	// ������ť���Ͻ�����������������
	int NUM_LEFT = 18, NUM_TOP = 186;	// ������ʾ�����Ͻ�����������������
	int NUM_WIDTH = 15, NUM_HEIGHT = 19;	// ������ʾ���Ŀ��
	int NUM_NUM = 7;	// ������ʾ����ʾ�ķ�����λ��
	int HELPDLG_WIDTH = 331, HELPDLG_HEIGHT = 406;	// �����Ի���Ŀ��
	int GAMEOVER_WIDTH = 331, GAMEOVER_HEIGHT = 233;	// ��Ϸ������ʾ�Ի���Ŀ��
}

/**
 * @author landys
 * ��Դ·������
 */
interface ResourcePath
{
	String IMG_PATH = "image/";
	String IMG_LEFT = IMG_PATH+"left.png";	// �������ͼƬ·��
	String IMG_RIGHT = IMG_PATH+"right.png";	// �Ұ�����ͼƬ·��
	String IMG_BOTTOM = IMG_PATH+"bottom.png";	// �ײ�����ͼƬ·��
	String IMG_BALLS = IMG_PATH+"balls.png";	// �������ͼƬ·��
	String IMG_EMPTY = IMG_PATH+"empty.png";	// û��ʱ�Ļ�ɫ���ͼƬ·��
	String IMG_NUM = IMG_PATH+"num.png";	// ����ͼƬ·��
	String IMG_HELP = IMG_PATH+"help.png";	// ������ť�ϵ�ͼƬ·��
	String IMG_NEW = IMG_PATH+"new.png";	// ���¿�ʼ��ť�ϵ�ͼƬ·��
	String IMG_HELP_ACT = IMG_PATH+"helpactive.png";	// ��꾭��ʱ������ť�ϵ�ͼƬ·��
	String IMG_NEW_ACT = IMG_PATH+"newactive.png";	// ��꾭��ʱ���¿�ʼ��ť�ϵ�ͼƬ·��
	String IMG_HELP_DLG = IMG_PATH+"helpdlg.png";	// �����Ի����ϵ�ͼƬ·��
	String IMG_GAMEOVER_DLG = IMG_PATH+"gameover.png";	// ��Ϸ�������ʾ�Ի����ϵ�ͼƬ·��
}

/**
 * @author landys
 * ��Ϸmain��
 */
public class DaemonBall extends JFrame
{
	/**
	 * �汾��
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param aTitle ����������
	 * ���캯��
	 */
	public DaemonBall(String aTitle)
	{
		super(aTitle);
		this.setResizable(false);
		
		this.setSize(FaceStyle.FWIDTH, FaceStyle.FHEIGHT);
		Container cp = this.getContentPane();
		cp.setLayout(null);	// ��ʹ�ò��ֹ�����
		
		mGp.setBounds(0, 0, FaceStyle.GAME_WIDTH, FaceStyle.GAME_HEIGHT);	// ��Ϸ����λ
		mSp.setLocation(0, FaceStyle.LEFT_HEIGHT);	// �ײ�����λ
		
		cp.add(mGp);
		cp.add(mSp);
		addWindowListener(new GameOverListener());
		this.setVisible(true);
	}
	
	/**
	 * �������г�ʼ��
	 */
	public void initialize()
	{				
		mGp.initialize();
	}
	
	/**
	 * @param args main���д����Ĳ���,�ڱ���Ϸ�в��ô�����
	 * main����,�Ǳ���Ϸ�����
	 */
	public static void main(String[] args)
	{
		DaemonBall frm = new DaemonBall("���������");	// ������Ϸ�ܽ������
		frm.initialize();	// ��ʼ��
	}
	
	/**
	 * @author landys
	 * ʵ�ֹرմ���ʱ�����رճ���
	 */
	class GameOverListener extends WindowAdapter
	{
		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
		 * ��Ҫ�رմ���ʱ���ٴ���
		 */
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().dispose();
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
		 * �رմ��ں��˳�����
		 */
		public void windowClosed(WindowEvent e)
		{
			System.exit(0);
		}
	}
	
	private GamePanel mGp = new GamePanel();	// ��Ϸ������
	private StatusPanel mSp = new StatusPanel();	// �ײ�������
}