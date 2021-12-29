package Calendar;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

class CalendarForm extends JFrame implements ActionListener {
	String[] day = new String[] { "일", "월", "화", "수", "목", "금", "토" };
	
	JPanel title_pan; // 년도와 월이 들어갈 패널
	JPanel day_pan; // 월~일까지 들어갈 패널
	JPanel date_pan; // 1~31일까지 들어갈 패널
	JLabel year_value;
	JLabel year_label;
	JLabel month_value;
	JLabel month_label;
	JLabel day_label;
	MyFrame myframe;
	String y_value;
	String m_value;
	JButton day_btn;
	JButton[] day_btns = new JButton[31];////////////////////////////////////////////////////////////////////////////////
	int year;
	int month;
	int now_year;
	int now_month;
	int now_day;
	ScheduleManager sm;
	Vector<Schedule> Schs;
	Vector<Schedule> monthSchdule;
	CalendarForm(String Title, MyFrame f, ScheduleManager sm) 
	{
		super(Title);
		this.sm=sm;
		setLayout(new BorderLayout());
		myframe = f;
  
		title_pan = new JPanel();
		day_pan = new JPanel();
		day_pan.setLayout(new GridLayout(0, 7));
		date_pan = new JPanel();
		date_pan.setLayout(new GridLayout(0, 7));
		year_value = new JLabel();
		year_label = new JLabel("년");
		month_value = new JLabel();
		month_label = new JLabel("월");
		month_value = new JLabel();
		Schs = sm.getSchs();
		monthSchdule = new Vector<Schedule>();
		
		for (int count = 0; count < 7; count++) 
		{
			day_label = new JLabel(day[count], JLabel.CENTER);
			day_pan.add(day_label);
			add(day_pan, "Center");
		}
	}

	void setvalue() 
	{
		month = Integer.parseInt(m_value);
	}

	void setbutton() 
	{
		Calendar calendar = Calendar.getInstance();
		now_year = calendar.get(Calendar.YEAR); // 2015
		now_month = calendar.get(Calendar.MONTH) + 1; // 1
		now_day = calendar.get(Calendar.DATE); // 21
		
		for(int i=0;i<Schs.size();i++)	//일정 중 출력하려는 연-월 일정 따로 저장
		{
			Schedule temp = Schs.get(i);
			if(temp.isCheck())
				continue;
			else if(Integer.parseInt(temp.getYear()) == year && Integer.parseInt(temp.getMonth()) == month)
				monthSchdule.add(temp);
		}
		
		year_value.setText("  " + year);
		month_value.setText("" + month);
		calendar.set(year, month - 1, 1);
		int last_day = calendar.getActualMaximum(Calendar.DATE);

		int day = 1;
		int first_day = calendar.get(Calendar.DAY_OF_WEEK);
		int u = first_day - 2;
		for (int a = 1; a < 6; a++)
		{
			for (int b = 1; b < 8; b++) 
			{
				u++;
				if (day <= last_day)
				{
					if (day == 1) 
					{
						for (int t = 1; t < first_day; t++)
						{
							day_btn = new JButton(" ");
							day_btn.setBackground(Color.white);
							date_pan.add(day_btn);
						}
					}
					
					day_btn = new JButton("" + day);
					int aaa = Integer.parseInt(day_btn.getActionCommand());
					int bbb = year;
					int ccc = month;
					if (bbb == now_year && ccc == now_month && aaa == now_day) 
					{
						day_btn.setBackground(Color.pink); // 현재날짜표시
					}
					else 
					{
						day_btn.setBackground(Color.white);
					}
					for(int j = 0; j < monthSchdule.size();j++)
					{
						Schedule temp = monthSchdule.get(j);
						if(Integer.parseInt(temp.getYear()) == bbb && Integer.parseInt(temp.getMonth()) == ccc && Integer.parseInt(temp.getDay()) == aaa)
							day_btn.setBackground(Color.cyan);
					}

					if (u % 7 == 0) 
					{
						day_btn.setForeground(Color.red);
					}
					date_pan.add(day_btn);
     
					day_btns[day-1] = day_btn;
					day_btns[day-1].addActionListener(new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							// TODO Auto-generated method stub
							int day = Integer.parseInt(e.getActionCommand());
							ShowScheduleList kddd = new ShowScheduleList(sm, year, month, day);				
						}
    	 
					});
					day++;

				} 
				else 
				{
					continue;
				}
			}

		}
	}

	void addComponent() 
	{
	
		title_pan.add(year_value);
		title_pan.add(year_label);
		title_pan.add(month_value);
		title_pan.add(month_label);

		add(title_pan, "North");
		date_pan.setLayout(new GridLayout(0, 7));
		date_pan.add(day_btn);
		day_pan.getComponent(0).setForeground(Color.red);
		day_pan.getComponent(6).setForeground(Color.blue);
		int com_num = date_pan.getComponentCount();
		for (int a = 6; a < com_num; a = a + 7) 
		{
			date_pan.getComponent(a).setForeground(Color.blue);
		}
		add(date_pan, "South");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

class MyFrame extends JFrame implements ActionListener {

	Label year_label, month_label;
	JButton view_cal;
	JTextField yearr, monthh;
	ScheduleManager sm;
	Vector<Schedule> schs;
	Queue<String> FUllHoliday;
	boolean check=false;
	public MyFrame(String Title,ScheduleManager sm) 
	{
		super(Title);
		this.sm=sm;
		schs=sm.getSchs();
		setLayout(new FlowLayout());
  
		yearr = new JTextField("2020", 4);
		monthh = new JTextField("5", 2);
		year_label = new Label("년");
		month_label = new Label("월");
  
		for (int month = 1; month <= 12; month++) 
			view_cal = new JButton("달력보기");

		view_cal.addActionListener(this);

		add(yearr);
		add(year_label);
		add(monthh);
		add(month_label);
		add(view_cal);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				dispose();
			}
		});

	}

	public void actionPerformed(ActionEvent e) 
    {
		
        if (e.getActionCommand() == "달력보기") 
        {
            try 
            {
            	check=true;
            	BasicCalendar bc= new BasicCalendar(Integer.parseInt(yearr.getText()));
            	Day da = new Day(bc);
            	FUllHoliday=da.getFUllHoliday();
            	while(!FUllHoliday.isEmpty())
            	{
            		Schedule temp = new Schedule(yearr.getText(),FUllHoliday.remove(),FUllHoliday.remove(),FUllHoliday.remove(),"","");
            		for(int i=0;i<schs.size();i++)
            		{
            			if(!( schs.elementAt(i).getTitle().equals( temp.getTitle() ) && schs.elementAt(i).getYear().equals( temp.getYear() )&& schs.elementAt(i).getMonth().equals( temp.getMonth() ) && schs.elementAt(i).getDay().equals( temp.getDay() ) ))
            				check=true;
            			else
            			{
            				check=false;
            				break;
            			}
            		}
            		if(check)
            			schs.add(temp);
            	}
            	sm.setSchs(schs);
            	CalendarForm c = new CalendarForm("달력보기", this, sm);
            	
            	c.year = Integer.parseInt(yearr.getText());
                c.month = Integer.parseInt(monthh.getText());
                
                if(c.year>2037 || c.year<1900 || c.month>12 || c.month<1)
                        throw new Exception();
                c.setBounds(600, 200, 320, 250);
                c.setVisible(true);
                c.setbutton();
                c.addComponent();
                c.pack();
                dispose();
                c.setResizable(false);
            }
            catch(NumberFormatException e2) 
            {
                    JOptionPane.showMessageDialog(null, "숫자를 입력해주세요");
            }
            catch(Exception e1) 
            {
                    JOptionPane.showMessageDialog(null, "입력 범위에 맞는 숫자를 입력해주세요");
            }

        }
    }
}



