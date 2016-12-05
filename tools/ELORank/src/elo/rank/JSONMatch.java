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
		public boolean teleopDefensesBreached;
		public boolean teleopTowerCaptured;
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
	public int getBlueTrueScore(){
		JSONScore team = score_breakdown.blue;
		int score = team.totalPoints-team.foulPoints;
		if(!isElimination()){
			if(team.teleopDefensesBreached){
				score += 20;
			}
			if(team.teleopTowerCaptured){
				score += 25;
			}
		}
		return score;
	}
	public int getRedTrueScore(){
		JSONScore team = score_breakdown.red;
		int score = team.totalPoints;
		if(!isElimination()){
			if(team.teleopDefensesBreached){
				score += 20;
			}
			if(team.teleopTowerCaptured){
				score += 25;
			}
		}
		return score;
	}
	public double getTrueOutcomeBlue(){
		if(getBlueTrueScore()==getRedTrueScore()){
			return 0.5;
		}
		return Math.round((double)getBlueTrueScore()/((double)getRedTrueScore()+getBlueTrueScore()));
		/*
		 */
	}
	public double getTrueOutcomeRed(){
		if(getBlueTrueScore()==getRedTrueScore()){
			return 0.5;
		}
		return Math.round((double)getRedTrueScore()/((double)getRedTrueScore()+getBlueTrueScore()));
	}
	public boolean isNull(){
		return alliances == null || score_breakdown == null;
	}
}

