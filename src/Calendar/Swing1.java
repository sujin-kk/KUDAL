package Calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;



public class Swing1 extends JFrame 
{
	int width;
	int height;
   JPanel panel1,panel2;
   Container c;
   JButton bt;
   JLabel tl2;
   JLabel tl3;
   JCheckBox lunar, yun;
   int year,month,day;
   int Solaryear,Solarmonth,Solarday; 
   int Lunaryear,Lunarmonth,Lunarday; 
   boolean islunar = false, isyun = false, find = false;
   BasicCalendar bc;
   private int Cal[][];
   private String yoil[] = {"��","ȭ","��","��","��","��","��"};
   LunarCalendar lc;
   Toolkit kit;
   public Swing1()
   {
      panel1 = new JPanel();
      panel2 = new JPanel();
      c = this.getContentPane();
      JTextField textyear = new JTextField(4);
      JTextField textmonth = new JTextField(4);
      JTextField textday = new JTextField(4);
      JLabel tl1 = new JLabel("��");
      JLabel tl11 = new JLabel("��");
      JLabel tl12 = new JLabel("��");
      JCheckBox lunar = new JCheckBox("���¿���");
      JCheckBox yun = new JCheckBox("���޿���");
      bt = new JButton("�Է�");
      kit=this.getToolkit();
      Dimension screenSize =kit.getScreenSize();
      this.width=screenSize.width;
      this.height=screenSize.height;
      
      c.setLayout(null);
      //c.add(panel1);
   
      textyear.setLocation(35, 25);
      textyear.setSize(50, 30);
      c.add(textyear); //�⵵ �Է� â
      
      tl1.setLocation(87, 25);
      tl1.setSize(30, 30);
      c.add(tl1);//��
      
      textmonth.setLocation(115, 25);
      textmonth.setSize(30, 30);
      c.add(textmonth); //�� �Է� â
      
      tl11.setLocation(147, 25);
      tl11.setSize(30, 30);
      c.add(tl11);//��
      
      textday.setLocation(175, 25);
      textday.setSize(30, 30);
      c.add(textday); //�� �Է� â
      
      tl12.setLocation(207, 25);
      tl12.setSize(30, 30);
      c.add(tl12);//��
      
      lunar.setLocation(35, 60);
      lunar.setSize(100, 30);
      c.add(lunar);	//���¿��� üũ�ڽ�
      
      yun.setLocation(145, 60);
      yun.setSize(100, 30);
	  c.add(yun);	//���޿��� üũ�ڽ�
	  yun.setVisible(false);	
	  
      lunar.addItemListener(new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				islunar = true;
				yun.setVisible(true);		  	
			}
			else
			{
				islunar = false;
				yun.setVisible(false);		
			}
		}
      });
      
      yun.addItemListener(new ItemListener() {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED)
				isyun = true;		
			else
				isyun=false;
		}
      });
      bt.setLocation(240, 30);
      bt.setSize(60, 30);
      c.add(bt); // �Է� ��ư
      
      bt.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) 
         {
            // TODO Auto-generated method stub
        	try 
        	{
        		year=Integer.parseInt(textyear.getText());
    			month=Integer.parseInt(textmonth.getText());
    			day=Integer.parseInt(textday.getText());
        		if(islunar==false)	//����϶�
        		{
        			lc = new LunarCalendar(year,month,day);
        			Lunaryear=lc.get(4);
        			Lunarmonth=lc.get(5);
        			Lunarday=lc.get(6);
        			bc = new BasicCalendar(year);
            		Cal= bc.getCal();
            		tl2.setText(yoil[Cal[month][day]]+"����");//label�ȿ� ���ڿ� �ٲ㼭 ���
            		if(lc.get(7)==1)
            			tl3.setText("���� : "+Lunarmonth+"�� "+Lunarday+"��(����)");
            		else
            			tl3.setText("���� : "+Lunarmonth+"�� "+Lunarday+"��(���)");
            		 tl3.setSize(150,30);
        		}
        		else	//�����϶�
        		{
        			for(int k=year-1;k<=year+1;k++)
        			{
        				bc = new BasicCalendar(k);
                		Cal= bc.getCal();
        				for(int i=1;i<=12;i++)
        				{
        					for(int j=1;j<=Cal[i].length;j++)
        					{
        						lc = new LunarCalendar(k,i,j);
        						if(lc.get(4)==year && lc.get(5)==month && lc.get(6)==day)	//��ȯ�� ��¥�� �Է¹��� ��¥�� ���� ��
        						{
        							if((isyun==true && lc.get(7)==1) || (isyun==false && lc.get(7)==0))	//��ȯ�� ��¥�� ���޿��ο� �Է¹��� ���޿��ΰ� ������ 
        							{
        								Solaryear=k;
        								Solarmonth=i;
        								Solarday=j;
        								find=true;
        								break;
        							}
        							else
        								find=false;
        						}
        						else
        							find=false;
        					}
        					if(find)
        						break;
        				}
        				if(find)
    						break;
        			}
        			if(find)
        			{
        				tl2.setText(yoil[Cal[Solarmonth][Solarday]]+"����");//label�ȿ� ���ڿ� �ٲ㼭 ���
        				tl3.setText("��� : "+Solarmonth+"�� "+Solarday+"��");
        			}
        			else
        			{
        				tl2.setSize(250,30);
        				tl2.setText("��ȯ�� �����߽��ϴ�.");
        				tl3.setSize(200,30);
        				tl3.setText("���� ���θ� Ȯ���ϼ���.");
        			}
        		}
        		
        		panel1.setVisible(true);//��ư������ ��� ���̰�
        		panel2.setVisible(false);   
        	}
        	catch(NumberFormatException e1) 
        	{
       		 	JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���");
       	 	}
        	catch(ArrayIndexOutOfBoundsException e2) 
        	{
        		JOptionPane.showMessageDialog(null, "�Է� ������ �´� ���ڸ� �Է����ּ���");
        	}
        	
         }
         
      });
      
      TitledBorder tb = new TitledBorder(new LineBorder(Color.black,2),"����");
      panel1.setLayout(null);
      panel1.setBorder(tb);
      panel1.setBounds(30,90,400,150);
      tl2 = new JLabel("?����");
      tl3 = new JLabel("���� : ?�� ?��");
      tl2.setLocation(30,30);
      tl2.setSize(100,30);
      panel1.add(tl2);
      tl3.setLocation(30,60);
      tl3.setSize(100,30);
      panel1.add(tl3);
      panel1.setVisible(false);//��� �Ⱥ��̰�
      
      panel2.setLayout(null);
      panel2.setBorder(tb);
      panel2.setBounds(30,90,400,150);
      
      c.add(panel1);
      c.add(panel2);
      
      setTitle("���� ���� ���");
      setSize(470,290);
      this.setLocation(width/2-200, height/2-150);
      setVisible(true);
   }
   
}
