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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class AddSchedule extends JFrame {
   int width;
   int height;
   Toolkit kit;
   Container c;
   
   TextField title, textyear, textmonth, textday, textplace;
   TextArea memo;
   JButton btn;
   ScheduleManager sm;
   Vector<Schedule> schs;
   ButtonGroup group;
   JRadioButton solar, lunar; //��� ���� ���� ��ư
   JCheckBox yun;
   boolean isLunar = false, isYun = false, isFind = false;
   int solYear, solMon, solDay, lunYear, lunMon, lunDay;
   LunarCalendar lc;
   int feb;
   BasicCalendar bc;
   private int Cal[][];
   
   public AddSchedule(ScheduleManager sm) 
   {
      this.sm=sm;
      schs=sm.getSchs();
      this.setTitle("���� ���");
      this.setSize(400,470); 
      kit=this.getToolkit();
      Dimension screenSize =kit.getScreenSize();
      this.width=screenSize.width;
      this.height=screenSize.height;
      this.setLocation(width/2-200, height/2-200);
      c = this.getContentPane();

      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("����"));
      c.add(title = new TextField(37));
      c.add(new JLabel("�Ͻ�"));
      c.add(textyear = new TextField(10));
      c.add(new JLabel("��"));
      c.add(textmonth = new TextField(5));
      c.add(new JLabel("��"));
      c.add(textday = new TextField(5));
      c.add(new JLabel("��"));
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("���"));
      solar.setSelected(true); //��� ���� ������ư, �⺻ ��
      c.add(lunar = new JRadioButton("����")); //���� ���� ������ư
      group.add(solar);
      group.add(lunar);
      c.add(yun = new JCheckBox("����")); //���´������� ���� üũ�ڽ� ���̱�
      yun.setVisible(false);
      
      JLabel space = new JLabel("                                                                       ");
      c.add(space);
      
      solar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(solar.isSelected()) {
               isLunar = false;
               yun.setVisible(false);
               space.setText("                                                                       ");
            }
         }
         
      });
      
      lunar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(lunar.isSelected()) {
               isLunar = true;
               yun.setVisible(true);
               space.setText("                                                     ");
            }
            else {
               isLunar = false;
               isYun = false;
               yun.setVisible(false);
               space.setText("                                                                       ");
            }
         }
      });
      
      yun.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(yun.isSelected()) 
               isYun = true;
            else
               isYun = false;
         }
      });

      c.add(new JLabel("���"));
      c.add(textplace = new TextField(30));

      c.add(new JLabel("            "));
      c.add(new JLabel("�޸�"));
      c.add(memo = new TextArea(8, 43));
      c.add(new JScrollPane(memo));
   
      btn = new JButton("����");
      c.add(btn);
   
      btn.addActionListener(new ActionListener() //�Է°� �����ؾ���
      {

         @Override
         public void actionPerformed(ActionEvent e) 
         {
            // TODO Auto-generated method stub
        	 try {
        	
            if(textyear.getText().equals("") && textmonth.getText().equals("") && textday.getText().equals("")) { // ��¥ �� ���� ��������
               if(title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               else 
               {
                  Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  dispose();   
               }
            }
                  
            
            else // ��¥ �ѹ��� ������ ������
            { //��¥�Է�!!
            	bc = new BasicCalendar(Integer.parseInt(textyear.getText()));
    			feb = bc.getFeb();
               if(textyear.getText().equals("") || textmonth.getText().equals("") || textday.getText().equals("")) // ��¥�� �ѹ��� �ޱ� 
                  JOptionPane.showMessageDialog(null, "��/��/���� ��� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               
               else if(title.getText().equals("")) // ���� ������ �ʼ�
                  JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE); 
               
               else if(!isNumeric(textyear.getText()) || Integer.parseInt(textyear.getText())<1900 || Integer.parseInt(textyear.getText())>2037) // �� ����
                  JOptionPane.showMessageDialog(null, "�⵵�� Ȯ���ϼ���.", "���", JOptionPane.WARNING_MESSAGE);
               
               else if(!isNumeric(textmonth.getText()) || Integer.parseInt(textmonth.getText())<1 || Integer.parseInt(textmonth.getText())>12) // �� ����
                  JOptionPane.showMessageDialog(null, "1������ 12�������� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               
               else if (((Integer.parseInt(textmonth.getText()) < 8
						&& (Integer.parseInt(textmonth.getText()) % 2 == 1))
						|| (Integer.parseInt(textmonth.getText()) >= 8)
								&& Integer.parseInt(textmonth.getText()) % 2 == 0) && (!isNumeric(textday.getText())||
						 (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 31))) // ��																					// ����
					JOptionPane.showMessageDialog(null, "1�Ϻ��� 31�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
				else if((Integer.parseInt(textmonth.getText()) == 2) &&(!isNumeric(textday.getText())||(Integer.parseInt(textday.getText()) < 1||Integer.parseInt(textday.getText()) > feb))) {
					JOptionPane.showMessageDialog(null, "1�Ϻ��� "+feb+"�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
				}
				else if ((((Integer.parseInt(textmonth.getText()) < 8
						&& (Integer.parseInt(textmonth.getText()) % 2 == 0))
						|| (Integer.parseInt(textmonth.getText()) >= 8)
								&& Integer.parseInt(textmonth.getText()) % 2 == 1)) && (!isNumeric(textday.getText())
						|| (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 30))) // ��
																													// ����
					JOptionPane.showMessageDialog(null, "1�Ϻ��� 30�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               else { // ������ ���  ���vs����
                  

               if( textmonth.getText().length()==2 && (int)Math.log10(Integer.parseInt(textmonth.getText()))==0 && textmonth.getText().contains("0") ) {
                  
                  if(textday.getText().length()==2 && (int)Math.log10(Integer.parseInt(textday.getText()))==0 && textday.getText().contains("0"))
                  {
                     //���ϵѴ� 0��
                     if(!isLunar) //���
                     { 
                    	 Schedule s = new Schedule(textyear.getText(),textmonth.getText().substring(1), textday.getText().substring(1),title.getText(),textplace.getText(),memo.getText());
                    	 schs.add(s);
                    	 sm.setSchs(schs);
                    	 dispose();
                     }   
                     else 
                     {
                        lunYear = Integer.parseInt(textyear.getText());
                        lunMon = Integer.parseInt(textmonth.getText().substring(1));
                        lunDay = Integer.parseInt(textday.getText().substring(1));
                        String sol[];
                        sol = lunarTosolar(lunYear, lunMon, lunDay, isYun);
                        if(sol==null)
                           JOptionPane.showMessageDialog(null, "���� ���� Ȯ�� �Ǵ� �������� �ʴ� ���� ��¥.", "���", JOptionPane.WARNING_MESSAGE);
                        else 
                        {
                           memo.append("\n���� "+lunYear+"�� "+lunMon+"�� "+lunDay+"��\n");
                           Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                           schs.add(s);
                           sm.setSchs(schs);
                           JOptionPane.showMessageDialog(null, sol[0]+"�� "+sol[1]+"�� "+sol[2]+"�Ͽ� �߰�");
                           dispose();

                        }
                     }
                  } 
                  
                  else 
                  {
                     //������ 0
                     if(!isLunar) 
                     { 
                    	 Schedule s = new Schedule(textyear.getText(),textmonth.getText().substring(1),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                    	 schs.add(s);
                    	 sm.setSchs(schs);
                    	 dispose();
                     }
                     else 
                     {
                        lunYear = Integer.parseInt(textyear.getText());
                        lunMon = Integer.parseInt(textmonth.getText().substring(1));
                        lunDay = Integer.parseInt(textday.getText());
                        String sol[];
                        sol = lunarTosolar(lunYear, lunMon, lunDay, isYun);
                        if(sol==null)
                           JOptionPane.showMessageDialog(null, "���� ���� Ȯ�� �Ǵ� �������� �ʴ� ���� ��¥.", "���", JOptionPane.WARNING_MESSAGE);
                        else 
                        {
                           memo.append("\n���� "+lunYear+"�� "+lunMon+"�� "+lunDay+"��\n");
                           Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                           schs.add(s);
                           sm.setSchs(schs);
                           JOptionPane.showMessageDialog(null, sol[0]+"�� "+sol[1]+"�� "+sol[2]+"�Ͽ� �߰�");
                           dispose();

                        }
                     }
                     
                     
                  } 
               }
               //�Ͽ��� 0
               else if( textday.getText().length()==2 && (int)Math.log10(Integer.parseInt(textday.getText()))==0 && textday.getText().contains("0") ) {

                  if(!isLunar) 
                  {
                	  Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText().substring(1),title.getText(),textplace.getText(),memo.getText());
                	  schs.add(s);
                	  sm.setSchs(schs);
                	  dispose();
                  }
                  else 
                  {
                     lunYear = Integer.parseInt(textyear.getText());
                     lunMon = Integer.parseInt(textmonth.getText());
                     lunDay = Integer.parseInt(textday.getText().substring(1));
                     String sol[];
                     sol = lunarTosolar(lunYear, lunMon, lunDay, isYun);
                     if(sol==null)
                        JOptionPane.showMessageDialog(null, "���� ���� Ȯ�� �Ǵ� �������� �ʴ� ���� ��¥.", "���", JOptionPane.WARNING_MESSAGE);
                     else 
                     {
                        memo.append("\n���� "+lunYear+"�� "+lunMon+"�� "+lunDay+"��\n");
                        Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                        schs.add(s);
                        sm.setSchs(schs);
                        JOptionPane.showMessageDialog(null, sol[0]+"�� "+sol[1]+"�� "+sol[2]+"�Ͽ� �߰�");
                        dispose();

                     }
                  }
                  
               } 
               else {
            	   //�׳� �⺻ 6��6��
                   if(!isLunar) {
                   Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                   schs.add(s);
                   sm.setSchs(schs);
                   dispose();
                   }
                   
                   else 
                   {
                      lunYear = Integer.parseInt(textyear.getText());
                      lunMon = Integer.parseInt(textmonth.getText());
                      lunDay = Integer.parseInt(textday.getText());
                      String sol[];
                      sol = lunarTosolar(lunYear, lunMon, lunDay, isYun);
                      if(sol==null)
                         JOptionPane.showMessageDialog(null, "���� ���� Ȯ�� �Ǵ� �������� �ʴ� ���� ��¥.", "���", JOptionPane.WARNING_MESSAGE);
                      else 
                      {
                         memo.append("\n���� "+lunYear+"�� "+lunMon+"�� "+lunDay+"��\n");
                         Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                         schs.add(s);
                         sm.setSchs(schs);
                         JOptionPane.showMessageDialog(null, sol[0]+"�� "+sol[1]+"�� "+sol[2]+"�Ͽ� �߰�");
                         dispose();

                      }
                   }
               }
              
               
               } //�����ϳ�
                     
            }}catch(NumberFormatException e1) {
             JOptionPane.showMessageDialog(null, "��¥�� ��Ȯ�� �Է����ּ���");

         }
         }
         
      
      });
      this.setVisible(true);
   }
   
   
   public AddSchedule(ScheduleManager sm, int year, int month, int day) 
   {
      this.sm=sm;
      schs=sm.getSchs();
      this.setTitle("���� ���");
      this.setSize(400,450); 
      kit=this.getToolkit();
      Dimension screenSize = kit.getScreenSize();
      this.width=screenSize.width;
      this.height=screenSize.height;
      this.setLocation(width/2-200, height/2-200);

      c = this.getContentPane();
      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("����"));
      c.add(title = new TextField(37));
      c.add(new JLabel("�Ͻ�"));
      c.add(textyear = new TextField(10));
      c.add(new JLabel("��"));
      c.add(textmonth = new TextField(5));
      c.add(new JLabel("��"));
      c.add(textday = new TextField(5));
      c.add(new JLabel("��"));
      
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("���"));
      solar.setSelected(true); //��� ���� ������ư, �⺻ ��
      c.add(lunar = new JRadioButton("����")); //���� ���� ������ư
      group.add(solar);
      group.add(lunar);
      c.add(yun = new JCheckBox("����")); //���´������� ���� üũ�ڽ� ���̱�
      yun.setVisible(false);
      
      JLabel space = new JLabel("                                                                       ");
      c.add(space);
      
      solar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(solar.isSelected()) {
               isLunar = false;
               yun.setVisible(false);
               space.setText("                                                                       ");
            }
         }
         
      });
      
      lunar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(lunar.isSelected()) {
               isLunar = true;
               yun.setVisible(true);
               space.setText("                                                     ");
            }
            else {
               isLunar = false;
               isYun = false;
               yun.setVisible(false);
               space.setText("                                                                       ");
            }
         }
      });
      
      yun.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(yun.isSelected()) 
               isYun = true;
            else
               isYun = false;
         }
      });
      
      
      
      c.add(new JLabel("���"));
      c.add(textplace = new TextField(30));
      c.add(new JLabel("            "));
      
      c.add(new JLabel("�޸�"));
      c.add(memo = new TextArea(8, 43));
      c.add(new JScrollPane(memo));
   
      btn = new JButton("����");
      c.add(btn);
   
      btn.addActionListener(new ActionListener() //�Է°� �����ؾ���
      {

         @Override
         public void actionPerformed(ActionEvent e) 
         {
            // TODO Auto-generated method stub
        	 try {
        	 bc = new BasicCalendar(Integer.parseInt(textyear.getText()));
 			feb = bc.getFeb();
            if(textyear.getText().equals("") && textmonth.getText().equals("") && textday.getText().equals("")) { // ��¥ �� ���� ��������
               if(title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               
               else 
               {
                  Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  dispose();
                  PlusSchedule ps = new PlusSchedule(sm, year, month, day);
               }
            }
            
            else {
               
            if(textyear.getText().equals("") || textmonth.getText().equals("") || textday.getText().equals("")) // ��¥�� �ѹ��� �ޱ� 
               JOptionPane.showMessageDialog(null, "��/��/���� ��� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
            
            else if(title.getText().equals("")) // ���� ������ �ʼ�
               JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE); 
            
            else if(!isNumeric(textyear.getText()) || (Integer.parseInt(textyear.getText())<1900 || Integer.parseInt(textyear.getText())>2037)) // �� ����
               JOptionPane.showMessageDialog(null, "�⵵�� Ȯ���ϼ���.", "���", JOptionPane.WARNING_MESSAGE);
            
            else if(!isNumeric(textmonth.getText()) || Integer.parseInt(textmonth.getText())<1 || Integer.parseInt(textmonth.getText())>12) // �� ����
               JOptionPane.showMessageDialog(null, "1������ 12�������� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
            
            else if (((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 1))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 0) && (!isNumeric(textday.getText())||
					 (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 31))) // ��																					// ����
				JOptionPane.showMessageDialog(null, "1�Ϻ��� 31�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
			else if((Integer.parseInt(textmonth.getText()) == 2) &&(!isNumeric(textday.getText())||(Integer.parseInt(textday.getText()) < 1||Integer.parseInt(textday.getText()) > feb))) {
				JOptionPane.showMessageDialog(null, "1�Ϻ��� "+feb+"�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
			}
			else if ((((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 0))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 1)) && (!isNumeric(textday.getText())
					|| (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 30))) // ��
																												// ����
				JOptionPane.showMessageDialog(null, "1�Ϻ��� 30�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
            else {
               
               if( textmonth.getText().length()==2 && (int)Math.log10(Integer.parseInt(textmonth.getText()))==0 && textmonth.getText().contains("0") ) {
                  
                  if(textday.getText().length()==2 && (int)Math.log10(Integer.parseInt(textday.getText()))==0 && textday.getText().contains("0")) {
                     if(!isLunar) {
                     Schedule s = new Schedule(textyear.getText(),textmonth.getText().substring(1), textday.getText().substring(1),title.getText(),textplace.getText(),memo.getText());
                     schs.add(s);
                     sm.setSchs(schs);
                     dispose();
                     PlusSchedule ps = new PlusSchedule(sm, year, month, day);
                     }

                  }
                  else {
                     if(!isLunar) {
                     Schedule s = new Schedule(textyear.getText(),textmonth.getText().substring(1),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                     schs.add(s);
                     sm.setSchs(schs);
                     dispose();
                     PlusSchedule ps = new PlusSchedule(sm, year, month, day);
                     }
                     
  
                  
                     
                  }
               } 
               
               else if( textday.getText().length()==2 && (int)Math.log10(Integer.parseInt(textday.getText()))==0 && textday.getText().contains("0") ) {
                  if(!isLunar) {
                  Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText().substring(1),title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  dispose();
                  PlusSchedule ps = new PlusSchedule(sm, year, month, day);
                  }
            
 
            
               }
               
               if(!isLunar) {
               Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText(),title.getText(),textplace.getText(),memo.getText());
               schs.add(s);
               sm.setSchs(schs);
               dispose();
               PlusSchedule ps = new PlusSchedule(sm, year, month, day);
               }
               
               else {
                  lunYear = Integer.parseInt(textyear.getText());
                  lunMon = Integer.parseInt(textmonth.getText());
                  lunDay = Integer.parseInt(textday.getText());
                  String sol[];
                  sol = lunarTosolar(lunYear, lunMon, lunDay, isYun);
                  if(sol==null)
                     JOptionPane.showMessageDialog(null, "���� ���� Ȯ�� �Ǵ� �������� �ʴ� ���� ��¥.", "���", JOptionPane.WARNING_MESSAGE);
                  else {
                     memo.append("\n���� "+lunYear+"�� "+lunMon+"�� "+lunDay+"��\n");
                     Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                     schs.add(s);
                     sm.setSchs(schs);
                     JOptionPane.showMessageDialog(null, sol[0]+"�� "+sol[1]+"�� "+sol[2]+"�Ͽ� �߰�");
                     PlusSchedule ps = new PlusSchedule(sm, year, month, day);
                     dispose();

                  }
               }
               
            }
            
         }
            }catch(NumberFormatException e1) {
             JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���");

         }
         }
         
      
      });
   
      this.setVisible(true);
      addWindowListener(new WindowAdapter()
      {
         public void windowClosing(WindowEvent e) 
         {
            PlusSchedule ps = new PlusSchedule(sm, year, month, day);
         }
      });
      
   }
   
   public AddSchedule(ScheduleManager sm, Schedule temp) 
   {
      this.sm=sm;
      schs=sm.getSchs();
      this.setTitle("���� ���");
      this.setSize(400,450); 
      kit=this.getToolkit();
      Dimension screenSize =kit.getScreenSize();
      this.width=screenSize.width;
      this.height=screenSize.height;
      this.setLocation(width/2-200, height/2-200);

      c = this.getContentPane();
      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("����"));
      
      c.add(title = new TextField(temp.getTitle(),37));
      c.add(new JLabel("�Ͻ�"));
      
      c.add(textyear = new TextField(temp.getYear(),10));
      c.add(new JLabel("��"));
      
      c.add(textmonth = new TextField(temp.getMonth(),5));
      c.add(new JLabel("��"));
      
      c.add(textday = new TextField(temp.getDay(),5));
      c.add(new JLabel("��"));
      
      
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("���"));
      solar.setSelected(true); //��� ���� ������ư, �⺻ ��
      c.add(lunar = new JRadioButton("����")); //���� ���� ������ư
      group.add(solar);
      group.add(lunar);
      c.add(yun = new JCheckBox("����")); //���´������� ���� üũ�ڽ� ���̱�
      yun.setVisible(false);
      
      JLabel space = new JLabel("                                                                       ");
      c.add(space);
      
      solar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(solar.isSelected()) {
               isLunar = false;
               yun.setVisible(false);
               space.setText("                                                                       ");
            }
         }
         
      });
      
      lunar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(lunar.isSelected()) {
               isLunar = true;
               yun.setVisible(true);
               space.setText("                                                     ");
            }
            else {
               isLunar = false;
               isYun = false;
               yun.setVisible(false);
               space.setText("                                                                       ");
            }
         }
      });
      
      yun.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(yun.isSelected()) 
               isYun = true;
            else
               isYun = false;
         }
      });

      
      c.add(new JLabel("���"));
      c.add(textplace = new TextField(temp.getPlace(),30));
      c.add(new JLabel("            "));
   
      c.add(new JLabel("�޸�"));
      c.add(memo = new TextArea(temp.getMemo(), 8, 43));
      c.add(new JScrollPane(memo));
      
   
      btn = new JButton("����");
      c.add(btn);
   
      btn.addActionListener(new ActionListener() //�Է°� �����ؾ���
      {

         @Override
         public void actionPerformed(ActionEvent e) 
         {
            // TODO Auto-generated method stub
        	 try {
        	 bc = new BasicCalendar(Integer.parseInt(textyear.getText()));
 			feb = bc.getFeb();
 			
            if(textyear.getText().equals("") && textmonth.getText().equals("") && textday.getText().equals("")) { // ��¥ �� ���� ��������
               if(title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               
               else 
               {
              
                  System.out.println(textplace.getText());
                  Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  dispose();
                  
                  
               }
            }
            
            else {
            if(textyear.getText().equals("") || textmonth.getText().equals("") || textday.getText().equals("")) // ��¥�� �ѹ��� �ޱ� 
               JOptionPane.showMessageDialog(null, "��/��/���� ��� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
            
            else if(title.getText().equals("")) // ���� ������ �ʼ�
               JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE); 
            
            else if(!isNumeric(textyear.getText()) || (Integer.parseInt(textyear.getText())<1900 || Integer.parseInt(textyear.getText())>2037)) // �� ����
               JOptionPane.showMessageDialog(null, "�⵵�� Ȯ���ϼ���.", "���", JOptionPane.WARNING_MESSAGE);
            
            else if(!isNumeric(textmonth.getText()) || Integer.parseInt(textmonth.getText())<1 || Integer.parseInt(textmonth.getText())>12) // �� ����
               JOptionPane.showMessageDialog(null, "1������ 12�������� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);

            else if (((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 1))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 0) && (!isNumeric(textday.getText())||
					 (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 31))) // ��																					// ����
				JOptionPane.showMessageDialog(null, "1�Ϻ��� 31�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
			else if((Integer.parseInt(textmonth.getText()) == 2) &&(!isNumeric(textday.getText())||(Integer.parseInt(textday.getText()) < 1||Integer.parseInt(textday.getText()) > feb))) {
				JOptionPane.showMessageDialog(null, "1�Ϻ��� "+feb+"�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
			}
			else if ((((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 0))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 1)) && (!isNumeric(textday.getText())
					|| (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 30))) // ��
																												// ����
				JOptionPane.showMessageDialog(null, "1�Ϻ��� 30�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
            else {
               
               if( textmonth.getText().length()==2 && (int)Math.log10(Integer.parseInt(textmonth.getText()))==0 && textmonth.getText().contains("0") ) {
                  if( textday.getText().length()==2 && (int)Math.log10(Integer.parseInt(textday.getText()))==0 && textday.getText().contains("0")) {
                     if(!isLunar) {
                     System.out.println(textplace.getText());
                     Schedule s = new Schedule(textyear.getText(),textmonth.getText().substring(1), textday.getText().substring(1),title.getText(),textplace.getText(),memo.getText());
                     schs.add(s);
                     sm.setSchs(schs);
                     dispose();
                     }

                  }
               
                  else {
                     if(!isLunar) {
                     System.out.println(textplace.getText());
                     Schedule s = new Schedule(textyear.getText(),textmonth.getText().substring(1),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                     schs.add(s);
                     sm.setSchs(schs);
                     dispose();
                     }

                  }
                  
               } //05���϶� �α��� && ���ڸ��� && 0����
               
               else if( textday.getText().length()==2 && (int)Math.log10(Integer.parseInt(textday.getText()))==0 && textday.getText().contains("0") ) {
                  if(!isLunar) {
                  System.out.println(textplace.getText());
                  Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText().substring(1),title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  dispose();
                  }        
         
               }
               
            if(!isLunar) {
            System.out.println(textplace.getText());
            Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText(),title.getText(),textplace.getText(),memo.getText());
            schs.add(s);
            sm.setSchs(schs);
            dispose();
            }
            
            else {
               lunYear = Integer.parseInt(textyear.getText());
               lunMon = Integer.parseInt(textmonth.getText());
               lunDay = Integer.parseInt(textday.getText());
               String sol[];
               sol = lunarTosolar(lunYear, lunMon, lunDay, isYun);
               if(sol==null)
                  JOptionPane.showMessageDialog(null, "���� ���� Ȯ�� �Ǵ� �������� �ʴ� ���� ��¥.", "���", JOptionPane.WARNING_MESSAGE);
               else {
                  System.out.println(textplace.getText());
                  memo.append("\n���� "+lunYear+"�� "+lunMon+"�� "+lunDay+"��\n");
                  Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  JOptionPane.showMessageDialog(null, sol[0]+"�� "+sol[1]+"�� "+sol[2]+"�Ͽ� �߰�");
                  dispose();

               }
            }

            }
            
         }
        	 }catch(NumberFormatException e1) {
                 JOptionPane.showMessageDialog(null, "��¥�� ���ڸ� �Է����ּ���");

             }
         }
      
      });
   
      this.setVisible(true);
      
   }
   
   public boolean isNumeric(String s) { //�⵵ ��������
      try { 
           Integer.parseInt(s); 
       } catch(NumberFormatException e) { 
           return false; 
       } catch(NullPointerException e) {
           return false;
       }
       return true;
   }
   
   public String[] lunarTosolar(int ly, int lm, int ld, boolean isYun){ //����ڰ� �Է��� ���� �����,���޿��� -> ������� ��ȯ�ؼ� ��ȯ
      //���µ��
	  try {
      boolean isFind = false;
      int solYear = 0, solMon=0, solDay=0;
      String y, m, d;
      String sol[] = new String[3];

      for(int k=ly-1 ; k<=ly+1 ; k++)
      {
         bc = new BasicCalendar(k);
          Cal= bc.getCal();
         for(int i=1;i<=12;i++)
         {
            for(int j=1;j<Cal[i].length;j++)
            {
               lc = new LunarCalendar(k,i,j);
               if(lc.get(4) == ly && lc.get(5) == lm && lc.get(6) == ld)   //��ȯ�� ��¥�� �Է¹��� ��¥�� ���� ��
               {
                  if((isYun==true && lc.get(7)==1) || (isYun==false && lc.get(7)==0))   //��ȯ�� ��¥�� ���޿��ο� �Է¹��� ���޿��ΰ� ������ 
                  {
                     solYear=k;
                     solMon=i;
                     solDay=j; //������� ��ȯ
                     
                     isFind=true;
                     break;
                  }
                  else
                     isFind=false;
               }
               else
                  isFind=false;
            }
            if(isFind)
               break;
         }
         if(isFind)
            break;
      }
      if(isFind)
      {
         y = Integer.toString(solYear);sol[0]=y;
         m = Integer.toString(solMon);sol[1] = m;
         d = Integer.toString(solDay);sol[2] =d;
         return sol;
      }
      else
      {
         return null;
      }
	  }
		catch(NumberFormatException e1) 
  	{
 		
 		 	return null;
 	 	}
  	catch(ArrayIndexOutOfBoundsException e2) 
  	{
  		
  		return null;
  	}
   
   }
   
   
}
