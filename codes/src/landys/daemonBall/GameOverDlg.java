package landys.daemonBall;
/**
 * 实现游戏结束提示对话框
 */

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;

/**
 * @author landys
 * 游戏结束提示对话框
 */
public class GameOverDlg extends JDialog
{
	/**
	 * @throws HeadlessException
	 * 构造函数
	 */
	public GameOverDlg() throws HeadlessException
	{
		super();
		
		setModal(true);
		setTitle("游戏结束");
		setSize(FaceStyle.GAMEOVER_WIDTH, FaceStyle.GAMEOVER_HEIGHT+28);
		setResizable(false);
		addWindowListener(new WindowAdapter()	// 窗体监听,关闭前销毁
		{
			public void windowClosing(WindowEvent evt)
			{
				GameOverDlg.this.dispose();
			}
		});
		mBkImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_GAMEOVER_DLG));
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * 实现自定义背景图片绘制
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(mBkImage, 0, 26, this);
	}
	
	private Image mBkImage;	// 背景图片
}
