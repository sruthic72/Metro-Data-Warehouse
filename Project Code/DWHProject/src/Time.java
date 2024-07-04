
public class Time {

	String date;
	int day;
	int month;
	int quarter;
	int year;
	
	public Time(String date, int day, int month, int quarter, int year) {
		
		this.date = date;
		this.day = day;
		this.month = month;
		this.quarter = quarter;
		this.year = year;
		
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
	
}
