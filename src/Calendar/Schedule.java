package Calendar;

import java.util.Scanner;


public class Schedule {
	//private BasicCalender bc = new BasicCalender();
	private String title, place, memo, year, month, day; //��������, ���, �޸�, ��, ��, �� 
	private boolean check;
	
	public Schedule(String year, String month, String day, String title, String place, String memo) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.title = title;
		this.place = place;
		this.memo = memo;
		if(year.equals("")||month.equals("")||day.equals(""))	//��¥�� �ϳ��� �Է��� �� �Ȱ�� �ұ�Ģ�� �������� ����
			check=true;
		
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = Integer.toString(year);
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = Integer.toString(month);
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = Integer.toString(day);
	}

	

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isCheck() {
		// TODO Auto-generated method stub
		return check;
	}
	

	
}

