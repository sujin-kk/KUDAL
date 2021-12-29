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
   JRadioButton solar, lunar; //양력 음력 라디오 버튼
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
      this.setTitle("일정 등록");
      this.setSize(400,470); 
      kit=this.getToolkit();
      Dimension screenSize =kit.getScreenSize();
      this.width=screenSize.width;
      this.height=screenSize.height;
      this.setLocation(width/2-200, height/2-200);
      c = this.getContentPane();

      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("제목"));
      c.add(title = new TextField(37));
      c.add(new JLabel("일시"));
      c.add(textyear = new TextField(10));
      c.add(new JLabel("년"));
      c.add(textmonth = new TextField(5));
      c.add(new JLabel("월"));
      c.add(textday = new TextField(5));
      c.add(new JLabel("일"));
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("양력"));
      solar.setSelected(true); //양력 여부 라디오버튼, 기본 값
      c.add(lunar = new JRadioButton("음력")); //음력 여부 라디오버튼
      group.add(solar);
      group.add(lunar);
      c.add(yun = new JCheckBox("윤달")); //음력누를때면 윤달 체크박스 보이기
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

      c.add(new JLabel("장소"));
      c.add(textplace = new TextField(30));

      c.add(new JLabel("            "));
      c.add(new JLabel("메모"));
      c.add(memo = new TextArea(8, 43));
      c.add(new JScrollPane(memo));
   
      btn = new JButton("저장");
      c.add(btn);
   
      btn.addActionListener(new ActionListener() //입력값 저장해야함
      {

         @Override
         public void actionPerformed(ActionEvent e) 
         {
            // TODO Auto-generated method stub
        	 try {
        	
            if(textyear.getText().equals("") && textmonth.getText().equals("") && textday.getText().equals("")) { // 날짜 다 비우면 비정기적
               if(title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               else 
               {
                  Schedule s = new Schedule(textyear.getText(),textmonth.getText(),textday.getText(),title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  dispose();   
               }
            }
                  
            
            else // 날짜 한번에 받으면 정기적
            { //날짜입력!!
            	bc = new BasicCalendar(Integer.parseInt(textyear.getText()));
    			feb = bc.getFeb();
               if(textyear.getText().equals("") || textmonth.getText().equals("") || textday.getText().equals("")) // 날짜는 한번에 받기 
                  JOptionPane.showMessageDialog(null, "년/월/일을 모두 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               
               else if(title.getText().equals("")) // 일정 제목은 필수
                  JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE); 
               
               else if(!isNumeric(textyear.getText()) || Integer.parseInt(textyear.getText())<1900 || Integer.parseInt(textyear.getText())>2037) // 년 예외
                  JOptionPane.showMessageDialog(null, "년도를 확인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
               
               else if(!isNumeric(textmonth.getText()) || Integer.parseInt(textmonth.getText())<1 || Integer.parseInt(textmonth.getText())>12) // 월 예외
                  JOptionPane.showMessageDialog(null, "1월부터 12월까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               
               else if (((Integer.parseInt(textmonth.getText()) < 8
						&& (Integer.parseInt(textmonth.getText()) % 2 == 1))
						|| (Integer.parseInt(textmonth.getText()) >= 8)
								&& Integer.parseInt(textmonth.getText()) % 2 == 0) && (!isNumeric(textday.getText())||
						 (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 31))) // 일																					// 예외
					JOptionPane.showMessageDialog(null, "1일부터 31일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				else if((Integer.parseInt(textmonth.getText()) == 2) &&(!isNumeric(textday.getText())||(Integer.parseInt(textday.getText()) < 1||Integer.parseInt(textday.getText()) > feb))) {
					JOptionPane.showMessageDialog(null, "1일부터 "+feb+"일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else if ((((Integer.parseInt(textmonth.getText()) < 8
						&& (Integer.parseInt(textmonth.getText()) % 2 == 0))
						|| (Integer.parseInt(textmonth.getText()) >= 8)
								&& Integer.parseInt(textmonth.getText()) % 2 == 1)) && (!isNumeric(textday.getText())
						|| (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 30))) // 일
																													// 예외
					JOptionPane.showMessageDialog(null, "1일부터 30일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               else { // 정상적 등록  양력vs음력
                  

               if( textmonth.getText().length()==2 && (int)Math.log10(Integer.parseInt(textmonth.getText()))==0 && textmonth.getText().contains("0") ) {
                  
                  if(textday.getText().length()==2 && (int)Math.log10(Integer.parseInt(textday.getText()))==0 && textday.getText().contains("0"))
                  {
                     //월일둘다 0들어감
                     if(!isLunar) //양력
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
                           JOptionPane.showMessageDialog(null, "윤달 여부 확인 또는 존재하지 않는 음력 날짜.", "경고", JOptionPane.WARNING_MESSAGE);
                        else 
                        {
                           memo.append("\n음력 "+lunYear+"년 "+lunMon+"월 "+lunDay+"일\n");
                           Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                           schs.add(s);
                           sm.setSchs(schs);
                           JOptionPane.showMessageDialog(null, sol[0]+"년 "+sol[1]+"월 "+sol[2]+"일에 추가");
                           dispose();

                        }
                     }
                  } 
                  
                  else 
                  {
                     //월에만 0
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
                           JOptionPane.showMessageDialog(null, "윤달 여부 확인 또는 존재하지 않는 음력 날짜.", "경고", JOptionPane.WARNING_MESSAGE);
                        else 
                        {
                           memo.append("\n음력 "+lunYear+"년 "+lunMon+"월 "+lunDay+"일\n");
                           Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                           schs.add(s);
                           sm.setSchs(schs);
                           JOptionPane.showMessageDialog(null, sol[0]+"년 "+sol[1]+"월 "+sol[2]+"일에 추가");
                           dispose();

                        }
                     }
                     
                     
                  } 
               }
               //일에만 0
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
                        JOptionPane.showMessageDialog(null, "윤달 여부 확인 또는 존재하지 않는 음력 날짜.", "경고", JOptionPane.WARNING_MESSAGE);
                     else 
                     {
                        memo.append("\n음력 "+lunYear+"년 "+lunMon+"월 "+lunDay+"일\n");
                        Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                        schs.add(s);
                        sm.setSchs(schs);
                        JOptionPane.showMessageDialog(null, sol[0]+"년 "+sol[1]+"월 "+sol[2]+"일에 추가");
                        dispose();

                     }
                  }
                  
               } 
               else {
            	   //그냥 기본 6월6일
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
                         JOptionPane.showMessageDialog(null, "윤달 여부 확인 또는 존재하지 않는 음력 날짜.", "경고", JOptionPane.WARNING_MESSAGE);
                      else 
                      {
                         memo.append("\n음력 "+lunYear+"년 "+lunMon+"월 "+lunDay+"일\n");
                         Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                         schs.add(s);
                         sm.setSchs(schs);
                         JOptionPane.showMessageDialog(null, sol[0]+"년 "+sol[1]+"월 "+sol[2]+"일에 추가");
                         dispose();

                      }
                   }
               }
              
               
               } //정상등록끝
                     
            }}catch(NumberFormatException e1) {
             JOptionPane.showMessageDialog(null, "날짜를 정확히 입력해주세요");

         }
         }
         
      
      });
      this.setVisible(true);
   }
   
   
   public AddSchedule(ScheduleManager sm, int year, int month, int day) 
   {
      this.sm=sm;
      schs=sm.getSchs();
      this.setTitle("일정 등록");
      this.setSize(400,450); 
      kit=this.getToolkit();
      Dimension screenSize = kit.getScreenSize();
      this.width=screenSize.width;
      this.height=screenSize.height;
      this.setLocation(width/2-200, height/2-200);

      c = this.getContentPane();
      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("제목"));
      c.add(title = new TextField(37));
      c.add(new JLabel("일시"));
      c.add(textyear = new TextField(10));
      c.add(new JLabel("년"));
      c.add(textmonth = new TextField(5));
      c.add(new JLabel("월"));
      c.add(textday = new TextField(5));
      c.add(new JLabel("일"));
      
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("양력"));
      solar.setSelected(true); //양력 여부 라디오버튼, 기본 값
      c.add(lunar = new JRadioButton("음력")); //음력 여부 라디오버튼
      group.add(solar);
      group.add(lunar);
      c.add(yun = new JCheckBox("윤달")); //음력누를때면 윤달 체크박스 보이기
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
      
      
      
      c.add(new JLabel("장소"));
      c.add(textplace = new TextField(30));
      c.add(new JLabel("            "));
      
      c.add(new JLabel("메모"));
      c.add(memo = new TextArea(8, 43));
      c.add(new JScrollPane(memo));
   
      btn = new JButton("저장");
      c.add(btn);
   
      btn.addActionListener(new ActionListener() //입력값 저장해야함
      {

         @Override
         public void actionPerformed(ActionEvent e) 
         {
            // TODO Auto-generated method stub
        	 try {
        	 bc = new BasicCalendar(Integer.parseInt(textyear.getText()));
 			feb = bc.getFeb();
            if(textyear.getText().equals("") && textmonth.getText().equals("") && textday.getText().equals("")) { // 날짜 다 비우면 비정기적
               if(title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               
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
               
            if(textyear.getText().equals("") || textmonth.getText().equals("") || textday.getText().equals("")) // 날짜는 한번에 받기 
               JOptionPane.showMessageDialog(null, "년/월/일을 모두 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
            
            else if(title.getText().equals("")) // 일정 제목은 필수
               JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE); 
            
            else if(!isNumeric(textyear.getText()) || (Integer.parseInt(textyear.getText())<1900 || Integer.parseInt(textyear.getText())>2037)) // 년 예외
               JOptionPane.showMessageDialog(null, "년도를 확인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            
            else if(!isNumeric(textmonth.getText()) || Integer.parseInt(textmonth.getText())<1 || Integer.parseInt(textmonth.getText())>12) // 월 예외
               JOptionPane.showMessageDialog(null, "1월부터 12월까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
            
            else if (((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 1))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 0) && (!isNumeric(textday.getText())||
					 (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 31))) // 일																					// 예외
				JOptionPane.showMessageDialog(null, "1일부터 31일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
			else if((Integer.parseInt(textmonth.getText()) == 2) &&(!isNumeric(textday.getText())||(Integer.parseInt(textday.getText()) < 1||Integer.parseInt(textday.getText()) > feb))) {
				JOptionPane.showMessageDialog(null, "1일부터 "+feb+"일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
			}
			else if ((((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 0))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 1)) && (!isNumeric(textday.getText())
					|| (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 30))) // 일
																												// 예외
				JOptionPane.showMessageDialog(null, "1일부터 30일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
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
                     JOptionPane.showMessageDialog(null, "윤달 여부 확인 또는 존재하지 않는 음력 날짜.", "경고", JOptionPane.WARNING_MESSAGE);
                  else {
                     memo.append("\n음력 "+lunYear+"년 "+lunMon+"월 "+lunDay+"일\n");
                     Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                     schs.add(s);
                     sm.setSchs(schs);
                     JOptionPane.showMessageDialog(null, sol[0]+"년 "+sol[1]+"월 "+sol[2]+"일에 추가");
                     PlusSchedule ps = new PlusSchedule(sm, year, month, day);
                     dispose();

                  }
               }
               
            }
            
         }
            }catch(NumberFormatException e1) {
             JOptionPane.showMessageDialog(null, "숫자만 입력해주세요");

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
      this.setTitle("일정 등록");
      this.setSize(400,450); 
      kit=this.getToolkit();
      Dimension screenSize =kit.getScreenSize();
      this.width=screenSize.width;
      this.height=screenSize.height;
      this.setLocation(width/2-200, height/2-200);

      c = this.getContentPane();
      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("제목"));
      
      c.add(title = new TextField(temp.getTitle(),37));
      c.add(new JLabel("일시"));
      
      c.add(textyear = new TextField(temp.getYear(),10));
      c.add(new JLabel("년"));
      
      c.add(textmonth = new TextField(temp.getMonth(),5));
      c.add(new JLabel("월"));
      
      c.add(textday = new TextField(temp.getDay(),5));
      c.add(new JLabel("일"));
      
      
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("양력"));
      solar.setSelected(true); //양력 여부 라디오버튼, 기본 값
      c.add(lunar = new JRadioButton("음력")); //음력 여부 라디오버튼
      group.add(solar);
      group.add(lunar);
      c.add(yun = new JCheckBox("윤달")); //음력누를때면 윤달 체크박스 보이기
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

      
      c.add(new JLabel("장소"));
      c.add(textplace = new TextField(temp.getPlace(),30));
      c.add(new JLabel("            "));
   
      c.add(new JLabel("메모"));
      c.add(memo = new TextArea(temp.getMemo(), 8, 43));
      c.add(new JScrollPane(memo));
      
   
      btn = new JButton("저장");
      c.add(btn);
   
      btn.addActionListener(new ActionListener() //입력값 저장해야함
      {

         @Override
         public void actionPerformed(ActionEvent e) 
         {
            // TODO Auto-generated method stub
        	 try {
        	 bc = new BasicCalendar(Integer.parseInt(textyear.getText()));
 			feb = bc.getFeb();
 			
            if(textyear.getText().equals("") && textmonth.getText().equals("") && textday.getText().equals("")) { // 날짜 다 비우면 비정기적
               if(title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               
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
            if(textyear.getText().equals("") || textmonth.getText().equals("") || textday.getText().equals("")) // 날짜는 한번에 받기 
               JOptionPane.showMessageDialog(null, "년/월/일을 모두 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
            
            else if(title.getText().equals("")) // 일정 제목은 필수
               JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE); 
            
            else if(!isNumeric(textyear.getText()) || (Integer.parseInt(textyear.getText())<1900 || Integer.parseInt(textyear.getText())>2037)) // 년 예외
               JOptionPane.showMessageDialog(null, "년도를 확인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            
            else if(!isNumeric(textmonth.getText()) || Integer.parseInt(textmonth.getText())<1 || Integer.parseInt(textmonth.getText())>12) // 월 예외
               JOptionPane.showMessageDialog(null, "1월부터 12월까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);

            else if (((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 1))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 0) && (!isNumeric(textday.getText())||
					 (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 31))) // 일																					// 예외
				JOptionPane.showMessageDialog(null, "1일부터 31일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
			else if((Integer.parseInt(textmonth.getText()) == 2) &&(!isNumeric(textday.getText())||(Integer.parseInt(textday.getText()) < 1||Integer.parseInt(textday.getText()) > feb))) {
				JOptionPane.showMessageDialog(null, "1일부터 "+feb+"일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
			}
			else if ((((Integer.parseInt(textmonth.getText()) < 8
					&& (Integer.parseInt(textmonth.getText()) % 2 == 0))
					|| (Integer.parseInt(textmonth.getText()) >= 8)
							&& Integer.parseInt(textmonth.getText()) % 2 == 1)) && (!isNumeric(textday.getText())
					|| (Integer.parseInt(textday.getText()) < 1 || Integer.parseInt(textday.getText()) > 30))) // 일
																												// 예외
				JOptionPane.showMessageDialog(null, "1일부터 30일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
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
                  
               } //05월일때 두글자 && 한자리수 && 0포함
               
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
                  JOptionPane.showMessageDialog(null, "윤달 여부 확인 또는 존재하지 않는 음력 날짜.", "경고", JOptionPane.WARNING_MESSAGE);
               else {
                  System.out.println(textplace.getText());
                  memo.append("\n음력 "+lunYear+"년 "+lunMon+"월 "+lunDay+"일\n");
                  Schedule s = new Schedule(sol[0],sol[1],sol[2],title.getText(),textplace.getText(),memo.getText());
                  schs.add(s);
                  sm.setSchs(schs);
                  JOptionPane.showMessageDialog(null, sol[0]+"년 "+sol[1]+"월 "+sol[2]+"일에 추가");
                  dispose();

               }
            }

            }
            
         }
        	 }catch(NumberFormatException e1) {
                 JOptionPane.showMessageDialog(null, "날짜는 숫자만 입력해주세요");

             }
         }
      
      });
   
      this.setVisible(true);
      
   }
   
   public boolean isNumeric(String s) { //년도 숫자인지
      try { 
           Integer.parseInt(s); 
       } catch(NumberFormatException e) { 
           return false; 
       } catch(NullPointerException e) {
           return false;
       }
       return true;
   }
   
   public String[] lunarTosolar(int ly, int lm, int ld, boolean isYun){ //사용자가 입력한 음력 년원일,윤달여부 -> 양력으로 변환해서 반환
      //음력등록
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
               if(lc.get(4) == ly && lc.get(5) == lm && lc.get(6) == ld)   //변환된 날짜가 입력받은 날짜와 같을 때
               {
                  if((isYun==true && lc.get(7)==1) || (isYun==false && lc.get(7)==0))   //변환된 날짜가 윤달여부와 입력받은 윤달여부가 같을때 
                  {
                     solYear=k;
                     solMon=i;
                     solDay=j; //양력으로 변환
                     
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
