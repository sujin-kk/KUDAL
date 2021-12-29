package Calendar;

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
import java.util.Locale;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class AddRegular extends JFrame {
   int width;
   int height;
   Toolkit kit;
   Container c;
   TextField title, textyear1, textmonth1, textday1, textyear2, textmonth2, textday2, textplace;
   // 1 시작날짜, 2 종료날짜
   TextArea memo;
   JButton btn;
   ScheduleManager sm;
   Vector<Schedule> schs;
   int feb,feb2;
   BasicCalendar bc;
   ButtonGroup group;
   JRadioButton btn1, btn2, btn3;
   String ty1, ty2, tm1, tm2, td1, td2;
   
   ButtonGroup group2;
   JRadioButton solar, lunar; //양력 음력 라디오 버튼
   JCheckBox yun1, yun2;
   boolean isLunar = false, isFind = false, isYun1 = false, isYun2= false;
   int lunYear1, lunYear2, lunMon1, lunMon2, lunDay1, lunDay2;
   LunarCalendar lc;
   private int Cal[][];

   public AddRegular(ScheduleManager sm) {
      this.sm = sm;
      schs = sm.getSchs();
      this.setTitle("일정 등록");
      this.setSize(400, 500);
      kit = this.getToolkit();
      Dimension screenSize = kit.getScreenSize();
      this.width = screenSize.width;
      this.height = screenSize.height;
      this.setLocation(width / 2 - 200, height / 2 - 200);
      
      new JScrollPane(c);
      c = this.getContentPane();
      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("제목"));
      c.add(title = new TextField(37));
      c.add(new JLabel("시작"));
      c.add(textyear1 = new TextField(5));
      c.add(new JLabel("년"));
      c.add(textmonth1 = new TextField(4));
      c.add(new JLabel("월"));
      c.add(textday1 = new TextField(4));
      c.add(new JLabel("일"));
      c.add(yun1 = new JCheckBox("윤달")); //음력누를때면 윤달 체크박스 보이기
      yun1.setVisible(false);
      
      JLabel space1 = new JLabel("                   ");
      c.add(space1);

      c.add(new JLabel("종료"));
      c.add(textyear2 = new TextField(5));
      c.add(new JLabel("년"));
      c.add(textmonth2 = new TextField(4));
      c.add(new JLabel("월"));
      c.add(textday2 = new TextField(4));
      c.add(new JLabel("일"));
      c.add(yun2 = new JCheckBox("윤달"));
      yun2.setVisible(false);
      JLabel space2 = new JLabel("                ");
      c.add(space2); 
      
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("양력"));
      solar.setSelected(true); //양력 여부 라디오버튼, 기본 값
      c.add(lunar = new JRadioButton("음력")); //음력 여부 라디오버튼
      group.add(solar);
      group.add(lunar);
      
   
      
      solar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(solar.isSelected()) {
               space1.setText("                  ");
               space2.setText("                  ");
               isLunar = false;
               yun1.setVisible(false);
               yun2.setVisible(false);
            }
         }
         
      });
      
      lunar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(lunar.isSelected()) {
               space1.setText("");
               space2.setText("");
               isLunar = true;
               yun1.setVisible(true);
               yun2.setVisible(true);
            }
            else {
               isLunar = false;
               isYun1 = false; isYun2= false;
               yun1.setVisible(false);
               yun2.setVisible(false);
            }
         }
      });
      
      yun1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(yun1.isSelected()) 
               isYun1 = true;
            else
               isYun1 = false;
         }
      });
      
      yun2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(yun2.isSelected()) 
               isYun2 = true;
            else
               isYun2 = false;
         }
      });
      
      c.add(new JLabel("                                                               "));
      c.add(new JLabel("반복 주기"));
      c.add(new JLabel("     "));
      btn1 = new JRadioButton("매년");
      btn2 = new JRadioButton("매월");
      btn3 = new JRadioButton("매주");
      group = new ButtonGroup();

      group.add(btn1);
      group.add(btn2);
      group.add(btn3);
      c.add(btn1);
      c.add(new JLabel("       "));
      c.add(btn2);
      c.add(new JLabel("       "));
      c.add(btn3);
      c.add(new JLabel("       "));

      c.add(new JLabel("장소"));
      c.add(textplace = new TextField(37));
      c.add(new JLabel("메모"));
      c.add(memo = new TextArea(6, 37));
      c.add(new JScrollPane(memo));

      btn = new JButton("저장");
      c.add(btn);

      btn.addActionListener(new ActionListener() // 입력값 저장해야함
      {

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
        	 try {
        		
     			
            if (textyear1.getText().equals("") || textmonth1.getText().equals("") || textday1.getText().equals("")
                  || textyear2.getText().equals("") || textmonth2.getText().equals("")
                  || textday2.getText().equals("")) { // 날짜 다 비우면 안됨
               if (title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               else
                  JOptionPane.showMessageDialog(null, "년/월/일을 모두 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
            }

            else // 날짜 적은경우 //시작 종료날짜 숫자 입력 오류창은 했고 그 종료날짜가 시작날짜보다 큰지만 검사하면 될듯?? 완료!
            {
            	ty1 = textyear1.getText();
     			bc = new BasicCalendar(Integer.parseInt(ty1));
     			feb = bc.getFeb();
     			ty2 = textyear2.getText();
     			bc = new BasicCalendar(Integer.parseInt(ty2));
     			feb2 = bc.getFeb();
               if (title.getText().equals("")) // 일정 제목은 필수
                  JOptionPane.showMessageDialog(null, "일정 제목을 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textyear1.getText()) || Integer.parseInt(textyear1.getText()) < 1900
                     || Integer.parseInt(textyear1.getText()) > 2037) // 년 예외
                  JOptionPane.showMessageDialog(null, "년도를 확인하세요.", "경고", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textmonth1.getText()) || Integer.parseInt(textmonth1.getText()) < 1
                       || Integer.parseInt(textmonth1.getText()) > 12) // 월 예외
                    JOptionPane.showMessageDialog(null, "1월부터 12월까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
               else if (((Integer.parseInt(textmonth1.getText()) < 8
						&& (Integer.parseInt(textmonth1.getText()) % 2 == 1))
						|| (Integer.parseInt(textmonth1.getText()) >= 8)
								&& Integer.parseInt(textmonth1.getText()) % 2 == 0) && (!isNumeric(textday1.getText())||
						 (Integer.parseInt(textday1.getText()) < 1 || Integer.parseInt(textday1.getText()) > 31))) // 일																					// 예외
					JOptionPane.showMessageDialog(null, "1일부터 31일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				else if((Integer.parseInt(textmonth1.getText()) == 2) &&(!isNumeric(textday1.getText())||(Integer.parseInt(textday1.getText()) < 1||Integer.parseInt(textday1.getText()) > feb))) {
					JOptionPane.showMessageDialog(null, "1일부터 "+feb+"일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else if ((((Integer.parseInt(textmonth1.getText()) < 8
						&& (Integer.parseInt(textmonth1.getText()) % 2 == 0))
						|| (Integer.parseInt(textmonth1.getText()) >= 8)
								&& Integer.parseInt(textmonth1.getText()) % 2 == 1)) && (!isNumeric(textday1.getText())
						|| (Integer.parseInt(textday1.getText()) < 1 || Integer.parseInt(textday1.getText()) > 30))) // 일
																													// 예외
					JOptionPane.showMessageDialog(null, "1일부터 30일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				

               else if (!isNumeric(textday1.getText()) || (((Integer.parseInt(textmonth1.getText()) < 8
                     && (Integer.parseInt(textmonth1.getText()) % 2 == 0))
                     || (Integer.parseInt(textmonth1.getText()) >= 8)
                           && Integer.parseInt(textmonth1.getText()) % 2 == 1))
                     && (Integer.parseInt(textday1.getText()) < 1 || Integer.parseInt(textday1.getText()) > 30)) // 일
                                                                                          // 예외
                  JOptionPane.showMessageDialog(null, "1일부터 30일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textyear2.getText()) || Integer.parseInt(textyear2.getText()) < 1900
                     || Integer.parseInt(textyear2.getText()) > 2037) // 년 예외
                  JOptionPane.showMessageDialog(null, "년도를 확인하세요.", "경고", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textmonth2.getText()) || Integer.parseInt(textmonth2.getText()) < 1
                     || Integer.parseInt(textmonth2.getText()) > 12) // 월 예외
                  JOptionPane.showMessageDialog(null, "1월부터 12월까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);

               else if (((Integer.parseInt(textmonth2.getText()) < 8
						&& (Integer.parseInt(textmonth2.getText()) % 2 == 1))
						|| (Integer.parseInt(textmonth2.getText()) >= 8)
								&& Integer.parseInt(textmonth2.getText()) % 2 == 0) && (!isNumeric(textday2.getText())||
						 (Integer.parseInt(textday2.getText()) < 1 || Integer.parseInt(textday2.getText()) > 31))) { // 일
																													// 예외
					JOptionPane.showMessageDialog(null, "1일부터 31일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				
				
				else if((Integer.parseInt(textmonth2.getText()) == 2) &&(!isNumeric(textday2.getText())||(Integer.parseInt(textday2.getText()) < 1||Integer.parseInt(textday2.getText()) > feb2))) {
					JOptionPane.showMessageDialog(null, "1일부터 "+feb2+"일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else if ((((Integer.parseInt(textmonth2.getText()) < 8
						&& (Integer.parseInt(textmonth2.getText()) % 2 == 0))
						|| (Integer.parseInt(textmonth2.getText()) >= 8)
								&& Integer.parseInt(textmonth2.getText()) % 2 == 1)) && (!isNumeric(textday2.getText())
						|| (Integer.parseInt(textday2.getText()) < 1 || Integer.parseInt(textday2.getText()) > 30))) // 일
																													// 예외
					JOptionPane.showMessageDialog(null, "1일부터 30일까지만 입력하십시오.", "경고", JOptionPane.WARNING_MESSAGE);

               else if (Integer.parseInt(textyear1.getText()) > Integer.parseInt(textyear2.getText()) //시작날짜 종료날짜
                     || Integer.parseInt(textyear1.getText()) == Integer.parseInt(textyear2.getText())
                           && Integer.parseInt(textmonth1.getText()) > Integer.parseInt(textmonth2.getText())
                     || Integer.parseInt(textyear1.getText()) == Integer.parseInt(textyear2.getText())
                           && Integer.parseInt(textmonth1.getText()) == Integer.parseInt(textmonth2.getText())
                           && Integer.parseInt(textday1.getText()) > Integer.parseInt(textday2.getText()))
                  JOptionPane.showMessageDialog(null, "시작 날짜보다 종료 날짜가 나중이어야 합니다.", "경고",
                        JOptionPane.WARNING_MESSAGE);

               else { // 정상적 등록
                  if(!isLunar) { //양력

                  // 종료날짜 월,일에 0포함되어 있는지 검사
                  if (textmonth2.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textmonth2.getText())) == 0
                        && textmonth2.getText().contains("0")) {

                     if (textday2.getText().length() == 2
                           && (int) Math.log10(Integer.parseInt(textday2.getText())) == 0
                           && textday2.getText().contains("0")) {
                        tm2 = textmonth2.getText().substring(1);
                        td2 = textday2.getText().substring(1);

                     } // 두개다

                     else {
                        tm2 = textmonth2.getText().substring(1);
                        td2 = textday2.getText();
                     } // 월만
                  }

                  else if (textday2.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textday2.getText())) == 0
                        && textday2.getText().contains("0")) {
                     tm2 = textmonth2.getText();
                     td2 = textday2.getText().substring(1);
                  } // 일만

                  else {
                     tm2 = textmonth2.getText();
                     td2 = textday2.getText();
                  }

                  // 시작날짜 월,일 0포함되어 있는지 검사하고 week호출해서 반복
                  if (textmonth1.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textmonth1.getText())) == 0
                        && textmonth1.getText().contains("0")) {

                     if (textday1.getText().length() == 2
                           && (int) Math.log10(Integer.parseInt(textday1.getText())) == 0
                           && textday1.getText().contains("0")) {
                        tm1 = textmonth1.getText().substring(1);
                        td1 = textday1.getText().substring(1);

                     } // 두개다

                     else {
                        tm1 = textmonth1.getText().substring(1);
                        td1 = textday1.getText();
                     } // 월만
                  }

                  else if (textday1.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textday1.getText())) == 0
                        && textday1.getText().contains("0")) {
                     tm1 = textmonth1.getText();
                     td1 = textday1.getText().substring(1);
                  } // 일만

                  else {
                     tm1 = textmonth1.getText();
                     td1 = textday1.getText();

                  }
                  week();// 매주 반복 함수
                  }
                  
                  else { //음력
               
                     lunYear1 = Integer.parseInt(textyear1.getText()); //사용자가 입력한 음력날짜
                     lunMon1 = Integer.parseInt(textmonth1.getText());
                     lunDay1 = Integer.parseInt(textday1.getText());
                     
                     lunYear2 = Integer.parseInt(textyear2.getText());
                     lunMon2 = Integer.parseInt(textmonth2.getText());
                     lunDay2 = Integer.parseInt(textday2.getText());
                     
                     String sol1[],sol2[]; //음력 > 양력으로 바꿔서 달력에 양력상으로 저장
                     sol1 = lunarTosolar(lunYear1, lunMon1, lunDay1, isYun1);
                     sol2 = lunarTosolar(lunYear2, lunMon2, lunDay2, isYun2);
                     if(sol1==null || sol2==null) {
                        JOptionPane.showMessageDialog(null, "윤달 여부 확인 또는 존재하지 않는 음력 날짜.", "경고", JOptionPane.WARNING_MESSAGE);
                     }
                     else {
                        ty1 = sol1[0];
                        tm1 = sol1[1];
                        td1 = sol1[2];
                        ty2 = sol2[0];
                        tm2 = sol2[1];
                        td2 = sol2[2];
                        week();
                     }
                     
                  }
               }//정상등록
           

               // dispose();
            }
        	 }catch(NumberFormatException e1) {
                 JOptionPane.showMessageDialog(null, "날짜를 정확히 입력해주세요");

             }

         }

      });

      this.setVisible(true);

   }

   public void week() {//반복함수

		// 이걸로 2월받아오면됨
		bc = new BasicCalendar(Integer.parseInt(ty1));
		feb = bc.getFeb();
		int re;

		if (btn1.isSelected()) {// 매년
			if(isLunar)
                JOptionPane.showMessageDialog(null, ty1+"년 "+tm1+"월 "+td1+"일부터 추가");
			
			re = setRE(textyear1.getText(), textmonth1.getText(), textday1.getText(), textyear2.getText(),
					textmonth2.getText(), textday2.getText(), 1);

			for (int i = 0; i <= re; i++) {
				Schedule s = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
				schs.add(s);
				sm.setSchs(schs);
				ty1 = Integer.toString(Integer.parseInt(ty1) + 1);
				
			}
			dispose();
		} else if (btn2.isSelected()) {// 매월
			if(isLunar)
                JOptionPane.showMessageDialog(null, ty1+"년 "+tm1+"월 "+td1+"일부터 추가");
			re = setRE(textyear1.getText(), textmonth1.getText(), textday1.getText(), textyear2.getText(),
					textmonth2.getText(), textday2.getText(), 2);

			for (int i = 0; i <= re; i++) {

				Schedule s = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
				schs.add(s);
				sm.setSchs(schs);

				tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
				if (Integer.parseInt(tm1) > 12)// 년도 바뀜
				{
					tm1 = "1";
					ty1 = Integer.toString(Integer.parseInt(ty1) + 1);
					feb = bc.getFeb();
				}

				if ((Integer.parseInt(tm1) == 4 || Integer.parseInt(tm1) == 6 || Integer.parseInt(tm1) == 9
						|| Integer.parseInt(tm1) == 11) && Integer.parseInt(td1) == 31) {//30일없는달
					tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
					i++;
				}
				if (Integer.parseInt(td1) > feb && Integer.parseInt(tm1) == 2) {//2월
					tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
					i++;
				}

			}
			dispose();
		} else if (btn3.isSelected()) {// 매주
			if(isLunar)
                JOptionPane.showMessageDialog(null, ty1+"년 "+tm1+"월 "+td1+"일부터 추가");
			Schedule ss = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
			schs.add(ss);
			sm.setSchs(schs);
			while (true) {
				td1 = Integer.toString(Integer.parseInt(td1) + 7);

				if (Integer.parseInt(tm1) == 1 || Integer.parseInt(tm1) == 3 || Integer.parseInt(tm1) == 5
						|| Integer.parseInt(tm1) == 7 || Integer.parseInt(tm1) == 8 || Integer.parseInt(tm1) == 10)// 마지막날
																													// 31일
																													// 일때
																													// (12월
																													// 제외)
				{
					if (Integer.parseInt(td1) > 31) {
						tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
						td1 = Integer.toString(Integer.parseInt(td1) - 31);
					}
				} else if (Integer.parseInt(tm1) == 12) {// 12월에서 넘어가면 년도 바뀜
					if (Integer.parseInt(td1) > 31) {
						td1 = Integer.toString(Integer.parseInt(td1) - 31);
						ty1 = Integer.toString(Integer.parseInt(ty1) + 1);
						tm1 = Integer.toString(1);
						bc = new BasicCalendar(Integer.parseInt(ty1));
						feb = bc.getFeb();// 년도 바꼈으니까 다시

					}
				} else if (Integer.parseInt(tm1) == 4 || Integer.parseInt(tm1) == 6 || Integer.parseInt(tm1) == 9
						|| Integer.parseInt(tm1) == 11)// 마지막날 30일 일때
				{
					if (Integer.parseInt(td1) > 30) {
						tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
						td1 = Integer.toString(Integer.parseInt(td1) - 30);
					}
				} else// 2월
				{
					if (Integer.parseInt(td1) > feb) {
						tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
						td1 = Integer.toString(Integer.parseInt(td1) - feb);
					}
				}

				if (Integer.parseInt(ty1) > Integer.parseInt(ty2)) {
					break;
				} else if (Integer.parseInt(ty1) == Integer.parseInt(ty2)) {
					if (Integer.parseInt(tm1) > Integer.parseInt(tm2)) {
						break;
					} else if (Integer.parseInt(tm1) == Integer.parseInt(tm2)) {
						if (Integer.parseInt(td1) > Integer.parseInt(td2)) {
							break;
						} else {
							Schedule s = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(),
									memo.getText());
							schs.add(s);
							sm.setSchs(schs);

						}
					} else if (Integer.parseInt(tm1) < Integer.parseInt(tm2)) {
						Schedule s = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
						schs.add(s);
						sm.setSchs(schs);

					}
				} else if (Integer.parseInt(ty1) < Integer.parseInt(ty2)) {
					Schedule s = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
					schs.add(s);
					sm.setSchs(schs);

				}
			}

			dispose();
		}
		else {
			JOptionPane.showMessageDialog(null, "반복 주기를 선택하십시오.", "경고", JOptionPane.WARNING_MESSAGE);
		}
	}

   
   
   public boolean isNumeric(String s) { // 년도 숫자인지
      try {
         Integer.parseInt(s);
      } catch (NumberFormatException e) {
         return false;
      } catch (NullPointerException e) {
         return false;
      }
      return true;
   }

   public int setRE(String textyear1, String textmonth1, String textday1, String textyear2, String textmonth2,
         String textday2, int rbtn) {

      int re = 0;
      int myear = Integer.parseInt(textyear2) - Integer.parseInt(textyear1);
      int mmonth = Integer.parseInt(textmonth2) - Integer.parseInt(textmonth1);
      int mday = Integer.parseInt(textday2) - Integer.parseInt(textday1);

      if (rbtn == 1) {
         if (mmonth >= 0) {
            if (mday >= 0)
               re = myear;
            else
               re = myear - 1;
         } else
            re = myear - 1;
      } else if (rbtn == 2) {
         if (mday >= 0)
            re = myear * 12 + mmonth;
         else
            re = myear * 12 + mmonth - 1;
      }

      return re;
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
