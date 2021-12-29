package Calendar;

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
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShowScheduleList extends JFrame{
	int width;
	int height;
	Vector<String> plan_list = new Vector();
	JList strList;
	Toolkit kit;
	Container c;
	JButton btn;
	ScheduleManager sm;
	Vector<Schedule> schs;
	Vector<Schedule> find;
	int year,month,day;
	public ShowScheduleList(ScheduleManager sm, int year, int month, int day) 
	{
		this.sm=sm;
		this.year=year;
		this.month=month;
		this.day=day;
		schs=sm.getSchs();
		this.setTitle("일정목록");
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
			if(!schs.elementAt(i).isCheck())	//날짜가 정해진 일정인 경우
				if(schs.elementAt(i).getYear().equals(Integer.toString(year))&&schs.elementAt(i).getMonth().equals(Integer.toString(month))&&schs.elementAt(i).getDay().equals(Integer.toString(day)))
				{
					plan_list.add(schs.elementAt(i).getTitle());
				}
		}
		
		strList = new JList(plan_list);
		
		c.add(new JScrollPane(strList)); 
		
		btn = new JButton("추가");
		btn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				dispose();
				PlusSchedule p = new PlusSchedule(sm,year,month,day);
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
	            	   try 
	            	   {
	            		   String title = (String) strList.getSelectedValuesList().get(0);
	            		   find = sm.searchSchedule(title, year, month, day);	                   	            
	            		   ShowDetail k =new ShowDetail(find);	            	                   
	                   }
	            	   catch(IndexOutOfBoundsException e1) {
	                   }
	               } 
	           }
	    });
		this.setVisible(true);
	}

	
}
