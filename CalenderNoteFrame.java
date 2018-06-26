package calendernote;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class CalenderNoteFrame extends JFrame implements ActionListener{
	private JFrame jf;
	private Container c;
	private JButton b1,b2,b3,b4,thisdayButton,notelistButoon,saveButton,deleteButton;
	private JComboBox yearbox,monthbox;
	private JButton[] weekButton=new JButton[7];//��������
	private JButton[] daysButton=new JButton[42];//�·�����
	private String years[]={"2015","2016","2017","2018","2019","2020","2021"};;
	private String months[]={"1","2","3","4","5","6","7","8","9","10","11","12"};
	private JPanel leftpanel,rightpanel;
	private JPanel leftCenter,cardpanel;
	private String year,month;//��¼��ݺ��·�
	private  int recordYear,recordMonth,listCount=0,cancel=1;//��¼��ݿ�/�·ݿ�ǰѡ����±�,listCount��¼һ���ж���������
	private boolean flag=false;//flag�ж�����Ƿ��л���,cancel�ñ�񲻻���Ϊÿ�ε����ť���ظ���������
	private JLabel timeLabel,dateLabel;
	private JTextArea noteja;//�����ı���
    private	DefaultTableModel model;
    private JTable table;
    private CardLayout card;
    private ArrayList<String> arraylist=new ArrayList<>();
	public CalenderNoteFrame(){
		 jf=new JFrame("�������±�");
		 c=jf.getContentPane();
		c.setLayout(new BorderLayout());
		jf.setSize(700, 700);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		/**
		 * ������������
		 */
		leftpanel=new JPanel(new BorderLayout());
		leftCenter=new JPanel();
		leftCenter.setLayout(new GridLayout(7, 7));
		
		/**
		 * ������ڰ�ť�����������ť����
		 */
		String[] week={"������","����һ","���ڶ�","������","������","������","������"};
		Font font=new Font("����",Font.PLAIN, 15);
		for(int i=0;i<week.length;i++){
			weekButton[i]=new JButton(week[i]);
			weekButton[i].setFont(font);
			//weekButton[i].setEnabled(false);
			leftCenter.add(weekButton[i]);
		}
		for( int i=0;i<42;i++){
			daysButton[i]=new JButton("");
			leftCenter.add(daysButton[i]);
		}
			
		
		
		/*
		 * �������
		 */
		String[] col={"ID","����","����"};
		model=new DefaultTableModel(col, 0);
		table=new JTable(model);
		table.setRowSorter(new TableRowSorter<>(model));
		JScrollPane js=new JScrollPane(table);
		/**
		 * ʹ�ÿ�Ƭ���֣�ʹ�ò�ͬ�����Խ��������л�
		 */
		card=new CardLayout();
		cardpanel=new JPanel(card);
		cardpanel.add(leftCenter,"left");
		cardpanel.add(js,"js");
		leftpanel.add(cardpanel, BorderLayout.CENTER);//����Ƭ��������ӵ�������
		/**
		 * ������
		 */
		yearbox=new JComboBox<>(years);
		yearbox.setEditable(false);
		yearbox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				//year=(String) yearbox.getSelectedItem();//��õ�������
				CalclulateDate();
			}
		});
		//System.out.println(yearbox.getSelectedItem());
		monthbox=new JComboBox<>(months);
		monthbox.setEditable(false);
		//System.out.println(monthbox.getSelectedItem());
		monthbox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				CalclulateDate();
			}
		});
		/**
		 * ʹ�ó���һ��ʼ�ʹ��ڵ�ǰ���ڵ�λ��
		 */
		Calendar cc=Calendar.getInstance();
		cc.set(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH), 1);
		int a=cc.get(Calendar.DAY_OF_WEEK)-1;
		for(int i=1;i<=calclulate(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH)+1);i++){
			daysButton[a].setText(""+i+"");
			//daysButton[a].addMouseListener(new MyAdapter(daysButton[a]));
			
			a++;
		}
		for(int i=0;i<years.length;i++){//���ҳ���ǰ��ݶ�Ӧ�������±�
			String temp=String.valueOf(cc.get(Calendar.YEAR));
			if(years[i].equals(temp)){
				yearbox.setSelectedIndex(i);
			}
		}		
		monthbox.setSelectedIndex(cc.get(Calendar.MONTH));
		
		
		b1=new JButton("<");
		b1.addActionListener(this);
		b2=new JButton(">");
		b2.addActionListener(this);
		b3=new JButton("<");
		b3.addActionListener(this);
		b4=new JButton(">");
		b4.addActionListener(this);
		thisdayButton=new JButton("��ǰ����");
		thisdayButton.addActionListener(this);
		notelistButoon=new JButton("�����б�");
		notelistButoon.addActionListener(this);
		
		JPanel jp=new JPanel(new FlowLayout());
		jp.add(b1);jp.add(yearbox);jp.add(b2);
		jp.add(b3);jp.add(monthbox);jp.add(b4);
		jp.add(thisdayButton);jp.add(notelistButoon);
		leftpanel.add(jp, BorderLayout.NORTH);
		c.add(leftpanel,BorderLayout.WEST);
			
		timeLabel=new JLabel();
		Font font2=new Font("����", Font.BOLD, 16);
		timeLabel.setFont(font2);
		Timer time=new Timer();
		TimerTask task=new TimerTask() {//���ö�ʱ�����ڽ�������ʾʱ��
			
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				long timemillis = System.currentTimeMillis();   
                //ת��������ʾ��ʽ   
               SimpleDateFormat df = new SimpleDateFormat("yyyy��MM��dd��      HH:mm:ss  E");  	
               timeLabel.setText("            ��ǰʱ�䣺    "+df.format(new Date(timemillis)));
			}
		};
		time.schedule(task, 1000,1000);//ÿ��һ��ִ��һ��
		rightpanel=new JPanel(new BorderLayout());
		rightpanel.add(timeLabel,BorderLayout.NORTH);
		
		JPanel leftcenter=new JPanel(new BorderLayout());
		dateLabel=new JLabel(" ",JLabel.CENTER);
		dateLabel.setFont(font2);
		
		noteja=new JTextArea(100, 100);
		
		saveButton=new JButton("����");
		saveButton.addActionListener(this);
		deleteButton=new JButton("ɾ��");
		deleteButton.addActionListener(this);
		JPanel buttonpanel=new JPanel(new FlowLayout());
		buttonpanel.add(saveButton);buttonpanel.add(deleteButton);
		leftcenter.add(noteja, BorderLayout.CENTER);
		leftcenter.add(dateLabel, BorderLayout.NORTH);
		rightpanel.add(leftcenter, BorderLayout.CENTER);
		rightpanel.add(buttonpanel, BorderLayout.SOUTH);
		c.add(rightpanel);
	}
	public int calclulate(int year,int month){//�����·ݵ������Լ��Ƿ�������
		boolean flag=false;
		int day=0;
		if((year%4==0&&year%100!=0)||year%400==0)
			flag=true;
		if(flag&&month==2){
			day=29;
		}else{
			day=28;
		}
		if(month==1||month==3||month==5||month==5||month==7||month==8||month==10||month==12){
			day=31;
		}else if(month==4||month==6||month==9||month==11){
			day=30;
		}
		return day;		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1){
			recordYear--;			
			if(recordYear>=0){
				yearbox.setSelectedIndex(recordYear);//ͨ��recordYear��һ������ݿ�������ǰ��һ
			}
			if(recordYear==-1){
				yearbox.setSelectedIndex(yearbox.getItemCount()-1);
			}
		}
		if(e.getSource()==b2){
			//record++;
			recordYear++;
			//System.out.println(record);
			if(recordYear<yearbox.getItemCount()){
				yearbox.setSelectedIndex(recordYear);//ͨ��recordYear��һ������ݿ����������һ
			}			
			//System.out.println(monthbox.getItemCount());
			if(recordYear>=(yearbox.getItemCount())){
				yearbox.setSelectedIndex(0);//��Ϊѡ���±���������Ϊ0������recordҲ���±�����Ϊ0
			}
		}
		if(e.getSource()==b3){
			recordMonth--;			
			if(recordMonth>=0){
				monthbox.setSelectedIndex(recordMonth);
			}
			if(recordMonth==-1){
				monthbox.setSelectedIndex(monthbox.getItemCount()-1);
				yearbox.setSelectedIndex(recordYear-1);
				CalclulateDate();
			}
		}
		if(e.getSource()==b4){
			//record++;
			recordMonth++;
			//System.out.println(record);
			if(recordMonth<monthbox.getItemCount()){
				monthbox.setSelectedIndex(recordMonth);
			}			
			//System.out.println(monthbox.getItemCount());
			if(recordMonth>=(monthbox.getItemCount())){
				monthbox.setSelectedIndex(0);
				yearbox.setSelectedIndex(recordYear+1);
				CalclulateDate();
			}
		}
		if(e.getSource()==thisdayButton){
			for(int i=0;i<daysButton.length;i++){//�������
				daysButton[i].setText("");
			}

			Calendar ccc=Calendar.getInstance();
			for(int i=0;i<years.length;i++){//���ҳ���ǰ��ݶ�Ӧ�������±�
				String temp=String.valueOf(ccc.get(Calendar.YEAR));
				if(years[i].equals(temp)){
					yearbox.setSelectedIndex(i);
				}
			}			
			ccc.set(ccc.get(Calendar.YEAR), ccc.get(Calendar.MONTH), 1);
			monthbox.setSelectedIndex(ccc.get(Calendar.MONTH));//���·ݿ�����Ϊ��ǰ���ڵ��·�
			/*int a=ccc.get(Calendar.DAY_OF_WEEK)-1;
			for(int i=1;i<=calclulate(ccc.get(Calendar.YEAR), ccc.get(Calendar.MONTH)+1);i++){
				daysButton[a++].setText(""+i+"");
			}*/
			CalclulateDate();
		}
		if(e.getSource()==notelistButoon){
			try {
				if(!flag){					
					card.show(cardpanel, "left");
					flag=true;
					if(cancel==2){
						listCount=0;
						 while(model.getRowCount()>0){//�ѱ�����ˢ�£��´���ʾ��ʱ����ͷ��ʼ��ʾ
							 System.out.println(model.getRowCount());
						      model.removeRow(model.getRowCount()-1);
						 }
					}
				}else if(flag){
					card.show(cardpanel, "js");
					flag=false;
					String note,noteTime;
					File file=new File("D://newfile//note");
					File[] notelist=file.listFiles();
					for(int i=0;i<notelist.length;i++){
						if(notelist[i].isFile()){
							listCount++;
							noteTime=notelist[i].getName();
							FileReader fr=new FileReader(notelist[i]);
							char ch[]=new char[(int)notelist[i].length()];
							fr.read(ch);
							note=String.valueOf(ch);
							String row[]={String.valueOf(listCount),noteTime,note};
							model.addRow(row);
							fr.close();
						}
					}
					cancel=2;//���ˢ�µı�־
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
		if(e.getSource()==saveButton){
			try {
				String str=dateLabel.getText();//��Ҫ�������������Ϊ�ļ���
				File file=new File("D://newfile//note",str);//��������strΪ�ļ������ı�
				//FileOutputStream fo=new FileOutputStream(file);				
				FileWriter fw=new FileWriter(file,true);//���ı����е��ı����浽�ļ���
				String ss=noteja.getText();
				fw.write(ss);
				//fo.close();
				fw.close();
				JOptionPane.showMessageDialog(this, "����ɹ���","��ʾ��",JOptionPane.WARNING_MESSAGE);
				noteja.setText("");
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
		if(e.getSource()==deleteButton){
			try {
				String str=dateLabel.getText();
				File file=new File("D://newfile//note//"+str);
				if(file.exists()){
					int r=JOptionPane.showInternalConfirmDialog(c, "ȷ��ɾ����","��ʾ��",JOptionPane.YES_NO_OPTION);
					if(r==0){
						file.delete();
						noteja.setText("");
					}					
				}else if(!file.exists()){
					JOptionPane.showMessageDialog(this, "��һ��û�м���","��ʾ��",JOptionPane.WARNING_MESSAGE);
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	
	public void CalclulateDate(){
		for(int i=0;i<daysButton.length;i++){
			daysButton[i].setText("");
		}
		year=(String)yearbox.getSelectedItem();//��õ�������
		month=(String)monthbox.getSelectedItem();//��õ�����·�
		Calendar ca=Calendar.getInstance();
		//int q=ca.get(Calendar.DAY_OF_MONTH);
		ca.set(Integer.parseInt(year),Integer.parseInt(month)-1, 1);
		int day=ca.get(Calendar.DAY_OF_WEEK)-1;//��һ����Ϊ�������쿪ʼ�㣬������=1
		for(int i=1;i<=calclulate(Integer.parseInt(year),Integer.parseInt(month));i++){
			daysButton[day].setText(""+i+"");
			daysButton[day].addMouseListener(new MyAdapter(daysButton[day]));
			daysButton[day].addActionListener(new setlabel(daysButton[day],day));
			/*if(i==q){
				daysButton[day].setBackground(Color.BLUE);
				
			}*///��ǳ���ǰ����
			day++;
		}
		recordMonth=monthbox.getSelectedIndex();//��¼��ǰ��ݿ�ֵ������
		recordYear=yearbox.getSelectedIndex();//��¼��ǰ�·ݿ�ֵ������
		//System.out.println("�·ݿ������"+recordMonth);
	}

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		new CalenderNoteFrame();
	}
	 class MyAdapter extends MouseAdapter{//ʹ������¼������������ٴ�����
		 JButton button=new JButton();
		 public MyAdapter(JButton b){
			 this.button=b;
		 }
		 public void mouseEntered(MouseEvent e){
			 button.setBackground(Color.RED);
		 }
		 public void mouseExited(MouseEvent e){
			 button.setBackground(null);
		 }
	 }
	 class setlabel implements ActionListener{//�����ťʱ��datelabel�����Ӧ������������
		 private JButton button;
		 private int day;
		 public  setlabel(JButton button,int day) {
			// TODO �Զ����ɵĹ��캯�����
			 this.button=button;
			 this.day=day;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO �Զ����ɵķ������
			String week=null;
			switch (day%7) {
			case 0:
				week="������";
			case 1:
				week="����һ";
				break;
			case 2:
				week="���ڶ�";
				break;
			case 3:
				week="������";
				break;
			case 4:
				week="������";
				break;
			case 5:
				week="������";
				break;
			case 6:
				week="������";
				break;
			}
			year=(String)yearbox.getSelectedItem();//��õ�������
			month=(String)monthbox.getSelectedItem();//��õ�����·�
			dateLabel.setText(year+"��"+month+"��"+button.getText()+"��"
					+week);
		}
		}
}

