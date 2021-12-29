package Calendar;

import java.util.Scanner;

public class BasicCalendar 
{
	
	private int month, day, first = 0, d=0, holicount=0;
	int feb;
	int year;
	private int Cal[][] = new int[13][32];
	private boolean HolidayCheck[][] = new boolean[13][32];
	Scanner scan=new Scanner(System.in);
	
	public BasicCalendar(int year) 
	{
		super();
		this.year=year;
		for(int i=1;i<13;i++)
		{
			if(i<8)
			{
				if(i%2==1)
				{
					Cal[i]=new int[32]; //31일
					HolidayCheck[i]=new boolean[32];
				}
				else
				{
					Cal[i]=new int[31]; //30일
					HolidayCheck[i]=new boolean[31];
				}
			}
			else
			{
				if(i%2==0)
				{
					Cal[i]=new int[32];
					HolidayCheck[i]=new boolean[32];
				}
				else
				{
					Cal[i]=new int[31];
					HolidayCheck[i]=new boolean[31];
				}
			}
		}
		MadeCal();
	}
	void IsLeapYear(int year)
	{
	    if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
	    {
	    	Cal[2] = new int[30]; //29일 -> 윤달
	        HolidayCheck[2]=new boolean[30];
	        feb=29;
	    }
	    else
	    {
	    	Cal[2] = new int[29]; //28일
	    	HolidayCheck[2]=new boolean[29];
	    	feb=28;
	    }
	}


	public void MadeCal() 
	{
		for(int i=0;i<HolidayCheck.length;i++)
		{
			for(int j=0;j<HolidayCheck[i].length;j++)
				HolidayCheck[i][j]=false;
		}
		
		IsLeapYear(year);
		year=year-1; //공식에 따라 년도에서 1 빼주기
		month=13; //공식에 따라 1월은 13
		day=1;
		//이탈리아 수학자 공식 1월 1일 요일알아내서 쭉 배열에 삽입
		first=(day+((month+1)*26)/10+(year%100)+((year%100)/4)+((year/100)/4)-2*(year/100))%7;
		/*400으로 나누어지면 윤년,100으로 나누어지고 400으로 안나눠지면 평년, 4로 나눠지면 윤년*/
		first+=5;
		if(first>6)
			first=first%7;
	
		year=year+1;
		
		
		for(int i=0;i<Cal.length;i++) 
		{
			for(int j=0;j<Cal[i].length;j++) 
			{
				if(i==0||j==0) 
				{
					Cal[i][j]=7; //의미없는 값
					continue;
				}
				else 
				{
					if(first==5||first==6)
					{
						holicount++;			
						HolidayCheck[i][j]=true; //토,일은 휴일					
					}
					Cal[i][j]=first;
					first=(first+1)%7;	
				
				}
			}
		}	
	}
	public int getYear() {
		return year;
	}
	public int getFeb() {
		return feb;
	}
	public int[][] getCal() 
	{
		return Cal;
	}
	public void setCal(int[][] Cal) 
	{
		this.Cal = Cal;
	}
	public boolean[][] getHolidayCheck() 
	{
		return HolidayCheck;
	}
	public void setHoildayCheck(boolean[][] HolidayCheck)
	{
		this.HolidayCheck = HolidayCheck;
	}
	public int getHolicount() {
		return holicount;
	}
	public void setHolicount(int holicount) {
		this.holicount = holicount;
	}
	
}


