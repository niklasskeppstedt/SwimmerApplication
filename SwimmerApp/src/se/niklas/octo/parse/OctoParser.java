package se.niklas.octo.parse;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import se.niklas.octo.domain.Event;
import se.niklas.octo.domain.PersonalBest;
import se.niklas.octo.domain.Swimmer;

public class OctoParser {

	private String url;
	private Document document;

	public OctoParser(String swimmerUrl) {
		this.url = swimmerUrl;
	}

	public Swimmer parseSwimmer(String octoId) {
		Swimmer swimmer = null;
		try {
			document = Jsoup.parse(new URL(url + octoId), 3000);
		} catch (IOException e) {
			System.err.println("Could not parse swimmer url");
		}
		String name = extractSwimmerName();
		String dateOfBirth = extractSwimmerYearOfBirth();
		String swimmingClub = extractSwimmerClub();
		swimmer = new Swimmer(name, dateOfBirth, octoId, swimmingClub);
		extractPersonalBests(swimmer);
		
		return swimmer;
	}
	
	public Set<Swimmer> parseSearchResult() {
		try {
			document = Jsoup.parse(new URL(url), 3000);
		} catch (IOException e) {
			System.err.println("Could not parse swimmer url");
		}
		Set<Swimmer> swimmers = new HashSet<>();
		Elements odd = document.select("tr.odd");
		odd.addAll(document.select("tr.even"));
		for (Element element : odd) {
			String firstName = element.select("td.name-column").first().ownText();
			String lastName = element.select("td").get(1).ownText();
			String yearOfBirth = element.select("td").get(3).ownText();
			String swimmingClub = element.select("td").get(4).ownText();
			String octoId = extractLinkParameter(element, "id");
			boolean added = swimmers.add(new Swimmer(firstName + " " + lastName, yearOfBirth, octoId, swimmingClub));
			if(!added) {
				System.err.println("Could not add swimmer, already in set");
			}
		}
		return swimmers;
	}

	private void extractPersonalBests(Swimmer swimmer) {
		Elements odds = document.select("tr.odd");
		Elements evens = document.select("tr.even");
		odds.addAll(evens);
		for (Element element : odds) {
			String competition = extractCompetition(element);
			String date = extractDate(element);
			Duration time = extractTime(element);
			Event event = Event.fromCode(extractLinkParameter(element, "event"));
			swimmer.addPersonalBest(new PersonalBest(event, time, competition, swimmer));
		}
	}

	private String extractLinkParameter(Element element, String parameterName) {
		String[] link = element.select("a").attr("href").split(parameterName +"=");
		return link[1];
	}

	private Duration extractTime(Element element) {
		String timeString = element.select("td").get(3).ownText().replace('.', ':').trim();
		String time = extractFromTextWithPattern(timeString, "\\d{2}:\\d{2}:\\d{2}");
		String[] split = time.split(":");
		return Duration.ofMinutes(Long.valueOf(split[0])).plusSeconds(Long.valueOf(split[1])).plusMillis(Long.valueOf(split[2]) * 10);
	}

	private String extractDate(Element element) {
		return element.select("td").get(2).ownText().trim();
	}

	private String extractCompetition(Element element) {
		return element.select("td").get(1).ownText().trim();
	}

	private String extractSwimmerYearOfBirth() {
		String dateOfBirth = null;
		String bornText = document.getElementsContainingOwnText("Född").text();
		dateOfBirth = extractFromTextWithPattern(bornText, "\\d{4}");
		return dateOfBirth;
	}

	private String extractSwimmerClub() {
		String clubConst = "Förening:";
		String swimmingClub = document.getElementsContainingOwnText(clubConst).text();
//		Född: 2003 Förening: Stockholms Kappsimningsklubb Licens: AG3903
		swimmingClub = swimmingClub.substring(swimmingClub.indexOf(clubConst) + clubConst.length(), swimmingClub.indexOf("Licens")).trim();
		return swimmingClub;
	}

	private String extractFromTextWithPattern(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()) {
			return matcher.group();
		}
		return "Not found";
		
	}

	private String extractSwimmerName() {
		String name;
		name = document.select("h2").text();
		return name;
	}

}