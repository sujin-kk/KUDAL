package Calendar;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CombineSchedule extends JFrame implements ActionListener{
	int width;
	int height;
	Toolkit kit;
	Container c;
	JButton btn1, btn2,btn3;
	JLabel title;
	ScheduleManager sm;
	public CombineSchedule(ScheduleManager sm) {
		this.sm=sm;
		this.setTitle("���� ����");
		this.setSize(500,500); 
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
		this.width=screenSize.width;
		this.height=screenSize.height;
		this.setLocation(width/2-200, height/2-200);
		 //�⺻ ������ ����
		
		c=this.getContentPane();
		
		btn1 = new JButton("���� �߰�");
		btn2 = new JButton("���� �˻�");
		btn3 = new JButton("���� ����");
		
		title = new JLabel("���� ����");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		c.setLayout(new FlowLayout(FlowLayout.CENTER, 400, 40));
		
		c.add(title);
		
		c.add(btn1);
		c.add(btn2);
		c.add(btn3);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn1) 
		{
			IsRegular a=new IsRegular(sm);
		}
		else if (e.getSource()==btn2) 
		{
			FindSchedule aaa= new FindSchedule(sm);
		}
		else if (e.getSource()==btn3) 
		{
			//��������
		}
	}

}
