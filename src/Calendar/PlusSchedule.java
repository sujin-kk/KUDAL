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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlusSchedule extends JFrame {
	int width;
	int height;
	Vector<String> plan_list = new Vector();
	JList strList;
	Toolkit kit;
	Container c;
	JButton btn;
	JPanel panel;
	ScheduleManager sm;
	Vector<Schedule> schs;
	int year,month,day;
	public PlusSchedule(ScheduleManager sm, int year, int month, int day) 
	{
		this.sm=sm;
		this.schs=sm.getSchs();
		this.year=year;
		this.month=month;
		this.day=day;
		this.setTitle("불규칙한 일정 등록");
		this.setSize(400,300); 
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
		this.width=screenSize.width;
		this.height=screenSize.height;
		this.setLocation(width/2-200, height/2-150);
	
	
		c = this.getContentPane();
	
		c.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		for(int i=0;i<schs.size();i++)
		{
			if(schs.elementAt(i).isCheck())	//날짜가 안정해진 일정인 경우
				plan_list.add(schs.elementAt(i).getTitle());
		}
		strList = new JList(plan_list);
	
		c.add(new JScrollPane(strList)); 
		btn = new JButton("직접등록");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				AddSchedule as = new AddSchedule(sm,year,month,day);
			}
		
		});
		c.add(btn);
	
		strList.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent evt) 
			{
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() == 2) // 더블클릭
				{
					try {
					 String i = (String) strList.getSelectedValuesList().get(0);
	                 Schedule temp = new Schedule(Integer.toString(year),Integer.toString(month),Integer.toString(day),sm.searchNonSchedule(i).getTitle(),sm.searchNonSchedule(i).getPlace(),sm.searchNonSchedule(i).getMemo());	    
	                 AddSchedule ad = new AddSchedule(sm,temp);
	                 dispose();
					}catch(IndexOutOfBoundsException e1) {
					}
				} 
			}
		});
	
		this.setVisible(true);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				ShowScheduleList kddd = new ShowScheduleList(sm, year, month, day);
			}
		});
	}
	

	
}
