package landys.daemonBall;
/**
 * ʵ�ְ����Ի���
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
 * ʵ�ְ����Ի�����
 */
public class HelpDlg extends JDialog
{
	/**
	 * @throws HeadlessException
	 * ���캯��
	 */
	public HelpDlg() throws HeadlessException
	{
		super();
		
		setModal(true);
		setTitle("������������");
		setSize(FaceStyle.HELPDLG_WIDTH, FaceStyle.HELPDLG_HEIGHT+28);
		setResizable(false);
		addWindowListener(new WindowAdapter()	// �������,�ر�ǰ����
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
	 * ʵ���Զ��屳��ͼƬ����
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(mBkImage, 0, 26, this);
	}
	
	private Image mBkImage;	// ����ͼƬ
}
