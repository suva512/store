package com.ezee.store.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {
	public static String dateTimeFormat(LocalDateTime localDateTime) {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return localDateTime.format(pattern);
	}
}

