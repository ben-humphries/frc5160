package elo.rank;

public class JSONMatch {
	public String comp_level;
	public int match_number;
	public JSONAllianceSuper alliances;
	public JSONScoreSuper score_breakdown;
	public static class JSONAllianceSuper{
		public JSONAlliance blue;
		public JSONAlliance red;
	}
	
	public static class JSONAlliance{
		public int score;
		public int trueScore; 
		public String[] teams; 
	}
	public static class JSONScoreSuper{
		public JSONScore blue;
		public JSONScore red; 
	}
	public static class JSONScore{
		public boolean kPaRankingPointAchieved;
		public boolean rotorRankingPointAchieved;
		public int autoFuelPoints;
		public int teleopFuelPoints;
		public int autoRotorPoints;
		public int teleopRotorPoints;
		public int teleopTakeoffPoints;
		public int teleopPoints;
		public int autoPoints; 
		public int foulPoints; 
		public int totalPoints;
	}
	public boolean isElimination(){
		return comp_level.contains("f");
	}
	public JSONAlliance getBlue(){
		return alliances.blue;
	}
	public JSONAlliance getRed(){
		return alliances.red;
	}
	public int getBlueTrueScore(int eloId){
		JSONScore team = score_breakdown.blue;
		int score = 0;
		if(eloId == 0){
			score = team.totalPoints-team.foulPoints;
			if(!isElimination()){
				if(team.kPaRankingPointAchieved){
					score += 20;
				}
				if(team.rotorRankingPointAchieved){
					score += 100;
				}
			}
		}
		return score;
	}
	public int getRedTrueScore(int eloId){
		JSONScore team = score_breakdown.red;
		int score = 0;
		//overall,auto,teleop,fuel,takeoff,gear;
		if(eloId == 0){
			score = team.totalPoints-team.foulPoints;
			if(!isElimination()){
				if(team.kPaRankingPointAchieved){
					score += 20;
				}
				if(team.rotorRankingPointAchieved){
					score += 100;
				}
			}
		}
		else if(eloId==1){
			score = team.autoPoints;
		}
		else if(eloId==2){
			score = team.teleopPoints;
		}
		else if(eloId==3){
			score = team.autoFuelPoints+team.teleopFuelPoints;
		}
		else if(eloId==4){
			score = team.teleopTakeoffPoints;
		}
		else if(eloId==5){
			score = team.autoRotorPoints+team.teleopRotorPoints;
		}
		return score;
	}
	public double getTrueOutcomeBlue(int eloId){
		if(getBlueTrueScore(eloId)==getRedTrueScore(eloId)){
			return 0.5;
		}
		return Math.round((double)getBlueTrueScore(eloId)/((double)getRedTrueScore(eloId)+getBlueTrueScore(eloId)));
	}
	public double getTrueOutcomeRed(int eloId){
		if(getBlueTrueScore(eloId)==getRedTrueScore(eloId)){
			return 0.5;
		}
		return Math.round((double)getRedTrueScore(eloId)/((double)getRedTrueScore(eloId)+getBlueTrueScore(eloId)));
	}
	public boolean isNull(){
		return alliances == null || score_breakdown == null;
	}
}

