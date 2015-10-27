package se.niklas.octo.sort;

import java.util.Comparator;

import se.niklas.octo.domain.MedleyTeam;

public class MedleyTeamComparator implements Comparator<MedleyTeam> {

	@Override
	public int compare(MedleyTeam team0, MedleyTeam team1) {
		return team0.getTime().compareTo(team1.getTime());
	}

}
