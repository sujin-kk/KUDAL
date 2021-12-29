package Calendar;

import java.util.Scanner;
import java.util.Vector;

public class ScheduleManager {
	
	private Vector<Schedule> schs;
	private Vector<Schedule> search = new Vector();
	private int count = 0;
	
	public ScheduleManager() {
		schs = new Vector<Schedule>();
	}
	
	public void addSchedule(Schedule s) {
		
		schs.add(s);
		this.count++; //총 일정 개수	
	}

	public Vector<Schedule> getSchs() {
		return schs;
	}


	public void setSchs(Vector<Schedule> schc) {
		// TODO Auto-generated method stub
		this.schs = schs;
	}
	

	public Schedule searchNonSchedule(String title) 
	{
		for(int i=0; i<schs.size(); i++)
		{
			if(this.schs.get(i).getTitle().contains(title)&&this.schs.get(i).isCheck()) 
			{
				return this.schs.elementAt(i);
			}
		}
		// 해당 일정 없을때 예외처리
		return null;
		
	}


	public Vector searchSchedule(String title, int year, int month, int day) 
	{
		Vector<Schedule> find = new Vector<Schedule>();
		// TODO Auto-generated method stub
		for(int i=0; i<schs.size(); i++)
		{
			if(this.schs.get(i).getTitle().equals(title)&&this.schs.get(i).getYear().equals(Integer.toString(year))&&this.schs.get(i).getMonth().equals(Integer.toString(month))&&this.schs.get(i).getDay().equals(Integer.toString(day))) 
			{
				find.add(this.schs.elementAt(i));
			}
		}
		return find;
	}
	
	public Vector searchScheVector(String title)
	{
		for(int i=0; i<schs.size(); i++)
		{
			if(this.schs.get(i).getTitle().equals(title)&&!this.schs.get(i).isCheck()) 
			{
				search.add(this.schs.get(i));
			}
		}
		return search;
	}
	public void Setsearch(Vector search)
	{
		this.search=search;
	}
	
	
	
}
