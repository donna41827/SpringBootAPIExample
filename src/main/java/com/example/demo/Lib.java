package com.example.demo;

import java.time.LocalDate;
import java.util.regex.*;

import org.springframework.stereotype.Component;

@Component("Lib")
public class Lib {

	public String GetTWDate(LocalDate d) {
		String str = String.valueOf(d.getYear()-1911)+ 
						rightPad("00"+String.valueOf(d.getMonthValue()),2)+
						rightPad("00"+String.valueOf(d.getDayOfMonth()),2);
		return str;
	}

	public String rightPad(String str, int length) {
		str = str.substring(str.length() - length, str.length());
		return str;
	}
	
	public String GetOnlyNumber(String str) 
	{
		Pattern p = Pattern.compile("[^0-9]");
		Matcher m = p.matcher(str);
		String result = m.replaceAll("");
		
		return result;
	}
}