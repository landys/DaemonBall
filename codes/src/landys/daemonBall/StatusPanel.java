package landys.daemonBall;
/**
 * �ױ�����,Ŀǰֻ����һ��Ŀ�����
 */

import java.awt.*;
import javax.swing.JPanel;

/**
 * @author landys
 * �ױ����������
 */
class StatusPanel extends JPanel
{
	/**
	 * ���캯��
	 */
	public StatusPanel()
	{
		super();

		this.setSize(FaceStyle.BOTTOM_WIDTH, FaceStyle.BOTTOM_HEIGHT);
		mBkImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ResourcePath.IMG_BOTTOM));
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * ʵ���Զ��屳������
	 */
	public void paint(Graphics g)
	{
		g.drawImage(mBkImage, 0, 0, this);
		super.paintChildren(g);
	}
	
	private Image mBkImage;	// ����ͼƬ
}
