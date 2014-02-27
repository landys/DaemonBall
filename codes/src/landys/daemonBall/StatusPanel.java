package landys.daemonBall;
/**
 * 底边区域,目前只存了一个目标分数
 */

import java.awt.*;
import javax.swing.JPanel;

/**
 * @author landys
 * 底边区域绘制类
 */
class StatusPanel extends JPanel
{
	/**
	 * 构造函数
	 */
	public StatusPanel()
	{
		super();

		this.setSize(FaceStyle.BOTTOM_WIDTH, FaceStyle.BOTTOM_HEIGHT);
		mBkImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_BOTTOM));
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * 实现自定义背景绘制
	 */
	public void paint(Graphics g)
	{
		g.drawImage(mBkImage, 0, 0, this);
		super.paintChildren(g);
	}
	
	private Image mBkImage;	// 背景图片
}
