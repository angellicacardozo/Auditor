package br.com.auditor.decorator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateDecorator {

	public static String decorate(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date.getTime());
	}

}
