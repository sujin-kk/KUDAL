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

public class IsRegular extends JFrame implements ActionListener{//정기적 비정기적 설정 스윙창
	int width;
	int height;
	Toolkit kit;
	Container c;
	JButton btn1, btn2;
	JLabel title;
	ScheduleManager sm;
	public IsRegular(ScheduleManager sm) {
		this.sm=sm;
		this.setTitle("일정 추가");
		this.setSize(500,500); 
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
		this.width=screenSize.width;
		this.height=screenSize.height;
		this.setLocation(width/2-200, height/2-200);
		//기본 프레임 설정
		
		c=this.getContentPane();
		
		btn1 = new JButton("정기적 일정");
		btn2 = new JButton("비정기적 일정");
		title = new JLabel("일정 추가");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		
		c.setLayout(new FlowLayout(FlowLayout.CENTER, 400, 40));
		
		c.add(title);
		
		c.add(btn1);
		c.add(btn2);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn1) 
		{
			AddRegular aaaa = new AddRegular(sm);
		}
		else if (e.getSource()==btn2) 
		{
			AddSchedule aaaa = new AddSchedule(sm);
		}
		
		
	}

}
