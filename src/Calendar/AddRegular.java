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
   // 1 ���۳�¥, 2 ���ᳯ¥
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
   JRadioButton solar, lunar; //��� ���� ���� ��ư
   JCheckBox yun1, yun2;
   boolean isLunar = false, isFind = false, isYun1 = false, isYun2= false;
   int lunYear1, lunYear2, lunMon1, lunMon2, lunDay1, lunDay2;
   LunarCalendar lc;
   private int Cal[][];

   public AddRegular(ScheduleManager sm) {
      this.sm = sm;
      schs = sm.getSchs();
      this.setTitle("���� ���");
      this.setSize(400, 500);
      kit = this.getToolkit();
      Dimension screenSize = kit.getScreenSize();
      this.width = screenSize.width;
      this.height = screenSize.height;
      this.setLocation(width / 2 - 200, height / 2 - 200);
      
      new JScrollPane(c);
      c = this.getContentPane();
      c.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      c.add(new JLabel("����"));
      c.add(title = new TextField(37));
      c.add(new JLabel("����"));
      c.add(textyear1 = new TextField(5));
      c.add(new JLabel("��"));
      c.add(textmonth1 = new TextField(4));
      c.add(new JLabel("��"));
      c.add(textday1 = new TextField(4));
      c.add(new JLabel("��"));
      c.add(yun1 = new JCheckBox("����")); //���´������� ���� üũ�ڽ� ���̱�
      yun1.setVisible(false);
      
      JLabel space1 = new JLabel("                   ");
      c.add(space1);

      c.add(new JLabel("����"));
      c.add(textyear2 = new TextField(5));
      c.add(new JLabel("��"));
      c.add(textmonth2 = new TextField(4));
      c.add(new JLabel("��"));
      c.add(textday2 = new TextField(4));
      c.add(new JLabel("��"));
      c.add(yun2 = new JCheckBox("����"));
      yun2.setVisible(false);
      JLabel space2 = new JLabel("                ");
      c.add(space2); 
      
      group = new ButtonGroup();
      c.add(solar = new JRadioButton("���"));
      solar.setSelected(true); //��� ���� ������ư, �⺻ ��
      c.add(lunar = new JRadioButton("����")); //���� ���� ������ư
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
      c.add(new JLabel("�ݺ� �ֱ�"));
      c.add(new JLabel("     "));
      btn1 = new JRadioButton("�ų�");
      btn2 = new JRadioButton("�ſ�");
      btn3 = new JRadioButton("����");
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

      c.add(new JLabel("���"));
      c.add(textplace = new TextField(37));
      c.add(new JLabel("�޸�"));
      c.add(memo = new TextArea(6, 37));
      c.add(new JScrollPane(memo));

      btn = new JButton("����");
      c.add(btn);

      btn.addActionListener(new ActionListener() // �Է°� �����ؾ���
      {

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
        	 try {
        		
     			
            if (textyear1.getText().equals("") || textmonth1.getText().equals("") || textday1.getText().equals("")
                  || textyear2.getText().equals("") || textmonth2.getText().equals("")
                  || textday2.getText().equals("")) { // ��¥ �� ���� �ȵ�
               if (title.getText().equals(""))
                  JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               else
                  JOptionPane.showMessageDialog(null, "��/��/���� ��� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
            }

            else // ��¥ ������� //���� ���ᳯ¥ ���� �Է� ����â�� �߰� �� ���ᳯ¥�� ���۳�¥���� ū���� �˻��ϸ� �ɵ�?? �Ϸ�!
            {
            	ty1 = textyear1.getText();
     			bc = new BasicCalendar(Integer.parseInt(ty1));
     			feb = bc.getFeb();
     			ty2 = textyear2.getText();
     			bc = new BasicCalendar(Integer.parseInt(ty2));
     			feb2 = bc.getFeb();
               if (title.getText().equals("")) // ���� ������ �ʼ�
                  JOptionPane.showMessageDialog(null, "���� ������ �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textyear1.getText()) || Integer.parseInt(textyear1.getText()) < 1900
                     || Integer.parseInt(textyear1.getText()) > 2037) // �� ����
                  JOptionPane.showMessageDialog(null, "�⵵�� Ȯ���ϼ���.", "���", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textmonth1.getText()) || Integer.parseInt(textmonth1.getText()) < 1
                       || Integer.parseInt(textmonth1.getText()) > 12) // �� ����
                    JOptionPane.showMessageDialog(null, "1������ 12�������� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
               else if (((Integer.parseInt(textmonth1.getText()) < 8
						&& (Integer.parseInt(textmonth1.getText()) % 2 == 1))
						|| (Integer.parseInt(textmonth1.getText()) >= 8)
								&& Integer.parseInt(textmonth1.getText()) % 2 == 0) && (!isNumeric(textday1.getText())||
						 (Integer.parseInt(textday1.getText()) < 1 || Integer.parseInt(textday1.getText()) > 31))) // ��																					// ����
					JOptionPane.showMessageDialog(null, "1�Ϻ��� 31�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
				else if((Integer.parseInt(textmonth1.getText()) == 2) &&(!isNumeric(textday1.getText())||(Integer.parseInt(textday1.getText()) < 1||Integer.parseInt(textday1.getText()) > feb))) {
					JOptionPane.showMessageDialog(null, "1�Ϻ��� "+feb+"�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
				}
				else if ((((Integer.parseInt(textmonth1.getText()) < 8
						&& (Integer.parseInt(textmonth1.getText()) % 2 == 0))
						|| (Integer.parseInt(textmonth1.getText()) >= 8)
								&& Integer.parseInt(textmonth1.getText()) % 2 == 1)) && (!isNumeric(textday1.getText())
						|| (Integer.parseInt(textday1.getText()) < 1 || Integer.parseInt(textday1.getText()) > 30))) // ��
																													// ����
					JOptionPane.showMessageDialog(null, "1�Ϻ��� 30�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
				

               else if (!isNumeric(textday1.getText()) || (((Integer.parseInt(textmonth1.getText()) < 8
                     && (Integer.parseInt(textmonth1.getText()) % 2 == 0))
                     || (Integer.parseInt(textmonth1.getText()) >= 8)
                           && Integer.parseInt(textmonth1.getText()) % 2 == 1))
                     && (Integer.parseInt(textday1.getText()) < 1 || Integer.parseInt(textday1.getText()) > 30)) // ��
                                                                                          // ����
                  JOptionPane.showMessageDialog(null, "1�Ϻ��� 30�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textyear2.getText()) || Integer.parseInt(textyear2.getText()) < 1900
                     || Integer.parseInt(textyear2.getText()) > 2037) // �� ����
                  JOptionPane.showMessageDialog(null, "�⵵�� Ȯ���ϼ���.", "���", JOptionPane.WARNING_MESSAGE);

               else if (!isNumeric(textmonth2.getText()) || Integer.parseInt(textmonth2.getText()) < 1
                     || Integer.parseInt(textmonth2.getText()) > 12) // �� ����
                  JOptionPane.showMessageDialog(null, "1������ 12�������� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);

               else if (((Integer.parseInt(textmonth2.getText()) < 8
						&& (Integer.parseInt(textmonth2.getText()) % 2 == 1))
						|| (Integer.parseInt(textmonth2.getText()) >= 8)
								&& Integer.parseInt(textmonth2.getText()) % 2 == 0) && (!isNumeric(textday2.getText())||
						 (Integer.parseInt(textday2.getText()) < 1 || Integer.parseInt(textday2.getText()) > 31))) { // ��
																													// ����
					JOptionPane.showMessageDialog(null, "1�Ϻ��� 31�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
				}
				
				
				else if((Integer.parseInt(textmonth2.getText()) == 2) &&(!isNumeric(textday2.getText())||(Integer.parseInt(textday2.getText()) < 1||Integer.parseInt(textday2.getText()) > feb2))) {
					JOptionPane.showMessageDialog(null, "1�Ϻ��� "+feb2+"�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
				}
				else if ((((Integer.parseInt(textmonth2.getText()) < 8
						&& (Integer.parseInt(textmonth2.getText()) % 2 == 0))
						|| (Integer.parseInt(textmonth2.getText()) >= 8)
								&& Integer.parseInt(textmonth2.getText()) % 2 == 1)) && (!isNumeric(textday2.getText())
						|| (Integer.parseInt(textday2.getText()) < 1 || Integer.parseInt(textday2.getText()) > 30))) // ��
																													// ����
					JOptionPane.showMessageDialog(null, "1�Ϻ��� 30�ϱ����� �Է��Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);

               else if (Integer.parseInt(textyear1.getText()) > Integer.parseInt(textyear2.getText()) //���۳�¥ ���ᳯ¥
                     || Integer.parseInt(textyear1.getText()) == Integer.parseInt(textyear2.getText())
                           && Integer.parseInt(textmonth1.getText()) > Integer.parseInt(textmonth2.getText())
                     || Integer.parseInt(textyear1.getText()) == Integer.parseInt(textyear2.getText())
                           && Integer.parseInt(textmonth1.getText()) == Integer.parseInt(textmonth2.getText())
                           && Integer.parseInt(textday1.getText()) > Integer.parseInt(textday2.getText()))
                  JOptionPane.showMessageDialog(null, "���� ��¥���� ���� ��¥�� �����̾�� �մϴ�.", "���",
                        JOptionPane.WARNING_MESSAGE);

               else { // ������ ���
                  if(!isLunar) { //���

                  // ���ᳯ¥ ��,�Ͽ� 0���ԵǾ� �ִ��� �˻�
                  if (textmonth2.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textmonth2.getText())) == 0
                        && textmonth2.getText().contains("0")) {

                     if (textday2.getText().length() == 2
                           && (int) Math.log10(Integer.parseInt(textday2.getText())) == 0
                           && textday2.getText().contains("0")) {
                        tm2 = textmonth2.getText().substring(1);
                        td2 = textday2.getText().substring(1);

                     } // �ΰ���

                     else {
                        tm2 = textmonth2.getText().substring(1);
                        td2 = textday2.getText();
                     } // ����
                  }

                  else if (textday2.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textday2.getText())) == 0
                        && textday2.getText().contains("0")) {
                     tm2 = textmonth2.getText();
                     td2 = textday2.getText().substring(1);
                  } // �ϸ�

                  else {
                     tm2 = textmonth2.getText();
                     td2 = textday2.getText();
                  }

                  // ���۳�¥ ��,�� 0���ԵǾ� �ִ��� �˻��ϰ� weekȣ���ؼ� �ݺ�
                  if (textmonth1.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textmonth1.getText())) == 0
                        && textmonth1.getText().contains("0")) {

                     if (textday1.getText().length() == 2
                           && (int) Math.log10(Integer.parseInt(textday1.getText())) == 0
                           && textday1.getText().contains("0")) {
                        tm1 = textmonth1.getText().substring(1);
                        td1 = textday1.getText().substring(1);

                     } // �ΰ���

                     else {
                        tm1 = textmonth1.getText().substring(1);
                        td1 = textday1.getText();
                     } // ����
                  }

                  else if (textday1.getText().length() == 2
                        && (int) Math.log10(Integer.parseInt(textday1.getText())) == 0
                        && textday1.getText().contains("0")) {
                     tm1 = textmonth1.getText();
                     td1 = textday1.getText().substring(1);
                  } // �ϸ�

                  else {
                     tm1 = textmonth1.getText();
                     td1 = textday1.getText();

                  }
                  week();// ���� �ݺ� �Լ�
                  }
                  
                  else { //����
               
                     lunYear1 = Integer.parseInt(textyear1.getText()); //����ڰ� �Է��� ���³�¥
                     lunMon1 = Integer.parseInt(textmonth1.getText());
                     lunDay1 = Integer.parseInt(textday1.getText());
                     
                     lunYear2 = Integer.parseInt(textyear2.getText());
                     lunMon2 = Integer.parseInt(textmonth2.getText());
                     lunDay2 = Integer.parseInt(textday2.getText());
                     
                     String sol1[],sol2[]; //���� > ������� �ٲ㼭 �޷¿� ��»����� ����
                     sol1 = lunarTosolar(lunYear1, lunMon1, lunDay1, isYun1);
                     sol2 = lunarTosolar(lunYear2, lunMon2, lunDay2, isYun2);
                     if(sol1==null || sol2==null) {
                        JOptionPane.showMessageDialog(null, "���� ���� Ȯ�� �Ǵ� �������� �ʴ� ���� ��¥.", "���", JOptionPane.WARNING_MESSAGE);
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
               }//������
           

               // dispose();
            }
        	 }catch(NumberFormatException e1) {
                 JOptionPane.showMessageDialog(null, "��¥�� ��Ȯ�� �Է����ּ���");

             }

         }

      });

      this.setVisible(true);

   }

   public void week() {//�ݺ��Լ�

		// �̰ɷ� 2���޾ƿ����
		bc = new BasicCalendar(Integer.parseInt(ty1));
		feb = bc.getFeb();
		int re;

		if (btn1.isSelected()) {// �ų�
			if(isLunar)
                JOptionPane.showMessageDialog(null, ty1+"�� "+tm1+"�� "+td1+"�Ϻ��� �߰�");
			
			re = setRE(textyear1.getText(), textmonth1.getText(), textday1.getText(), textyear2.getText(),
					textmonth2.getText(), textday2.getText(), 1);

			for (int i = 0; i <= re; i++) {
				Schedule s = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
				schs.add(s);
				sm.setSchs(schs);
				ty1 = Integer.toString(Integer.parseInt(ty1) + 1);
				
			}
			dispose();
		} else if (btn2.isSelected()) {// �ſ�
			if(isLunar)
                JOptionPane.showMessageDialog(null, ty1+"�� "+tm1+"�� "+td1+"�Ϻ��� �߰�");
			re = setRE(textyear1.getText(), textmonth1.getText(), textday1.getText(), textyear2.getText(),
					textmonth2.getText(), textday2.getText(), 2);

			for (int i = 0; i <= re; i++) {

				Schedule s = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
				schs.add(s);
				sm.setSchs(schs);

				tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
				if (Integer.parseInt(tm1) > 12)// �⵵ �ٲ�
				{
					tm1 = "1";
					ty1 = Integer.toString(Integer.parseInt(ty1) + 1);
					feb = bc.getFeb();
				}

				if ((Integer.parseInt(tm1) == 4 || Integer.parseInt(tm1) == 6 || Integer.parseInt(tm1) == 9
						|| Integer.parseInt(tm1) == 11) && Integer.parseInt(td1) == 31) {//30�Ͼ��´�
					tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
					i++;
				}
				if (Integer.parseInt(td1) > feb && Integer.parseInt(tm1) == 2) {//2��
					tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
					i++;
				}

			}
			dispose();
		} else if (btn3.isSelected()) {// ����
			if(isLunar)
                JOptionPane.showMessageDialog(null, ty1+"�� "+tm1+"�� "+td1+"�Ϻ��� �߰�");
			Schedule ss = new Schedule(ty1, tm1, td1, title.getText(), textplace.getText(), memo.getText());
			schs.add(ss);
			sm.setSchs(schs);
			while (true) {
				td1 = Integer.toString(Integer.parseInt(td1) + 7);

				if (Integer.parseInt(tm1) == 1 || Integer.parseInt(tm1) == 3 || Integer.parseInt(tm1) == 5
						|| Integer.parseInt(tm1) == 7 || Integer.parseInt(tm1) == 8 || Integer.parseInt(tm1) == 10)// ��������
																													// 31��
																													// �϶�
																													// (12��
																													// ����)
				{
					if (Integer.parseInt(td1) > 31) {
						tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
						td1 = Integer.toString(Integer.parseInt(td1) - 31);
					}
				} else if (Integer.parseInt(tm1) == 12) {// 12������ �Ѿ�� �⵵ �ٲ�
					if (Integer.parseInt(td1) > 31) {
						td1 = Integer.toString(Integer.parseInt(td1) - 31);
						ty1 = Integer.toString(Integer.parseInt(ty1) + 1);
						tm1 = Integer.toString(1);
						bc = new BasicCalendar(Integer.parseInt(ty1));
						feb = bc.getFeb();// �⵵ �ٲ����ϱ� �ٽ�

					}
				} else if (Integer.parseInt(tm1) == 4 || Integer.parseInt(tm1) == 6 || Integer.parseInt(tm1) == 9
						|| Integer.parseInt(tm1) == 11)// �������� 30�� �϶�
				{
					if (Integer.parseInt(td1) > 30) {
						tm1 = Integer.toString(Integer.parseInt(tm1) + 1);
						td1 = Integer.toString(Integer.parseInt(td1) - 30);
					}
				} else// 2��
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
			JOptionPane.showMessageDialog(null, "�ݺ� �ֱ⸦ �����Ͻʽÿ�.", "���", JOptionPane.WARNING_MESSAGE);
		}
	}

   
   
   public boolean isNumeric(String s) { // �⵵ ��������
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
