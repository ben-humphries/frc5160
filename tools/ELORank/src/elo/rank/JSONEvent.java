package elo.rank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONEvent implements Comparable<JSONEvent>{
	public String event_code; 
	public int year; 
	public String end_date; 
	public Date getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(end_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int compareTo(JSONEvent o) {
		return getDate().compareTo(o.getDate());
	}
}
