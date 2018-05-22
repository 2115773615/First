package notebook;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NoteBookJframe extends JFrame implements ActionListener{
	private JTextArea addja,showja;
	private JButton addjb,showjb;
	private JPanel addjp,showjpp;
	private JScrollPane showjp;
	public NoteBookJframe(){
		JFrame jf=new JFrame("���׼��±�һ");
		Container c=jf.getContentPane();
		c.setLayout(new GridLayout(2, 1));
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		
		addja=new JTextArea(10, 30);
		addjb=new JButton("���");
		addjb.addActionListener(this);
		addjp=new JPanel(new GridLayout(2, 1));
		addjp.add(addja);
		addjp.add(addjb);
		
		showja=new JTextArea(10, 30);
		showjb=new JButton("�鿴");
		showjb.addActionListener(this);
		showjp=new JScrollPane();
		showjp.setViewportView(showja);
		//showjp.add(showjb);
		showjpp=new JPanel(new GridLayout(2, 1));
		showjpp.add(showjp);
		showjpp.add(showjb);
		c.add(addjp);
		c.add(showjpp);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==addjb){
			if(addja.getText().equals("")){
				JOptionPane.showMessageDialog(this, "δ������Ϣ","��ʾ��",JOptionPane.WARNING_MESSAGE);
			}else{
				String str=addja.getText();
				new WriteNoteBook(str);
				JOptionPane.showMessageDialog(this, "��ӳɹ�","��ʾ��",JOptionPane.WARNING_MESSAGE);
				addja.setText("");
			}
		}
		if(e.getSource()==showjb){
			ReaderNoteBook rn=new ReaderNoteBook();
			String str=rn.readerNoteBook();
			showja.setText(str);
		}
	}
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		new NoteBookJframe();
	}


}
