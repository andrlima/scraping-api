package br.com.exemplo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

	public static String formatarDataEmString(Date data, String mask) {
		DateFormat formatter = new SimpleDateFormat(mask);
		return formatter.format(data);
	}
}

// Mascar para a formatação de data para texto

