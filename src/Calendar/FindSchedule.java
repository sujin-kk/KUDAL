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
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FindSchedule extends JFrame {
	int width;
	int height;
	Vector<String> plan_list = new Vector();
	JList strList;
	Toolkit kit;
	Container c;
	JButton btn;
	JPanel panel1,panel2;
	ScheduleManager sm;
	Vector<Schedule> schs;
	Vector<Schedule> search;
	JScrollPane scroll;
	public FindSchedule(ScheduleManager sm) 
	{
		this.sm=sm;
		schs=sm.getSchs();
		this.setTitle("일정 검색");
		this.setSize(400,300); 
		kit=this.getToolkit();
		Dimension screenSize =kit.getScreenSize();
		this.width=screenSize.width;
		this.height=screenSize.height;
		this.setLocation(width/2-200, height/2-150);
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		c = this.getContentPane();
		c.setLayout(new BorderLayout());

		JTextField schedulename = new JTextField(10);
		panel2.add(schedulename);
		btn = new JButton("검색");
		btn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				panel1.setVisible(true);
				search=sm.searchScheVector(schedulename.getText());		
				for(int i=0;i<search.size();i++)
				{
					plan_list.add(Integer.toString(i+1));
				}
			}
		
		});
		panel2.add(btn);
		c.add(panel2,BorderLayout.NORTH);
		strList = new JList(plan_list);
		panel1.add(strList);
		scroll = new JScrollPane(strList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel1.add(scroll);
		
		

		
		//scroll.setViewportView(strList);
		strList.setPreferredSize(new Dimension(300, 500));
	
		c.add(panel1,BorderLayout.CENTER);
		panel1.setVisible(false);
		strList.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent evt) 
			{
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() == 2) // 더블클릭
				{
					String i = (String) strList.getSelectedValuesList().get(0);
	                ShowDetail k =new ShowDetail(search.elementAt(Integer.parseInt(i)));
				} 
			}
		});
	
		this.setVisible(true);
		}
	
}
