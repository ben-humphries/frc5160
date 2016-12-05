package elo.rank;

import java.util.HashMap;

public class TeamDB {
	public HashMap<String,Team> data = new HashMap<String,Team>(); 
	public static final double K = 24;
	public void Update(JSONMatch match){
		if(!match.isNull()){
		double ab = AverageBlueElo(match);
		double ar = AverageRedElo(match);
		double adb = Math.pow(10, ab/400);
		double adr = Math.pow(10, ar/400);
		UpdateBlueElo(match, adr);
		UpdateRedElo(match, adb);
		//UpdateBlueEloTeamStyle(match, adr, adb);
		//UpdateRedEloTeamStyle(match, adr, adb);
		}
	}
	private void UpdateBlueEloTeamStyle(JSONMatch match, double adjustedRedElo, double adjustedBlueElo){
		double expectedOutcome = adjustedBlueElo/(adjustedBlueElo+adjustedRedElo);
		double deltaElo = K*(match.getTrueOutcomeBlue()-expectedOutcome);
		for(String id : match.alliances.blue.teams){
			Team team = data.get(id);
			team.elo = (int) (team.elo + deltaElo);
		}
	}
	private void UpdateRedEloTeamStyle(JSONMatch match, double adjustedRedElo, double adjustedBlueElo){
		double expectedOutcome = adjustedRedElo/(adjustedBlueElo+adjustedRedElo);
		double deltaElo = K*(match.getTrueOutcomeRed()-expectedOutcome);
		for(String id : match.alliances.red.teams){
			Team team = data.get(id);
			team.elo = (int) (team.elo + deltaElo);
		}
	}
	private void UpdateBlueElo(JSONMatch match, double adjustedRedElo){
		for(String id : match.alliances.blue.teams){
			Team team = data.get(id);
			double adjustedElo = Math.pow(10, team.elo/400);
			double expectedOutcome = adjustedElo/(adjustedElo+adjustedRedElo);
			double outcome = match.getTrueOutcomeBlue();
			team.elo = (int) (team.elo + K*(outcome-expectedOutcome));
		}
	}
	private void UpdateRedElo(JSONMatch match, double adjustedBlueElo){
		for(String id : match.alliances.red.teams){
			Team team = data.get(id);
			double adjustedElo = Math.pow(10, team.elo/400);
			double expectedOutcome = adjustedElo/(adjustedElo+adjustedBlueElo);
			double outcome = match.getTrueOutcomeRed();
			team.elo = (int) (team.elo + K*(outcome-expectedOutcome));
		}
	}
	private double AverageBlueElo(JSONMatch match){
		double sum = 0;
		for(String id : match.alliances.blue.teams){
			if(data.get(id)==null){
				data.put(id, new Team(id));
				sum += data.get(id).elo;
			}
			else{
				sum += data.get(id).elo;
			}
		}
		return sum/match.alliances.blue.teams.length;
	}
	private double AverageRedElo(JSONMatch match){
		double sum = 0;
		for(String id : match.alliances.red.teams){
			if(data.get(id)==null){
				data.put(id, new Team(id));
				sum += data.get(id).elo;
			}
			else{
				sum += data.get(id).elo;
			}
		}
		return sum/match.alliances.red.teams.length;
	}
}
