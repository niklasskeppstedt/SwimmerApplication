package se.niklas.octo.domain;

import java.time.Duration;

import org.junit.Test;

public class PersonalBestTest {

	@Test
	public void testToString() {
		PersonalBest personalBest1 = new PersonalBest(Event.BACKSTROKE_200, Duration.ofMinutes(3).plusSeconds(25).plusMillis(360), "Test Competition", new Swimmer("Test Testsson","2003", "12345", "Simklubben"));
		PersonalBest personalBest2 = new PersonalBest(Event.FREESTYLE_1500, Duration.ofMinutes(0).plusSeconds(25).plusMillis(360), "Test Competition", new Swimmer("Test Testsson","2003", "12345", "Simklubben"));
		System.out.println(personalBest1);
		System.out.println(personalBest2);
		
	}

}
