package warehouse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WareHouseJframe extends JFrame implements ActionListener{
	private JLabel idjl,namejl,pricejl,stockjl,deletjl;
	private JTextField idjt,namejt,pricejt,stockjt,deletjt;
	private JTextArea showja;
	private JButton addjb,deletjb,showjb;
	private JScrollPane js;
	private JPanel jp,jp2;
	/**
	 * 
	 */
	public WareHouseJframe(){
		JFrame jf=new JFrame("��Ʒ������");
		Container c=jf.getContentPane();
		GridBagLayout grid=new GridBagLayout();
		jp=new JPanel();
		jp2=new JPanel();
		jp2.setLayout(grid);
		jp.setLayout(grid);
		c.setLayout(new BorderLayout());
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
		GridBagConstraints gr=new GridBagConstraints();
		gr.gridx=0;
		gr.gridy=4;
		idjl=new JLabel("��Ʒ��ţ�");
		grid.setConstraints(idjl, gr);
		gr.gridx=1;
		gr.gridy=4;
		idjt=new JTextField(10);
		grid.setConstraints(idjt, gr);
		jp.add(idjl);
		jp.add(idjt);
		
		
		
		gr.gridx=0;
		gr.gridy=3;
		namejl=new JLabel("��Ʒ���ƣ�");
		grid.setConstraints(namejl, gr);
		gr.gridx=1;
		gr.gridy=3;
		namejt=new JTextField(10);
		grid.setConstraints(namejt, gr);
		jp.add(namejl);
		jp.add(namejt);
		
		gr.gridx=0;
		gr.gridy=2;
		pricejl=new JLabel("��Ʒ�۸�");
		grid.setConstraints(pricejl, gr);
		gr.gridx=1;
		gr.gridy=2;
		pricejt=new JTextField(10);
		grid.setConstraints(pricejt, gr);
		jp.add(pricejl);
		jp.add(pricejt);
		
		
		
		gr.gridx=0;
		gr.gridy=1;
		stockjl=new JLabel("��Ʒ��棺");
		grid.setConstraints(stockjl, gr);
		gr.gridx=1;
		gr.gridy=1;
		stockjt=new JTextField(10);
		grid.setConstraints(stockjt, gr);
		jp.add(stockjl);
		jp.add(stockjt);
		
		gr.gridx=1;
		gr.gridy=5;
		addjb=new JButton("�����Ʒ");
		addjb.addActionListener(this);
		grid.setConstraints(addjb, gr);
		jp.add(addjb);
		
		
		gr.gridx=0;
		gr.gridy=7;
		deletjl=new JLabel("Ҫɾ������Ʒ��ţ�");
		grid.setConstraints(deletjl, gr);
		gr.gridx=1;
		gr.gridy=7;
		deletjt=new JTextField(10);
		grid.setConstraints(deletjt, gr);
		gr.gridx=1;
		gr.gridy=8;
		deletjb=new JButton("ɾ������Ʒ");
		deletjb.addActionListener(this);
		grid.setConstraints(deletjb, gr);
		jp.add(deletjl);
		jp.add(deletjt);
		jp.add(deletjb);
		
		
		gr.gridx=2;
		gr.gridy=1;
		showja=new JTextArea(10,30);
		js=new JScrollPane(showja);
		grid.setConstraints(js, gr);
		
		gr.gridx=2;
		gr.gridy=5;
		showjb=new JButton("�鿴��Ʒ");
		grid.setConstraints(showjb, gr);
		showjb.addActionListener(this);
		jp2.add(js);
		jp2.add(showjb);
		
		c.add(jp,BorderLayout.WEST);
		c.add(jp2,BorderLayout.EAST);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==addjb){
			if(idjt.getText().equals("")||namejt.getText().equals("")||pricejt.getText().equals("")||
					stockjt.getText().equals("")){
				JOptionPane.showMessageDialog(this, "��������������Ʒ��Ϣ","��ʾ��",JOptionPane.WARNING_MESSAGE);
			}else{
				Merchandise mer=new Merchandise();
				mer.setId(Integer.parseInt(idjt.getText()));
				mer.setName(namejt.getText());
				mer.setPrice(pricejt.getText());
				mer.setStock(stockjt.getText());
				FindMerchandise findmers=new FindMerchandise();
				Merchandise merss=findmers.findMerchandise(mer);
				if(merss==null){
					Addmerchandise addmer=new Addmerchandise();
					Merchandise mers=addmer.addMerchandise(mer);
					JOptionPane.showMessageDialog(this, "��Ʒ��ӳɹ�","��ʾ��",JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "����Ʒ�Ѵ���","��ʾ��",JOptionPane.WARNING_MESSAGE);
				}
				//System.out.println("��ӳɹ���");
			}
		}
		if(e.getSource()==showjb){
			StringBuffer stb=new StringBuffer();
			ShowMerchandise showmer=new ShowMerchandise();
			ArrayList<Merchandise>list=showmer.showMerchandise();
			Merchandise mer=new Merchandise();
			for(int i=0;i<list.size();i++){
				mer=list.get(i);
				stb.append("��Ʒ��ţ�"+mer.getId()+""+" ��Ʒ���ƣ�"+mer.getName()+""+" ��Ʒ�۸�"+mer.getPrice()+
						""+" ��Ʒ��棺"+mer.getStock()+" "+"\n");
			}
			showja.setText(stb.toString());
		}
		if(e.getSource()==deletjb){
			if(deletjt.getText().equals("")){
				JOptionPane.showMessageDialog(this, "������Ҫɾ������Ʒ���","��ʾ��",JOptionPane.WARNING_MESSAGE);
			}else{
				Merchandise mer=new Merchandise();
				mer.setId(Integer.parseInt(deletjt.getText()));
				FindMerchandise findmer=new FindMerchandise();
				Merchandise mers=findmer.findMerchandise(mer);
				if(mers!=null){
					DleteMerchandise dlemer=new DleteMerchandise();
					dlemer.delteMerchandise(mer);
					JOptionPane.showMessageDialog(this, "��Ʒɾ���ɹ�","��ʾ��",JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "û�и���Ʒ���","��ʾ��",JOptionPane.WARNING_MESSAGE);
				}
			}			
		}
	}
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		new WareHouseJframe();
	}
	

}
