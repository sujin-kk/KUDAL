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
import javax.swing.JPanel;



public class MainFrame extends JFrame implements ActionListener {
	int width;
	int height;
	Toolkit kit;
	Container c;
	JButton btn1, btn2, btn3, btn4, btn5;
	JLabel title;
	ScheduleManager sm = new ScheduleManager();
	public MainFrame() {
		this.setTitle("KuDal");
		this.setSize(500,500); 
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
		this.width=screenSize.width;
		this.height=screenSize.height;
		this.setLocation(width/2-200, height/2-200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //기본 프레임 설정
		
		c=this.getContentPane();
		
		btn1 = new JButton("달력 보여주기");
		btn2 = new JButton("요일 검색");
		btn3 = new JButton("휴일 판별");
		btn4 = new JButton("일정 추가");
		btn5 = new JButton("일정 검색");
		title = new JLabel("KU DAL");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		
		c.setLayout(new FlowLayout(FlowLayout.CENTER, 400, 40));
		
		c.add(title);
		
		c.add(btn1);
		c.add(btn2);
		c.add(btn3);
		c.add(btn4);
		c.add(btn5);
		
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn1) 
		{
			 MyFrame f = new MyFrame("달력",sm);
			 f.setBounds(400, 200, 300, 200);
			 f.setVisible(true);
		}
		else if (e.getSource()==btn2) 
		{
			Swing1 aa = new Swing1();
		}
		else if (e.getSource()==btn3) 
		{
			Swing2 aaa = new Swing2();
		}
		else if (e.getSource()==btn4) 
		{
			CombineSchedule cs = new CombineSchedule(sm);
		}
		else
		{
			FindSchedule aaaaa= new FindSchedule(sm);
		}
		
	}

}

