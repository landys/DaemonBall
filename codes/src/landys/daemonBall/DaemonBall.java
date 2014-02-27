package landys.daemonBall;
/**
 * 主要为游戏入口类,继承了JFrame
 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * @author landys
 * 球颜色常量
 */
interface BallColor
{
	// EMPTY表示该块上没有球,WHITE是魔法球,可以消去周围任意球
	int EMPTY = 0, RED = 1, ORANGE = 2, YELLOW = 3, GREEN = 4, 
		BLUE = 5, INDIGO = 6, PURPLE = 7, WHITE = 8;
}

/**
 * @author landys
 * 游戏界面布局常量
 */
interface FaceStyle
{
	int FWIDTH = 546, FHEIGHT = 517;	// Frame宽高
	int LEFT_WIDTH = 390, LEFT_HEIGHT = 442;	// 左边区域宽高
	int GAME_WIDTH = 540, GAME_HEIGHT = 442;	// 主要游戏区的宽高,包括左右两区域
	int RIGHT_WIDTH = 150, RIGHT_HEIGHT = 442;	// 右边区域宽高
	int BOTTOM_WIDTH = 540, BOTTOM_HEIGHT = 43;	// 底部区域宽高
	int BALL_PANEL_LEFT = 21, BALL_PANEL_TOP = 62;	// 游戏球活动区左上角相对于左区域的坐标
	int BALL_WIDTH = 40, BALL_HEIGHT = 40;	// 小球图片的宽高,包括一条两球间的空隙
	int BALL_HNUM = 9, BALL_VNUM = 9;	// 横向和纵向球的个数
	int BALL_PANEL_WIDTH = BALL_HNUM * BALL_WIDTH;	// 游戏球活动区宽
	int BALL_PANEL_HEIGHT = BALL_VNUM * BALL_HEIGHT;	// 游戏球活动区高
	int RAND_BALL_LEFT = 12, RAND_BALL_TOP = 94;	// 下面三个球的提示区左上角相对于右区域的坐标
	int BTN_WIDTH = 85, BTN_HEIGHT = 25;	// 重新开始和帮助按钮的宽高
	int NEW_LEFT = 20, NEW_TOP = 313;	// 重新开始按钮左上角相对于右区域的坐标
	int HELP_LEFT = 20, HELP_TOP = 360;	// 帮助按钮左上角相对于右区域的坐标
	int NUM_LEFT = 18, NUM_TOP = 186;	// 分数显示区左上角相对于右区域的坐标
	int NUM_WIDTH = 15, NUM_HEIGHT = 19;	// 分数显示区的宽高
	int NUM_NUM = 7;	// 分数显示区显示的分数的位数
	int HELPDLG_WIDTH = 331, HELPDLG_HEIGHT = 406;	// 帮助对话框的宽高
	int GAMEOVER_WIDTH = 331, GAMEOVER_HEIGHT = 233;	// 游戏结束提示对话框的宽高
}

/**
 * @author landys
 * 资源路径常量
 */
interface ResourcePath
{
	String IMG_PATH = "image/";
	String IMG_LEFT = IMG_PATH+"left.png";	// 左半区域图片路径
	String IMG_RIGHT = IMG_PATH+"right.png";	// 右半区域图片路径
	String IMG_BOTTOM = IMG_PATH+"bottom.png";	// 底部区域图片路径
	String IMG_BALLS = IMG_PATH+"balls.png";	// 所有球的图片路径
	String IMG_EMPTY = IMG_PATH+"empty.png";	// 没球时的灰色块的图片路径
	String IMG_NUM = IMG_PATH+"num.png";	// 数字图片路径
	String IMG_HELP = IMG_PATH+"help.png";	// 帮助按钮上的图片路径
	String IMG_NEW = IMG_PATH+"new.png";	// 重新开始按钮上的图片路径
	String IMG_HELP_ACT = IMG_PATH+"helpactive.png";	// 鼠标经过时帮助按钮上的图片路径
	String IMG_NEW_ACT = IMG_PATH+"newactive.png";	// 鼠标经过时重新开始按钮上的图片路径
	String IMG_HELP_DLG = IMG_PATH+"helpdlg.png";	// 帮助对话框上的图片路径
	String IMG_GAMEOVER_DLG = IMG_PATH+"gameover.png";	// 游戏结结束提示对话框上的图片路径
}

/**
 * @author landys
 * 游戏main类
 */
public class DaemonBall extends JFrame
{
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param aTitle 标题栏文字
	 * 构造函数
	 */
	public DaemonBall(String aTitle)
	{
		super(aTitle);
		this.setResizable(false);
		
		this.setSize(FaceStyle.FWIDTH, FaceStyle.FHEIGHT);
		Container cp = this.getContentPane();
		cp.setLayout(null);	// 不使用布局管理器
		
		mGp.setBounds(0, 0, FaceStyle.GAME_WIDTH, FaceStyle.GAME_HEIGHT);	// 游戏区定位
		mSp.setLocation(0, FaceStyle.LEFT_HEIGHT);	// 底部区定位
		
		cp.add(mGp);
		cp.add(mSp);
		addWindowListener(new GameOverListener());
		this.setVisible(true);
	}
	
	/**
	 * 程序运行初始化
	 */
	public void initialize()
	{				
		mGp.initialize();
	}
	
	/**
	 * @param args main运行传进的参数,在本游戏中不用传参数
	 * main函数,是本游戏的入口
	 */
	public static void main(String[] args)
	{
		DaemonBall frm = new DaemonBall("搞怪碰碰球");	// 构造游戏总界面对象
		frm.initialize();	// 初始化
	}
	
	/**
	 * @author landys
	 * 实现关闭窗口时真正关闭程序
	 */
	class GameOverListener extends WindowAdapter
	{
		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
		 * 将要关闭窗口时销毁窗体
		 */
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().dispose();
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
		 * 关闭窗口后退出程序
		 */
		public void windowClosed(WindowEvent e)
		{
			System.exit(0);
		}
	}
	
	private GamePanel mGp = new GamePanel();	// 游戏区对象
	private StatusPanel mSp = new StatusPanel();	// 底部区对象
}