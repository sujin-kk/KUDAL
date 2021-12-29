package Calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;



public class Swing2 extends JFrame 
{
	int width;
	int height;
	JPanel panel1,panel2;
	Container c;
	JButton bt;
	JLabel tl2;
	JTextArea tl3;
	int year;
	BasicCalendar bc;
	Day da;
	Toolkit kit;
	public Swing2()
	{
		panel1 = new JPanel();
		panel2 = new JPanel();
		c = this.getContentPane();
		JTextField textyear = new JTextField(4);
		JLabel tl1 = new JLabel("��");
		bt = new JButton("�Է�");
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
	    this.width=screenSize.width;
	    this.height=screenSize.height;
	    
		c.setLayout(new BorderLayout());
		//c.add(panel1);
	
		panel2.add(textyear); //�⵵ �Է� â
		panel2.add(tl1);//��
		panel2.add(bt); // �Է� ��ư
		
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				try {
					year=Integer.parseInt(textyear.getText());
					bc= new BasicCalendar(year);
					da= new Day(bc);
					Queue<Integer> GoldenWeek = da.GoldenWeek;
					panel1.setVisible(true);//��ư������ ��� ���̰�
		        	tl2.setText("�� ���� �� : "+da.getHolicount());//label�ȿ� ���ڿ� �ٲ㼭 ���
		    		if(GoldenWeek.isEmpty())
		    			tl3.setText("Ȳ�ݿ��ް� �����ϴ�.");
		 
		    		else
		    		{
		    			tl3.setText("Ȳ�ݿ���\n");
		    			while(!GoldenWeek.isEmpty())
		    				tl3.append(GoldenWeek.remove()+"�� "+GoldenWeek.remove()+"�� ~ "+GoldenWeek.remove()+"�� "+GoldenWeek.remove()+"��\n");
		    		}
		        	//panel2.setVisible(false);//�⺻â ������	
				}catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���");
				}catch(ArrayIndexOutOfBoundsException e2) {
					JOptionPane.showMessageDialog(null, "�Է� ������ �´� ���ڸ� �Է����ּ���");
				}
				              	
			}
			
		});
		
		TitledBorder tb = new TitledBorder(new LineBorder(Color.black,2),"����");

		tl2 = new JLabel("�� ���� �� : ?��");
		tl3 = new JTextArea();
		panel1.add(tl2);
		panel1.add(tl3);
		JScrollPane scrollPane = new JScrollPane(tl3);
	    panel1.add(scrollPane);
		panel1.setVisible(false);//��� �Ⱥ��̰�
	      
	    c.add(panel1,BorderLayout.CENTER);
	    c.add(panel2,BorderLayout.NORTH);
	    
		setTitle("���� ���� ���");
		setSize(470,270);
		this.setLocation(width/2-200, height/2-150);
		setVisible(true);
	}
	
}
