package landys.daemonBall;
/**
 * ʵ����Ϸ������ʾ�Ի���
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
 * ��Ϸ������ʾ�Ի���
 */
public class GameOverDlg extends JDialog
{
	/**
	 * @throws HeadlessException
	 * ���캯��
	 */
	public GameOverDlg() throws HeadlessException
	{
		super();
		
		setModal(true);
		setTitle("��Ϸ����");
		setSize(FaceStyle.GAMEOVER_WIDTH, FaceStyle.GAMEOVER_HEIGHT+28);
		setResizable(false);
		addWindowListener(new WindowAdapter()	// �������,�ر�ǰ����
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
	 * ʵ���Զ��屳��ͼƬ����
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(mBkImage, 0, 26, this);
	}
	
	private Image mBkImage;	// ����ͼƬ
}
