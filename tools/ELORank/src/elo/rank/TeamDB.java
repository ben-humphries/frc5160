package elo.rank;

import java.util.HashMap;

public class TeamDB {
	public static HashMap<String,Team> data = new HashMap<String,Team>(); 
	public static final double K = 24;
	public void Update(JSONMatch match){
		if(!match.isNull()){
		for(int i = 0; i < Team.ELO_COUNT; i++){
		double avre = AverageRedElo(match, i)/400d;
		double avbe = AverageBlueElo(match, i)/400d;
		UpdateBlueElo(match, Math.pow(10, avre), i);
		UpdateRedElo(match, Math.pow(10, avbe), i);
		}
		//UpdateBlueEloTeamStyle(match, adr, adb);
		//UpdateRedEloTeamStyle(match, adr, adb);
		}
	}
	private void UpdateBlueEloTeamStyle(JSONMatch match, double adjustedRedElo, double adjustedBlueElo, int eloId){
		double expectedOutcome = adjustedBlueElo/(adjustedBlueElo+adjustedRedElo);
		double deltaElo = K*(match.getTrueOutcomeBlue(eloId)-expectedOutcome);
		for(String id : match.alliances.blue.teams){
			Team team = data.get(id);
			team.elos[eloId] = (team.elos[eloId] + deltaElo);
		}
	}
	private void UpdateRedEloTeamStyle(JSONMatch match, double adjustedRedElo, double adjustedBlueElo, int eloId){
		double expectedOutcome = adjustedRedElo/(adjustedBlueElo+adjustedRedElo);
		double deltaElo = K*(match.getTrueOutcomeRed(eloId)-expectedOutcome);
		for(String id : match.alliances.red.teams){
			Team team = data.get(id);
			team.elos[eloId] =  (team.elos[eloId] + deltaElo);
		}
	}
	private void UpdateBlueElo(JSONMatch match, double adjustedRedElo, int eloId){
		for(String id : match.alliances.blue.teams){
			HashMap d = data;
			Team team = data.get(id);
			double adjustedElo = Math.pow(10, team.elos[eloId]/400);
			double expectedOutcome = adjustedElo/(adjustedElo+adjustedRedElo);
			double outcome = match.getTrueOutcomeBlue(eloId);
			team.elos[eloId] =  (team.elos[eloId] + K*(outcome-expectedOutcome));
		}
	}
	private void UpdateRedElo(JSONMatch match, double adjustedBlueElo, int eloId){
		for(String id : match.alliances.red.teams){
			Team team = data.get(id);
			double adjustedElo = Math.pow(10, team.elos[eloId]/400);
			double expectedOutcome = adjustedElo/(adjustedElo+adjustedBlueElo);
			double outcome = match.getTrueOutcomeRed(eloId);
			team.elos[eloId] = (team.elos[eloId] + K*(outcome-expectedOutcome));
		}
	}
	private double AverageBlueElo(JSONMatch match, int eloId){
		double sum = 0;
		for(String id : match.alliances.blue.teams){
			if(data.get(id)==null){
				data.put(id, new Team(id));
				sum += data.get(id).elos[eloId];
			}
			else{
				sum += data.get(id).elos[eloId];
			}
		}
		return sum/match.alliances.blue.teams.length;
	}
	private double AverageRedElo(JSONMatch match, int eloId){
		double sum = 0;
		for(String id : match.alliances.red.teams){
			if(data.get(id)==null){
				data.put(id, new Team(id));
				sum += data.get(id).elos[eloId];
			}
			else{
				sum += data.get(id).elos[eloId];
			}
		}
		return sum/match.alliances.red.teams.length;
	}
	
	

}
