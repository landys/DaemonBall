package landys.daemonBall;
/**
 * 实现帮助对话框
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
 * 实现帮助对话框类
 */
public class HelpDlg extends JDialog
{
	/**
	 * @throws HeadlessException
	 * 构造函数
	 */
	public HelpDlg() throws HeadlessException
	{
		super();
		
		setModal(true);
		setTitle("搞怪碰碰球帮助");
		setSize(FaceStyle.HELPDLG_WIDTH, FaceStyle.HELPDLG_HEIGHT+28);
		setResizable(false);
		addWindowListener(new WindowAdapter()	// 窗体监听,关闭前销毁
		{
			public void windowClosing(WindowEvent evt)
			{
				HelpDlg.this.dispose();
			}
		});
		mBkImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_HELP_DLG));
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
