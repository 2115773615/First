package notepad;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.management.monitor.MonitorSettingException;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.View;
import javax.swing.undo.UndoManager;

public class NotePadJframe extends JFrame{
	private JFrame jf;
	private Container c;
	private JMenuBar menu;
	 public JMenu fileMenu,editMenu,formatMenu,lookMenu,helpMenu;
	private JMenuItem newItem,openItem,saveItem,saveotherItem,exitItem,UndoItem,ClipItem,CopyItem,stickItem,deleteItem
	,findItem,findnextItem,replaceItem,turnItem,selectAllItem,timeItem,aboutItem,statusItem;
	private JCheckBoxMenuItem NewlineItem,fontItem;
	public static JTextArea noteja;
	private JScrollPane js;
	private boolean saved=false;//�ж��Ƿ��Ѿ������
	private String fstr,namestr;//��õ�ǰ�ļ��ľ���·��,����ļ���
	private File file;
	public NotePadJframe(){
		
		JFrame jf=new JFrame();
		Container c=jf.getContentPane();
		
		
		noteja=new JTextArea(100, 100);
		js=new JScrollPane(noteja);
		c.add(js);
		/**
		 * getDocument()���doucumentʵ����ͨ��ע�� addUndoableEditListener
		 * (UndoableEditListener listener)����ʵ�ֳ�������
		 */
		UndoManager u=new UndoManager();
		noteja.getDocument().addUndoableEditListener(u);
		
		menu=new JMenuBar();
		jf.setJMenuBar(menu);
		/**
		 * �ļ��˵��Լ��Ӳ˵�
		 */
		fileMenu=new JMenu("�ļ���F��");
		fileMenu.setMnemonic('F');
		menu.add(fileMenu);
		
		newItem=new JMenuItem("�½���N��");
		newItem.setMnemonic('N');
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));//Ctrl+N
		newItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if(noteja.getText().length()!=0){	
					if(saved==true){
						int r=JOptionPane.showInternalConfirmDialog(c, "�Ƿ񽫸��ı��浽"+fstr,"��ʾ��"
								,JOptionPane.YES_NO_OPTION);//����û�������İ�ť����һ����ťΪ0���Դ�����
						if(r==0){
							SavedFile();
						}else if(r==1){
							noteja.setText("");
							saved=false;
							jf.setTitle("�ޱ���-���±�");
						}
					}else{
						int r=JOptionPane.showInternalConfirmDialog(c, "�Ƿ񱣴�","��ʾ��"
								,JOptionPane.YES_NO_OPTION);//����û�������İ�ť����һ����ťΪ0���Դ�����
						if(r==0){
							SavedFile();
						}else if(r==1){
							noteja.setText("");
						}
					}
				}
			}
		});
		fileMenu.add(newItem);
		
		openItem=new JMenuItem("�򿪣�O��");
		openItem.setMnemonic('O');
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				JFileChooser filec=new JFileChooser();
				int returnV=filec.showOpenDialog(c);
				if(returnV==filec.APPROVE_OPTION){
					File file=filec.getSelectedFile();
					//noteja.setText("����·����"+file.getAbsolutePath()+"\n");
					try {
						FileReader fr=new FileReader(file);
						char[] ch=new char[(int) file.length()];
						fr.read(ch);
						String str=String.valueOf(ch).trim();
						noteja.setText(str);
						System.out.println(file.getName());
						jf.setTitle(file.getName().trim()+"-���±�");
						fr.close();
						
					} catch (Exception e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				if(noteja.getText()!=null){
					saved=true;//���ø��ļ��ѱ����
					fstr=file.getAbsolutePath();//��ø��ļ��ľ���·��
				}
				}
			}
		});
		fileMenu.add(openItem);
		
		saveItem=new JMenuItem("���棨S��");
		saveItem.setMnemonic('S');
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������				
				SavedFile();
				jf.setTitle(namestr+"-���±�");//�ѱ����Ϊ��ǰ�ļ���
			}
		});
		fileMenu.add(saveItem);
		
		saveotherItem=new JMenuItem("���Ϊ��A��");
		saveotherItem.setMnemonic('A');
		fileMenu.add(saveotherItem);
		saveotherItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
			try {
				JFileChooser jfile=new JFileChooser();
				int value=jfile.showSaveDialog(c);
				if( value==jfile.APPROVE_OPTION){
					File ff=jfile.getSelectedFile();
					FileWriter fw=new FileWriter(ff);
					String strr=noteja.getText();
					fw.write(strr);
					fw.close();
					fstr=ff.getAbsolutePath();	
					namestr=ff.getName();
					jf.setTitle(namestr+"-���±�");
					saved=true;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}							
			}
		});
		fileMenu.addSeparator();
		
		
		exitItem=new JMenuItem("�˳���X��");
		exitItem.setMnemonic('X');
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);
		
		
		/**
		 * �༭�˵����Ӳ˵�
		 */
		editMenu=new JMenu("�༭��E��");
		editMenu.setMnemonic('E');
		menu.add(editMenu);
		
		UndoItem=new JMenuItem("������U��");
		UndoItem.setMnemonic('U');
		UndoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				u.undo();
			}
		});
		editMenu.add(UndoItem);
		editMenu.addSeparator();
		
		
		ClipItem=new JMenuItem("���У�T��");
		ClipItem.setMnemonic('T');
		ClipItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				noteja.cut();
			}
		});
		ClipItem.setEnabled(false);
		editMenu.add(ClipItem);
		
		CopyItem=new JMenuItem("���ƣ�C��");
		CopyItem.setMnemonic('C');
		CopyItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				noteja.copy();
			}
		});
		CopyItem.setEnabled(false);
		editMenu.add(CopyItem);
		
		stickItem=new JMenuItem("ճ����P��");
		stickItem.setMnemonic('P');
		stickItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				noteja.paste();
			}
		});
		editMenu.add(stickItem);
		
		deleteItem=new JMenuItem("ɾ����L��");
		deleteItem.setMnemonic('L');
		deleteItem.setEnabled(false);
		editMenu.add(deleteItem);
		editMenu.addSeparator();
		
		findItem=new JMenuItem("���ң�D��");
		findItem.setMnemonic('D');
		findItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				new ReplaceFrame();
			}
		});
		editMenu.add(findItem);
		
		findnextItem=new JMenuItem("������һ����N��");
		findnextItem.setMnemonic('N');
		editMenu.add(findnextItem);
		
		replaceItem=new JMenuItem("�滻��R��");
		replaceItem.setMnemonic('R');
		editMenu.add(replaceItem);
		
		turnItem=new JMenuItem("ת����G��");
		turnItem.setMnemonic('G');
		editMenu.add(turnItem);
		editMenu.addSeparator();
		
		selectAllItem=new JMenuItem("ȫѡ��A��");
		selectAllItem.setMnemonic('A');
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		selectAllItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				noteja.selectAll();
				if(noteja.getText()!=null){
					deleteItem.setEnabled(true);
					ClipItem.setEnabled(true);
					CopyItem.setEnabled(true);
				}
			}
		});
		editMenu.add(selectAllItem);
		
		timeItem=new JMenuItem("ʱ�䣨D��");
		timeItem.setMnemonic('D');
		timeItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		timeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				Date date=new Date();
				String time=String.format("%tT", date);
				String day=String.format("%tF", date);
				String times=time+" "+day;
				String notes=noteja.getText();
				int pos=noteja.getCaretPosition();//��ȡ��ǰ����λ��
				if(notes==null)
				noteja.setText(time+" "+times);
				else{
					//String str=notes+" "+times;
					noteja.insert(times, pos);//�ڹ���λ�ò���ʱ��
				}
			}
		});
		editMenu.add(timeItem);
		
		formatMenu=new JMenu("��ʽ��O��");
		formatMenu.setMnemonic('O');
		menu.add(formatMenu);
		
		NewlineItem=new JCheckBoxMenuItem("�Զ����У�W��");
		NewlineItem.setMnemonic('W');
		formatMenu.add(NewlineItem);
		
		fontItem=new JCheckBoxMenuItem("���壨F��");
		fontItem.setMnemonic('F');
		formatMenu.add(fontItem);
		
		lookMenu=new JMenu("�鿴��V��");
		lookMenu.setMnemonic('V');
		menu.add(lookMenu);
		
		statusItem=new JMenuItem("״̬����S��");
		statusItem.setMnemonic('S');
		lookMenu.add(statusItem);
		
		helpMenu=new JMenu("������H��");
		helpMenu.setMnemonic('H');
		menu.add(helpMenu);
		
		aboutItem=new JMenuItem("���ڼ��±���A��");
		aboutItem.setMnemonic('A');
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				JDialog jd=new JDialog(jf, "����", true);
				jd.setLayout(new GridLayout(4, 1));
				JLabel label1=new JLabel("���±�");
				JLabel label2=new JLabel("Version1.0");
				JLabel label3=new JLabel("JRE Version 1.8.0");
				JLabel label4=new JLabel("Author:���ʰ���");
				jd.setSize(250, 300);
				JButton jb=new JButton("ȷ��");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO �Զ����ɵķ������
						jd.dispose();
					}
				});
				jd.add(label1);
				jd.add(label2);
				jd.add(label3);
				jd.add(label4);
				jd.add(jb);
				jd.setVisible(true);
			}
		});
		helpMenu.add(aboutItem);
		
		jf.setSize(600, 300);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
	}
	
	public void SavedFile(){
		try {
			JFileChooser jfile=new JFileChooser();
			
		 if(saved==false){
			 int value=jfile.showSaveDialog(c);
			if( value==jfile.APPROVE_OPTION){
				File ff=jfile.getSelectedFile();
				FileWriter fw=new FileWriter(ff);
				String strr=noteja.getText();
				fw.write(strr);
				fw.close();
				saved=true;
				fstr=ff.getAbsolutePath();	
				namestr=ff.getName();
			}								
		}
		 else if(saved){
			 File fff=new File(fstr);
			 FileWriter ffw=new FileWriter(fff);
			 String strr=noteja.getText();
			 ffw.write(strr);
			 ffw.close();
			 namestr=fff.getName();
		 }
			}
		 catch (Exception e2) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		new NotePadJframe();
	}

}
