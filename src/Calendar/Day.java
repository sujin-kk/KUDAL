package Calendar;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Day{
	
	Scanner scan = new Scanner(System.in);
	private int Cal[][];
	private boolean HolidayCheck[][];
	private int SolorHoliday[][] = {
			{3,1},{5,5},{6,6},{10,3},{12,25}
	};
	String name[] = {"3*1��","��̳�","������","��õ��","ũ��������"};
	Queue<String> FUllHoliday = new LinkedList<String>();
	Queue<Integer> GoldenWeek = new LinkedList<Integer>();

	BasicCalendar bc;
	LunarCalendar lc;
	public Day(BasicCalendar bc) 
	{
		super();
		this.bc = bc;
		Cal = bc.getCal();
		HolidayCheck = bc.getHolidayCheck();
		MakeVector();
		MakeHoliday();
		FindGoldenWeek();
		bc.setCal(Cal);
		bc.setHoildayCheck(HolidayCheck);
	}

	public void MakeVector()
	{
		
		for(int i=0;i<SolorHoliday.length;i++)
		{
			FUllHoliday.add(Integer.toString(SolorHoliday[i][0]));
			FUllHoliday.add(Integer.toString(SolorHoliday[i][1]));
			FUllHoliday.add(name[i]);
		}
	}
	public void FindGoldenWeek() 
	{
		int a=0,b=0,c=0,d=0, count=0;
		for(int i=1;i<HolidayCheck.length;i++)
		{
			for(int j=1;j<HolidayCheck[i].length;j++)
			{
				if(HolidayCheck[i][j]==true)
				{
					a=i;b=j;
					while(HolidayCheck[a][b]==true)
					{
						c=a;
						d=b;
						count++;
						b=(b+1)%(HolidayCheck[i].length);
						if(b==0)
						{
							b=1;
							a=a+1;
							if(a==13)
								break;
						}
					}
					if(count>=3)
					{
						GoldenWeek.add(i);
						GoldenWeek.add(j);
						GoldenWeek.add(c);
						GoldenWeek.add(d);
						i=c;
						j=d;
					}
					count=0;
				}
			}
		}
	}
	public void MakeHoliday()
	{
		int count=bc.getHolicount();
		int lunarmonth,lunarday;
		//����, �߼��� ������ ������
		for(int i=0;i<SolorHoliday.length;i++)
		{
			if(HolidayCheck[SolorHoliday[i][0]][SolorHoliday[i][1]]!=true)
			{
				count++;
				HolidayCheck[SolorHoliday[i][0]][SolorHoliday[i][1]]=true;
			}
		}
		//���� �߼�
		for(int j=1;j<=12;j++)
		{
			for(int k=1;k<Cal[j].length;k++)
			{
				lc = new LunarCalendar(bc.getYear(),j,k);	//����� �������� ��ȯ
				lunarmonth=lc.get(5);
				lunarday=lc.get(6);
				if(lc.get(7)==0)	//������ �ƴ϶��
				{
					if( lunarmonth==1 && lunarday==1)	//�����϶�
					{
						HolidayCheck[j][k]=true;    //������ ǥ��
						FUllHoliday.add(Integer.toString(j));	//����
						FUllHoliday.add(Integer.toString(k));
						FUllHoliday.add("����");
						if(k==1)    //������ ?�� 1�� �϶�
						{	            
							HolidayCheck[j-1][Cal[j-1].length-1]=true;    //�� ���� �������� ���Ϸ� üũ 
							FUllHoliday.add(Integer.toString(j-1));	//����
							FUllHoliday.add(Integer.toString(Cal[j-1].length-1));
							FUllHoliday.add("��������");
						
							HolidayCheck[j][2]=true;    //���� ������ ���� üũ
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(2));
							FUllHoliday.add("��������");
						}
						else if(k==Cal[j].length-1) //?�� ���������̶��
						{
							HolidayCheck[j][k-1]=true;    //���� ���� ���� üũ
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(k-1));
							FUllHoliday.add("��������");
	                	 
							HolidayCheck[j+1][1]=true;    //������ 1�� ���� üũ	    
							FUllHoliday.add(Integer.toString(j+1));	//����
							FUllHoliday.add(Integer.toString(1));
							FUllHoliday.add("��������");
						}
						else    //�� �� �ش�ȵǴ� ���
						{
							HolidayCheck[j][k-1]=true;
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(k-1));
							FUllHoliday.add("��������");
							HolidayCheck[j][k+1]=true;
	                     
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(k+1));
							FUllHoliday.add("��������");
						}
						count=count+3;
					}
					else if(lunarmonth==4 && lunarday==8) //��ó�� ���� ���϶�
					{
						HolidayCheck[j][k]=true;    //������ ǥ��
						FUllHoliday.add(Integer.toString(j));	//����
						FUllHoliday.add(Integer.toString(k));
						FUllHoliday.add("��ó�� ���� ��");
						count++;
					}
					else if(lunarmonth==8 && lunarday==15) //�߼��϶�
					{
						HolidayCheck[j][k]=true;    //������ ǥ��
						FUllHoliday.add(Integer.toString(j));	//����
						FUllHoliday.add(Integer.toString(k));
						FUllHoliday.add("�߼�");
						if(k==1)    //������ ?�� 1�� �϶�
						{	            
							HolidayCheck[j-1][Cal[j-1].length-1]=true;    //�� ���� �������� ���Ϸ� üũ 
							FUllHoliday.add(Integer.toString(j-1));	//����
							FUllHoliday.add(Integer.toString(Cal[j-1].length-1));
							FUllHoliday.add("�߼�����");
						
							HolidayCheck[j][2]=true;    //���� ������ ���� üũ
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(2));
							FUllHoliday.add("�߼�����");
						}
						else if(k==Cal[j].length-1) //?�� ���������̶��
						{
							HolidayCheck[j][k-1]=true;    //���� ���� ���� üũ
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(k-1));
							FUllHoliday.add("�߼�����");
	                	 
							HolidayCheck[j+1][1]=true;    //������ 1�� ���� üũ	    
		                	FUllHoliday.add(Integer.toString(j+1));	//����
		                	FUllHoliday.add(Integer.toString(1));
		                	FUllHoliday.add("�߼�����");
						}
						else    //�� �� �ش�ȵǴ� ���
						{
							HolidayCheck[j][k-1]=true;
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(k-1));
							FUllHoliday.add("�߼�����");
							
							HolidayCheck[j][k+1]=true;	                  
							FUllHoliday.add(Integer.toString(j));	//����
							FUllHoliday.add(Integer.toString(k+1));
							FUllHoliday.add("�߼�����");
						}
						count=count+3;
					}
				
				}
			}
		}
		bc.setHolicount(count);
	}

	public int getHolicount() {
		return bc.getHolicount();
	}
	public Queue<String> getFUllHoliday() {
		return FUllHoliday;
	}
	public Queue<Integer> getGoldenWeek() {
		// TODO Auto-generated method stub
		return GoldenWeek;
	}
		
}
