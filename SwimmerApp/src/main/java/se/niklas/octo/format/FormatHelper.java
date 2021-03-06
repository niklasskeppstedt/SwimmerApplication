package se.niklas.octo.format;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatHelper {
	
	public static String formatDuration(Duration duration) {
		LocalTime localTime = LocalTime.ofNanoOfDay(duration.toNanos());
		return localTime.format(DateTimeFormatter.ofPattern("mm:ss:SS"));
	}

}
