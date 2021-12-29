package Calendar;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ShowDetail extends JFrame {
	int width;
	int height;
	Toolkit kit;
	Container c;
	TextField title, year, month, day, place;
	String s_title="제목", s_year="년", s_month="월", s_day="일", s_place="장소", s_memo="메모";
	TextArea memo;
	JPanel panel;
	JScrollPane scroll;
	
	public ShowDetail(Vector<Schedule> find) 
	{
		this.setTitle("일정 상세");
		this.setSize(400,450); 
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
		this.width=screenSize.width;
		this.height=screenSize.height;
		this.setLocation(width/2-200, height/2-200);
		panel=new JPanel();
		for(int i=0;i<find.size();i++)
		{
			Schedule s=find.elementAt(i);
			c = this.getContentPane();
			c.setLayout(new BorderLayout());
			
			panel.add(new JLabel("제목"));
			panel.add(title = new TextField(s.getTitle(), 37));
			panel.add(new JLabel("일시"));
			panel.add(year = new TextField(s.getYear(),10));
			panel.add(new JLabel("년"));
			panel.add(month = new TextField(s.getMonth(),6));
			panel.add(new JLabel("월"));
			panel.add(day = new TextField(s.getDay(),6));
			panel.add(new JLabel("일"));
			panel.add(new JLabel("장소"));
			panel.add(place = new TextField(s.getPlace(), 37));
			panel.add(new JLabel("메모"));
			panel.add(memo = new TextArea(s.getMemo(), 8, 43));
			panel.add(new JScrollPane(memo));
		}
		c.add(panel);
		scroll = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    c.add(scroll);
	    panel.setPreferredSize(new Dimension(300, 800));
		this.setVisible(true);
	}
	
	public ShowDetail(Schedule s) 
	{
		this.setTitle("일정 상세");
		this.setSize(400,450); 
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
		this.width=screenSize.width;
		this.height=screenSize.height;
		this.setLocation(width/2-200, height/2-200);
				
		c = this.getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		c.add(new JLabel("제목"));
		c.add(title = new TextField(s.getTitle(), 37));
		c.add(new JLabel("일시"));
		c.add(year = new TextField(s.getYear()));
		c.add(new JLabel("년"));
		c.add(month = new TextField(s.getMonth()));
		c.add(new JLabel("월"));
		c.add(day = new TextField(s.getDay()));
		c.add(new JLabel("일"));
		c.add(new JLabel("장소"));
		c.add(place = new TextField(s.getPlace(), 37));
		c.add(new JLabel("메모"));
		c.add(memo = new TextArea(s.getMemo(), 8, 43));
		c.add(new JScrollPane(memo));
		
		this.setVisible(true);
	}
	
}
